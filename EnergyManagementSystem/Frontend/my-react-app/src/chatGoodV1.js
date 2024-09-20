import React, { useEffect, useState } from 'react'
import {over} from 'stompjs';
import SockJS from 'sockjs-client';
import './chat.css';
import axios from 'axios';
import { Tab } from '@mui/material';
import { TiTickOutline } from "react-icons/ti";

var stompClient =null;
const ChatRoom = () => {
    const [privateChats, setPrivateChats] = useState(new Map());     
    const [publicChats, setPublicChats] = useState([]); 
    const [tab,setTab] =useState("CHATROOM");
    const [userData, setUserData] = useState({
        username: '',
        receivername: '',
        connected: false,
        message: ''
      });

    const [isTyping, setIsTyping] = useState(null); 
    const [whoIsTyping, setWhoIsTyping] = useState(''); 
    const [readReceipts, setReadReceipts] = useState(new Map());
    const [currentDateTime, setCurrentDateTime] = useState(new Date().toLocaleString());

    let typingTimer;

    useEffect(() => {
      //connect();
      getCurrentUsername();
      console.log(userData);
    }, [userData.username]);

    const connect =()=>{
        let Sock = new SockJS('http://localhost:8080/ws');
        stompClient = over(Sock);
        stompClient.connect({},onConnected, onError);
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
            case "MESSAGE":
                publicChats.push(payloadData);
                setPublicChats([...publicChats]);
                break;
            case "TYPING":
                setWhoIsTyping(`${payloadData.senderName} is typing...`);
                setTimeout(() => setWhoIsTyping(''), 1000);
                break;
        }
    }
    
    const onPrivateMessage = (payload)=>{
        var payloadData = JSON.parse(payload.body);
        if(privateChats.get(payloadData.senderName)){
            privateChats.get(payloadData.senderName).push(payloadData);
            setPrivateChats(new Map(privateChats));
        }else{
            let list =[];
            list.push(payloadData);
            privateChats.set(payloadData.senderName,list);
            setPrivateChats(new Map(privateChats));
        }
       
    }

    const onError = (err) => {
        console.log(err);
        
    }



    const sendPrivateValue=()=>{
        if (stompClient) {
            var chatMessage = {
                senderName: userData.username,
                receiverName: tab,
                message: userData.message,
                status: "MESSAGE"
            };
    
            // Create a new map from the existing privateChats
            const updatedPrivateChats = new Map(privateChats);
    
            // Get the current chat array for the receiver or initialize it
            const currentChat = updatedPrivateChats.get(tab) || [];
    
            // Append the new message
            updatedPrivateChats.set(tab, [...currentChat, chatMessage]);
    
            // Update the state with the new map
            setPrivateChats(updatedPrivateChats);
    
            // Reset the message input
            setUserData({ ...userData, "message": "" });
    
            // Send the message
            stompClient.send("/app/private-message", {}, JSON.stringify(chatMessage));
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
        

        setTimeout(() => setWhoIsTyping(''), 1000);
    }
   
    const getCurrentUsername = () => {
        axios.get('http://localhost:8083/springg-demo/auth/getUsername')
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

    const notifyMessageRead = (messageId, senderName) => {
        setCurrentDateTime(new Date().toLocaleString());
        const readReceipt = {
            messageId: messageId, 
            senderName: senderName, 
            receiverName: userData.username 
        };
        stompClient.send("/app/message-read", {}, JSON.stringify(readReceipt));
    };

    const handleReadReceipt = (receipt) => {
        const readReceipt = JSON.parse(receipt.body);
        setReadReceipts(new Map(readReceipts.set(readReceipt.messageId, true)));
        console.log("Receipt red" + readReceipt);
    };

    const handleTabChange = (name) => {
        setTab(name);

        const chats = privateChats.get(name) || [];
        chats.forEach(chat => {
            if (!readReceipts.get(chat.messageId) && chat.senderName !== userData.username) {
                notifyMessageRead(chat.messageId, chat.senderName);
            }
        });
    };


    return (
        <div className="container">
        {userData.connected ?
            <div className="chat-box">
                <div className="member-list">
                    <ul>
                    {[...privateChats.keys()].map((name, index) => (
                        name === userData.username ?
                        <li  className={`member ${tab === name && "active"}`} key={index}>
                            {name} (Me) 
                        </li>
                        :
                        <li onClick={() => { handleTabChange(name), setTab(name) }} className={`member ${tab === name && "active"}`} key={index}>
                            {name}
                        </li>
                    ))}
                    </ul>
                </div>
                <div className="chat-content">
                    <ul className="chat-messages">
                        {tab && privateChats.get(tab) && privateChats.get(tab).map((chat, index) => (
                            <li className={`message ${chat.senderName === userData.username && "self"}`} key={index}>
                                {chat.senderName !== userData.username && <div className="avatar">{chat.senderName}</div>}
                                <div className="message-data">{chat.message}</div>
                                {chat.senderName === userData.username && <div className="avatar self">{chat.senderName}</div>}
                                {readReceipts.get(chat.messageId) && <span className="read-receipt"><TiTickOutline/></span>}
                            </li>
                        ))}
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

export default ChatRoom
