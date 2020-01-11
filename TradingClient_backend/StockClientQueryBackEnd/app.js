var express = require('express');
var app = express();

var mysql = require('mysql');
var connection = mysql.createConnection({
	host : 'localhost',
	user : 'xqbackup',
	password : '123456',
	database : 'stocksystem'
});
connection.connect();


app.all('/api/querystock', function(req, res) {
	//req.header("Access-Control-Allow-Origin","*");
	//req.header("Access-Control-Allow-Headers","content-type");
	//req.header("Access-Control-Allow-Methods","DELETE,PUT,POST,GET,OPTIONS");
	
	res.header("Access-Control-Allow-Origin","*");
	res.header("Access-Control-Allow-Headers","content-type");
	res.header("Access-Control-Allow-Methods","DELETE,PUT,POST,GET,OPTIONS")
;	
	id = req.query.stockID;
	//id = '111';
	console.log(id);

	var sn;
	connection.query('SELECT stock_name from stock_inf WHERE stock_id = ' + id, function(err, result) {
		if(err) {
			sn = '';
		}
		else {
			sn = result[0].stock_name;
			//console.log(sn);
		}	
	});

	var nt;
	connection.query('SELECT Price FROM instructionset WHERE ID = ' + id + ' AND State = 1 ORDER BY Date DESC LIMIT 1', function(err, result) {
		if(err) {
			nt = '';
		}
		else {
			//console.log(result[0]);
			nt = result[0].Price.toString();
			//console.log(nt);
		}
		///res.send({newest: nt});
	});
	
	var nsl;
	connection.query('SELECT MIN(Price) FROM instructionset WHERE ID = ' + id + ' AND State = 0 AND Buy = 0', function(err, result) {
		if(err) {
			nsl = '';
		}
		else {
			//console.log(result[0]);
			nsl = result[0]['MIN(Price)'].toString();
			//console.log(nsl);
		}
		///res.send({newest: nt});
	});

	var nbh;
	connection.query('SELECT MAX(Price) FROM instructionset WHERE ID = ' + id + ' AND State = 0 AND Buy = 1', function(err, result) {
		if(err) {
			nbh = '';
		}
		else {
			nbh = result[0]['MAX(Price)'].toString();
			//console.log(nbh);
		}
		///res.send({newest: nt});
	});

	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var day = date.getDate();
	if(month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if(day >= 0 && day <= 0) {
		day = "0" + day;
	}
	var today = year + month + day;
	var tomonth = year + month;
	console.log(today);

	var dh;
	connection.query('SELECT MAX(Price) FROM instructionset WHERE ID = ' + id + ' AND State = 1 AND MID(Date, 1, 8) = ' + today, function(err, result) {
		if(err) {
			dh = '';
		}
		else {
			//console.log(result[0]['MAX(Price)']);
			if(result[0]['MAX(Price)'] == null){
				dh = '';
			} 
			else{
				dh = result[0]['MAX(Price)'].toString();
			}//console.log(dh);
		}
		//res.send({newest: nt});
	});

	var dl;
	connection.query('SELECT MIN(Price) FROM instructionset WHERE ID = ' + id + ' AND State = 1 AND MID(Date, 1, 8) = ' + today, function(err, result) {
		if(err) {
			dl = '';
		}
		else {
			if(result[0]['MIN(Price)'] == null){
				dl = '';
			}
			else{
				dl = result[0]['MIN(Price)'].toString();
			}//console.log(nt);
		}
		///res.send({newest: nt});
	});

	var mh;
	connection.query('SELECT MAX(Price) FROM instructionset WHERE ID = ' + id + ' AND State = 1 AND MID(Date, 1, 6) = ' + tomonth, function(err, result) {
		if(err) {
			mh = '';
		}
		else {
			if(result[0]['MAX(Price)'] == null) {
				mh = '';
			}
			else{
				mh = result[0]['MAX(Price)'].toString();
			}//console.log(nt);
		}
		///res.send({newest: nt});
	});

	var ml;
	connection.query('SELECT MIN(Price) FROM instructionset WHERE ID = ' + id + ' AND State = 1 AND MID(Date, 1, 6) = ' + tomonth, function(err, result) {
		if(err) {
			ml = '';
		}
		else {
			if(result[0]['MIN(Price)'] == null){
				ml = '';
			}
			else{
				ml = result[0]['MIN(Price)'].toString();
			}//console.log(nt);
		}
		///res.send({newest: nt});
	});

	var wh;
	connection.query('SELECT MAX(Price) FROM instructionset WHERE ID = ' + id + ' AND State = 1 AND ' + today + '- CAST(MID(Date, 1, 8) AS UNSIGNED) <= 7', function(err, result) {
		if(err) {
			//console.log('err');
			wh = '';
		}
		else {
			if(result[0]['MAX(Price)'] == null) {
				wh = '';
			}
			else {
				wh = result[0]['MAX(Price)'].toString();
			}//console.log(nt);
		}
		///res.send({newest: nt});
	});

	var wl;
	connection.query('SELECT MIN(Price) FROM instructionset WHERE ID = ' + id + ' AND State = 1 AND ' + today + '- CAST(MID(Date, 1, 8) AS UNSIGNED) <= 7', function(err, result) {
		if(err) {
			wl = '';
		}
		else {
			if(result[0]['MIN(Price)'] == null) {
				wl = '';
			}
			else{
				wl = result[0]['MIN(Price)'].toString();
			}//console.log(nt);
		}
		///res.send({newest: nt});
	});

	setTimeout(function(){
		res.send({
 			stockName: sn,
            newest: nt,
            nowSellLow: nsl,
            nowBuyHigh: nbh,
            dayHigh: dh,
            dayLow: dl,
            weekLow: wl,
            weekHigh: wh,
            monthHigh: mh,
            monthLow: ml,
            information: 'None'
		});
	}, 5000);
	
	//res.send({
	//	stockName: sn,
	//	newest: nt,
	//	nowSellLow: '2',
	//	nowBuyHigh: '3',
	//	dayHigh: '4',
	//	dayLow: '5',
	//	weekLow: '6',
	//	weekHigh: '7',
	//	monthHigh: '8',
	//	monthLow: '9',
	//	information: 'wqwqwq'
	//});
});

var server = app.listen(8082, function() {
});
