package hello.control;

import hello.model.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials="true")
@RestController
public class StockController {

    private final static int maxAge = 60 * 60 * 24 * 90;
    private final static String BankerCookieName = "BankerStockCookie";
    private final static String BankerCookieIdName = "BankerStockCookieId";
    private final static String UserCookieName = "UserStockCookie";
    private final static String UserCookieIdName = "UserStockCookieId";
    private final static String LegalUserType = "Legal";
    private final static String PersonalUserType = "Personal";
    private final static String LogoutType = "Logout";
    private final static String FrozenStatus = "Frozen";
    private final static String NormalStatus = "Normal";

    @Autowired
    private StockAccountBankerRepository StockAccountBankerRepository;
    @Autowired
    private StockAccountUserRepository StockAccountUserRepository;
    @Autowired
    private StockAccountPersonalUserRepository StockAccountPersonalUserRepository;
    @Autowired
    private StockAccountLegalUserRepository StockAccountLegalUserRepository;
    @Autowired
    private SecurityStockRepository SecurityStockRepository;

    private String getBankStockCookie(String id, String password){
        return DigestUtils.md5DigestAsHex((id + DigestUtils.md5DigestAsHex(password.getBytes())).getBytes());
    }
    private String getUserStockCookie(String id, String password){
        return DigestUtils.md5DigestAsHex((id + DigestUtils.md5DigestAsHex(password.getBytes())).getBytes());
    }

    private SimpleStatus setCookie(HttpServletResponse response, int remember_status, Cookie cookie, Cookie cookieId){
        switch (remember_status){
            case 0:
                response.addCookie(cookie);
                response.addCookie(cookieId);
                break;
            case 1:
                cookie.setMaxAge(maxAge);
                cookieId.setMaxAge(maxAge);
                response.addCookie(cookie);
                response.addCookie(cookieId);
                break;
            default:
                return new SimpleStatus(2, "status illegal");
        }
        return new SimpleStatus(0, "success");
    }

    @RequestMapping("/banker_login")
    public SimpleStatus bankLogin(@RequestParam String user_id
            , @RequestParam String user_password
            , @RequestParam(defaultValue = "0") int remember_status
            , HttpServletResponse response) {
        user_password = DigestUtils.md5DigestAsHex(user_password.getBytes());
        List<StockAccountBanker> list = StockAccountBankerRepository.getBankerLogin(user_id, user_password);
        if (list.size() > 0){
            String stockCookie = getBankStockCookie(user_id, user_password);
            Cookie cookie = new Cookie(BankerCookieName, stockCookie);
            Cookie cookieId = new Cookie(BankerCookieIdName, user_id);
            return setCookie(response, remember_status, cookie, cookieId);
        }
        else{
            return new SimpleStatus(1, "login failed please try again");
        }
    }

    @RequestMapping("/banker_login_status")
    public SimpleStatus getBankerLoginStatus(@CookieValue(value = BankerCookieIdName, defaultValue = "") String id, @CookieValue(value = BankerCookieName, defaultValue = "") String cookie){
        List<StockAccountBanker> list = StockAccountBankerRepository.getBankerById(id);
        for (StockAccountBanker banker: list) {
            if (cookie.equals(getBankStockCookie(banker.getId(), banker.getPassword()))) {
                return new SimpleStatus(0, id);
            }
        }
        return new SimpleStatus(3, "please login again");
    }

    private UserFindByBanker getUserByBanker(String id, String cookie, String account_id) {
        SimpleStatus status = getBankerLoginStatus(id, cookie);
        List<StockAccountUser> list;
        if (status.getStatus() == 0){
            list = StockAccountUserRepository.getUserById(Long.parseLong(account_id));
            if (list.size() > 0) {
                StockAccountUser user = list.get(0);
                return new UserFindByBanker(0, user);
            }
            else{
                return new UserFindByBanker(1, null);
            }
        }
        else{
            return new UserFindByBanker(1, null);
        }
    }


    @RequestMapping("/user_add_by_banker")
    public @ResponseBody SimpleStatus addUserByBanker(@CookieValue(value = BankerCookieIdName, defaultValue = "") String id
            , @CookieValue(value = BankerCookieName, defaultValue = "") String cookie
                                                      //, @RequestParam String account_id
                                                      //, @RequestParam String password
            , @RequestParam String account_type){
        SimpleStatus status = getBankerLoginStatus(id, cookie);
        if (status.getStatus() != 0){
            return status;
        }
        StockAccountUser user = new StockAccountUser();
        user.setAccount_type(account_type);
        user.setStatus(NormalStatus);
        StockAccountUserRepository.save(user);
        return new SimpleStatus(0, user.getAccount_id().toString());
    }

    private SimpleStatus setUserStatus(String id, String cookie, String account_id, String fromStatus, String toStatus){
        UserFindByBanker result = getUserByBanker(id, cookie, account_id);
        if (result.getStatus() != 0){
            return new SimpleStatus(result.getStatus(), "select failed");
        }
        StockAccountUser user = result.getUser();
        if (user == null){
            return new SimpleStatus(1, "user not exists");
        }
        if (!user.getStatus().equals(fromStatus)){
            return new SimpleStatus(1, "user is " + user.getStatus());
        }
        user.setStatus(toStatus);
        return new SimpleStatus(0, "success");
    }

    @Transactional
    @RequestMapping("/legal_user_freeze_by_banker")
    public SimpleStatus freezeLegalUserByBanker(@CookieValue(value = BankerCookieIdName, defaultValue = "") String id
            , @CookieValue(value = BankerCookieName, defaultValue = "") String cookie
            , @RequestParam String legal_num) {
        List<StockAccountLegalUser> list = StockAccountLegalUserRepository.findByLegal_num(legal_num);
        if (list.size() == 0){
            return new SimpleStatus(1, "user not exists");
        }
        StockAccountLegalUser legalUser= list.get(0);
        Long account_id = list.get(0).getAccount_id();
        return setUserStatus(id, cookie, account_id.toString(), NormalStatus, FrozenStatus);
    }

    @Transactional
    @RequestMapping("/personal_user_freeze_by_banker")
    public SimpleStatus freezePersonalUserByBanker(@CookieValue(value = BankerCookieIdName, defaultValue = "") String id
            , @CookieValue(value = BankerCookieName, defaultValue = "") String cookie
            , @RequestParam String id_num) {
        List<StockAccountPersonalUser> list = StockAccountPersonalUserRepository.findById_num(id_num);
        if (list.size() == 0){
            return new SimpleStatus(1, "user not exists freeze not found");
        }
        StockAccountPersonalUser personalUser = list.get(0);
        Long account_id = personalUser.getAccount_id();
        return setUserStatus(id, cookie, account_id.toString(), NormalStatus, FrozenStatus);
    }

    @Transactional
    @RequestMapping("/legal_user_unfreeze_by_banker")
    public SimpleStatus unfreezeLegalUserByBanker(@CookieValue(value = BankerCookieIdName, defaultValue = "") String id
            , @CookieValue(value = BankerCookieName, defaultValue = "") String cookie
            , @RequestParam String legal_num) {
        List<StockAccountLegalUser> list = StockAccountLegalUserRepository.findByLegal_num(legal_num);
        if (list.size() == 0){
            return new SimpleStatus(1, "user not exists");
        }
        StockAccountLegalUser legalUser= list.get(0);
        Long account_id = list.get(0).getAccount_id();
        return setUserStatus(id, cookie, account_id.toString(), FrozenStatus, NormalStatus);
    }

    @Transactional
    @RequestMapping("/personal_user_unfreeze_by_banker")
    public SimpleStatus unfreezePersonalUserByBanker(@CookieValue(value = BankerCookieIdName, defaultValue = "") String id
            , @CookieValue(value = BankerCookieName, defaultValue = "") String cookie
            , @RequestParam String id_num) {
        List<StockAccountPersonalUser> list = StockAccountPersonalUserRepository.findById_num(id_num);
        if (list.size() == 0){
            return new SimpleStatus(1, "user not exists");
        }
        StockAccountPersonalUser personalUser = list.get(0);
        Long account_id = personalUser.getAccount_id();
        return setUserStatus(id, cookie, account_id.toString(), FrozenStatus, NormalStatus);
    }


    private int getUserStock(Long account_id){
        List<security_stock> list = SecurityStockRepository.getStockByUserId(account_id.toString());
        if (list.size() == 0){
            return -1;
        }
        for (security_stock stock : list) {
            if (stock.getNum() != 0)
                return stock.getNum();
        }
        return 0;
    }

    @RequestMapping("/legal_user_delete_by_banker")
    public SimpleStatus deleteLegalUserByBanker(@CookieValue(value = BankerCookieIdName, defaultValue = "") String id
            , @CookieValue(value = BankerCookieName, defaultValue = "") String cookie
            , @RequestParam String legal_num) {
        List<StockAccountLegalUser> list = StockAccountLegalUserRepository.findByLegal_num(legal_num);
        if (list.size() == 0){
            return new SimpleStatus(1, "user not exists");
        }
        StockAccountLegalUser legalUser= list.get(0);
        Long account_id = list.get(0).getAccount_id();
        UserFindByBanker result = getUserByBanker(id, cookie, account_id.toString());
        StockAccountUser user = result.getUser();
        if (result.getStatus() != 0 || user == null){
            return new SimpleStatus(1, "user not exists");
        }
        if (getUserStock(account_id) != 0){
            return new SimpleStatus(2, "user holds stock");
        }
        StockAccountLegalUserRepository.delete(legalUser);
        StockAccountUserRepository.delete(user);
        return new SimpleStatus(0, "success");
    }

    @RequestMapping("/personal_user_delete_by_banker")
    public SimpleStatus deletePersonalUserByBanker(@CookieValue(value = BankerCookieIdName, defaultValue = "") String id
            , @CookieValue(value = BankerCookieName, defaultValue = "") String cookie
            , @RequestParam String id_num) {
        List<StockAccountPersonalUser> list = StockAccountPersonalUserRepository.findById_num(id_num);
        if (list.size() == 0){
            return new SimpleStatus(1, "user not exists not found" + id_num);
        }
        StockAccountPersonalUser personalUser = list.get(0);
        Long account_id = personalUser.getAccount_id();
        UserFindByBanker result = getUserByBanker(id, cookie, account_id.toString());
        StockAccountUser user = result.getUser();
        if (result.getStatus() != 0 || user == null){
            return new SimpleStatus(1, "user not exists");
        }
        if (getUserStock(account_id) != 0){
            return new SimpleStatus(2, "user holds stock");
        }
        StockAccountPersonalUserRepository.delete(personalUser);
        StockAccountUserRepository.delete(user);
        return new SimpleStatus(0, "success");
    }

    @RequestMapping("/personal_user_add_by_banker")
    public SimpleStatus addPersonalUserByBanker(@CookieValue(value = BankerCookieIdName, defaultValue = "") String id
            , @CookieValue(value = BankerCookieName, defaultValue = "") String cookie
            , @RequestParam String name
            , @RequestParam String gender
            , @RequestParam String id_num
            , @RequestParam String address
            , @RequestParam String job
            , @RequestParam String degree
            , @RequestParam String organization
            , @RequestParam String phone_num
            , @RequestParam (defaultValue = "0") String agency
            , @RequestParam(defaultValue = "") String agent_id_num
    ){
        System.out.println("angency:" + agency);
        int agency_int;
        if(agency.equals("true")){
            agency_int = 1;
        }
        else{
            agency_int = 0;
        }
        //System.out.println("angency_int:" + agency_int);
        List<StockAccountPersonalUser> list = StockAccountPersonalUserRepository.findById_num(id_num);
        if (list.size() != 0){
            return new SimpleStatus(3, "User already exists");
        }
        SimpleStatus status = addUserByBanker(id, cookie, PersonalUserType);
        if (status.getStatus() != 0){
            return status;
        }
        StockAccountPersonalUser user = new StockAccountPersonalUser( Long.parseLong(status.getMessage())
                , new Date()
                , name
                , gender
                , id_num
                , address
                , job
                , degree
                , organization
                , phone_num
                , agency_int
                , agent_id_num);
        StockAccountPersonalUserRepository.save(user);

        return new SimpleStatus(0, "success");
    }

    @RequestMapping("/personal_user_find_by_banker")
    public StockAccountPersonalUser getPersonalUserByBanker(@CookieValue(value = BankerCookieIdName, defaultValue = "") String id
            , @CookieValue(value = BankerCookieName, defaultValue = "") String cookie
            , @RequestParam String Id_num){
        SimpleStatus status = getBankerLoginStatus(id, cookie);
        if (status.getStatus() != 0){
            return new StockAccountPersonalUser();
        }
        List<StockAccountPersonalUser> list = StockAccountPersonalUserRepository.findById_num(Id_num);
        if (list.size() == 0){
            return new StockAccountPersonalUser();
        }
        return list.get(0);
    }

    @RequestMapping("/legal_user_add_by_banker")
    public SimpleStatus addLegalUserByBanker(@CookieValue(value = BankerCookieIdName, defaultValue = "") String id
            , @CookieValue(value = BankerCookieName, defaultValue = "") String cookie
            , @RequestParam String legal_num
            , @RequestParam String license_num
            , @RequestParam String legal_name
            , @RequestParam String legal_idnum
            , @RequestParam String legal_address
            , @RequestParam String legal_phone
            , @RequestParam String authorize_name
            , @RequestParam String authorize_idnum
            , @RequestParam String authorize_address
            , @RequestParam String authorize_phone){
        List<StockAccountLegalUser> list = StockAccountLegalUserRepository.findByLegal_num(legal_num);
        if (list.size() != 0){
            return new SimpleStatus(3, "User already exists");
        }
        SimpleStatus status = addUserByBanker(id, cookie, LegalUserType);
        if (status.getStatus() != 0){
            return status;
        }
        StockAccountLegalUser user = new StockAccountLegalUser(Long.parseLong(status.getMessage())
                , legal_num
                , license_num
                , legal_name
                , legal_idnum
                , legal_address
                , legal_phone
                , authorize_name
                , authorize_idnum
                , authorize_address
                , authorize_phone);
        StockAccountLegalUserRepository.save(user);
        return new SimpleStatus(0, "success");
    }

    @RequestMapping("/legal_user_find_by_banker")
    public StockAccountLegalUser getLegalUserByBanker(@CookieValue(value = BankerCookieIdName, defaultValue = "") String id
            , @CookieValue(value = BankerCookieName, defaultValue = "") String cookie
            , @RequestParam String legal_num){
        if (getBankerLoginStatus(id, cookie).getStatus() != 0) {
            return new StockAccountLegalUser();
        }
        List<StockAccountLegalUser> list = StockAccountLegalUserRepository.findByLegal_num(legal_num);
        if (list.size() == 0){
            return new StockAccountLegalUser();
        }
        return list.get(0);
    }


}
