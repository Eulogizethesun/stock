package TransactionManager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class DBoperations {
    private ApplicationContext context;
    private DataSource datasource;
    private Connection connection;
    private Statement statement;
    private ResultSet rs;

    DBoperations()throws Exception{
        context=new ClassPathXmlApplicationContext("applicationContext.xml");
        //ApplicationContext context=new FileSystemXmlApplicationContext("/WEB-INF/applicationContext.xml");
        datasource=context.getBean("dataSource",DataSource.class);
        connection=datasource.getConnection();
        statement=connection.createStatement();
    }
    //创建管理员账户
    public void createaccount(String ID,String passwd,int authortiy)throws  Exception {
        String sql="insert into manager_account value(\""+ID+"\",\""+passwd+"\","+authortiy+")";
        statement.execute(sql);
    }
    //获取所有用户信息
    public ResultSet getallaccount()throws Exception{
        String sql="select * from manager_account where authority >= 3";
        rs=statement.executeQuery(sql);
        return rs;
    }
    //查询用户信息
    public ResultSet getsomeaccount(String username)throws Exception{
        String sql="select * from manager_account where authority >= 3 and user_id like \""+username+"\"";
        rs=statement.executeQuery(sql);
        return rs;
    }
    //获取密码
    public  String getpasswd(String ID)throws Exception{

        String sql="select * from manager_account where user_id=\""+ID+"\"";
        rs=statement.executeQuery(sql);
        String passwd="";
        int exist=0;
        while(rs.next()){
            exist=1;
            passwd=rs.getString("passward");
        }
        if(exist==0){
            return "";
        }
        return passwd;
    }
    //获取用户权限
    public int getauthority(String ID)throws Exception{
        String sql="select * from manager_account where user_id=\""+ID+"\"";
        rs=statement.executeQuery(sql);
        int authority=-1;
        while(rs.next()){
            authority=rs.getInt("authority");
        }
        return authority;
    }
    //修改权限
    public void changeauthority(String username, int authority)throws  Exception{
        String sql="update manager_account set authority="+authority+" where user_id=\""+username+"\"";
        statement.execute(sql);
    }
    //修改密码
    public void changepasswd(String username,String passwd)throws Exception{
        String sql="update manager_account set passward=\""+passwd+"\" where user_id=\""+username+"\"";
        statement.execute(sql);
    }

    //获取权限内所有股票信息
    public ResultSet GetAllStocks(int authority)throws Exception{
        String sql="select * from stock_inf where stock_authority >= "+authority;
        rs=statement.executeQuery(sql);
        return rs;
    }

    //查询股票（按名字查询）
    public ResultSet GetSomeStocksByName(String stock_name,int authority)throws Exception{
        String sql="select * from stock_inf where stock_authority >= "+authority +" and stock_name like \""+stock_name+"\"";
        rs=statement.executeQuery(sql);
        return rs;
    }
    //查询股票（按ID查询）
    public ResultSet GetSomeStocksByID(String stock_id,int authority)throws Exception{
        String sql="select * from stock_inf where stock_authority >= "+authority +" and stock_id like \""+stock_id+"\"";
        rs=statement.executeQuery(sql);
        return rs;
    }

    //获取某只股票的买指令（按价格降序）
    /*public ResultSet GetBuyInstructions(String stock_id)throws  Exception{
        String sql="select * from instruction_set where inst_type= \"B\" and stock_id =\""+stock_id+"\" order by price DESC";
        rs=statement.executeQuery(sql);
        return rs;
    }
    //获取某只股票的卖指令（按价格升序）
    public ResultSet GetSellInstructions(String stock_id)throws  Exception{
        String sql="select * from instruction_set where inst_type= \"S\" and stock_id =\""+stock_id+"\" order by price ASC";
        rs=statement.executeQuery(sql);
        return rs;
    }
    //获取最新的买卖指令
    public ResultSet GetLatestInst(String stock_id)throws  Exception{
        String sql="select max(inst_no), price, inst_num, inst_type from instruction_set where stock_id=\""+stock_id+"\"";
        rs=statement.executeQuery(sql);
        return rs;
    }*/
    //获取某只股票的买指令（按价格降序）
    public ResultSet GetBuyInstructions(String stock_id)throws  Exception{
        String sql="select * from instructionset where Buy= 1 and ID =\""+stock_id+"\" and State=1 order by price DESC";
        rs=statement.executeQuery(sql);
        return rs;
    }
    //获取某只股票的卖指令（按价格升序）
    public ResultSet GetSellInstructions(String stock_id)throws  Exception{
        String sql="select * from instructionset where Buy= 0 and ID =\""+stock_id+"\" and State=1 order by price ASC";
        rs=statement.executeQuery(sql);
        return rs;
    }
    //获取最新的买卖指令
    public ResultSet GetLatestInst(String stock_id)throws  Exception{
        String sql="select max(Number), Price, Amount, Buy from instructionset where ID=\""+stock_id+"\"";
        rs=statement.executeQuery(sql);
        return rs;
    }
    //改变股票的状态（重启/暂停）
    public void ChangeStockState(String stock_id, int state)throws Exception{
        String sql="update stock_inf set stock_state = "+state+" where stock_id=+\""+stock_id+"\"";
        statement.execute(sql);
    }
    //修改股票的涨跌停限制
    public void ChangeLimit(String stock_id, double upper, double lower)throws Exception{
        String sql="update stock_inf set upper_limit="+upper+" where stock_id=\""+stock_id+"\"";
        statement.execute(sql);
        sql="update stock_inf set lower_limit="+lower+" where stock_id=\""+stock_id+"\"";
        statement.execute(sql);
    }
    public void ChangeLimitState(String stock_id,int state)throws Exception{
        String sql="update stock_inf set limit_state="+state+" where stock_id=\""+stock_id+"\"";
        statement.execute(sql);
    }


}
