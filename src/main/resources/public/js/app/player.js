define("player", ["lib/bean"],
function(bean) {
    function Player(game, x, y) {
        this.x = x;
        this.y = y;
        this.direction = {'x': 0, 'y': 0};
        this.standSprite = game.add.sprite(x, y, 'player-stand');
        this.standSprite.animations.add('stand', [0, 1, 2, 1], 10, true)
        this.standSprite.animations.play('stand')
        this.standSprite.visible = false;
        this.walkSprite = game.add.sprite(x, y, 'player-walk');
        this.walkSprite.animations.add('walk-diagonal', [10, 5, 0, 5], 10, true);
        this.walkSprite.animations.add('walk-straight', [11, 7, 3, 7], 10, true);
        this.walkSprite.visible = false;

    }

    Player.preload = function(game) {
        game.load.spritesheet('player-stand', 'img/player-stand.png', 64, 64);
        game.load.spritesheet('player-walk', 'img/player-walk.png', 64, 64);
    };

    Player.prototype.show = function(game) {
        var that = this;
        bean.on(game, 'update', function() {
            if(that.direction.x == 0 && that.direction.y == 0) {
                that.standSprite.visible = true;
                that.walkSprite.visible = false;
            } else {
                that.standSprite.visible = false;
                that.walkSprite.visible = true;

                var diagonal = Math.abs(that.direction.x) + Math.abs(that.direction.y) == 2;
                that.walkSprite.animations.play(diagonal ? 'walk-diagonal' : 'walk-straight');
                var settings = that.direction.x <=  0 && that.direction.y == -1 ? { angle:   0, anchor: [0, 0] } : 
                               that.direction.x ==  1 && that.direction.y <=  0 ? { angle:  90, anchor: [0, 1] } :
                               that.direction.x >=  0 && that.direction.y ==  1 ? { angle: 180, anchor: [1, 1] } :
                               that.direction.x == -1 && that.direction.y >=  0 ? { angle: 270, anchor: [1, 0] } : 0;

                that.walkSprite.anchor.setTo(settings.anchor[0], settings.anchor[1]);
                that.walkSprite.angle = settings.angle;
           }

            that.move();
        });
    };

    Player.prototype.hide = function(game) {
       this.standSprite.destroy(true); 
       this.walkSprite.destroy(true); 
    };

    Player.prototype.setDirection = function(direction) {
        var changed = 
            this.direction.x != direction.x ||
            this.direction.y != direction.y;

        if(changed) {
            this.direction = direction;
            bean.fire(this, 'direction.change');
        }
    }

    Player.prototype.move = function() {
        var movX = this.direction.x;
        var movY = this.direction.y;

        this.walkSprite.x += movX;
        this.walkSprite.y += movY;
        this.standSprite.x += movX;
        this.standSprite.y += movY;
    }

    return Player;
});

