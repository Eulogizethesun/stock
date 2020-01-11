package zju.se.modelandrepository;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "instructionset")
public class Record implements Serializable{
    @Column(name = "Number")
    @Id
    private int record_id;
    @Column(name = "User_ID")
    private String fundId;
    @Column(name = "ID")
    private String stockId;
    @Column(name = "Buy")
    private long buy;
    @Column(name = "Amount")
    private int amount;
    @Column(name = "Date")
    private String recordTimestamp;
    @Column(name = "Price")
    private double price;
    @Column(name = "State")
    private long state;

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public int getRecord_id() {
        return record_id;
    }

    public long getBuy() {
        return buy;
    }

    public long getState() {
        return state;
    }

    public String getFundId() {
        return fundId;
    }

    public String getRecordTimestamp() {
        return recordTimestamp;
    }

    public String getStockId() {
        return stockId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setBuy(long buy) {
        this.buy = buy;
    }

    public void setFundId(String fundId) {
        this.fundId = fundId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public void setRecord_timestamp(String recordTimestamp) {
        this.recordTimestamp = recordTimestamp;
    }

    public void setState(long state) {
        this.state = state;
    }

    public void setStock_id(String stockId) {
        this.stockId = stockId;
    }

}
