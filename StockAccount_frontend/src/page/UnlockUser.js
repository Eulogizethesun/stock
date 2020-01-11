import React from "react";

import { Radio, Input, Row, Col, message } from 'antd';

import LegalUserForm from './form/LegalUserForm'
import PersonalUserForm from './form/PersonalUserForm'

import {api} from '../web.config.json'

const Search = Input.Search;

class UnlockUser extends React.Component {
    state = {
        status: 0,
        user: {},
        type: 'Personal'
    }
    handleTypeChange = (e) => {
        this.setState({
            type: e.target.value
        });
    }
    findPersonalUser = (account_id) => {
        const data = new URLSearchParams();
        data.append('Id_num', account_id);
        fetch(api + '/personal_user_find_by_banker', {
            body: data,
            method: "POST",
            credentials: 'include',
            mode: "cors"
        })
            .then(data => data.json())
            .then(json => {
                if (json.account_id !== null){
                    this.setState({
                        status: 1,
                        user: json
                    })
                }
                else{
                    message.error('User ID not found');
                }
            })
            .catch(err => {
                message.error(err.message)
                console.error(err)
            })
    }
    findLegalUser = (account_id) => {
        const data = new URLSearchParams();
        data.append('legal_num', account_id);
        fetch(api + '/legal_user_find_by_banker', {
            body: data,
            method: "POST",
            credentials: 'include',
            mode: "cors"
        })
            .then(data => data.json())
            .then(json => {
                if (json.account_id !== null) {
                    this.setState({
                        status: 2,
                        user: json
                    })
                }
                else{
                    message.error('User ID not found');
                }
            })
            .catch(err => {
                message.error(err.message)
                console.error(err)
            })
    }
    unlockUser = (values) => {
        const id = this.state.status === 1?values["id_num"]:values["legal_num"];
        const data = new URLSearchParams();
        if (this.state.status === 1) {
            data.append('id_num', id);
        }
        else if (this.state.status === 2) {
            data.append('legal_num', id);
        }
        var method ;
        if (this.state.status === 1) {
            method = '/personal_user_unfreeze_by_banker';
        }
        else if (this.state.status === 2) {
            method = '/legal_user_unfreeze_by_banker';

        }
        fetch(api + method, {
            body: data,
            method: "POST",
            credentials: 'include',
            mode: "cors"
        })
        .then(data => data.json())
        .then(json => {
            if (json.status === 0){
                message.success(json.message)
                this.setState({
                    status: 0,
                    user: {}
                })
            }
            else{
                message.error(json.message)
            }
        })
        .catch(err => {
            console.error(err)
            message.error(err.message)
        })
    }
    render(){
        let type = this.state.type;
        const form = ()=>{
            if (this.state.status === 1){
                return <PersonalUserForm handle={this.unlockUser} isSubmit={true} button={'Unlock'} values={this.state.user} isChange={false} />
            }
            else if (this.state.status === 2){
                return <LegalUserForm handle={this.unlockUser} isSubmit={true} button={'Unlock'} values={this.state.user} isChange={false} />
            }
        }
        return (
            <div>
                <Row>
                    <Radio.Group value={type} defaultValue="a" onChange={this.handleTypeChange}>
                        <Radio.Button value='Personal'>个人用户</Radio.Button>
                        <Radio.Button value='Legal'>法人用户</Radio.Button>
                    </Radio.Group>
                </Row>
                <Row>
                    <Col span={12}>
                        <Search
                            placeholder={this.state.type === "Personal" ? "input personal ID" : "input legal ID"}
                            enterButton="Search"
                            size="large"
                            // onSearch={value => {this.findUser(value)}}
                            onSearch={value => {this.state.type === "Personal" ? this.findPersonalUser(value) : this.findLegalUser(value)}}
                        />
                    </Col>
                </Row>
                <Row>
                    <Col span={12} offset={6}>
                        {form()}
                    </Col>
                </Row>
            </div>
        )
    }
}

export default UnlockUser;