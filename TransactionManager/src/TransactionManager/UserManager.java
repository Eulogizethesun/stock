package TransactionManager;

import Data.*;
import org.springframework.jdbc.core.metadata.Db2CallMetaDataProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


@Controller
@CrossOrigin
@RequestMapping("/manager")
public class UserManager {

    //登陆
    @RequestMapping(value="/login",method = RequestMethod.GET)
    @ResponseBody
    public  Manager_inf_send login(@RequestParam(value="username") String username,@RequestParam(value="passwd") String password){

        String user_id=username;//获取账户
        String passwd=password;//获取密码
        int authority=-1;
        System.out.println("用户"+user_id+"请求登陆");
        Manager_inf_send manager=new Manager_inf_send();

        try{
            DBoperations db=new DBoperations();
            String pass_t = db.getpasswd(user_id);
            if(pass_t.equals("")){
                manager.setStateCode(-2);
            }
            else if(pass_t.equals(passwd)){//匹配密码
                authority=db.getauthority(user_id);
                manager.setStateCode(0);
                System.out.println("用户"+user_id+"登陆成功");
            }
            else{
                manager.setStateCode(-1);
                System.out.println("用户"+user_id+"密码错误");
            }
            manager.setAuthority(authority);
        }catch(Exception e){
            manager.setStateCode(-2);
            System.out.println("数据库读取失败!");
            e.printStackTrace();
        }
        manager.setUsername(user_id);
        return manager;
    }

    //获取所有用户
    @RequestMapping("/all")
    public @ResponseBody
    Manager_inf_pers GetAllManager(@RequestParam(value="own_authority")int own_authority){
        Manager_inf_pers res=new Manager_inf_pers();
        List<Manager_inf_per> m=new ArrayList<Manager_inf_per>();
        if(own_authority!=2){
            res.setStateCode(-2);
        }
        else{
            try{
                DBoperations db=new DBoperations();
                ResultSet rs=db.getallaccount();
                while(rs.next()){
                    Manager_inf_per t=new Manager_inf_per();
                    t.setUsername(rs.getString("user_id"));
                    t.setAuthority(rs.getInt("authority"));
                    m.add(t);
                }
                res.setStateCode(0);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        res.setUsers(m);
        return res;
    }

    //查询用户（按用户名）
    @RequestMapping("/some")
    public @ResponseBody
    Manager_inf_pers GetSomeManager(@RequestParam(value="username")String username,@RequestParam(value="own_authority")int own_authority){
        Manager_inf_pers res=new Manager_inf_pers();
        List<Manager_inf_per> m=new ArrayList<Manager_inf_per>();
        if(own_authority!=2){
            res.setStateCode(-2);
        }
        else{
            try{
                DBoperations db=new DBoperations();
                ResultSet rs=db.getsomeaccount(username);
                while(rs.next()){
                    Manager_inf_per t=new Manager_inf_per();
                    t.setUsername(rs.getString("user_id"));
                    t.setAuthority(rs.getInt("authority"));
                    m.add(t);
                }
                if(m.isEmpty()){
                    res.setStateCode(-1);
                }
                res.setStateCode(0);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        res.setUsers(m);
        return res;
    }
    //修改登录密码
    @RequestMapping("/ChangePassword")
    public @ResponseBody
    ReturnState change_passwd(@RequestBody Manager_inf m){
        String username=m.getUsername();
        String passwd=m.getOld_passwd();
        ReturnState s=new ReturnState();
        try{
            DBoperations db=new DBoperations();
            String pass_t = db.getpasswd(username);
            if(pass_t.equals(m.getNew_passwd())){
                s.setStateCode(-2);
            }
            else if(pass_t.equals(passwd)){

                db.changepasswd(username,m.getNew_passwd());
                s.setStateCode(0);
            }
            else{
                s.setStateCode(-1);
            }

        }catch(Exception e){
            System.out.println("数据库读取/修改失败!");
            e.printStackTrace();
            s.setStateCode(-3);
        }

        return s;
    }

    @RequestMapping("/Create")
    public @ResponseBody ReturnState CrateManager(@RequestBody changeauthority_inf c){
        ReturnState s=new ReturnState();
        String username=c.getUsername();
        int authority=c.getAuthority();
        int own_authority=c.getOwn_authority();
        if(own_authority!=2){
            s.setStateCode(-3);//没有权限
        }
        else{
            try{
                DBoperations db=new DBoperations();
                String pass_t = db.getpasswd(username);
                if(pass_t.equals("")){
                    if(authority<=2||authority>10){
                        s.setStateCode(-2);//设置非法权限
                    }
                    else{
                        db.createaccount(username,"12345678",authority);
                        s.setStateCode(0);//创建成功
                    }

                }
                else {
                    s.setStateCode(-1);//账户已存在

                }

            }catch(Exception e){
                System.out.println("数据库读取/修改失败!");
                e.printStackTrace();
                s.setStateCode(-4);
            }
        }
        return s;
    }

    @RequestMapping("/ResetPassword")
    public @ResponseBody ReturnState CrateManager(@RequestBody Reset r){
        ReturnState s=new ReturnState();
        String username=r.getUsername();
        int own_authority=r.getOwn_authority();
        if(own_authority!=2){
            s.setStateCode(-3);//没有权限
        }
        else{
            try{
                DBoperations db=new DBoperations();
                String pass_t = db.getpasswd(username);
                if(pass_t.equals("")){
                    s.setStateCode(-1);//账户不存在
                }
                else {

                    db.changepasswd(username,"12345678");
                    s.setStateCode(0);//创建成功
                }

            }catch(Exception e){
                System.out.println("数据库读取/修改失败!");
                e.printStackTrace();
                s.setStateCode(-2);
            }
        }
        return s;
    }

    @RequestMapping("/ChangeAuthority")
    public @ResponseBody ReturnState ChangeAuthority(@RequestBody changeauthority_inf ca){
        ReturnState s=new ReturnState();
        String username=ca.getUsername();
        int authority=ca.getAuthority();
        int own_authority=ca.getOwn_authority();
        if(own_authority!=2){
            s.setStateCode(-3);//没有权限
        }
        else{
            try{
                DBoperations db=new DBoperations();
                String pass_t = db.getpasswd(username);
                if(pass_t.equals("")){
                    s.setStateCode(-1);//不存在该用户
                }
                else {
                    if(authority<=2||authority>10){
                        s.setStateCode(-2);//修改成非法权限
                    }
                    else{
                        db.changeauthority(username,authority);
                        s.setStateCode(0);
                    }

                }

            }catch(Exception e){
                System.out.println("数据库读取/修改失败!");
                e.printStackTrace();
                s.setStateCode(-4);
            }
        }

        return s;
    }

}
