requirejs(["game", "ws", "chat", "input", "player", "lib/bean"],
function(game, ws, chat, input, Player, bean) {

    bean.on(game, 'preload', function() { ws.connect(); } );
    bean.on(game, 'preload', function() { chat.initialize(); } );
    bean.on(game, 'preload', function() { Player.preload(this); } );

    bean.on(game, 'create', function() { input.initialize(this); } );
    bean.one(game, 'create', function() {
        var yourself = new Player(game, 200, 100);

        bean.on(game, 'update', function() {
            yourself.setDirection(input.direction(game));
        });

        bean.on(yourself, 'direction.change', function() {
            ws.sendMove(this.direction);
        });

        yourself.show(game);
    });

});
