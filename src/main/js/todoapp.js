'use strict';

const React = require('react');
const ReactDOM = require('react-dom');

// XXX
const ListGroup = require('react-bootstrap').ListGroup;
const ListGroupItem = require('react-bootstrap').ListGroupItem;
const FormControl = require('react-bootstrap').FormControl;
const FormGroup = require('react-bootstrap').FormGroup;
const ControlLabel = require('react-bootstrap').ControlLabel;
const Button = require('react-bootstrap').Button;
const InputGroup = require('react-bootstrap').InputGroup;
const Checkbox = require('react-bootstrap').Checkbox;

const ESCAPE_KEY = 27;
const ENTER_KEY = 13;

class TodoApp extends React.Component {

    constructor(props) {
        super(props);
        
        this.state = {todoItems: [ {id:1, title:"Todo1", completed: false}, {id:2, title:"Todo2", completed: false} ]};
    }

    componentDidMount() {
    }
    
    render() {
    	var todoItems = this.state.todoItems.map( todo => <TodoItem key={todo.id} todo={todo}/> );
    	
    	var header = (
    		<header className="header">
    			<h1>Todos</h1>
				<input className="new-todo" placeholder="Is there anything else to do?"
				value={this.state.newTodo}
				onKeyDown={this.handleNewTodoKeyDown}
				onChange={this.handleChange}
				autoFocus={true}
				/>    			
    		</header>
			);
    	
    	return (
    		<div>
    			{header}
    			<ul className="todo-list">
    				{todoItems}
    			</ul>    			
    		</div>
    		);
    }
    	/*var todoItems = <TodoItem
							key={todo.id}
							todo={todo}
							onToggle={this.toggle.bind(this, todo)}
							onDestroy={this.destroy.bind(this, todo)}
							onEdit={this.edit.bind(this, todo)}
							editing={this.state.editing === todo.id}
							onSave={this.save.bind(this, todo)}
							onCancel={this.cancel}
							
    					/>;
    	
    	return
    	    <div>
				<header className="header">
					<h1>Todos</h1>
					<input
						className="new-todo"
						placeholder="Please, enter new task"
						value={this.state.newTodo}
						onKeyDown={this.handleNewTodoKeyDown}
						onChange={this.handleChange}
						autoFocus={true}
					/>
				</header>
				<section className="main">
					<input
						id="toggle-all"
						className="toggle-all"
						type="checkbox"
						onChange={this.toggleAll}
						checked={activeTodoCount === 0}
					/>
					<label
						htmlFor="toggle-all"
					/>
					<ul className="todo-list">
						{todoItems}
					</ul>
				</section>
			    <footer class="info">
			    	<p>Double-click to edit a todo</p>
			    </footer>    
			</div>;
    }
    */
}

class TodoItem extends React.Component {
    constructor(props) {
        super(props);
        
        this.state = { editing: false };
    }

    componentDidMount() {
    }
    
	classNames () {
		var classes = [];

		for (var i = 0; i < arguments.length; i++) {
			var arg = arguments[i];
			if (!arg) continue;

			var argType = typeof arg;

			if (argType === 'string' || argType === 'number') {
				classes.push(arg);
			} else if (Array.isArray(arg) && arg.length) {
				var inner = classNames.apply(null, arg);
				if (inner) {
					classes.push(inner);
				}
			} else if (argType === 'object') {
				for (var key in arg) {
					if (hasOwn.call(arg, key) && arg[key]) {
						classes.push(key);
					}
				}
			}
		}

		return classes.join(' ');
	}
    
    render() {
        return (
            <li className={this.classNames({
                completed: this.props.todo.completed,
                editing: this.props.editing
            })}>
            <div className="view">
                    <input
                        className="toggle"
                        type="checkbox"
                        checked={this.props.todo.completed}
                        onChange={this.props.onToggle}
                    />
                    <label onDoubleClick={this.handleEdit}>
                        {this.props.todo.title}
                    </label>
                    <button className="destroy" onClick={this.props.onDestroy} />
                </div>
                <input
                    ref="editField"
                    className="edit"
                    value={this.state.editText}
                    onBlur={this.handleSubmit}
                    onChange={this.handleChange}
                    onKeyDown={this.handleKeyDown}
                />
            </li>        		
        		
        );
    }
        		/*
            <li className={classNames({
                completed: this.props.todo.completed,
                editing: this.props.editing
            })}>
                <div className="view"> TODO ITEM! XXX
                    <input
                        className="toggle"
                        type="checkbox"
                        checked={this.props.todo.completed}
                        onChange={this.props.onToggle}
                    />
                    <label onDoubleClick={this.handleEdit}>
                        {this.props.todo.title}
                    </label>
                    <button className="destroy" onClick={this.props.onDestroy} />
                </div>
                <input
                    ref="editField"
                    className="edit"
                    value={this.state.editText}
                    onBlur={this.handleSubmit}
                    onChange={this.handleChange}
                    onKeyDown={this.handleKeyDown}
                />
            </li>
            */
}


ReactDOM.render(
    <TodoApp />,
    document.getElementById('todoAppReact')
);
