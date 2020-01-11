import React from 'react';
import ReactDOM from 'react-dom';

export function Cookie(){
  this.setCookie=(name_list, value_list, expired, rem)=>{
    var d = new Date();
    if(rem){
      d.setTime(d.getTime() + expired*24*60*60*1000);
    }
    else{
      d.setTime(d.getTime() - expired*24*60*60*1000);
    }
    var expires = "expires="+d.toUTCString();
    var cookie_text = ""
    for(var i=0;i<name_list.length;i+=1){
      cookie_text += name_list[i] + "=" +value_list[i];
      if(i!==name_list.length - 1){
        cookie_text += "&";
      }
    }
    cookie_text+=";";
    console.log(cookie_text);
    document.cookie = cookie_text + expires + ";path=/";
  }
  this.getCookie=(name_list) =>{
    var returnData = [];
    var decodedCookie = decodeURIComponent(document.cookie);
    console.log(decodedCookie);
    var ca = decodedCookie.split(';');
    ca = ca.toString().split("\&");
    for(var k = 0; k < name_list.length; k+= 1){
      var name = name_list[k] + "=";
      for(var i = 0; i <ca.length; i++) {
          var c = ca[i];
          console.log(c);
          while (c.charAt(0) == ' ') {
              c = c.substring(1);
          }
          if (c.indexOf(name) == 0) {
              returnData.push(c.substring(name.length, c.length));
              break;
          }
      }
      if(k===name_list.length){
        returnData.push("");
      }
    }
    console.log(returnData);
    return returnData;
  }


  this.checkCookie=() => {
    var username = this.getCookie("username");
    if (username != "") {
        alert("Welcome again " + username);
    } else {
        username = prompt("Please enter your name:", "");
        if (username != "" && username != null) {
            this.setCookie("username", username, 365);
        }
    }
  }
}


export var CookieInstance = new Cookie();