package Data;

import java.util.ArrayList;
import java.util.List;
//查询权限内所有股票的返回格式
public class Stocks_inf {
    int stateCode;
    List<Stock_inf> stocks=new ArrayList<Stock_inf>();

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public int getStateCode() {
        return stateCode;
    }

    public List<Stock_inf> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock_inf> stocks) {
        this.stocks = stocks;
    }
}
