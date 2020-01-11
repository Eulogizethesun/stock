import { Layout, Menu, Breadcrumb, Icon, Affix, BackTop } from 'antd';
import { StockText } from './frames/stockFrame.js';
import { UserText } from './frames/userFrame.js'
import { AboutText } from './frames/aboutFrame.js'
import { MainText } from './frames/mainFrame.js'
import { DetailText } from './frames/detailFrame.js'
import async from '../async'
import React from 'react';
import ReactDOM from 'react-dom';
import {
  // BrowserRouter as Router,
  Route,
  // Switch,
  Link,
  NavLink,
  Redirect
} from 'react-router-dom'
import { BrowserRouter } from 'react-router-dom';
import { user } from '../objects/users.js';
import LoginApp from '../login.js';
import DashText from './frames/dashFrame.jsx';
import FeedBack from './frames/feedbackFrame.js';
const { Header, Content, Footer, Sider } = Layout;
const { SubMenu } = Menu;

const map = {'/dashboard': '1', '/stock': '2','/detail': '2','/auth': '5','/user': '3'}

// let StockText = async(() => import("./frames/stockFrame"));

export default class SiderDemo extends React.Component {
  state = {
    collapsed: false,
  };

  onCollapse = collapsed => {
    console.log(collapsed);
    this.setState({ collapsed });
  };

  render() {
    return (
      <BrowserRouter>
      <Redirect path="/login" exact={true} to={this.props.directTo}/>
      <Layout style={{ minHeight: '100vh' }}>
        <Sider collapsible collapsed={this.state.collapsed} onCollapse={this.onCollapse}>
          <Affix target={() => this.container}>
          <div className="logo" />
          
          <Menu theme="dark" defaultSelectedKeys={[map[this.props.directTo]]} mode="inline">
            <Menu.Item key="1"><Icon type="dashboard" />
              <span style={{fontFamily: "Tahoma, Helvetica, Arial,'Microsoft YaHei',sans-serif"}}>工作面板</span>
              <NavLink to='/dashboard'></NavLink></Menu.Item>
            <SubMenu
              key="sub1"
              title={
                <span>
                  <Icon type="audit" />
                  <span>业务管理</span>
                </span>
              }
            >
              <Menu.Item key="2" onClick={user.load_all_stock}><Icon type="euro" />
              <span style={{fontFamily: "Tahoma, Helvetica, Arial,'Microsoft YaHei',sans-serif"}}>股票管理</span>
              <NavLink to='/stock'></NavLink></Menu.Item>
              {user.auth === 2 ?  <Menu.Item key="5"  onClick={user.load_all_user} ><Icon type="usergroup-add" />
              <span style={{fontFamily: "Tahoma, Helvetica, Arial,'Microsoft YaHei',sans-serif"}}>员工管理</span>
              <NavLink to='/auth'></NavLink></Menu.Item> : null}
            </SubMenu>
            <SubMenu
              key="sub2"
              title={
                <span>
                  <Icon type="user" />
                  <span>个人信息</span>
                </span>
              }
            >
              <Menu.Item key="3"><Icon type="safety" />
              <span style={{fontFamily: "Tahoma, Helvetica, Arial,'Microsoft YaHei',sans-serif"}}>密码修改</span>
              <NavLink to='/user'></NavLink></Menu.Item>
            </SubMenu>
            <Menu.Item key="4">
              <Icon type="file" />
              <span>后台反馈</span>
              <NavLink to='/feedback'></NavLink>
            </Menu.Item>
            <Menu.Item key="6" onClick={logout}>
              <Icon type="logout" />
              <span>安全退出</span>
              
            </Menu.Item>
          </Menu>
          </Affix>
        </Sider>
        <Layout>
          <Header style={{ background: '#fff', padding: "15px", display: "inline" }}><h4><Icon type="tag"/>&nbsp;&nbsp;股票交易管理系统</h4></Header>
          <Content style={{  margin: '0 16px', scrollBehavior: "auto" }} id="ms-main-content">
            <Breadcrumb style={{ margin: '16px 0' }}>
              <Breadcrumb.Item>Administrator</Breadcrumb.Item>
              <Breadcrumb.Item>{this.props.user.name}</Breadcrumb.Item>
            </Breadcrumb>
            
            <div style={{ padding: 24, background: '#ffffff', minHeight: 360 }}>
              <Route path='/dashboard' exact component={DashText}></Route>
              <Route path='/stock' exact component={StockText}></Route>
              <Route path='/user' exact component={UserText}></Route>
              <Route path='/auth' exact component={AboutText}></Route>
              <Route path='/detail' exact component={DetailText}></Route>
              <Route path='/feedback' exact component={FeedBack}></Route>
            </div>
          </Content>
          <Footer style={{ textAlign: 'center' }}>Created by Group x</Footer>
        </Layout>
      </Layout>
      <div>
        <BackTop />
      </div>
      </BrowserRouter>
    );
  }
}


function logout() {

  user.logout();
}