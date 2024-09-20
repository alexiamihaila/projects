import React, { useState, useEffect } from "react";
import './User.css'
import axios from "axios";
import Stomp from "webstomp-client"; 
import SockJS from 'sockjs-client'; 
import { Link } from "react-router-dom";

async function fetchDataFromServer(url) {
  try {
    const token = localStorage.getItem('accessToken'); // Retrieve the token from storage
    const response = await fetch(url, {
      headers: {
        'Authorization': `Bearer ${token}`, // Include the token in the header
      },
    });

    if (!response.ok) {
      throw new Error(`Fetch error: ${response.status}`);
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error(error);
    return null;
  }
}

function User() {
  const [devices, setDevice] = useState([]);
  const [notifications, setNotifications] = useState([]);
  const [currentUserId, setCurrentUserId] = useState(null);

  useEffect(() => {
    console.log("useEffect is running...");
   
    async function fetchData() {
      console.log("we are in fetch data")
      try {
        const response = await fetchDataFromServer("http://localhost:3003/spring-demo/client/user/viewPersonalDevices");
        setDevice(response); 
        console.log(response);
      } catch (error) {
        console.error(error);
      }
    }
  /*
    const socket = new SockJS('http://localhost:8087/ws');
    const stompClient = Stomp.over(socket);

    fetchData();

    stompClient.connect({}, () => {
      console.log("WebSocket connected");
      stompClient.subscribe("/topic/notification", (notification) => {
        console.log("Notif received!");
        handleNotification(notification.body);
      });
    });

    return () => {
      stompClient.disconnect();
    };*/
    fetchData();
  }, []); 

  /*
  const handleNotification = (message) => {
    const userIdMatch = message.match(/User: (\d+)/);
  
    if (userIdMatch) {
      const notificationUserId = parseInt(userIdMatch[1]);
      console.log("User ID in notification:", notificationUserId);
  
      const apiUrl = "http://localhost:8083/springg-demo/client/getCurrentUserId";
  
      axios.get(apiUrl, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('accessToken')}`, // Include the token in the header
        },
      })
      .then(response => {
        const currentUserId = response.data;
        console.log("Current User ID:", currentUserId);
  
        if (currentUserId === notificationUserId) {
          setNotifications((prevNotifications) => [...prevNotifications, message]);
        } else {
          console.log("User IDs do not match!");
        }
      })
      .catch(error => {
        console.error("Error fetching user ID:", error);
      });
    }
  };

  const clearNotifications = () => {
    setNotifications([]);
  };
`*/
  const searchDevice = () => {
    /*
    clearNotifications();
  
    console.log("Device is 9");
  
    axios.post('http://localhost:8087/api/simulator/publish', null, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('accessToken')}`, // Include the token in the header
      },
    })
    .then(function (response) {
        console.log('Success:', response.data);
    })
    .catch(function (error) {
        console.error('Error:', error);
    });*/
  };
  

  return (
    <div id="content">
      <h1 className="text-2xl mb-4 mt-8 text-center">Devices of user</h1>
      
      <div className="search-container">
        <button onClick={searchDevice}>START</button>
        <Link to="/chart" className="chart-link">Go to chart page</Link>
        <div className="button-container">
          <Link to={'/chatAdmin'}>
            <button className="button"> Chat </button>
          </Link>
         </div>
      </div>

      <section id="cart">
        <table width="100%">
          <thead>
            <tr>
              <td>Device ID</td>
              <td>Description</td>
              <td>Address</td>
              <td>Maximum consumption/hour</td>
            </tr>
          </thead>
          <tbody>
            {devices.map((device, index) => (
              <tr key={index}>
                <td>{device.id_device}</td>
                <td>{device.description}</td>
                <td>{device.address}</td>
                <td>{device.consumption}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>

      <div className="notification-container">
        <h2>Notifications</h2>
        <ul>
          {notifications.map((notification, index) => (
            <li key={index}>{notification}</li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default User;
