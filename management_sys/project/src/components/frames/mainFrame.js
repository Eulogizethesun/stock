import React from 'react';
import ReactDOM from 'react-dom';
import { service } from '../main.js';
import { m, s, u, a} from '../../index.js';
import { user } from '../../objects/users.js';
import { STATE_LOGIN } from '../../globals.js';
import { CookieInstance }from '../../utils/simple-cookie.js';

const MAX_INFO_LEN = 20;
export function MainText () {
  var name_list = ["username", "password"];
  var returnData = CookieInstance.getCookie(name_list);
  if(user.state == STATE_LOGIN){
    return (<div class="mainframe"style={{padding:"1px 16px",height:"1000px"}}>
        <h2>Welcome {user.name}! </h2>
        {/* <h3>Try to scroll this area, and see how the sidenav sticks to the page</h3>
        <p>Notice that this div element has a left margin of 25%. This is because the side navigation is set to 25% width. If you remove the margin, the sidenav will overlay/sit on top of this div.</p>
        <p>Also notice that we have set overflow:auto to sidenav. This will add a scrollbar when the sidenav is too long (for example if it has over 50 links inside of it).</p>
        <p>Some text..</p>
        <p>Some text..</p>
        <p>Some text..</p>
        <p>Some text..</p>
        <p>Some text..</p>
        <p>Some text..</p>
        <p>Some text..</p> */}
      </div>);
  }
  else{
    return (<div class="mainframe"style={{padding:"1px 16px",height:"1000px"}}>
      <h2 class="text-center">Log in</h2>
      <form action="">
      <label for="ms-username" class="col-md-2 control-label">Username</label>
      <input id="ms-username" class="form-control" name="ms-username"  type="text" defaultValue={returnData[0]}></input>
      <br/>
      <label for="ms-passwd" class="col-md-2 control-label">Password</label>
      <input id="ms-passwd" class="form-control" name="ms-passwd" defaultValue={returnData[1]} type="password" ></input>
      <br/>
      <input id="ms-rem-passwd" name="ms-rem-passwd" type="checkbox" defaultChecked="true" /><label>Remember Password</label>
      <br/>
      </form>
      <label id="login-message"></label>
      <br/>
      <button type="submit" class="btn btn-default" onClick={login}>Log in</button>

    </div>);
  }
};


export function login(){
  var userName = document.getElementById('ms-username').value;
  var passwd = document.getElementById('ms-passwd').value;
  var d_userName = document.getElementById('ms-username').placeholder;
  var d_passwd = document.getElementById('ms-passwd').placeholder;
  var save_passwd = document.getElementById('ms-rem-passwd').checked;
  console.log(save_passwd);
  if (userName.length === 0 && d_userName!==0){
    userName = d_userName;
  }
  if (passwd.length === 0 && d_passwd!==0){
    passwd = d_passwd;
  }
  if (userName.length === 0){
    alert("username cannot be empty!");
  }
  else if (passwd.length ===0){
    alert("password cannot be empty!");
  }
  else if(userName.length > MAX_INFO_LEN){
    alert("invalid username!");
  }
  else if(userName.length > MAX_INFO_LEN){
    alert("invalid password!");
  }
  else{
    user.name = userName;
    user.passwd = passwd;
    user.login(passwd,save_passwd);
  }
}


