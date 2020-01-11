import React from 'react';
import ReactDOM from 'react-dom';
import { service } from '../main.js';
import { m, s, u, a} from '../../index.js'
import { user } from '../../objects/users.js';
import { Stock } from '../../objects/stock.js';
import { SetstockPage, GetstockPage } from '../../globals.js';
import { Row,Col,Carousel,Button, Card,Input } from 'antd';
import zhCN from 'antd/lib/locale-provider/zh_CN';
import moment from 'moment';
import 'moment/locale/zh-cn';
import 'antd/dist/antd.css';
import photo1 from './1.jpg'

const Search=Input.Search;
var current_page = GetstockPage();

export var per = [ 
    {name:'huawei',id:'001',price:'10.23',state:'on',up:'100',low:'0'}, 
    {name:'sumsung',id:'002',price:'1.24',state:'on',up:'100',low:'0'}
    ]; 
    
export var page_set = [];
var firstTime = 0;
export class StockText extends React.Component{
    
    /*componentDidMount() {
        if(firstTime===0){
            Onload(10*(current_page-1),10*current_page - 1);
            firstTime =1;
        }
    }*/

    /*render() {
        const { date } = {date:null};
        return (
          <LocaleProvider locale={zhCN}>
            <div style={{ width: 400, margin: '100px auto' }}>
              <DatePicker onChange={this.handleChange} />
              <div style={{ marginTop: 20 }}>
              <Alert message={`当前日期：${date ? date.format('YYYY-MM-DD') : '未选择'}`} type="success" />
              </div>
            </div>
          </LocaleProvider>
        );
      }*/

    render(){
    return (<div class="mainframe"style={{padding:"1px 16px 24px", height:"100%",background:"rgba(240,242,245)"}}>
    <br/>
    <div className="gutter-example" style={{marginLeft:"40px",marginRight:"40px"}}>
    <div>
      <img  src={photo1} class="initImg coverImg" style={{width: "100%"}}></img>
    </div>
    </div>

    <div><br/></div>

    <div className="gutter-example" style={{marginLeft:"40px",marginRight:"40px"}}>
                    <Card class="text-center" title="股票管理" bordered={false}>

                        <table class="table table-striped" id='stocktable'>
                        <tbody>
                        <tr>
                        <th>公司名称</th>
                        <th>代码</th>
                        <th>价格</th>
                        <th>状态</th>
                        <th>涨幅</th>
                        <th>跌幅</th>
                        <th>涨跌幅状态</th>
                        <th>中止</th>
                        <th>重启</th>
                        <th>详情</th>
                        </tr>
                        </tbody>
                        </table>
                        <br/>
                        <div class="container col-md-4 col-md-offset-4" style={{width:"100%"}}>
                        <ul class="pagination " style={{width:"100%"}} >
                        <li><a class="inactive" href="javascript:void(0)" id="pre" onClick={Pagechange.bind(this,"pre")}>«</a></li>
                        <li><a class="active" href="javascript:void(0)"   id="page-1" onClick={Pagechange.bind(this,"page-1")}>1</a></li>
                        <li><a class="inactive" href="javascript:void(0)" id="page-2" onClick={Pagechange.bind(this,"page-2")}>2</a></li>
                        <li><a class="inactive" href="javascript:void(0)" id="page-3" onClick={Pagechange.bind(this,"page-3")}>3</a></li>
                        <li><a class="inactive" href="javascript:void(0)" id="page-4" onClick={Pagechange.bind(this,"page-4")}>4</a></li>
                        <li><a class="inactive" href="javascript:void(0)" id="page-5" onClick={Pagechange.bind(this,"page-5")}>5</a></li>
                        <li><a class="inactive" href="javascript:void(0)" id="page-6" onClick={Pagechange.bind(this,"page-6")}>6</a></li>
                        <li><a class="inactive" href="javascript:void(0)" id="page-7" onClick={Pagechange.bind(this,"page-7")}>7</a></li>
                        <li><a class="inactive" href="javascript:void(0)" id="next" onClick={Pagechange.bind(this,"next")}>»</a></li>
                        </ul>
                        </div>
                        <br/>
                    </Card>
    </div>

    <div><br/></div>


    <div className="gutter-example" style={{marginLeft:"40px",marginRight:"40px"}}>
    <Card class="text-center" title="股票搜索" bordered={false}>

    <br/>
    <Row>
        <Col span={2}>
        </Col>
        <Col span={2} style={{paddingTop: "10px"}}>
            <label >股票名</label>
        </Col>
        <Col span={19}>
        <Search id='searchname' placeholder="输入需查询的股票名称" enterButton="Search" size="large" onSearch={search.bind(this, "words")}/>
        </Col>
    </Row>
    <br/><br/>
    <Row>
        <Col span={2}>
        </Col>
        <Col span={2} style={{paddingTop: "10px"}}>
            <label >股票代码</label>
        </Col>
        <Col span={19}>
        <Search id='searchcode' placeholder="输入需查询的股票代码" enterButton="Search" size="large" onSearch={search.bind(this,"id")}/>
        </Col>
    </Row>
    <br/><br/>
    {/*
    <div class="container col-md-12 col-md-pull-1">
    <form class="form-horizontal" role="form">
    <div class="form-group">
		<label for="lastname" class="col-md-2 control-label">股票名</label>
		<div class="container col-md-10">
            <div class="container col-md-10">
			<input type="text" class="form-control col-md-10" id="searchname" placeholder="请输入名字" name="Searname"></input>
            </div>
            <div class="form-group container col-md-2">
			<button type="button" class="btn btn-default" onClick={search.bind(this, "words")}>搜索</button>
		    </div>
		</div>
	</div>
	<div class="form-group">
		<label for="lastname" class="col-md-2 control-label">股票代码</label>
		<div class="container col-md-10">
            <div class="container col-md-10">
			<input type="text" class="form-control col-md-10" id="searchcode" placeholder="请输入代码" name="Searcode"></input>
            </div>
            <div class="form-group container col-md-2">
			<button type="button" class="btn btn-default" onClick={search.bind(this,"id")}> 搜索</button>
		    </div>
		</div>
        </div>
    </form>
    </div>*/}
    </Card>
    </div>

        

    </div>);}
}

function search(method,event){
    if(firstTime<=2){
        if(method === "words"){
            var string = document.getElementById("searchname").value;
            var new_string = "%";
            for(var i=0;i<string.length;i++){
                new_string += string.charAt(i);
                new_string += "%";
            }
            console.log(new_string);
            user.search("words", new_string);
        }
        else if(method === "id"){
            var id = document.getElementById("searchcode").value;
            var new_string = "%";
            for(var i=0;i<id.length;i++){
                new_string += id.charAt(i);
                new_string += "%";
            }
            console.log(new_string);
            user.search("id", new_string);
        }
    }
}

function restart(id,num,event){
    var row=document.getElementById(id);
    if(row.childNodes[3].innerHTML=='正在运行')
    {
        alert('Error : It is on now' );
    }
    else
    {
        modify_state(row.childNodes[1].innerHTML, 1);
        row.childNodes[3].innerHTML='正在运行';
        alert('restart success');
    }

}
function stop(id,num, event){
    var row=document.getElementById(id);
    if(row.childNodes[3].innerHTML=='已关闭')
    {
        
        alert('Error : It is off now' );
    }
    else
    {
        modify_state(row.childNodes[1].innerHTML, 0);
        row.childNodes[3].innerHTML='已关闭';
        alert('stop success');
    }

}

function detail(id, event){
    // global;
    var row=document.getElementById(id);
    var stock_id = row.childNodes[1].innerHTML;
    var stock_name = row.childNodes[0].innerHTML;
    var stock_price = row.childNodes[2].innerHTML;
    var stock_state = row.childNodes[3].innerHTML === "已关闭" ? 0: 1;
    var limit_State = row.childNodes[6].innerHTML === "待更新" ? 0: 1;
    console.log(stock_state);
    var ulm = row.childNodes[4].childNodes[0].innerHTML;
    var llm = row.childNodes[5].childNodes[0].innerHTML;
    var selected_stock = new Stock(stock_id, stock_name, stock_price, ulm, llm, stock_state);
    selected_stock.limit_state = limit_State;
    // m  = service.mainFrame;
    user.get_stock_detail(selected_stock);
}

function modify(id,type,num,event){
    var v=prompt("输入数值");
    if(parseFloat(v)>=0 && parseFloat(v)<=100){
        var row=document.getElementById(id);
        console.log(row);
        var id = row.childNodes[1].innerText;
        if(type==='up')
        {
            if(v===null){
                v = row.childNodes[4].childNodes[0].innerHTML;
            }
            row.childNodes[4].childNodes[0].innerHTML=v+' ';
            modify_limit(row.childNodes[1].innerHTML, parseFloat(v), parseFloat(row.childNodes[5].childNodes[0].innerHTML));
            
        }
        else if(type==='low')
        {
            if(v===null){
                v = row.childNodes[5].childNodes[0].innerHTML;
            }
            row.childNodes[5].childNodes[0].innerHTML=v+' ';
            modify_limit(row.childNodes[1].innerHTML, parseFloat(row.childNodes[4].childNodes[0].innerHTML), parseFloat(v))
        }
    }else{
        alert("invalid value");
    }
}

function Pagechange(location,event){
    user.arg = location;
    if(location!==null){
        var dest=document.getElementById(location);
        user.arg2 = dest.innerHTML;
    }
    PagechangeSub(location,null,event);
};

function PagechangeSub(location,offset,event){
    var capcity=10;
    var dest=document.getElementById(location);
    if(dest === null){
        return;
    }
    var destpage = dest.innerHTML;
    if(offset === null){
        current_page = destpage;
        console.log("A");
    }
    else{
        console.log("B");
        current_page = offset;
        console.log(offset);
        destpage = offset;
        document.getElementById(location).innerText = offset;
    }
    SetstockPage(current_page);
    if(location=='pre')
    {
        destpage=document.getElementById('page-4').innerHTML;
        destpage=parseInt(destpage)-7;
        if(destpage<=4) destpage=4;
        Setmiddle(destpage);
    }
    else if(location=='next')
    {
        destpage=document.getElementById('page-4').innerHTML;
        destpage=parseInt(destpage)+7;
        Setmiddle(destpage);
    }
    else
    {
        Unload();
        Onload((destpage-1)*capcity,destpage*capcity-1);
        Setmiddle(destpage);
    }

}

export function UpdatePage(){
    // var capcity=10;
    current_page = GetstockPage();
    // Onload((destpage-1)*capcity,destpage*capcity-1);
    //SetPer();
}

function Setmiddle(page)
{
    var max_page = parseInt(per.length / 10) + 1;
    var convert=new Array('pre','page-1','page-2','page-3','page-4','page-5','page-6','page-7','next');
    page=parseInt(page);
    if(page>=4)
    {
        var over_page = false;
        var head = 'page-';
        var base = page - 4;
        Setactive(convert[4]);
        for(var i=1;i<=7;i+=1){
            document.getElementById(head + i.toString()).innerHTML = base + i;
            if(base + i>max_page){
                document.getElementById(head + i.toString()).style.display = "none";
                over_page = true;
            }else{
                document.getElementById(head + i.toString()).style.display = "inline";
            }
        }
        if(over_page){
            document.getElementById("next").style.display = "none";
        }else{
            document.getElementById("next").style.display = "inline";
        }
    }
    else
    {
        var over_page = false;
        var head = 'page-';
        Setactive(convert[page]);
        for(var i=1;i<=7;i+=1){
            document.getElementById(head + i.toString()).innerHTML = i;
            if(i>max_page){
                document.getElementById(head + i.toString()).style.display = "none";
                over_page = true;
            }else{
                document.getElementById(head + i.toString()).style.display = "inline";
            }
        }
        if(over_page){
            document.getElementById("next").style.display = "none";
        }else{
            document.getElementById("next").style.display = "inline";
        }
    }
    if(max_page >= 7){
        document.getElementById(head + "1").style.marginLeft = "0px";
    }else{
        document.getElementById(head + "1").style.marginLeft = ((4-max_page)*40 + 20).toString() + "px";
    }
}


function Setactive(location)
{
    for(var i = 1; i<=7; i++){
        document.getElementById('page-'+i.toString()).setAttribute('class','inactive');
    }

    document.getElementById(location).setAttribute('class','active');
}

function getDataRow(s,i){ 
    var row = document.createElement('tr'); //创建行 
    //(i%2)==0?row.setAttribute('class','alt'):row.setAttribute('class','noalt');
    row.setAttribute('id','stockrow'+i);
    var nameCell = document.createElement('td'); //创建第一列name
    nameCell.innerHTML = s.stock_name; //填充数据 
    nameCell.style.paddingTop = "15px";
    row.appendChild(nameCell); //加入行 ，下面类似 
    var idCell = document.createElement('td');//创建第二列id
    idCell.innerHTML = s.stock_id; 
    idCell.style.paddingTop = "15px";
    row.appendChild(idCell); 
    var priceCell = document.createElement('td');//创建第3列price
    priceCell.innerHTML = s.stock_price;
    priceCell.style.paddingTop = "15px"; 
    row.appendChild(priceCell); 
    var stateCell = document.createElement('td');//创建第4列state
    stateCell.style.paddingTop = "15px";
    stateCell.innerHTML = s.stock_state === 0 ? "已关闭" : "正在运行"; 
    row.appendChild(stateCell); 
    var upCell = document.createElement('td');//创建第5列up
    var celltext=document.createElement('span');
    celltext.innerHTML = s.upper_limit+' '; 
    celltext.style.paddingTop = "5px";
    celltext.setAttribute("class", "col-md-5");
    upCell.appendChild(celltext);
    
    row.appendChild(upCell); 
    var btnmodify = document.createElement('button'); 
    btnmodify.setAttribute('class',"ant-btn ant-btn-secondary");
    //btnmodify.setAttribute('id',i);
    //btnmodify.setAttribute('class',"ant-btn ant-btn-secondary ant-btn-circle ant-btn-icon-only");
    //var i=document.createElement('i');icon.setAttribute('class',"action action-edit");
    //btnmodify.setAttribute('icon',"search");
    btnmodify.innerHTML='修改';
    //删除操作 
    btnmodify.onclick=modify.bind(this,'stockrow'+i,'up', i);
    upCell.append(btnmodify)
    var lowCell = document.createElement('td');//6 low
    var celltext=document.createElement('span');
    celltext.innerHTML = s.lower_limit+' '; 
    celltext.setAttribute("class", "col-md-5");
    celltext.style.paddingTop = "5px";
    lowCell.appendChild(celltext);
    row.appendChild(lowCell); 
    var btnmodify = document.createElement('button'); 
    //btnmodify.setAttribute('id',i);
    btnmodify.setAttribute('class',"ant-btn ant-btn-secondary");
    btnmodify.innerHTML='修改';
    //删除操作 
    btnmodify.onclick=modify.bind(this,'stockrow'+i,'low', i);
    lowCell.append(btnmodify)
    //到这里，json中的数据已经添加到表格中，下面为每行末尾添加删除按钮 
    
    var lstateCell = document.createElement('td');//创建第4列state
    lstateCell.style.paddingTop = "15px";
    lstateCell.innerHTML = s.limit_State === 0 ? "待更新" : "最新"; 
    row.appendChild(lstateCell); 
    var btnCell = document.createElement('td');//创建第四列，操作列 
    row.appendChild(btnCell); 
    var btnstop = document.createElement('button'); //7 stop
    //btnmodify.setAttribute('id',i);
    btnstop.setAttribute('class',"ant-btn ant-btn-secondary");
    btnstop.innerHTML='中止交易';
    //btnstop.setAttribute('value','中止交易'); 
    //删除操作 
    btnstop.onclick=stop.bind(this,'stockrow'+i,i);
    btnCell.appendChild(btnstop); //把删除按钮加入td，别忘了 
    var btnCell = document.createElement('td');//创建第四列，操作列 
    row.appendChild(btnCell); 
    var btnre = document.createElement('button'); 
    //btnmodify.setAttribute('id',i);
    btnre.setAttribute('class',"ant-btn ant-btn-secondary");
    //btnre.setAttribute('value','重启交易'); 
    btnre.innerHTML='重启交易';//8 restart
    //删除操作 
    btnre.onclick=restart.bind(this,'stockrow'+i,i);
    btnCell.appendChild(btnre); //把删除按钮加入td，别忘了
    var btnCell = document.createElement('td');//创建第四列，操作列 
    row.appendChild(btnCell); 
    var btndetail = document.createElement('button'); 
    btndetail.setAttribute('class',"ant-btn ant-btn-secondary");
    btndetail.innerHTML='详情';//8 restart
    //删除操作 
    btndetail.onclick=detail.bind(this,'stockrow'+i,i);
    btnCell.appendChild(btndetail); //把删除按钮加入td，别忘 
    return row; //返回tr数据   
}

    function Onload(start,end){ //显示 start<=i<=end   每页10个  0-9  10-19  
         var tbody = document.getElementById('stocktable').childNodes[0]; 
         console.log(per);
         for(var i = start;(i < per.length)&&(i<=end); i++){ //遍历一下json数据 
           var trow = getDataRow(per[i],i); //定义一个方法,返回tr数据 
           tbody.appendChild(trow); 
        }
        }

    function Unload(){ 
        var tbody = document.getElementById('stocktable').childNodes[0]; 
        var childs = tbody.childNodes; 
        for(var i = childs .length - 1; i >= 1; i--) {
        tbody.removeChild(childs[i]);
        }
    }

    function modify_limit(id, upper, lower){
        user.modify_limit(id, upper, lower);
    }

    function modify_state(id, state){
        user.modify_state(id, state);
    }

export function SetPer(data, location){
    per = data;
    Unload();
    Onload(10*(current_page-1),10*current_page - 1);
    PagechangeSub(location, user.arg2);
}
