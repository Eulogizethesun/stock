package zju.se.modelandrepository;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ResponseBody
public class RecordResponseBody implements Serializable {
    private int key;
    private String sellOrBuy;
    private String time;
    private String stockId;
    private int number;
    private List<String> states = new ArrayList<>();
    public RecordResponseBody(Record record)
    {
        this.key = record.getRecord_id();
        this.sellOrBuy = String.valueOf(record.getBuy() == 0 ? "Sell" : "Buy");
        this.time = record.getRecordTimestamp();
        this.stockId = record.getStockId();
        this.number = record.getAmount();
        this.states.add(record.getState() == 0 ? "交易中" : "交易完成");
    }

    public int getKey(){
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getTime() {
        return time;
    }

    public String getStockId() {
        return stockId;
    }

    public int getNumber() {
        return number;
    }

    public String getSellOrBuy() {
        return sellOrBuy;
    }

    public List<String> getStates() {
        return states;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSellOrBuy(String sellOrBuy) {
        this.sellOrBuy = sellOrBuy;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }
}
