package instruction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import queue.inst;
import queue.make;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@Controller
public class SpringController {
	private JdbcTemplate jdbcTemplate;
	private final static String INSERT_INSTRUCTION_SQL = "INSERT INTO instructionset (ID, User_ID, Buy, Amount, Date, Price, state) VALUES (?,?,?,?,?,?,?);";
    private make make;
    private SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");
    private int total_Count=0;
    
    static double eps=1e-10;
    
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public @ResponseBody Map<String,String> helloWorld(@RequestBody requestSave save) throws ParseException{
		//ID,User_ID,Buy,Amount,Date,Price;
		System.out.println(save.ID);
		System.out.println(save.User_ID);
		System.out.println(save.Buy);
		System.out.println(save.Amount);
		System.out.println(save.Date);
		System.out.println(save.Price);
		
		HashMap<String,String> map=new HashMap<String,String>();
		//Map<String, Object> buy_Data = jdbcTemplate.queryForMap("select * from instructionset where Number = 1");
		//System.out.println(buy_Data.get("Buy"));
		if(save!=null)
		{
			//Object[] args = {save.getID(),save.getUser_ID(),save.getBuy(),save.getAmount(),save.getDate(),save.getPrice(),0};
			
			Object[] args = {save.ID,save.User_ID,save.Buy,save.Amount,save.Date,save.Price,0};

			
			//Check if fund enough
			

			double money = jdbcTemplate.queryForObject("select fund from capital_account where User_ID = \"" + save.User_ID + "\";",double.class);
			double price = Double.parseDouble(save.Price);
			double amount = Double.parseDouble(save.Amount);
			
			System.out.printf("%.2f %.2f\n",money + eps, price * amount);
			
			if(save.Buy.equals("1") && money + eps < price * amount )
			{
				map.put("valid","0");
				
				map.put("msg","");
			}
			else
			{
				map.put("valid","1");
				map.put("msg","");
				
				System.out.println("BEGIN insert into instruction set");
				//jdbcTemplate.execute("insert into instructionset values(2,\"A11\",\"zfx\",1,10,\"156052427070\",10.00,0);");
				jdbcTemplate.update(INSERT_INSTRUCTION_SQL, args);
				System.out.println("END insert into instruction set");
				
				int Number = jdbcTemplate.queryForObject("select max(number) from instructionset;",int.class);

				
				if (save.Buy.equals("1")) {
					jdbcTemplate.execute("update capital_account set freezing = freezing + " + (price*amount) + 
									", fund = fund - " + (price*amount) + " where user_id = \"" + save.User_ID + "\";");
				}
				
				inst inst = new inst();
				Date now=sdf.parse(save.Date);
				inst.set(1-Integer.parseInt(save.Buy), Number, now.getTime(), save.User_ID, save.ID, Double.parseDouble(save.Price),Double.parseDouble(save.Amount));
				make.insert(inst);
				make.run();
			}
		}
		else
		{
			map.put("valid","0");
			map.put("msg","");
		}
		return map;
	}
	
	@RequestMapping(value="/cancel",method=RequestMethod.POST)
	public @ResponseBody Map<String,String> Thisone(@RequestBody requestCancel cancel)
	{
		System.out.println(cancel.Number);
		System.out.println(make.undo_instruction(cancel.Number));
		return null;
	}
	
	SpringController()
	{
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/db1");
		dataSource.setUsername("root");	
		dataSource.setPassword("flash19980203");
		jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
		make = new make(jdbcTemplate);
		
		/*Timer timer = new Timer();
		Date startDate = new Date();
		timer.scheduleAtFixedRate(make, startDate, 1000);*/
	}
}