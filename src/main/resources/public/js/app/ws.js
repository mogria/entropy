define("ws", ["lib/sockjs", "lib/stomp"],
function(SockJS, Stomp) {

    var socket = new SockJS("/generator");
    var stompClient = Stomp.over(socket);
    
    var receiveChatMessageCallback = function(message) { };
    
    return {
        client: stompClient,
        connect: function() {
            stompClient.connect({}, function(frame) {
                console.log('Connected: ' + frame);

                stompClient.subscribe('/newenvironment', function(name) {
                });

                stompClient.subscribe('/environment', function(name) {
                    console.log("name: " + name);
                });

                stompClient.subscribe('/app/chat', function(chatMessage) {
                    console.log(chatMessage);
                    chatMessageCallback(chatMessage);
                })

                stompClient.send('/chat/message', {}, JSON.stringify({
                    "message": "some message"
                }));
            }, function(error) {
                console.log("Couldn't connect");
                console.log(error);
            });
        },
        sendMove: function(direction) {
            stompClient.send('/app/move', {}, JSON.stringify(direction));
        },
        sendChatMessage: function(message) {
            stompClient.send('/chat/message', {}, JSON.stringify({
                "message": message
            }));
        },
        receiveChatMessage: function(callback) {
            receiveChatMessageCallback = callback;
        }
    }
});
