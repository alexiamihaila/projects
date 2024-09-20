import { React, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function AddUser() {
  const [user, setUser] = useState({
    username: '',
    password: '',
    role: ''
  });

  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();

    // Retrieve token from local storage
    const token = localStorage.getItem('accessToken');

    // Include the token in the request headers
    axios.post('http://localhost:3003/springg-demo/admin/users/addUser', user, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    })
      .then(res => {
        navigate('/admin/adminPage');
      })
      .catch(error => console.log(error));
  };

  return (
    <div className="update-user-container">
      <div className="form-container">
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="username">Username</label>
            <input
              type="text"
              name="username"
              className="form-control"
              placeholder="Enter Username"
              value={user.username} onChange={e => setUser({ ...user, username: e.target.value })} />
          </div>
          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input
              type="text"
              name="password"
              className="form-control"
              placeholder="Enter Password"
              value={user.password} onChange={e => setUser({ ...user, password: e.target.value })} />
          </div>
          <div className="form-group">
            <label htmlFor="role">Role</label>
            <input
              type="text"
              name="role"
              className="form-control"
              placeholder="Enter Role"
              value={user.role} onChange={e => setUser({ ...user, role: e.target.value })} />
          </div>
          <button className="btn btn-info">Save</button>
        </form>
      </div>
    </div>
  );
}

export default AddUser;
