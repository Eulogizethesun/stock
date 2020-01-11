package Data;

public class changeauthority_inf {
    String username;
    int authority;
    int own_authority;

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public int getAuthority() {
        return authority;
    }

    public int getOwn_authority() {
        return own_authority;
    }

    public String getUsername() {
        return username;
    }

    public void setOwn_authority(int own_authority) {
        this.own_authority = own_authority;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
