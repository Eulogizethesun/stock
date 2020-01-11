import React from 'react';
import logo from './logo.svg';
import './App.css';
import { user } from './objects/users.js';
import { Stock } from './objects/stock.js';
import { service } from './components/main.js';
import { Side } from './components/menu.js'
import { Top } from './components/toolbar.js'
import { MainText } from './components/frames/mainFrame.js'
import { StockText } from './components/frames/stockFrame.js'
import { AboutText } from './components/frames/aboutFrame.js'
import { UserText } from './components/frames/userFrame.js'
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-theme.css';




// service.toolBar = service.toolBar = (<div>
//   <p id="userinfo">Welcome, {user.name}!
//   <button onClick={user.login}>login</button></p>
//   </div>);


export var navside=<Side />
export var navtop=<Top />
export var m=<MainText />
export var u=<UserText />
export var s=<StockText />
export var a=<AboutText />

// function HelloMessage(props) {
//     return <h1>Hello {props.name}!</h1>;
// }
//
// const hel = <HelloMessage name="Runoob"/>;

class App extends React.Component {
  render() {
    const name = user.name;
    service.menu = <p>This.is.the.toolBar.</p>;
    service.menu=navside;
    service.mainFrame=m;
    service.toolBar=navtop;
    

    return service.draw();   
  }
}

export default App;
