
var game = new Phaser.Game(800, 600, Phaser.AUTO, 'game', {
    preload: preload,
    create: create,
    update: update
});

var socket = new SockJS("/generator");
var stompClient = Stomp.over(socket);

function playerMovement() {
    var direction = arrowKeys();
    if(direction[0] == 0 && direction[1] == 0) return;
    else sendMove(direction[0], direction[1]);
}

function sendMove(x, y) {
    stompClient.send('/app/move', {}, JSON.stringify({x: x, y: y}));
}

function connect() {

    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/newenvironment', function(name) {
        });

        stompClient.subscribe('/environment', function(name) {
            console.log("name: " + name);
        });

    });
}

function preload() {
    game.stage.backgroundColor = '#aeaeae';
    game.load.spritesheet('player-stand', 'img/player-stand.png', 64, 64);
    game.load.spritesheet('player-walk', 'img/player-walk.png', 64, 64);
}

function create() {
    connect();

    var player_stand = game.add.sprite(300, 200, 'player-stand');

    player_stand.animations.add('stand', [0, 1, 2, 1], 10, true)
    player_stand.animations.play('stand')

    var player_walk = game.add.sprite(400, 200, 'player-walk');
    player_walk.anchor.setTo(1, 1);
    player_walk.angle += 180;
    player_walk.animations.add('walk-diagonal', [0, 5, 10, 5], 10, true);
    player_walk.animations.play('walk-diagonal');
    player_walk.x = 400;
    player_walk.y = 200;


    game.input.keyboard.addKeyCapture(Phaser.Keyboard.LEFT);
    game.input.keyboard.addKeyCapture(Phaser.Keyboard.RIGHT);
    game.input.keyboard.addKeyCapture(Phaser.Keyboard.UP);
    game.input.keyboard.addKeyCapture(Phaser.Keyboard.DOWN);

}

function update() {
    playerMovement();
}

function arrowKeys() {
    var direction = [0, 0];

    var keys = {};
    keys[Phaser.Keyboard.LEFT]  = {cord: 0, direction: -1};
    keys[Phaser.Keyboard.RIGHT] = {cord: 0, direction:  1};
    keys[Phaser.Keyboard.UP]    = {cord: 1, direction: -1};
    keys[Phaser.Keyboard.DOWN]  = {cord: 1, direction:  1};

    Object.keys(keys).forEach(function(key) {
        if(game.input.keyboard.isDown(key)) {
            direction[keys[key].cord] += keys[key].direction;
        }
    });

    return direction;
}
