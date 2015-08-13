requirejs(["game", "ws", "input", "lib/bean"],
function(game, ws, input, bean) {

    ws.connect();

    bean.on(game, 'create.end', function() { input.initialize(game); } );
    bean.on(game, 'update.start', playerMovement);


    function playerMovement() {
        var direction = input.direction(game);
        if(direction.x == 0 && direction.y == 0) return;
        else ws.sendMove(direction);
    }

});
