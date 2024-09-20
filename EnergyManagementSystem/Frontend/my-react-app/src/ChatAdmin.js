import React, { useEffect, useState } from 'react'
import {over} from 'stompjs';
import SockJS from 'sockjs-client';
import './chat.css';
import axios from 'axios';
import {parseJwt} from './util.js';


var stompClient =null;
const ChatRoom = () => {
    const [privateChats, setPrivateChats] = useState(new Map());     
    const [tab,setTab] =useState("CHATROOM");
    const [userData, setUserData] = useState({
        username: '',
        receivername: '',
        connected: false,
        message: ''
      });

    const [isTyping, setIsTyping] = useState(null); 
    const [whoIsTyping, setWhoIsTyping] = useState(''); 
        
    const [isRead, setIsRead] = useState('');

    const [users, setUsers] = useState([]);
  
    let typingTimer;

    useEffect(() => {
      //connect();

      getCurrentUsername();

    }, [userData.username] );


    useEffect(() =>{
     const token = localStorage.getItem('accessToken');

      const decodedToken = parseJwt(token);

      const authorities = decodedToken.authorities;
        if (authorities && authorities.length > 0) 
        {
            const role = authorities[0];
            console.log(role);

            if (role === 'ADMIN')
            {
                axios.get('http://localhost:3003/springg-demo/admin/users/allUsers',  {
                        headers: {
                        'Authorization': `Bearer ${token}`,
                        },})
                    .then(response => {
                        
                        const users = response.data;
                        setUsers(users); 
                    })
                    .catch(error => {
                        console.error('Error fetching users:', error);
                    });
            }
            else{
                setTab('alexiamihaila');
                const currentUser = userData
                setUsers([currentUser]);
            }
        }
    }, [userData.username]);

    const connect =()=>{
        const token = localStorage.getItem('accessToken');
        const decodedToken = parseJwt(token);
    
        if (decodedToken) {
          const authorities = decodedToken.authorities;
          if (authorities && authorities.length > 0) {
            const role = authorities[0];
            console.log('Role: ' + role);
            if(role === 'CLIENT' || role === 'ADMIN'){

            let Sock = new SockJS('http://localhost:8083/ws');
            stompClient = over(Sock);
            stompClient.connect({},onConnected, onError);
            }
            else{
                console.log("You are not authorized for this!")
            }
          }
        }
    }

    const onConnected = () => {
        console.log("Stomp clietn: " + stompClient)
        setUserData({...userData,"connected": true});
        stompClient.subscribe('/chatroom/public', onMessageReceived);
        stompClient.subscribe('/user/'+userData.username+'/private', onPrivateMessage);
        stompClient.subscribe('/user/' + userData.username + '/queue/typing', onTypingReceived);
        stompClient.subscribe('/user/' + userData.username + '/queue/read-receipt', handleReadReceipt);
        userJoin();
    }

    const userJoin=()=>{
          var chatMessage = {
            senderName: userData.username,
            status:"JOIN"
          };
          stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
    }

    const onMessageReceived = (payload)=>{
        var payloadData = JSON.parse(payload.body);
        switch(payloadData.status){
            case "JOIN":
                if(!privateChats.get(payloadData.senderName)){
                    privateChats.set(payloadData.senderName,[]);
                    setPrivateChats(new Map(privateChats));
                }
                break;
            
        }
    }
    
    const onPrivateMessage = (payload)=>{
        const payloadData = JSON.parse(payload.body);

        setIsRead('');
    
        // Use functional update for privateChats
        setPrivateChats(prevChats => {
            const updatedChats = new Map(prevChats);
    
            if (!updatedChats.has(payloadData.senderName)) {
                updatedChats.set(payloadData.senderName, []);
            }
    
            const messages = updatedChats.get(payloadData.senderName);
    
            // Check if message already exists
            if (!messages.some(message => message.messageId === payloadData.messageId)) {
                messages.push(payloadData);
            }
    
            updatedChats.set(payloadData.senderName, messages);
    
            return updatedChats;
        });
    
        if (payloadData.status === 'TYPING') {
            setTimeout(() => setWhoIsTyping(''), 1000);
        }
    }

    const onError = (err) => {
        console.log(err);
        
    }



    const sendPrivateValue=()=>{
        if (stompClient) {
            const messageId = Date.now(); 
            const chatMessage = {
                senderName: userData.username,
                receiverName: tab,
                message: userData.message,
                messageId: messageId, 
                status: 'MESSAGE',
                read: false,
            };

            setPrivateChats(prevChats => {
                const updatedChats = new Map(prevChats);
                const messages = updatedChats.get(tab) || [];
                updatedChats.set(tab, [...messages, chatMessage]);
                return updatedChats;
            });
    
            stompClient.send('/app/private-message', {}, JSON.stringify(chatMessage));
            setUserData({ ...userData, message: '' });
            setIsRead('');
        }
    }

    const handleUsername=(event)=>{
        const {value}="alexiamihaila";
        setUserData({...userData,"username": value});
    }

    const registerUser=()=>{
        connect();
    }



    const handleMessageChange = (event) => {
        const { value } = event.target;
        setUserData({ ...userData, "message": value });
        handleTyping(); // Call the typing handler
        
    };

    const handleTyping = () => {
        console.log(stompClient);
        if (stompClient !== null) {
            console.log("aici");
            stompClient.send("/app/typing", {}, JSON.stringify({ 
                senderName: userData.username,
                receiverName: tab,

                status: "TYPING" 
            }));
        }
        console.log("Se ajunge aici 2")
        clearTimeout(typingTimer);
        typingTimer = setTimeout(() => {
            setIsTyping(false);
        }, 1000);
    };


    const onTypingReceived = (payload)=>{
        var payloadData = JSON.parse(payload.body);

        setWhoIsTyping(`${payloadData.senderName} is typing...`);
        setIsRead('');

        setTimeout(() => setWhoIsTyping(''), 1000);
    }


    const handleTabPress =() => {
        setUserData({ ...userData});
        handleRead(); 
        
    };

    const handleRead = () => {
       
        if (stompClient !== null && privateChats.has(tab)) {
            const messages = privateChats.get(tab);
            const unreadMessages = messages.filter((message) => message.senderName !== userData.username && !message.read);
        
            if (unreadMessages.length > 0) {
              stompClient.send("/app/message-read", {}, JSON.stringify({ 
                senderName: tab,
                receiverName: userData.username,
                status: "READ" 
              }));
              
            
              const updatedMessages = messages.map((message) => {
                if (message.senderName !== userData.username && !message.read) {
                  return { ...message, read: true };
                }
                return message;
              });
        
             
            }
          }
        
    };


    const handleReadReceipt = (payload)=>{
    
        var payloadData = JSON.parse(payload.body);

        setIsRead(`${payloadData.receiverName} read at ` + payloadData.date);
            
        
    }



   
    const getCurrentUsername = () => {
        axios.get('http://localhost:3003/springg-demo/auth/getUsername')
            .then(response => {
                setUserData(prevUserData => ({
                ...prevUserData,
                username: response.data
            }));
                console.log('Current username:', response.data);
            })
            .catch(error => {
                console.error('Error fetching username:', error);
            });
    };


    

    return (
        <div className="container">
        {userData.connected ?
            <div className="chat-box">
                <div className="member-list">
                    <ul>
                    {users.map((user, index) => (
                        user.username === userData.username ?
                        <li  className={`member ${tab === user.username && "active"}`} key={index}>
                            {user.username} (Me) 
                        </li>
                        :
                        <li onClick={() => { setTab(user.username) }} className={`member ${tab === user.username && "active"}`} key={index}>
                            {user.username}
                        </li>
                    ))}
                    </ul>
                </div>
                <div className="chat-content" >
                        <ul className="chat-messages" onClick={handleTabPress}>
                        {tab && privateChats.get(tab) && privateChats.get(tab).map((chat, index) => (
                                <li className={`message ${chat.senderName === userData.username && "self"}`} key={index}>
                                    {chat.senderName !== userData.username && <div className="avatar">{chat.senderName}</div>}
                                    <div className="message-data">{chat.message}</div>
                                    {chat.senderName === userData.username && <div className="avatar self">{chat.senderName}</div>}
                                </li>
                            ))}
                           <li className="typing-indicator">
                            {isRead}
                           </li>
                            <li className="typing-indicator">
                                {whoIsTyping} 
                            </li>
                        </ul>
                  

                    <div className="send-message">
                        <input type="text" className="input-message" placeholder="enter the message" value={userData.message} onChange={handleMessageChange} />
                        <button type="button" className="send-button" onClick={sendPrivateValue}>send</button>
                    </div>
                </div>
            </div>
            :
            <div className="register">
                <input
                    id="user-name"
                    placeholder="Enter your name"
                    name="userName"
                    value={userData.username || ''}
                    onChange={handleUsername}
                    margin="normal"
                />
                <button type="button" onClick={registerUser}>
                    connect
                </button>
            </div>}
    </div>
    )
}

export default ChatRoom;
