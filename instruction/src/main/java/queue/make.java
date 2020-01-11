package queue;

import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TimerTask;

import org.springframework.jdbc.core.JdbcTemplate;

public class make{
	static int MAX_STOCK_NUMBER=100005;
	static int INF=100000000;
	static double eps=1e-8;
	
	static int stock_num=0;
	static HashMap<String, Integer>stock_map=new HashMap<String, Integer>();
	static String stock_int2string[] = new String[MAX_STOCK_NUMBER];
	
	static PriorityQueue<inst>[] q_buy=new PriorityQueue[MAX_STOCK_NUMBER];
	static PriorityQueue<inst>[] q_sell=new PriorityQueue[MAX_STOCK_NUMBER];
	
	//static Set<Integer> freezing_stock = new HashSet<Integer>();
	static Set<Integer> wait_q=new HashSet<Integer>();
	private static JdbcTemplate jdbctemplate;
	//private final static String INSERT_STOCK_SQL = "INSERT INTO security_stock ( User_id, stock_id, ava_price, num) VALUES (?,?,?,?,?,?,?)";
	
	
	static int sign_d(double x) {
		if (x>eps) return 1;
		if (x<-eps) return -1;
		return 0;
	}
	
	static String getSecurityID(String capitalId) {
		String res = jdbctemplate.queryForObject("select securities_id from capital_account where user_id = \"" + capitalId + "\";", String.class);
		return res;
	}
	
	static void tran_finish(inst buy,inst sell,double price,double tot){
		System.out.println("begin tran");
		//output
		System.out.printf("Transaction confirmed!\n");
		System.out.printf("STOCK :	%s\n",buy.stock_id);
		System.out.printf("PRICE :	%.2f\n",price);
		System.out.printf("TOTAL : %.2f\n",tot);
		System.out.printf("\n");
		/*buy.print();
		sell.print();
		System.out.printf("\n");*/
		
		//System.out.printf("%d %.2f %.2f\n",buy.stock_id,price,tot);
		
		//finished
		int stock_id=stock_map.get(buy.stock_id);
		
		String user_buy_security_id = getSecurityID(buy.user_id);
		String user_sell_security_id = getSecurityID(sell.user_id);
		
		//buy
		jdbctemplate.execute("update security_stock set num = num + " + tot + " , price = price + " + (tot * price) + 
				" where User_id = \"" + user_buy_security_id + "\" and stock_id = \"" + buy.stock_id + "\";");
		
		//sell
		jdbctemplate.execute("update security_stock set num = num - " + tot + " , price = price - " + (tot * price) + 
				" where User_id = \"" + user_sell_security_id + "\" and stock_id = \"" + sell.stock_id + "\";");
		
		//buy
		jdbctemplate.execute("update capital_account set freezing = freezing - " + (price*tot) + 
				", fund = fund + " + ((buy.price-price)*tot) + " where user_id = \"" + buy.user_id + "\";");

		//sell
		jdbctemplate.execute("update capital_account set fund = fund + " + (price*tot) + " where user_id = \"" + sell.user_id + "\";");
		
		//buy
		//jdbctemplate.execute("update instructionset set State = 1 where Number = " + buy.inst_id + ";");
		
		//sell
		//jdbctemplate.execute("update instructionset set State = 1 where Number = " + sell.inst_id + ";");
		
		q_buy[stock_id].remove();
		q_sell[stock_id].remove();
		
		buy.tot-=tot;
		sell.tot-=tot;
		
		if (sign_d(buy.tot)!=0) 	q_buy[stock_id].add(buy);
		else
		{
			jdbctemplate.execute("update instructionset set state=1 where Number="+buy.inst_id+";");
		}
		if (sign_d(sell.tot)!=0) 	q_sell[stock_id].add(sell);
		else
		{
			jdbctemplate.execute("update instructionset set state=1 where Number="+sell.inst_id+";");
		}
		
		System.out.println("end tran");

	}
	
	static boolean tran_confirm(inst buy,inst sell,double price,double tot){
		//----TODO----
		//tell other modules && waiting response
		boolean if_confirmed=true;	
		//------------
		
		return if_confirmed;
	}

	static double daily_limit_max(String stock_id){
		//TODO
		//need know yesterday price
		//return yesterday_price*1.1;	//or 1.05
		double MAX = jdbctemplate.queryForObject("select upper_limit from stock_inf where stock_id = \"" + stock_id + "\";",double.class);
		return MAX;
	}
	static double daily_limit_min(String stock_id){
		//TODO
		//need know yesterday price
		//return yesterday_price*0.9;	//or 0.95
		double MIN = jdbctemplate.queryForObject("select lower_limit from stock_inf where stock_id = \"" + stock_id + "\";",double.class);
		return MIN;
	}
	
	static boolean judge(inst buy,inst sell){
		if (sell.price>buy.price) return false;
		return true;
	}
	
	static double get_price(inst buy,inst sell) {
		double price;
		//int stock_id=stock_map.get(buy.stock_id);
		price=(buy.price+sell.price)/2;
		price=Math.min(daily_limit_max(buy.stock_id),price);
		price=Math.max(daily_limit_min(buy.stock_id),price);
		return price;
	}
	
	static double get_tot(inst buy,inst sell) {
		double tot;
		tot=Math.min(buy.tot,sell.tot);
		return tot;
	}

	static void inst_expired(inst x){
		//TODO
		//tell other modules
		
		System.out.printf("Instruction expired!\n");
		x.print();
		
		int stock_id=stock_map.get(x.stock_id);
		int State = jdbctemplate.queryForObject("select State from instructionset where Number = " + x.inst_id + ";",int.class);
		
		if (x.type==0) {
			q_buy[stock_id].remove();
			if (State == 0) {
				jdbctemplate.execute("update capital_account set freezing = freezing - " + (x.price*x.tot) + 
									 ", fund = fund + " + (x.price*x.tot) + " where user_id = \"" + x.user_id + "\";");
			}
		}
		else
			q_sell[stock_id].remove();
		
		if (State == 0) {
			jdbctemplate.execute("update instructionset set State = 1 where Number = " + x.inst_id + ";");
		}
	}

	static boolean if_expired(inst x){
		if (new Date().getTime()/86400000!=x.time_stamp/86400000) return true;
		//int stock_id = stock_map.get(x.stock_id);
		int State = jdbctemplate.queryForObject("select State from instructionset where Number = " + x.inst_id + ";",int.class);
		if (State == 1) return true;
		return false;
	}

	public static boolean if_freezing(String x) {
		int State = jdbctemplate.queryForObject("select stock_state from stock_inf where stock_id = \"" + x + "\";",int.class);
		if (State == 0) return true;
		return false;
	}
	
	public void run(){
		for(int stock_id:wait_q){
			//if (freezing_stock.contains(stock_id)) continue;
			if (if_freezing(stock_int2string[stock_id])) continue;
			while(q_buy[stock_id].size()>0&&q_sell[stock_id].size()>0){
				inst buy=q_buy[stock_id].element();
				inst sell=q_sell[stock_id].element();
				
				if (if_expired(buy)){
					inst_expired(buy);
					continue;
				}
				if (if_expired(sell)){
					inst_expired(sell);
					continue;
				}
				
				if (judge(buy,sell)){
					double price=get_price(buy,sell);
					double tot=get_tot(buy,sell);
					
					if (tran_confirm(buy,sell,price,tot)){
						tran_finish(buy,sell,price,tot);
					} 
				} else break;
			}
		}
		
		wait_q.clear();
		//System.out.println(new Date().getTime() + "run");
	}

	public static void insert(inst x){
		System.out.println("begin insert!");
		x.print();
		int stock_id;
		if (!stock_map.containsKey(x.stock_id)) {
			stock_map.put(x.stock_id, ++stock_num);
			stock_int2string[stock_num] = x.stock_id;
			stock_id=stock_num;
		} else stock_id=stock_map.get(x.stock_id);
		
		//security_stock
		
		System.out.println("Check security_stock");
		System.out.println("select id from security_stock where User_id = \"" + getSecurityID(x.user_id) + "\" and stock_id = \"" + x.stock_id + "\";");
		System.out.println(jdbctemplate.queryForList("select id from security_stock where User_id = \"" + getSecurityID(x.user_id) + "\" and stock_id = \"" + x.stock_id + "\";").size());
		if (jdbctemplate.queryForList("select id from security_stock where User_id = \"" + getSecurityID(x.user_id) + "\" and stock_id = \"" + x.stock_id + "\";").size()==0) {
			System.out.println("insert into security_stock (User_id,stock_id,price,num) values(\"" + getSecurityID(x.user_id) + "\"," + "\"" + x.stock_id + "\",0,0);");
			jdbctemplate.execute("insert into security_stock (User_id,stock_id,price,num) values(\"" + getSecurityID(x.user_id) + "\"," + "\"" + x.stock_id + "\",0,0);");
		}
		
		System.out.println("Checked");
		
		if (x.type==0)
			q_buy[stock_id].add(x);
		else
			q_sell[stock_id].add(x);
		
		wait_q.add(stock_id);
		
		System.out.println("end insert");
	}
	
	public make(JdbcTemplate jdbcttt) {
		System.out.println("begin make");
		jdbctemplate=jdbcttt;
		wait_q.clear();
		for (int i=0;i<MAX_STOCK_NUMBER;i++){
			q_buy[i]=new PriorityQueue<inst>();
			q_sell[i]=new PriorityQueue<inst>();
		}
		System.out.println("end make");
	}
	
	/*
	public static void freeze_stock(String x) {
		int stock_id = stock_map.get(x);
		freezing_stock.add(stock_id);
	}
	
	public static void unfreeze_stock(String x) {
		int stock_id = stock_map.get(x);
		if (freezing_stock.contains(stock_id)) {
			freezing_stock.remove(stock_id);
		}
	}
	 */
	
	public static boolean undo_instruction(String Number) {
		int State = jdbctemplate.queryForObject("select State from instructionset where Number = " + Number + ";",int.class);
		if (State == 1)  return false;
		
		int type = jdbctemplate.queryForObject("select Buy from instructionset where Number = " + Number + ";",int.class);
		if (type == 1) {
			String user_id = jdbctemplate.queryForObject("select User_ID from instructionset where Number = " + Number + ";",String.class);
			double price = jdbctemplate.queryForObject("select Price from instructionset where Number = " + Number + ";",double.class);
			double amount = jdbctemplate.queryForObject("select Amount from instructionset where Number = " + Number + ";",double.class);
			jdbctemplate.execute("update capital_account set freezing = freezing - " + (price*amount) + 
					", fund = fund + " + (price*amount) + " where  user_id = \"" + user_id + "\";");
		}
		

		jdbctemplate.execute("update instructionset set State = 1 where Number = " + Number + ";");
		
		return true;
	}
}
