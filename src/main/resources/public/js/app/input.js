define("input", ["lib/phaser"],
function(Phaser) {
    var arrowKeysConfig = {};

    arrowKeysConfig[Phaser.Keyboard.LEFT]  = {cord: 'x', direction: -1};
    arrowKeysConfig[Phaser.Keyboard.RIGHT] = {cord: 'x', direction:  1};
    arrowKeysConfig[Phaser.Keyboard.UP]    = {cord: 'y', direction: -1};
    arrowKeysConfig[Phaser.Keyboard.DOWN]  = {cord: 'y', direction:  1};

    function getArrowKeys() {
        return Object.keys(arrowKeysConfig);
    }

    return {
        initialize: function(game) {
            getArrowKeys().forEach(function(key) {
                game.input.keyboard.addKeyCapture(key);
            });
        },

        direction: function (game) {
            var direction = {x: 0, y: 0};
            getArrowKeys().forEach(function(key) {
                if(game.input.keyboard.isDown(key)) {
                    direction[arrowKeysConfig[key].cord] += arrowKeysConfig[key].direction;
                }
            });
            return direction;
        }
    }
});
