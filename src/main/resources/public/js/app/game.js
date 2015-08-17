define("game", ["lib/phaser", "lib/bean"],
function(Phaser, bean) {

    var game = new Phaser.Game(800, 600, Phaser.AUTO, 'game', {
        preload: preload,
        create: create,
        update: update
    });

    function preload() {
        game.stage.backgroundColor = '#aeaeae';
        
        bean.fire(game, 'preload');
    }

    function create() {
        bean.fire(game, 'create');
    }

    function update() {
        bean.fire(game, 'update');
    }

    return game; 
});
