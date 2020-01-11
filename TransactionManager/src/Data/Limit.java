package Data;

import java.util.Date;
//修改股票涨跌停限制的请求格式
public class Limit {
    String stock_id;
    double upper_limit;
    double lower_limit;


    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public void setUpper_limit(double upper_limit) {
        this.upper_limit = upper_limit;
    }

    public void setLower_limit(double lower_limit) {
        this.lower_limit = lower_limit;
    }

    public double getUpper_limit() {
        return upper_limit;
    }

    public double getLower_limit() {
        return lower_limit;
    }
}
