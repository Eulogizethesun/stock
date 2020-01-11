package zju.se.modelandrepository;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "fund_security")
public class FundSecurity implements Serializable {
    private final static long serialVersionID = 1l;

    @Column(name = "fund_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String fundId;

    @Column(name = "security_id", nullable = false)
    private String securityId;

    public String getFundId()
    {
        return fundId;
    }

    public void setFundId(String fundId)
    {
        this.fundId = fundId;
    }

    public String getSecurityId()
    {
        return securityId;
    }

    public void setSecurityId(String securityId)
    {
        this.securityId = securityId;
    }
}
