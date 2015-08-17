requirejs(["game", "ws", "input", "player", "lib/bean"],
function(game, ws, input, Player, bean) {

    ws.connect();

    bean.on(game, 'preload', function()  { Player.preload(this); } );

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
