package TransactionManager;

import Data.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;


@Controller
@CrossOrigin
@RequestMapping("/stock")
public class TransactionManager {
    List<Limit> limits=new ArrayList<Limit>();

    @RequestMapping(value="/all",method=RequestMethod.GET)
    public @ResponseBody
    Stocks_inf GetAllStocks(@RequestParam(value="authority")int authority){//获取权限内所有股票
        List<Stock_inf> stocks=new ArrayList<Stock_inf>();
        Stocks_inf res=new Stocks_inf();
        try{
            DBoperations db=new DBoperations();//连接数据库
            ResultSet rs=db.GetAllStocks(authority);//进行股票信息查询
            while(rs.next()){//读取并储存每一只股票的信息
                Stock_inf stock=new Stock_inf();
                stock.setStock_id(rs.getString("stock_id"));
                stock.setStock_name(rs.getString("stock_name"));
                stock.setLower_limit(rs.getDouble("lower_limit"));
                stock.setUpper_limit(rs.getDouble("upper_limit"));
                stock.setStock_state(rs.getInt("stock_state"));
                stock.setStock_price(rs.getDouble("stock_price"));
                stock.setLimit_State(rs.getInt("limit_state"));
                stocks.add(stock);
            }
            if(stocks.isEmpty()){
                System.out.println("无股票信息或无权限访问！");
                res.setStateCode(-1);
            }
            else{
                res.setStateCode(0);
            }
            res.setStocks(stocks);

        }catch(Exception e){
            System.out.println("数据库读取失败!");
            e.printStackTrace();
            res.setStateCode(-2);
        }
        return res;
    }
    //查询某一范围内的股票（按股票名搜索）
    @RequestMapping(value="/GetByName",method=RequestMethod.GET)
    public @ResponseBody Stocks_inf GetSomeStocksByNAME(@RequestParam(value="stock_name")String stock_name,@RequestParam(value="authority")int authority){
        List<Stock_inf> stocks=new ArrayList<Stock_inf>();
        Stocks_inf res=new Stocks_inf();

        try{
            String t=new String(stock_name.getBytes("ISO-8859-1"), "UTF-8");
            DBoperations db=new DBoperations();//连接数据库
            ResultSet rs=db.GetSomeStocksByName(stock_name,authority);//查询数据库
            while(rs.next()){//读取并存储每一只股票的信息
                Stock_inf stock=new Stock_inf();
                stock.setStock_id(rs.getString("stock_id"));
                stock.setStock_name(rs.getString("stock_name"));
                stock.setLower_limit(rs.getDouble("lower_limit"));
                stock.setUpper_limit(rs.getDouble("upper_limit"));
                stock.setStock_state(rs.getInt("stock_state"));
                stock.setStock_price(rs.getDouble("stock_price"));
                stock.setLimit_State(rs.getInt("limit_state"));
                stocks.add(stock);
            }
            if(stocks.isEmpty()){
                res.setStateCode(-1);
                System.out.println("无股票信息或无权限访问！");
            }
            else{
                res.setStateCode(0);
            }
            res.setStocks(stocks);

        }catch(Exception e){
            System.out.println("数据库读取失败!");
            e.printStackTrace();
            res.setStateCode(-1);
        }
        return res;
    }

    //查询某一范围内的股票（按股票ID搜索）
    @RequestMapping(value="/GetByID",method=RequestMethod.GET)
    public @ResponseBody Stocks_inf GetSomeStocksByID(@RequestParam(value="stock_id")String stock_id,@RequestParam(value="authority")int authority){
        List<Stock_inf> stocks=new ArrayList<Stock_inf>();
        Stocks_inf res=new Stocks_inf();
        try{
            DBoperations db=new DBoperations();//连接数据库
            ResultSet rs=db.GetSomeStocksByID(stock_id,authority);//查询数据库
            while(rs.next()){//读取并存储每一只股票的信息
                Stock_inf stock=new Stock_inf();
                stock.setStock_id(rs.getString("stock_id"));
                stock.setStock_name(rs.getString("stock_name"));
                stock.setLower_limit(rs.getDouble("lower_limit"));
                stock.setUpper_limit(rs.getDouble("upper_limit"));
                stock.setStock_state(rs.getInt("stock_state"));
                stock.setStock_price(rs.getDouble("stock_price"));
                stock.setLimit_State(rs.getInt("limit_state"));
                stocks.add(stock);
            }
            if(stocks.isEmpty()){
                res.setStateCode(-1);
                System.out.println("无股票信息或无权限访问！");
            }
            else{
                res.setStateCode(0);
            }
            res.setStocks(stocks);

        }catch(Exception e){
            System.out.println("数据库读取失败!");
            e.printStackTrace();
            res.setStateCode(-1);
        }
        return res;
    }

    //查询某只股票的具体信息（包括最新成交价、最新成交量、买卖指令）
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody  Stock_concrete_inf GetConcreteInf(@RequestParam(value="stock_id") String stock_id){
        Stock_concrete_inf stock_insts=new Stock_concrete_inf();
        List<inst_inf> insts_B=new ArrayList<inst_inf>();
        List<inst_inf> insts_S=new ArrayList<inst_inf>();
        try{
            DBoperations db=new DBoperations();//连接数据库
            ResultSet rs=db.GetBuyInstructions(stock_id);//获取买指令
            while(rs.next()){
                inst_inf inst=new inst_inf();
                inst.setInst_no(rs.getInt("Number"));
                inst.setInst_type("B");
                inst.setInst_num(rs.getInt("Amount"));
                inst.setPrice(rs.getDouble("Price"));
                inst.setStock_id(rs.getString("ID"));
                inst.setUser_id(rs.getString("User_ID"));
                inst.setOp_time(rs.getString("Date"));
                insts_B.add(inst);
            }
            stock_insts.setBuyInsts(insts_B);

            rs=db.GetSellInstructions(stock_id);//获取卖指令
            while(rs.next()){
                inst_inf inst=new inst_inf();
                inst.setInst_no(rs.getInt("Number"));
                inst.setInst_type("S");
                inst.setInst_num(rs.getInt("Amount"));
                inst.setPrice(rs.getDouble("Price"));
                inst.setStock_id(rs.getString("ID"));
                inst.setUser_id(rs.getString("User_ID"));
                inst.setOp_time(rs.getString("Date"));
                insts_S.add(inst);
            }
            stock_insts.setSellInsts(insts_S);
            //获取并储存最新成交量、成交价、类型
            rs=db.GetLatestInst(stock_id);
            rs.next();
            stock_insts.setLatest_num(rs.getInt("Amount"));
            stock_insts.setLatest_price(rs.getDouble("Price"));
            stock_insts.setLatest_type(rs.getInt("Buy")==0?"S":"B");
            stock_insts.setStateCode(0);
        }catch(Exception e){
            System.out.println("数据库读取失败!");
            e.printStackTrace();
            stock_insts.setStateCode(-1);
        }

        return stock_insts;
    }
    //重启或暂停股票交易
    @RequestMapping("/ChangeState")
    public @ResponseBody ReturnState ChangeState(@RequestBody StockState c){
        ReturnState s=new ReturnState();
        try{
            DBoperations db=new DBoperations();
            db.ChangeStockState(c.getStock_id(),c.getState());
            s.setStateCode(0);
        }catch (Exception e){
            System.out.println("修改状态失败");
            e.printStackTrace();
            s.setStateCode(-1);

        }
        sendChangeState(c);
        return s;
    }
    void sendChangeState(StockState c){

    }
    //修改股票的涨跌停限制
    @CrossOrigin
    @RequestMapping("/limit")
    public @ResponseBody ReturnState ChangeLimit(@RequestBody Limit l){

        Limit t=new Limit();
        t.setLower_limit(l.getLower_limit());
        t.setUpper_limit(l.getUpper_limit());
        t.setStock_id(l.getStock_id());
        String stock_id=l.getStock_id();
        for (Limit item: limits) {
            if(item.getStock_id().equals(stock_id)){
                limits.remove(item);
                break;
            }
        }
        System.out.println("股票"+l.getStock_id()+"涨跌停限制修改添加！");
        limits.add(t);
        ReturnState r=new ReturnState();
        try{
            DBoperations db=new DBoperations();
            db.ChangeLimitState(l.getStock_id(),0);
        }catch (Exception e){
            System.out.println("修改状态失败");
            e.printStackTrace();
            r.setStateCode(-1);
        }

        r.setStateCode(0);
        return r;
    }
    //第二天0:00生效
    @Scheduled(cron="0 0/1 0 * * ?")
    private void ApplyLimits(){

        try{
            DBoperations db=new DBoperations();
            for(Limit l : limits){
                db.ChangeLimit(l.getStock_id(),l.getUpper_limit(),l.getLower_limit());
                db.ChangeLimitState(l.getStock_id(),1);
                System.out.println("股票"+l.getStock_id()+"涨跌停限制修改生效！");
            }
			limits.clear();
            System.out.println("Clear limits.");
        }catch(Exception e){
            e.printStackTrace();
        }


    }


}