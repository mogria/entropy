define("ws", ["lib/sockjs", "lib/stomp"],
function(SockJS, Stomp) {

    var socket = new SockJS("/generator");
    var stompClient = Stomp.over(socket);

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

            });
        },
        sendMove: function(direction) {
            stompClient.send('/app/move', {}, JSON.stringify(direction));
        }
    }
});
