package queue;

public class inst implements Comparable{
	int type;	//0:buy	1:sell
	int inst_id;
	long time_stamp;	//system time in milliseconds from 1970.1.1
	String user_id;
	String stock_id;
	double price;
	double tot;
	public void set(int _type,int _inst_id,long _time_stamp,String _user_id,String _stock_id,double _price,double _tot) {
		type=_type;
		inst_id=_inst_id;
		time_stamp=_time_stamp;
		user_id=_user_id;
		stock_id=_stock_id;
		price=_price;
		tot=_tot;
	}
	public void print(){
		if (type>0) 
			System.out.printf("SELL	:\n");
		else 
			System.out.printf("BUY	:\n");
		System.out.printf("	Inst ID	%d\n",inst_id);
		System.out.printf("	TIME	%d\n",time_stamp);
		System.out.printf("	User_id %s\n",user_id);
		System.out.printf("	STOCK	%s\n",stock_id);
		System.out.printf("	PRICE	%.2f\n",price);
		System.out.printf("	TOT	%.2f\n",tot);
		System.out.printf("\n");
	}
	
    public int compareTo(Object x){
    	inst b=(inst)x;
		if (type==0){
			if (price>b.price) return -1;
			if (price<b.price) return 1;
			if (time_stamp<b.time_stamp) return -1;
			if (time_stamp>b.time_stamp) return 1;
			return 0;
		} else{
			if (price<b.price) return -1;
			if (price>b.price) return 1;
			if (time_stamp<b.time_stamp) return -1;
			if (time_stamp>b.time_stamp) return 1;
			return 0;
		}
    }	
}
