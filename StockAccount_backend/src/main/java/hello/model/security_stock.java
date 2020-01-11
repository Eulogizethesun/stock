package hello.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class security_stock {
    @Id
    @GeneratedValue
    private Long id;
    private String User_id;
    private String stock_id;
    private int price;
    private int num;


    public Long getId() {
        return id;
    }

    public String getUser_id() {
        return User_id;
    }

    public String getStock_id() {
        return stock_id;
    }

    public int getNum() {
        return num;
    }
}
