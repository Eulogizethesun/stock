package zju.se.modelandrepository;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@ResponseBody
public class SecurityStockResponseBody implements Serializable{
    private String key;
    private String code;
    private String name;
    private long number;
    private double price;
    private double cost;
    private double income;
    public SecurityStockResponseBody(double currentPrice, SecurityStock securityStock){
        this.key = String.valueOf(securityStock.getId());
        this.code = securityStock.getStockId();
        this.name = securityStock.getStockId();
        this.number = securityStock.getNum();
        this.price = currentPrice;
        this.cost = securityStock.getTotalPrice() / this.number;
        double x = this.number * (this.price - this.cost);
        this.income = Math.round(x*10)/10.00;
    }

    public double getPrice() {
        return price;
    }

    public double getCost() {
        return cost;
    }

    public double getIncome() {
        return income;
    }

    public long getNumber() {
        return number;
    }

    public String getCode() {
        return code;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
