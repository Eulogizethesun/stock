import React from 'react';
import ReactDOM from 'react-dom';
import { user } from './users.js';
import { request } from '../utils/httpRequest.js';
import { service } from '../components/main.js';
import { URLParam, GETRequest, POSTRquest } from '../utils/httpRequest.js';
import { STATE_LOGIN_OUT, STATE_LOGIN, LOGIN_URL, LOGIN_URL_TEST, MOD_PASSWORD_URL }  from '../globals.js'


export function userInstructions(){
  this.modifyUlm = (user, stock, value) =>{

  }

  this.modifyLlm = (user, stock, value) =>{

  }

  this.stopTransaction = (user, stock) =>{

  }

  this.restartTransaction = (user, stock) =>{

  }

  this.modifyUlmCallback = (data)=>{

  }

  this.modifyLlmCallback = (data)=>{

  }

  this.stopTransCallback = (data)=>{

  }

  this.restartTransCallback = (data)=>{

  }
}
