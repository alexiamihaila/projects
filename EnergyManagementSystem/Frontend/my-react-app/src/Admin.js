import React, { useState, useEffect } from "react";
import { Link } from 'react-router-dom';
import axios from 'axios';
import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

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

function Admin() {
  const [device, setDevice] = useState([]);
  const [user, setUser] = useState([]);

  let stompClient;

  useEffect(() => {
    async function fetchData() {
      try {
        const response = await fetchDataFromServer("http://localhost:3003/spring-demo/admin/devices/allDevices");
        setDevice(response);
      } catch (error) {
        console.error(error);
      }
    }

    fetchData();
  }, []);

  useEffect(() => {
    async function fetchUsers() {
      try {
        const response = await fetchDataFromServer("http://localhost:3003/springg-demo/admin/users/allUsers");
        setUser(response);
      } catch (error) {
        console.error(error);
      }
    }

    fetchUsers();
  }, []);

  const handleDeleteUser = async (itemId) => {
    try {
      const token = localStorage.getItem('accessToken');

      const response = await axios.delete(`http://localhost:3003/springg-demo/admin/users/deleteUser/${itemId}`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });

      if (response.status === 200) {
        const updatedUsers = user.filter(user => user.id_user !== itemId);
        setUser(updatedUsers);

        const updatedDevices = await fetchDataFromServer("http://localhost:3003/spring-demo/admin/devices/allDevices");
        setDevice(updatedDevices);
      } else {
        // Handle error response
      }
    } catch (error) {
      console.error(error);
    }
  };

  const handleDeleteDevice = async (itemId) => {
    try {
      const token = localStorage.getItem('accessToken');

      const response = await axios.delete(`http://localhost:3003/spring-demo/admin/devices/deleteDevice/${itemId}`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });

      if (response.status === 200) {
        const updatedDevices = device.filter(device => device.id_device !== itemId);
        setDevice(updatedDevices);
      } else {
        // Handle error response
      }
    } catch (error) {
      console.error(error);
    }
  };



  return (
    <div id="content">

      <div className="button-container">
        <Link to={'/chatAdmin'}>
          <button className="button"> Chat </button>
        </Link>
      </div>
      <h1 className="text-2xl mb-4 mt-8 text-center">Admin Page</h1>
      <div className="button-container">
        <Link to={'/admin/addDevice'}>
          <button className="button"> Add Device</button>
        </Link>
      </div>
      <section id="cart">
        <table width="100%">
          <thead>
            <tr>
              <td>Description</td>
              <td>Address</td>
              <td>Maximum consumption/hour</td>
              <td>Owner of device</td>
              <td></td>
              <td></td>
            </tr>
          </thead>
          <tbody>
            {device.map((device, index) => (
              <tr key={index}>
                <td>{device.description}</td>
                <td>{device.address}</td>
                <td>{device.consumption}</td>
                <td>{device.id_user}</td>
                <td>
                  <button
                    className="button"
                    onClick={() => handleDeleteDevice(device.id_device)}
                  >
                    Delete
                  </button>
                </td>
                <td>
                  <Link to={`/admin/updateDevice/${device.id_device}`}>
                    <button className="button"> Edit </button>
                  </Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>
  
      <div className="button-container">
        <Link to={'/admin/addUser'}>
          <button className="button"> Add User</button>
        </Link>
      </div>
      <section id="cart">
        <table width="100%">
          <thead>
            <tr>
              <td>ID</td>
              <td>Username</td>
              <td>Password</td>
              <td>Role</td>
              <td></td>
              <td></td>
            </tr>
          </thead>
          <tbody>
            {user.map((user, index) => (
              <tr key={index}>
                <td>{user.id_user}</td>
                <td>{user.username}</td>
                <td>{user.password}</td>
                <td>{user.role}</td>
                <td>
                  <button
                    className="button"
                    onClick={() => handleDeleteUser(user.id_user)}
                  >
                    Delete
                  </button>
                </td>
                <td>
                  <Link to={`/admin/updateUser/${user.id_user}`}>
                    <button className="button"> Edit </button>
                  </Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>
    </div>
  );
}

export default Admin;
