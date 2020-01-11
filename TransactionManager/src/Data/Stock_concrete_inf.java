package Data;

import java.util.List;
//每只股票具体信息的格式
public class Stock_concrete_inf {
    double latest_price;
    int latest_num;
    String latest_type;
    List<inst_inf> BuyInsts;
    List<inst_inf> SellInsts;
    int stateCode;

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public double getLatest_price() {
        return latest_price;
    }

    public int getLatest_num() {
        return latest_num;
    }

    public List<inst_inf> getBuyInsts() {
        return BuyInsts;
    }

    public List<inst_inf> getSellInsts() {
        return SellInsts;
    }

    public String getLatest_type() {
        return latest_type;
    }

    public void setBuyInsts(List<inst_inf> buyInsts) {
        BuyInsts = buyInsts;
    }

    public void setLatest_num(int latest_num) {
        this.latest_num = latest_num;
    }

    public void setLatest_price(double latest_price) {
        this.latest_price = latest_price;
    }

    public void setLatest_type(String latest_type) {
        this.latest_type = latest_type;
    }

    public void setSellInsts(List<inst_inf> sellInsts) {
        SellInsts = sellInsts;
    }
}
