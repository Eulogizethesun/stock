package Data;
//登陆时的返回格式
public class Manager_inf_send {
    String username;
    int authority=-1;
    int stateCode;

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public String getUsername(){return username;}

    public int getAuthority(){return authority;}
    public void setUsername(String username){this.username=username;}

   public void setAuthority(int authority){this.authority=authority;}

}
