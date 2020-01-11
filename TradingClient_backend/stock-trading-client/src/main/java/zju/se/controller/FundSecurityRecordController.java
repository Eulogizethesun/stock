package zju.se.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zju.se.modelandrepository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "*", allowCredentials = "true")
@RestController
@RequestMapping(value = "/api")
public class FundSecurityRecordController {
//  it has no life
//    @Autowired
//    private FundSecurityRepository fundSecurityRepository;
//  it has no life
    @Autowired
    private CapitalAccountRepository capitalAccountRepository;

    @Autowired
    private SecurityStockRepository securityStockRepository;

    @Autowired
    private Stock_infRepository stock_infRepository;

    @RequestMapping(value = "/myStock", method = RequestMethod.POST)
    public List<SecurityStockResponseBody> getSecurity(
            @RequestBody Map<String, String> httpMessageBody
    )
    {
        String securityId = null;
        List<CapitalAccount> lca = capitalAccountRepository.getAccount(httpMessageBody.get("userinfo"));

        if(lca.size() == 0){
            return null;
        }
        else {
            securityId = lca.get(0).getSecurities_id();

            List<SecurityStockResponseBody> ssrbs = securityStockRepository.findAllBySecurityId(securityId)
                    .stream()
                    .map(r ->
                    {
                        double currentPrice = 10.0;
                        Optional<Stock_inf> optionalStock_inf = stock_infRepository.findById(r.getStockId());
                        Stock_inf stock_inf = optionalStock_inf.orElse(null);
                        return new SecurityStockResponseBody(stock_inf.getStock_price(), r);
                    })
                    .collect(Collectors.toList());
            System.out.println(securityId);
            System.out.println(ssrbs.isEmpty());

            return ssrbs;
        }
    }
}
