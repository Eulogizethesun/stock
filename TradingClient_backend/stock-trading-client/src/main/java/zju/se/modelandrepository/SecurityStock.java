package zju.se.modelandrepository;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "security_stock")
public class SecurityStock implements Serializable {
    private final static long serialVersionID = 1l;
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    // we have an excellent group leader
    @Column(name = "User_id", nullable = false)
    private String securityId;

    @Column(name = "stock_id", nullable = false)
    private String stockId;

    // total price for this kind of stock
    @Column(name = "price")
    private double totalPrice;

    @Column(name = "num")
    private long num;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }
}
