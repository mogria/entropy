define("player", []
function() {
    function Player(game, x, y) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.direction = {x: 0, y: 0};

        game.add.sprite(x, y, 'player-stand');

        bean.on(game, 'update.start', function() {
        });
    }

    Player.prototype.
    return Player;
});

