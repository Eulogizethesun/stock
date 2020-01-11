import React from 'react';
import ReactDOM from 'react-dom';
import { service } from './main.js';
import { user } from '../objects/users.js';
import { m, s, u, a } from '../index.js';
import {StockText} from './frames/stockFrame.js';
import {AboutText} from './frames/aboutFrame.js';
import { SetPageType } from '../globals.js';
const ROOT_AUTH = 2;
export function Side(props) {
    var element;
    if(user.auth !== ROOT_AUTH){
        element = (<ul class="sidenav" id="sidenav">
        <li><a class="active" id="side1" href="#home" onClick={setclassside1}>主页</a></li>
        <li><a class="inactive" id="side2" href="#mainframe" onClick={setclassside2}>股票管理</a></li>
        <li><a class="inactive" id="side3" href="#mainframe" onClick={setclassside3}>密码修改</a></li>
        <li><a class="inactive" id="side4" href="#mainframe" style={{"display": "none"}} onClick={setclassside4}>账户管理</a></li>
        </ul>);
      } else {
        element = (<ul class="sidenav" id="sidenav">
        <li><a class="active" id="side1" href="#home" onClick={setclassside1}>主页</a></li>
        <li><a class="inactive" id="side2" href="#mainframe" onClick={setclassside2}>股票管理</a></li>
        <li><a class="inactive" id="side3" href="#mainframe" onClick={setclassside3}>密码修改</a></li>
        <li><a class="inactive" id="side4" href="#mainframe" onClick={setclassside4}>账户管理</a></li>
        </ul>);
      }
    
    return element;
}

export function setclassside1(){
    service.mainFrame=m;
    var item1 = document.getElementById('side1');
    var item2 = document.getElementById('side2');
    var item3 = document.getElementById('side3');
    var item4 = document.getElementById('side4');
    item1.setAttribute('class', 'active');
    item2.setAttribute('class', 'inactive');
    item3.setAttribute('class', 'inactive');
    item4.setAttribute('class', 'inactive');
    SetPageType(1);
    service.draw();
}
export function setclassside2(){
    user.load_all_stock();
    service.mainFrame=<StockText />;
    var item1 = document.getElementById('side1');
    var item2 = document.getElementById('side2');
    var item3 = document.getElementById('side3');
    var item4 = document.getElementById('side4');
    item1.setAttribute('class', 'inactive');
    item2.setAttribute('class', 'active');
    item3.setAttribute('class', 'inactive');
    item4.setAttribute('class', 'inactive');
    SetPageType(2);
    service.draw();
}
export function setclassside3(){
    service.mainFrame=u;
    var item1 = document.getElementById('side1');
    var item2 = document.getElementById('side2');
    var item3 = document.getElementById('side3');
    var item4 = document.getElementById('side4');
    item1.setAttribute('class', 'inactive');
    item2.setAttribute('class', 'inactive');
    item3.setAttribute('class', 'active');
    item4.setAttribute('class', 'inactive');
    SetPageType(3);
    service.draw();
}
export function setclassside4(){
    // service.mainFrame=a;
    user.load_all_user();
    service.mainFrame=<AboutText />;
    var item1 = document.getElementById('side1');
    var item2 = document.getElementById('side2');
    var item3 = document.getElementById('side3');
    var item4 = document.getElementById('side4');
    item1.setAttribute('class', 'inactive');
    item2.setAttribute('class', 'inactive');
    item3.setAttribute('class', 'inactive');
    item4.setAttribute('class', 'active');
    SetPageType(4);
    service.draw();
}
