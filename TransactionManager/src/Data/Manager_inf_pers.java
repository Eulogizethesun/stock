package Data;

import java.util.ArrayList;
import java.util.List;

public class Manager_inf_pers {
    int stateCode;
    List<Manager_inf_per> users=new ArrayList<Manager_inf_per>();

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public int getStateCode() {
        return stateCode;
    }

    public List<Manager_inf_per> getUsers() {
        return users;
    }

    public void setUsers(List<Manager_inf_per> users) {
        this.users = users;
    }
}
