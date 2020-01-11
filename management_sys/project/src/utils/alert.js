import {Alert} from 'antd';
import React from 'react';
import ReactDOM from 'react-dom';
import { setShowAlert, getShowAlert } from '../globals.js';

export class AlertMsg extends React.Component {
    destroyOnClose
    state = {
        visible: true,
    };

    handleClose = () => {
        this.setState({visible: false});
        
    };

    render() {
        return(
        <div>
            {this.state.visible ? (
                <Alert message = "asasa"
                type = "error"
                closable
                afterClose={this.handleClose}
                />
            ): null}
        </div>
        );
    }
}