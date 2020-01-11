import React from 'react';
import ReactDOM from 'react-dom';
import { service } from '../components/main.js';
import { SetPer } from '../components/frames/stockFrame.js'
import { SetPer_user } from '../components/frames/aboutFrame.js'
import { URLParam, GETRequest, POSTRquest } from '../utils/httpRequest.js';
import { Stock, Instruction } from './stock.js';
import { STATE_LOGIN_OUT, STATE_LOGIN, LOGIN_URL, LOGIN_URL_TEST, 
         MOD_STATE_URL, MOD_PASSWORD_URL,MOD_LIMIT_URL,GET_ALL_URL,
         GET_STOCK_DETAIL_URL ,T_URL,SEARCH_BY_NAME,SEARCH_BY_ID,
         GET_ALL_USR, MOD_MNG_AHU, ADD_NEW_MNG, RST_PASSWORD_URL, 
         SEARCH_MNG_BY_NAME}  from '../globals.js'
import { DetailText } from '../components/frames/detailFrame.js';
import { CookieInstance } from '../utils/simple-cookie.js';
import SiderDemo from '../components/mainLayout.js';
import LoginApp from '../login';
export var per = [];

export function AdminUser(name, auth) {
    this.name = name;
    this.auth = auth;
    this.passwd = "";
    this.bool_para = false;
    this.state = STATE_LOGIN_OUT;
    this.tmpStock = new Stock("id","name",100,2,23,0,20,20,20,1,2);
    this.arg = "page-1";
    this.arg2 = 1;
    this.argu = "page-1";
    this.argu2 = 1;

    this.show = () => {
      return <h1>User: {this.name}, Auth: {this.auth}</h1>;
    }

    this.loginCallback = (data) => {
      var LOGIN_SUCCESS = 0;
      var INVALID_PASSWORD = -1;
      var INVALID_USERNAME = -2;
      // data = {"stateCode":LOGIN_SUCCESS, "username":"user.name", "authority": 2};
      var stateCode = data["stateCode"];
      if(stateCode === LOGIN_SUCCESS){
        this.name = data["username"];
        this.auth = data["authority"];
        if(this.auth === -1){
          this.state = STATE_LOGIN_OUT;
        }
        this.state = STATE_LOGIN;
        console.log("callback: " + this.name);
        var name_list = ["username", "password"];
        var para_list = [this.name, this.passwd];
        console.log(this.bool_para);
        CookieInstance.setCookie(name_list, para_list,1, this.bool_para);
        ReactDOM.render(<SiderDemo user={user} directTo="/dashboard"/> , document.getElementById('root'));
      }
      else if(stateCode === INVALID_PASSWORD ){
        ReactDOM.render(<LoginApp  alertShow={true} alertText={"密码不正确"} alertType={"warning"}/> , 
        document.getElementById('root'));
      }
      else if(stateCode === INVALID_USERNAME ){
        ReactDOM.render(<LoginApp  alertShow={true} alertText={"用户名不存在"} alertType={"warning"}/> , 
        document.getElementById('root'));
      }
      // service.draw();
    }

    this.login = (passwd, save_password) => {
      var url = LOGIN_URL_TEST;
      var url2 = URLParam(url, "username", this.name);
      url2 = URLParam(url2, "passwd", passwd);
      this.passwd = passwd;
      this.bool_para = save_password;
      console.log(url2);
      // this.loginCallback(url);
      GETRequest(url2, this.loginCallback);
    }

    this.modifyPasswordCall = (data) => {
      if(user.state === STATE_LOGIN_OUT){
        return;
      }
      var stateCode = data['stateCode'];
      switch(stateCode){
        case 0:{
          alert("succeed");
          break;
        }
        case -1:{
          alert("previous password is not correct");
          break;
        }
        case -2:{
          alert("the password is duplicated");
          break;
        }
      }
    }

    this.modifyPassword = (prePassword, newPassword) => {
      var url = MOD_PASSWORD_URL;
      var data = {"username": this.name, "old_passwd": prePassword,
       "new_passwd": newPassword};
      console.log(data);
      POSTRquest(url, data, this.modifyPasswordCall);
    }

    this.logout = () => {
      this.state = STATE_LOGIN_OUT;
      this.name = "Guest";
      this.passwd = "";
      this.auth = "11";
      ReactDOM.render(<LoginApp  alertShow={true} alertText={"您已成功退出登录"} alertType={"success"}/> , 
        document.getElementById('root'));
    }

    this.modifyCallback = (data) => {
      var stateCode = data['stateCode'];
      if(stateCode === 0){
        alert("succeed");
      }else{
        alert("failed");
      }
      this.load_all_stock();
    }

    this.modify_limit = (id, upper, lower) => {
      if(user.state === STATE_LOGIN_OUT){
        return;
      }
      var url = MOD_LIMIT_URL;
      var data = {"stock_id": id, "upper_limit": upper, "lower_limit": lower};
      console.log(data);
      POSTRquest(url, data, this.modifyCallback);
    }

    this.modify_state = (id, state) => {
      var url = MOD_STATE_URL;
      var data = {"stock_id": id, "state": state};
      console.log(data);
      POSTRquest(url, data, this.modifyCallback);
    }

    this.load_all_stock = () => {
      if(user.state === STATE_LOGIN_OUT){
        return;
      }
      var url =  GET_ALL_URL;
      var url2 = URLParam(url, "authority", this.auth);
      console.log(url2);
      GETRequest(url2, this.load_all_callback);
    }

    this.load_all_callback = (data) => {
      var stateCode = data['stateCode'];
      var dataset = data['stocks']
      if(stateCode === 0){
        
        ReactDOM.render(<SiderDemo user={this} directTo="/stock"/> , document.getElementById('root'));
        SetPer(dataset, this.arg);
      }
      else if(stateCode === -1){
        alert("no record");
      }
      else if(stateCode === -2){
        alert("load error");
      }
    }

    this.load_all_user = () => {
      if(user.state === STATE_LOGIN_OUT){
        return;
      }
      var url =  GET_ALL_USR;
      var url2 = URLParam(url, "own_authority", this.auth);
      console.log(url2);
      GETRequest(url2, this.load_user_callback);
    }

    this.load_user_callback = (data) => {
      var stateCode = data['stateCode'];
      var dataset = data['users']
      if(stateCode === 0){   
        SetPer_user(dataset,this.argu);
        ReactDOM.render(<SiderDemo user={this} directTo="/auth"/> , document.getElementById('root'));
      }
      else{
        alert("authority error");
      }
      
    }

    this.get_detail_callback=(data) => {
      // {"BuyInsts":[]}
      // var data = {"latest_price": 1.0, "latest_num": 2, "latest_type": "A",
      //             "buyInsts": [{"inst_no":0, "inst_type":"A", "inst_num":2, "price":5000, 
      //               "stock_id":"sn123211", "user_id":"123456", "op_time":"2018-08-08"}], 
      //             "sellInsts":[{"inst_no":0, "inst_type":"A", "inst_num":2, "price":5000, 
      //             "stock_id":"sn123211", "user_id":"123456", "op_time":"2018-08-08"}],
      //             "stateCode": 0};
      var stateCode = data['stateCode'];
      switch(stateCode){
        case 0:{
          var i = 0;
          var BuyInsts=[];
          var SellInsts=[];
          for(i=0;i<data['buyInsts'].length;i++){
            BuyInsts.push(new Instruction(data['buyInsts'][i]));
          }
          for(i=0;i<data['buyInsts'].length;i++){
            SellInsts.push(new Instruction(data['sellInsts'][i]));
          }
          this.tmpStock.latest_num = data['latest_num'];
          this.tmpStock.latest_price = data['latest_price'];
          this.tmpStock.latest_type = data['latest_type'];
          this.tmpStock.BuyInsts = BuyInsts;
          this.tmpStock.SellInsts = SellInsts;
          // console.log(this.tmpStock.SellInsts[0].op_time)
          // service.mainFrame = new DetailText(this.tmpStock);
          ReactDOM.render(<SiderDemo user={user} directTo="/detail"/> , document.getElementById('root'));
          break;
        }
        case -1:{
          alert("error");
          break;
        }
      }
      // service.draw(); 
    }

    this.get_stock_detail = (selected_stock) =>{
      if(user.state === STATE_LOGIN_OUT){
        return;
      }
      var url = GET_STOCK_DETAIL_URL;
      this.tmpStock = selected_stock;
      var url2 = URLParam(url, "stock_id", selected_stock.stock_id);
      console.log(url2);
      GETRequest(url2, this.get_detail_callback);
      // this.get_detail_callback();
    }

    this.search = (method, string) =>{
      if(user.state === STATE_LOGIN_OUT){
        return;
      }
      var url;
      var url2;
      if(method === "words"){
        url = SEARCH_BY_NAME;
        url2 = URLParam(url,  "stock_name", string);
        url2 = URLParam(url2,  "authority", this.auth);
      }
      else if(method === "id"){
        url = SEARCH_BY_ID;
        url2 = URLParam(url,  "stock_id", string);
        url2 = URLParam(url2,  "authority", this.auth);
      }
      console.log(url2);
      GETRequest(url2, this.load_all_callback);
    }

    this.search_user = (method, string) => {
      if(user.state === STATE_LOGIN_OUT){
        return;
      }
      var url;
      var url2;
      if(method === "username"){
        url = SEARCH_MNG_BY_NAME;
        url2 = URLParam(url,  "username", string);
        url2 = URLParam(url2,  "own_authority", this.auth);
      }
      console.log(url2);
      GETRequest(url2, this.load_user_callback);
    }

    this.reset_user_pwd_call = (data) => {
      var stateCode = data['stateCode'];
      switch(stateCode){
        case 0:{
          alert("success");
          break;
        }
        case -1:{
          alert("invalid username");
          break;
        }
        case -2:{
          alert("operation error");
          break;
        }
        case -3:{
          alert("authority error");
          break;
        }
      }
    }

    this.reset_user_pwd = (user_name) => {
      if(user.state === STATE_LOGIN_OUT){
        return;
      }
      var url = RST_PASSWORD_URL;
      var data = {"own_authority": this.auth, "username": user_name};
      console.log(data);
      POSTRquest(url, data, this.reset_user_pwd_call);
    }

    this.modify_auth_call = (data) => {
      var stateCode = data['stateCode'];
      switch(stateCode){
        case 0:{
          alert("success");
          break;
        }
        case -1:{
          alert("invalid username");
          break;
        }
        case -2:{
          alert("invalid authority");
          break;
        }
        case -3:{
          alert("authority error");
          break;
        }
      }
      this.load_all_user();
    }

    this.modify_auth = (user_name, auth) => {
      if(user.state === STATE_LOGIN_OUT){
        return;
      }
      var url = MOD_MNG_AHU;
      var data = {"own_authority": this.auth, "username": user_name, "authority": auth};
      console.log(data);
      POSTRquest(url, data, this.modify_auth_call);
    }

    this.add_account_call = (data) => {
      var stateCode = data['stateCode'];
      switch(stateCode){
        case 0:{
          alert("success");
          break;
        }
        case -1:{
          alert("duplicated username");
          break;
        }
        case -2:{
          alert("invalid authority");
          break;
        }
        case -3:{
          alert("authority error");
          break;
        }
      }
      this.load_all_user();
    }
  
    this.add_account = (user_name, user_auth) => {
      if(user.state === STATE_LOGIN_OUT){
        return;
      }
      var url = ADD_NEW_MNG;
      var data = {"own_authority": this.auth, "username": user_name, "authority": user_auth};
      console.log(data);
      POSTRquest(url, data, this.add_account_call);
    }
};

export var user = new AdminUser("Guest", 2);


