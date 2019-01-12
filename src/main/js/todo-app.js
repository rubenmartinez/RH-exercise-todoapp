'use strict';

const React = require('react');
const ReactDOM = require('react-dom');

const ListGroup = require('react-bootstrap').ListGroup;
const ListGroupItem = require('react-bootstrap').ListGroupItem;
const FormControl = require('react-bootstrap').FormControl;
const FormGroup = require('react-bootstrap').FormGroup;
const ControlLabel = require('react-bootstrap').ControlLabel;
const Button = require('react-bootstrap').Button;
const InputGroup = require('react-bootstrap').InputGroup;
const Checkbox = require('react-bootstrap').Checkbox;

class TodoApp extends React.Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
    }
}

ReactDOM.render(
    <TodoApp />,
    document.getElementById('todoAppReact')
);
