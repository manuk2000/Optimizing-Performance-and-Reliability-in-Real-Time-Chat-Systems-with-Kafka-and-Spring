'use strict';

// var usernamePage = document.querySelector('#username-page');
// var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;
var userEmail = null;
var userPassword = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

// function connect(event) {
//     username = document.querySelector('#name').value;
//     userEmail = document.querySelector('#email').value;
//     userPassword = document.querySelector('#password').value;
//
//     if(username && userEmail && userPassword) {
//         // usernamePage.classList.add('hidden');
//         // chatPage.classList.remove('hidden');
//         window.location.href = "chat.html";
//
//
//         var socket = new SockJS('/ws');
//         stompClient = Stomp.over(socket);
//
//         stompClient.connect({}, onConnected, onError);
//     }
//     event.preventDefault();
// }


function connect(event) {
    username = document.querySelector('#name').value;
    userEmail = document.querySelector('#email').value;
    userPassword = document.querySelector('#password').value;

    if (username && userEmail && userPassword) {
        // Construct the request body
        var requestBody = {
            name: username,
            email: userEmail,
            password: userPassword
        };

        // Make a POST request to the backend endpoint
        fetch('http://localhost:8000/api/v1/auth/registration', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
        })
            .then(response => {
                if (response.ok) {
                    // Redirect to chat.html if the registration was successful
                    window.location.href = "login.html";
                } else {
                    // Handle error response
                    console.log('Registration failed ');
                    displayErrorMessage('Registration failed ')
                }
            })
            .catch(error => {
                console.log('Error registering HAYEREN:', error);
                displayErrorMessage(error)
            });

        // var socket = new SockJS('/ws');
        // stompClient = Stomp.over(socket);
        //
        // stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

function displayErrorMessage(message) {
    document.getElementById('errorMessage').textContent = message;
}

//
// function onConnected() {
//     // Subscribe to the Public Topic
//     stompClient.subscribe('/topic/public', onMessageReceived);
//
//     // Tell your username to the server
//     stompClient.send("/app/chat.addUser",
//         {},
//         JSON.stringify({sender: username, type: 'JOIN'})
//     )
//
//     connectingElement.classList.add('hidden');
// }


// function sendMessage(event) {
//     var messageContent = messageInput.value.trim();
//     if(messageContent && stompClient) {
//         var chatMessage = {
//             sender: username,
//             content: messageInput.value,
//             type: 'CHAT'
//         };
//         stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
//         messageInput.value = '';
//     }
//     event.preventDefault();
// }

//
// function onMessageReceived(payload) {
//     var message = JSON.parse(payload.body);
//
//     var messageElement = document.createElement('li');
//
//     if(message.type === 'JOIN') {
//         messageElement.classList.add('event-message');
//         message.content = message.sender + ' joined!';
//     } else if (message.type === 'LEAVE') {
//         messageElement.classList.add('event-message');
//         message.content = message.sender + ' left!';
//     } else {
//         messageElement.classList.add('chat-message');
//
//         var avatarElement = document.createElement('i');
//         var avatarText = document.createTextNode(message.sender[0]);
//         avatarElement.appendChild(avatarText);
//         avatarElement.style['background-color'] = getAvatarColor(message.sender);
//
//         messageElement.appendChild(avatarElement);
//
//         var usernameElement = document.createElement('span');
//         var usernameText = document.createTextNode(message.sender);
//         usernameElement.appendChild(usernameText);
//         messageElement.appendChild(usernameElement);
//     }
//
//     var textElement = document.createElement('p');
//     var messageText = document.createTextNode(message.content);
//     textElement.appendChild(messageText);
//
//     messageElement.appendChild(textElement);
//
//     messageArea.appendChild(messageElement);
//     messageArea.scrollTop = messageArea.scrollHeight;
// }

//
// function getAvatarColor(messageSender) {
//     var hash = 0;
//     for (var i = 0; i < messageSender.length; i++) {
//         hash = 31 * hash + messageSender.charCodeAt(i);
//     }
//     var index = Math.abs(hash % colors.length);
//     return colors[index];
// }

usernameForm.addEventListener('submit', connect, true)
// messageForm.addEventListener('submit', sendMessage, true)
