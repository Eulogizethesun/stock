package hello.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class StockAccountLegalUser {
    @Id
    //@GeneratedValue
    private Long account_id;  // 法人股票账户号码
    @Column(nullable = false, unique = true)
    private String legal_num;  //法人注册登记号码
    @Column(nullable = false)
    private String license_num;
    @Column(nullable = false)
    private String legal_name;
    @Column(nullable = false)
    private String legal_idnum;   // 法定代表人身份证号码
    @Column(nullable = false)
    private String legal_address;
    @Column(nullable = false)
    private String legal_phone;
    @Column(nullable = false)
    private String authorize_name;
    @Column(nullable = false)
    private String authorize_idnum;
    @Column(nullable = false)
    private String authorize_address;
    @Column(nullable = false)
    private String authorize_phone;

    public StockAccountLegalUser(){}

    public StockAccountLegalUser(Long account_id
            , String legal_num
            , String license_num
            , String legal_name
            , String legal_id_num
            , String legal_address
            , String legal_phone
            , String authorize_name
            , String authorize_id_num
            , String authorize_address
            , String authorize_phone) {
        this.account_id = account_id;
        this.legal_num = legal_num;
        this.license_num = license_num;
        this.legal_name = legal_name;
        this.legal_idnum = legal_id_num;
        this.legal_address = legal_address;
        this.legal_phone = legal_phone;
        this.authorize_name = authorize_name;
        this.authorize_idnum = authorize_id_num;
        this.authorize_address = authorize_address;
        this.authorize_phone = authorize_phone;
    }

    public Long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }

    public String getLegal_num() {
        return legal_num;
    }

    public void setLegal_num(String legal_num) {
        this.legal_num = legal_num;
    }

    public String getLicense_num() {
        return license_num;
    }

    public void setLicense_num(String license_num) {
        this.license_num = license_num;
    }

    public String getLegal_name() {
        return legal_name;
    }

    public void setLegal_name(String legal_name) {
        this.legal_name = legal_name;
    }

    public String getLegal_idnum() {
        return legal_idnum;
    }

    public void setLegal_idnum(String legal_id_num) {
        this.legal_idnum = legal_id_num;
    }

    public String getLegal_address() {
        return legal_address;
    }

    public void setLegal_address(String legal_address) {
        this.legal_address = legal_address;
    }

    public String getLegal_phone() {
        return legal_phone;
    }

    public void setLegal_phone(String legal_phone) {
        this.legal_phone = legal_phone;
    }

    public String getAuthorize_name() {
        return authorize_name;
    }

    public void setAuthorize_name(String authorize_name) {
        this.authorize_name = authorize_name;
    }

    public String getAuthorize_idnum() { return authorize_idnum; }

    public void setAuthorize_idnum(String authorize_id_num) {
        this.authorize_idnum = authorize_id_num;
    }

    public String getAuthorize_address() {
        return authorize_address;
    }

    public void setAuthorize_address(String authorize_address) {
        this.authorize_address = authorize_address;
    }

    public String getAuthorize_phone() {
        return authorize_phone;
    }

    public void setAuthorize_phone(String authorize_phone) {
        this.authorize_phone = authorize_phone;
    }
}
