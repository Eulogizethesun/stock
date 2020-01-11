package Data;
//修改股票状态时接收的包形式
public class StockState {
    String stock_id;
    int state;

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public String getStock_id() {
        return stock_id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
