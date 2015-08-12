var game = new Phaser.Game(800, 600, Phaser.AUTO, 'game', {
    preload: preload,
    create: create,
    update: update
});

function connect() {
    var socket = new SockJS("/endpoint");
    var stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/name', function(name) {
            console.log("name: " + name);
        });

        stompClient.send('/app/hello', {}, "my name");
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
    player_walk.anchor.setTo(7 / 8, 7 / 8);
    player_walk.angle += 180;
    player_walk.animations.add('walk-diagonal', [0, 5, 10, 5], 10, true);
    player_walk.animations.play('walk-diagonal');
    player_walk.x = 400;
    player_walk.y = 200;


}

function update() {
}
