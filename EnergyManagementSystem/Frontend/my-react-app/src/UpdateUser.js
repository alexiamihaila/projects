import React, { useEffect, useState } from "react";
import './Update.css'; 
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";

async function fetchDataFromServer(url) {
  try {
    const token = localStorage.getItem('accessToken');
    const response = await axios.get(url, {
      headers: {
        'Authorization': `Bearer ${token}`,
      },
    });

    return response.data;
  } catch (error) {
    console.error(error);
    return null;
  }
}

function UpdateUser() {
  const { userId } = useParams();
  const navigate = useNavigate();

  const [user, setUser] = useState({
    userId: userId,
    username: '',
    password: '',
    role: ''
  });

  useEffect(() => {
    async function fetchData() {
      try {
        const response = await fetchDataFromServer(`http://localhost:3003/springg-demo/admin/getUserWithId/${userId}`);
        setUser({
          ...user,
          username: response.username,
          password: response.password,
          role: response.role
        });
      } catch (error) {
        console.error(error);
      }
    }

    fetchData();
  }, [userId]);

  const handleSubmit = (e) => {
    e.preventDefault();

    axios.put(`http://localhost:3003/springg-demo/admin/users/updateUsers/${userId}`, user, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
      },
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
          <button className="btn btn-info">Update</button>
        </form>
      </div>
    </div>
  );
}

export default UpdateUser;
