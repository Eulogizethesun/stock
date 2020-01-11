import React from 'react';
import ReactDOM from 'react-dom';
import { service } from './main.js';
import { m, s, u, a} from '../index.js'
import { user } from '../objects/users.js'
import { STATE_LOGIN, SetPageType, pageType }  from '../globals.js'
import { StockText, per, Onload } from './frames/stockFrame.js'
import { AboutText } from './frames/aboutFrame.js';

function setclasstop1(){
    service.mainFrame=m;
    var item1 = document.getElementById('top1');
    var item2 = document.getElementById('top2');
    var item3 = document.getElementById('top3');
    var item4 = document.getElementById('top4');
    item1.setAttribute('class', 'active');
    item2.setAttribute('class', 'inactive');
    item3.setAttribute('class', 'inactive');
    item4.setAttribute('class', 'inactive');
    SetPageType(1);
    service.draw();
}
function setclasstop2(){
    user.load_all_stock();
    service.mainFrame=<StockText />;
    var item1 = document.getElementById('top1');
    var item2 = document.getElementById('top2');
    var item3 = document.getElementById('top3');
    var item4 = document.getElementById('top4');
    item1.setAttribute('class', 'inactive');
    item2.setAttribute('class', 'active');
    item3.setAttribute('class', 'inactive');
    item4.setAttribute('class', 'inactive');
    SetPageType(2);
    service.draw();
}
function setclasstop3(){
    service.mainFrame=u;
    var item1 = document.getElementById('top1');
    var item2 = document.getElementById('top2');
    var item3 = document.getElementById('top3');
    var item4 = document.getElementById('top4');
    item1.setAttribute('class', 'inactive');
    item2.setAttribute('class', 'inactive');
    item3.setAttribute('class', 'active');
    item4.setAttribute('class', 'inactive');
    SetPageType(3)
    service.draw();
}
function setclasstop4(){
    user.load_all_user();
    service.mainFrame=<AboutText />;
    var item1 = document.getElementById('top1');
    var item2 = document.getElementById('top2');
    var item3 = document.getElementById('top3');
    var item4 = document.getElementById('top4');
    item1.setAttribute('class', 'inactive');
    item2.setAttribute('class', 'inactive');
    item3.setAttribute('class', 'inactive');
    item4.setAttribute('class', 'active');
    SetPageType(4);
    service.draw();
}

const ROOT_AUTH = 2;
export function Top(props) {
  var element;
  if(user.auth !== ROOT_AUTH){
    if(user.state !== STATE_LOGIN){
      element = <ul class="topnav" id="topnav">
          <li><a class="active" id="top1" href="#home" onClick={setclasstop1}>主页</a></li>
          <li><a class="inactive" id="top2" href="#mainframe" onClick={setclasstop2}>股票管理</a></li>
          <li><a class="inactive" id="top3" href="#contact" onClick={setclasstop3}>密码修改</a></li>
          <li ><a class="inactive" id="top4" href="#about" style={{"display": "none"}} onClick={setclasstop4}>账户管理</a></li>
          <li style={{float:'right'}}><a class="inactive" id="top5" href="#about"></a></li>
          </ul>;
    }
    else{
      element = <ul class="topnav" id="topnav">
          <li><a class="active" id="top1" href="#home" onClick={setclasstop1}>主页</a></li>
          <li><a class="inactive" id="top2" href="#mainframe" onClick={setclasstop2}>股票管理</a></li>
          <li><a class="inactive" id="top3" href="#contact" onClick={setclasstop3}>密码修改</a></li>
          <li ><a class="inactive" id="top4" href="#about" style={{"display": "none"}}  onClick={setclasstop4}>账户管理</a></li>
          <li style={{float:'right'}}><a class="inactive" id="top5" href="#about" onClick={logout}>登出</a></li>
          </ul>;
    }
  } else {
    if(user.state !== STATE_LOGIN){
      element = <ul class="topnav" id="topnav">
          <li><a class="active" id="top1" href="#home" onClick={setclasstop1}>主页</a></li>
          <li><a class="inactive" id="top2" href="#mainframe" onClick={setclasstop2}>股票管理</a></li>
          <li><a class="inactive" id="top3" href="#contact" onClick={setclasstop3}>用户</a></li>
          <li ><a class="inactive" id="top4" href="#about" onClick={setclasstop4}>账户管理</a></li>
          <li style={{float:'right'}}><a class="inactive" id="top5" href="#about"></a></li>
          </ul>;
    }
    else{
      element = <ul class="topnav" id="topnav">
          <li><a class="active" id="top1" href="#home" onClick={setclasstop1}>主页</a></li>
          <li><a class="inactive" id="top2" href="#mainframe" onClick={setclasstop2}>股票管理</a></li>
          <li><a class="inactive" id="top3" href="#contact" onClick={setclasstop3}>密码修改</a></li>
          <li ><a class="inactive" id="top4" href="#about"  onClick={setclasstop4}>账户管理</a></li>
          <li style={{float:'right'}}><a class="inactive" id="top5" href="#about" onClick={logout}>登出</a></li>
          </ul>;
    }

  }

  return element;
}


function getClientHeight()
{
  var clientHeight=0;
  if(document.body.clientHeight&&document.documentElement.clientHeight)
  {
  var clientHeight = (document.body.clientHeight<document.documentElement.clientHeight)?document.body.clientHeight:document.documentElement.clientHeight;
  }
  else
  {
  var clientHeight = (document.body.clientHeight>document.documentElement.clientHeight)?document.body.clientHeight:document.documentElement.clientHeight;
  }
  return clientHeight;
}

function login() {
    user.login();
}

function logout() {
    if(pageType === 4){
        SetPageType(1);
    }
    user.logout();
}
// const element = <ul>
// <li><a class="active" href="#home">主页</a></li>
// <li><a href="#news">股票</a></li>
// <li><a href="#contact">用户</a></li>
// <li><a href="#about">关于</a></li>
// </ul>;
