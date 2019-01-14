'use strict';

const TODOS_ENDPOINT="/api/v1/usertodos";

var filters = {
	all : function(todos) {
		return todos;
	},
	active : function(todos) {
		return todos.filter(function(todo) {
			return !todo.completed;
		});
	},
	completed : function(todos) {
		return todos.filter(function(todo) {
			return todo.completed;
		});
	}
};

var app = new Vue({
	el : '#todoAppVue',

	// app initial state
	data : {
		todos : [],
		newTodoTitle : '',
		textFilter: '',
		editedTodo : null,
		visibility : 'all'
	},
	
	mounted: function()	 {
        axios.get(TODOS_ENDPOINT).then((response) => {
        	this.todos = response.data;
        });
	},

	computed : {
		filteredTodos : function() {
			return filters[this.visibility](this.todos).filter( todo => todo.title.includes(this.textFilter) );
		},
		remaining : function() {
			return filters.active(this.todos).length;
		},
		allDone : {
			get : function() {
				return this.remaining === 0;
			},
			set : function(value) {
				this.filteredTodos.forEach(function(todo) {
					todo.completed = value;
				});
			}
		}
	},

	methods : {
		pluralize : function(word, count) {
			return word + (count === 1 ? '' : 's');
		},
		
		addTodo : function() {
			var value = this.newTodoTitle && this.newTodoTitle.trim();
			if (!value) {
				return;
			}
			
			var newTodo = {
				title : value,
				completed : false
			};
			
			axios.post(TODOS_ENDPOINT, newTodo).then((response) => {
				this.todos.push(response.data);
				this.newTodoTitle = '';
	        });
		},

		removeTodo : function(todo) {
			axios.delete(TODOS_ENDPOINT + "/" + todo.id).then((response) => {
				var index = this.todos.indexOf(todo);
				this.todos.splice(index, 1);
	        });
		},

		editTodo : function(todo) {
			this.beforeEditCache = todo.title;
			this.editedTodo = todo;
		},

		doneEdit : function(todo) {
			if (!this.editedTodo) {
				return;
			}
			this.editedTodo = null;
			todo.title = todo.title.trim();
			if (!todo.title) {
				this.removeTodo(todo);
			}
			else {
				axios.put(TODOS_ENDPOINT + "/" + todo.id, todo);
			}
		},

		cancelEdit : function(todo) {
			this.editedTodo = null;
			todo.title = this.beforeEditCache;
		},
		
		switchCompleted: function(todo) {
			axios.put(TODOS_ENDPOINT + "/" + todo.id, todo);
			todo.completed = !todo.completed;
		},

		removeCompleted : function() {
			filters.completed(this.todos).forEach( todo => this.removeTodo(todo) );
		},
		
		clearTextFilter : function() {
			this.textFilter = '';
		}
	},

	directives : {
		'todo-focus' : function(el, binding) {
			if (binding.value) {
				el.focus();
			}
		}
	}
});

var router = new Router();

['all', 'active', 'completed'].forEach(function (visibility) {
	router.on(visibility, function () {
		app.visibility = visibility;
	});
});

router.configure({
	notfound: function () {
		window.location.hash = '';
		app.visibility = 'all';
	}
});

router.init();
