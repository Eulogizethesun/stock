import React from 'react';
import ReactDOM from 'react-dom';
import { user } from './objects/users.js';
import { setShowAlert, getShowAlert } from './globals.js';
import { CookieInstance }from './utils/simple-cookie.js';
import { Button, Icon, Input, Row, Col, Card, Checkbox, Layout, Avatar, Alert } from "antd";
import "antd/dist/antd.css";
import "./css/login.css"
import { service } from './components/main.js';
const MAX_INFO_LEN = 20;
const {Header, Footer, Sider, Content} = Layout;

export default class LoginApp extends React.Component {
    render() {
      var name_list = ["username", "password"];
      var returnData = CookieInstance.getCookie(name_list);
      return (
        <Layout className="layout" style={{height: "100%" , justifyContent: "center", margin: "0px"}}>
          <Header id="header-msg" style={{padding: "0", textAlign : "center", backgroundColor: "#f4f4f4"}}>
          <Row type="flex" justify="start" >
            <Col span={6} offset={9}>
          {this.props.alertShow === true ? <AlertMsg message={this.props.alertText} type={this.props.alertType}/> :null}</Col>
          </Row>
          </Header>
          <Content>
          <Row type="flex" justify="start" >
            <Col span={6} offset={9}>
              <div id="ms-login-pannel" style={{ padding: "24px 0px 24px 0px" }} >
                <Card title="管理员登录" extra={<a href="#">忘记密码</a>}>
                  <p style={{ textAlign: "center"}}>
                    <Avatar size={64} icon="user" />
                  </p>
                  <p style={{ padding: "20px 30px 0px 30px" }} > 
                  <Input prefix={<Icon type="user" style={{ fontSize: 13 }} />} 
                      id="ms-username" class="form-control" name="ms-username" 
                      type="text" defaultValue={returnData[0]}></Input>
                  </p>
                  <p style={{ padding: "20px 30px 0px 30px" }} > 
                  <Input prefix={<Icon type="lock" style={{ fontSize: 13 }} />} 
                      id="ms-passwd" class="form-control" name="ms-passwd" 
                      type="password" defaultValue={returnData[1]} ></Input>
                  </p>
                  <p style={{ padding: "20px 30px 0px 30px" }} > 
                    <div>
                      <Checkbox id="ms-rem-passwd" name="ms-rem-passwd" type="checkbox" 
                        defaultChecked="true">记住我</Checkbox>
                    </div>
                  </p>
                  <p style={{ padding: "20px 30px 0px 30px" }} > 
                    <Button type="primary" id="ms-login-btn" onClick={login}>登录</Button>
                  </p>
                </Card>
              </div>
            </Col>
          </Row>
          </Content>
          <Footer style={{textAlign: "center"}}>Designed by Group x in Software Engineering Foundation</Footer>
        </Layout>
      );
    }
  }
    
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
        ReactDOM.render(<LoginApp  alertShow={true} alertText={"用户名不能为空"} alertType={"error"}/> , 
        document.getElementById('root'));
      }
      else if (passwd.length === 0 ){
        ReactDOM.render(<LoginApp  alertShow={true} alertText={"密码不能为空"} alertType={"error"}/> , 
        document.getElementById('root'));
      }
      else if(userName.length > MAX_INFO_LEN){
        ReactDOM.render(<LoginApp  alertShow={true} alertText={"用户名溢出无效"} alertType={"error"}/> , 
        document.getElementById('root'));
      }
      else if(passwd.length > MAX_INFO_LEN){
        ReactDOM.render(<LoginApp  alertShow={true} alertText={"密码溢出无效"} alertType={"error"}/> , 
        document.getElementById('root'));
      }
      else{
        user.name = userName;
        user.passwd = passwd;
        user.login(passwd,save_passwd);
        // window.location.href = "/main";
      }
    }
    
    
class AlertMsg extends React.Component {
      destroyOnClose
      state = {
          visible: true,
      };
  
      handleClose = () => {
          ReactDOM.render(<LoginApp alertShow={false}/> , document.getElementById('root'));
      };
  
      render() {
          return(
          <div style={{width: "100%", textAlign:"center"}}>
              {this.state.visible ? (
                  <Alert message = {this.props.message}
                  type = {this.props.type}
                  closable
                  showIcon
                  afterClose={this.handleClose}
                  />
              ): null}
          </div>
          );
      }
    }