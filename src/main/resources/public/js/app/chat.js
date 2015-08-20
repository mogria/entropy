define("chat", ["ws", "lib/zepto"],
function(ws, $) {

    function sendMessage() {
        var message = $("#chatmessage").val();
        ws.sendChatMessage(message);
        $("#chatmessage").val("");
        $("#chatmessage").focus();
    }

    function receiveMessage() {
        var callback = function(message) {
            var box = $("<div />");
            box.text(message);
            box.appendTo($("#messages"));
            receiveMessage();
        };
        ws.receiveChatMessage(callback);
    }

    return {
        initialize: function() {
            $("#entry button").click(function() {
                sendMessage();
            });
            receiveMessage();
        }
    };

});
