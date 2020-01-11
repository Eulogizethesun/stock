package Data;

public class Manager_inf {
    String username;
    String old_passwd;
    String new_passwd;

    public String getUsername(){return username;}

    public String getOld_passwd() {
        return old_passwd;
    }

    public String getNew_passwd() {
        return new_passwd;
    }

    public void setUsername(String username){this.username=username;}

    public void setNew_passwd(String new_passwd) {
        this.new_passwd = new_passwd;
    }

    public void setOld_passwd(String old_passwd) {
        this.old_passwd = old_passwd;
    }
}
