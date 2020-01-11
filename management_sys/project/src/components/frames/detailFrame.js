import React from 'react';
import ReactDOM from 'react-dom';
import { service } from '../main.js';
import { m, s, u, a} from '../../index.js'
import { Stock } from '../../objects/stock.js'
import { user } from '../../objects/users.js'
import { StockText, UpdatePage } from './stockFrame.js';
import { MainText } from './mainFrame.js';
import SiderDemo from '../mainLayout.js';
import { Descriptions, Badge, Button, Icon, Row, Col,Card } from 'antd';


function getInstructionList(id, instructionList){
    var plist = []
    for(var i=0; i< instructionList.length; i++){
        var element2 = (<div style={{padding:"0px"}}>
            <Card bordered={false}>
                <Row>
                    <Col>
                         <span> <b>No. {instructionList[i].inst_no}</b></span>
                    </Col>
                </Row>
                <Row>
                    <Col span={8}>
                        <span>数量: {instructionList[i].inst_num}</span>
                    </Col>
                    <Col span={8}>
                        <span>价格: {instructionList[i].price}</span>
                    </Col>
                    <Col span={8}>
                        <span>购买人: {instructionList[i].user_id}</span>
                    </Col>
                </Row><br></br>
                <Row>
                    <Col>
                         <span>时间: {instructionList[i].op_time}</span>
                    </Col>
                </Row>

            </Card> 
        </div>); 
        plist.push(element2)
    }
    return plist;

}

function restorePage(){
    // user.load_all_stock();
    ReactDOM.render(<SiderDemo user={user} directTo="/stock"/> , document.getElementById('root'));
    user.load_all_stock();
}

 export class DetailText extends React.Component{
    render() {
    var element = ( 
    <div class="mainframe"style={{padding:"1px 16px",height:"100%"}}>
    <Descriptions title="股票详情" bordered>
        <Descriptions.Item label="股票名称">{user.tmpStock.stock_name}</Descriptions.Item>
        <Descriptions.Item label="股票代码">{user.tmpStock.stock_id}</Descriptions.Item>
        <Descriptions.Item label="涨跌幅监控">{user.tmpStock.lower_limit}/{user.tmpStock.upper_limit}
        {user.tmpStock.limit_state === 1 ? null :"（修改处理中）"}</Descriptions.Item>
        <Descriptions.Item label="最新成交价">{user.tmpStock.latest_price}</Descriptions.Item>
        <Descriptions.Item label="最新成交量" span={3}>
        {user.tmpStock.latest_num}
        </Descriptions.Item>
        <Descriptions.Item label="交易状态" span={3}>
        {user.tmpStock.state==='off' ?
        <Badge status="error" text="关闭" />
        : <Badge status="processing" text="开启" />}
        </Descriptions.Item>
        <Descriptions.Item label="买指令" span={1.5}>
        <div id="ms-detailed-buylist">{getInstructionList("ms-detailed-buylist",user.tmpStock.BuyInsts)}</div>
        </Descriptions.Item>
        <Descriptions.Item label="卖指令">
        <div id="ms-detailed-selllist">{getInstructionList("ms-detailed-selllist",user.tmpStock.SellInsts)}</div>
        </Descriptions.Item>
    </Descriptions>
    <br></br>
    <Button onClick={restorePage} type="normal"><Icon type="left"/>返回</Button>
    </div>);

    return element;
  }
}
