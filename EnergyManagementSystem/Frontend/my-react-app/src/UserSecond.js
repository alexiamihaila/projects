import React, { useState, useEffect } from "react";
import './User.css'
import axios from "axios";
import Stomp from "webstomp-client"; 
import SockJS from 'sockjs-client'; 
import { Link } from "react-router-dom";

async function fetchDataFromServer(url) {
  try {
    const response = await fetch(url);

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



function UserSecond() {
  const [devices, setDevice] = useState([]);
  const [notifications, setNotifications] = useState([]);
  const [currentUserId, setCurrentUserId] = useState(null);

  useEffect(() => {
    console.log("useEffect is running...");
   
    async function fetchData() {
      console.log("we are in fetch data")
      try {
          const response = await fetchDataFromServer("http://localhost:3003/spring-demo/user/viewPersonalDevices");
          setDevice(response); 
          console.log(response);
      } catch (error) {
          console.error(error);
      }
  }

    const socket = new SockJS('http://localhost:8087/ws');

    const stompClient = Stomp.over(socket);

    fetchData();

    stompClient.connect({}, () => {
      console.log("WebSocket connected");
        stompClient.subscribe("/topic/notification2", (notification) => {
          console.log("Notif 2 received!");
            handleNotification(notification.body);
        });
    });

   
    return () => {
        stompClient.disconnect();
    };
  }, []); 

  const handleNotification = (message) => {
    const userIdMatch = message.match(/User: (\d+)/);
  
    if (userIdMatch) {
      const notificationUserId = parseInt(userIdMatch[1]);
      console.log("User ID in notification:", notificationUserId);
  
      const apiUrl = "http://localhost:3003/springg-demo/getCurrentUserId";
  
      axios.get(apiUrl)
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

  const searchDevice = () => {
    clearNotifications();
  
    console.log("Device is 16");
  
    axios.post('http://localhost:8087/api/simulator/publish2')
    .then(function (response) {
        console.log('Success:', response.data);
    })
    .catch(function (error) {
        console.error('Error:', error);
    });
};

  return (
      <div id="content">
       
        <h1 className="text-2xl mb-4 mt-8 text-center">Devices of user</h1>
       
        <div class="search-container">
          <button onClick={searchDevice}>START2</button>
          <Link to="/chart" className="chart-link">Go to chart page</Link>
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

export default UserSecond;
