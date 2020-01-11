import React from 'react';
import ReactDOM from 'react-dom';
// latest_price": 1.0, "latest_num": 2, "latest_type": "A",
//     "BuyInsts": [{"inst_no":0, "isnt_type":"A", "isnt_num":2, "price":5000, 
//       "stock_id":"sn123211", "user_id":"123456", "op_time":"2018-08-08"}], 
//     "SellInsts":[{"inst_no":0, "isnt_type":"A", "isnt_num":2, "price":5000, 
//     "stock_id":"sn123211", "user_id":"123456", "op_time":"2018-08-08"}],
//     "stateCode": 


export function Stock(id, name, price, ulm, llm, state, latest_price, latest_num, latest_type, BuyInsts, SellInsts){
    this.stock_id = id;
    this.stock_name = name;
    this.price = price;
    this.upper_limit = ulm;
    this.lower_limit = llm;
    this.limit_state = 1;
    this.state = state;
    this.BuyInsts = BuyInsts;
    this.SellInsts = SellInsts;
    this.latest_num = latest_num;
    this.latest_price = latest_price;
    this.latest_type = latest_type;
};

export function Instruction(json_inst){
    this.inst_no = json_inst['inst_no'];
    this.inst_type = json_inst['inst_type'];
    this.inst_num = json_inst['inst_num'];
    this.price = json_inst['price'];
    this.stock_id = json_inst['stock_id'];
    this.user_id = json_inst['user_id'];
    this.op_time = json_inst['op_time'];
    this.print = ()=>{
        return '{"inst_no":'+this.inst_no.toString()+ ', "inst_type":"'+this.inst_type+'", "inst_num":'+this.inst_num.toString()
        + ', "price":'+this.price.toString()+ ', "stock_id":"'+this.stock_id+', "user_id":'+this.user_id+', "op_time":'+
        this.op_time+'}';
    }
};
