import React from 'react';
import ReactDOM from 'react-dom';
import {Statistic, Row, Col, Card, Timeline, Icon, Calendar } from 'antd';
function onPanelChange(value, mode) {
    console.log(value, mode);
}
class DashText extends React.Component {
    render() {
        return (
            <div style={{backgroundColor: "#ffffff"}}>
                <Row>
                    <Col span={8} style={{margin: "12px"}}>
                        <Card title="系统通知">
                            <p style={{color: "#3c3c3c"}}>
                            <Icon type="notification"/><label>&nbsp;&nbsp;</label>
                                关于股票管理系统于2019-1-23的维护通知
                            </p>
                            <p style={{color: "#3c3c3c"}}>
                            <Icon type="notification"/><label>&nbsp;&nbsp;</label>
                                周股市总结
                            </p>
                            <p style={{color: "#3c3c3c"}}>
                            <Icon type="notification"/><label>&nbsp;&nbsp;</label>
                                今日股市总结
                            </p>
                            <p style={{color: "red"}}>
                                <Icon type="notification"/><label>&nbsp;&nbsp;</label>
                                [热]新管理系统上线！
                            </p>
                            
                        </Card>
                        <Card title="捷径" style={{marginTop: "24px", paddingLeft: "12px", display: "inline-blocks"}}>
                            <Row>
                            <Col span={6}>
                            <div style={{color: "#7c7c7c"}}>
                            <Icon type="mail" style={{fontSize: "48px"}}/><br></br>
                            <label>&nbsp;&nbsp;邮箱</label>    
                            </div>
                            </Col>
                            <Col span={6}>
                            <div style={{color: "#7c7c7c"}}>
                            <Icon type="video-camera" style={{fontSize: "48px"}}/><br></br>
                            <label>&nbsp;&nbsp;视频</label>    
                            </div>
                            </Col>
                            <Col span={6}>
                            <div style={{color: "#7c7c7c"}}>
                            <Icon type="cloud" style={{fontSize: "48px"}}/><br></br>
                            <label>&nbsp;&nbsp;&nbsp;&nbsp;云</label>    
                            </div>
                            </Col>
                            <Col span={6}>
                            <div style={{color: "#7c7c7c"}}>
                            <Icon type="contacts" style={{fontSize: "48px"}}/><br></br>
                            <label>&nbsp;联系人</label>    
                            </div>
                            </Col>
                            </Row>
                        </Card>
                    </Col>
                    <Col span={7} style={{margin: "12px"}}>
                        <Card title="任务线" >
                            <Timeline>
                                <Timeline.Item color="green">开始研发后台管理系统 2019-05-01</Timeline.Item>
                                <Timeline.Item color="green">继续研发后台管理系统 2019-05-05</Timeline.Item>
                                <Timeline.Item color="red">
                                <p>还在研发后台管理系统 修复bugs</p>
                                <p>还在研发后台管理系统 如何串联各组件</p>
                                <p>还在研发后台管理系统 ReactJS太难了</p>
                                <p> 2019-05-21</p>
                                </Timeline.Item>
                                <Timeline.Item>
                                <p>准备第一次上交作业</p>
                                <p>准备第二次测试</p>
                                <p>准备通宵 2019-06-01</p>
                                </Timeline.Item>
                            </Timeline>
                        </Card>
                    </Col>
                    <Col span={7} style={{margin: "12px"}}>
                        <Card title="日历">
                            <Calendar fullscreen={false} onPanelChange={onPanelChange} />
                        </Card> 
                    </Col>
                </Row>
            
 
            
            </div>
        );
    }
}

export default DashText;