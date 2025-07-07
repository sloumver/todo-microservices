import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { todoService } from '../services/api';

function TodoList() {
  const [todos, setTodos] = useState([]);
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [editingTodo, setEditingTodo] = useState(null);
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const username = localStorage.getItem('username');

  useEffect(() => {
    fetchTodos();
  }, []);

  const fetchTodos = async () => {
    try {
      const response = await todoService.getTodos();
      setTodos(response.data.content || []);
    } catch (err) {
      setError('Failed to fetch todos');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    const todoData = {
      title,
      description,
      status: 'PENDING'
    };

    try {
      if (editingTodo) {
        await todoService.updateTodo(editingTodo.id, todoData);
      } else {
        await todoService.createTodo(todoData);
      }
      setTitle('');
      setDescription('');
      setEditingTodo(null);
      fetchTodos();
    } catch (err) {
      setError('Failed to save todo');
    }
  };

  const handleEdit = (todo) => {
    setEditingTodo(todo);
    setTitle(todo.title);
    setDescription(todo.description);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this todo?')) {
      try {
        await todoService.deleteTodo(id);
        fetchTodos();
      } catch (err) {
        setError('Failed to delete todo');
      }
    }
  };

  const handleStatusToggle = async (todo) => {
    const updatedTodo = {
      ...todo,
      status: todo.status === 'PENDING' ? 'COMPLETED' : 'PENDING'
    };
    
    try {
      await todoService.updateTodo(todo.id, updatedTodo);
      fetchTodos();
    } catch (err) {
      setError('Failed to update todo status');
    }
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate('/login');
  };

  return (
    <div>
      <div className="header">
        <h1>Todo List</h1>
        <button className="btn logout-btn" onClick={handleLogout}>
          Logout
        </button>
      </div>
      
      <div className="container">
        <p>Welcome, {username}!</p>
        
        <div className="todo-form">
          <h3>{editingTodo ? 'Edit Todo' : 'Add New Todo'}</h3>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label>Title:</label>
              <input
                type="text"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                required
              />
            </div>
            <div className="form-group">
              <label>Description:</label>
              <input
                type="text"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              />
            </div>
            {error && <p className="error-message">{error}</p>}
            <button type="submit" className="btn">
              {editingTodo ? 'Update Todo' : 'Add Todo'}
            </button>
            {editingTodo && (
              <button
                type="button"
                className="btn btn-secondary"
                onClick={() => {
                  setEditingTodo(null);
                  setTitle('');
                  setDescription('');
                }}
                style={{ marginLeft: '10px' }}
              >
                Cancel
              </button>
            )}
          </form>
        </div>

        <div className="todos-list">
          {todos.length === 0 ? (
            <p>No todos yet. Create your first todo!</p>
          ) : (
            todos.map((todo) => (
              <div key={todo.id} className="todo-item">
                <h3>{todo.title}</h3>
                <p>{todo.description}</p>
                <p>Status: <strong>{todo.status}</strong></p>
                <div className="todo-actions">
                  <button
                    className="btn btn-secondary"
                    onClick={() => handleStatusToggle(todo)}
                  >
                    Mark as {todo.status === 'PENDING' ? 'Completed' : 'Pending'}
                  </button>
                  <button
                    className="btn"
                    onClick={() => handleEdit(todo)}
                  >
                    Edit
                  </button>
                  <button
                    className="btn btn-danger"
                    onClick={() => handleDelete(todo.id)}
                  >
                    Delete
                  </button>
                </div>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  );
}

export default TodoList;