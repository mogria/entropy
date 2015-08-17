requirejs(["game", "ws", "input", "lib/bean"],
function(game, ws, input, bean) {

    ws.connect();

    bean.on(game, 'create.end', function() { input.initialize(game); } );
    bean.on(game, 'update.start', playerMovement);


    var previousDirection =  {x: 0, y: 0};
    function playerMovement() {
        var direction = input.direction(game);
        if(direction.x != previousDirection.x || direction.y != previousDirection.y) {
            previousDirection = direction;
            ws.sendMove(direction);
        }
    }

});
