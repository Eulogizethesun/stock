import React from 'react';
import ReactDOM from 'react-dom';
import '../css/main.css';
import { user } from '../objects/users.js';
import { STATE_LOGIN, pageType }  from '../globals.js'
import { m, s, u, a } from '../index.js'
import {Top} from './toolbar.js'
import {Side} from './menu.js'
import {MainText} from './frames/mainFrame.js'
import {StockText} from './frames/stockFrame.js'
import {AboutText} from './frames/aboutFrame.js'
import {UserText} from './frames/userFrame.js'

var not_login_flushed = 0;


export function main(mainFrame, toolBar, menu){
  this.mainFrame = mainFrame;
  this.toolBar = toolBar;
  this.menu = menu;

  this.draw = () =>{
    this.flush();
    const main_element = (
      <div class="ms-container">
        {/* <div id="ms-toolBar">{this.toolBar}}</div> */}
        <div id="ms-menu-mainFrame-container">
          <div id="ms-menu">
            {this.menu}
          </div>
          <div id="ms-mainFrame">
            {this.mainFrame}
          </div>
        </div>
      </div>
    );
    ReactDOM.render(main_element, document.getElementById('root'));
    return main_element;
    
  }

  this.flush = ()=>{
    if(user.state === STATE_LOGIN){
      not_login_flushed = 0;
      this.mainFrame = <MainText/>;
    }
    else if(not_login_flushed === 0){
      switch(pageType){
        case 1:{
          this.mainFrame = <MainText />;
          break;
        }
        case 2:{
          user.load_all_stock();
          this.mainFrame = <StockText />;
          break;
        }
        case 3:{
          this.mainFrame = <UserText />;
          break;
        }
        case 4:{
          user.load_all_user();
          this.mainFrame = <AboutText />;
          break;
        }

      }
      
      not_login_flushed = 1;
    }
    this.menu=<Side />;
    this.toolBar=<Top />;
  }
}

var service = new main();

export var service;
