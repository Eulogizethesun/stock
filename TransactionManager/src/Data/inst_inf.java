package Data;
//查询某只股票具体信息时，其中指令的格式
public class inst_inf {
    int inst_no;
    String inst_type;
    int inst_num;
    double price;
    String stock_id;
    String user_id;
    String op_time;

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public String getStock_id() {
        return stock_id;
    }

    public String getInst_type() {
        return inst_type;
    }

    public double getPrice() {
        return price;
    }

    public int getInst_no() {
        return inst_no;
    }

    public int getInst_num() {
        return inst_num;
    }

    public String getOp_time() {
        return op_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setInst_no(int inst_no) {
        this.inst_no = inst_no;
    }

    public void setInst_num(int inst_num) {
        this.inst_num = inst_num;
    }

    public void setInst_type(String inst_type) {
        this.inst_type = inst_type;
    }

    public void setOp_time(String op_time) {
        this.op_time = op_time;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
