package zju.se.modelandrepository;

import javax.persistence.Entity;
import javax.persistence.Id;

//每只股票的信息的格式
@Entity
public class Stock_inf {
    @Id
    String stock_id;
    String stock_name;
    double stock_price;
    double upper_limit;
    double lower_limit;
    int stock_state;
    int limit_State;

    public double getStock_price() {
        return stock_price;
    }

    public double getLower_limit() {
        return lower_limit;
    }

    public int getStock_state() {
        return stock_state;
    }

    public double getUpper_limit() {
        return upper_limit;
    }

    public String getStock_id() {
        return stock_id;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setLower_limit(double lower_limit) {
        this.lower_limit = lower_limit;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public void setStock_price(double stock_price) {
        this.stock_price = stock_price;
    }

    public void setStock_state(int stock_state) {
        this.stock_state = stock_state;
    }

    public void setUpper_limit(double upper_limit) {
        this.upper_limit = upper_limit;
    }

    public int getLimit_State() {
        return limit_State;
    }

    public void setLimit_State(int limit_State) {
        this.limit_State = limit_State;
    }
}
