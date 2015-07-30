var game = new Phaser.Game(800, 600, Phaser.AUTO, 'game', {
    preload: preload,
    create: create,
    update: update
});

function connect() {
    var socket = new SockJS("/spring-websocket-endpoint/endpoint");
    var stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
    });
}

function preload() {
    game.load.image('player', 'img/player.png');
}

function create() {
}

function update() {
}
