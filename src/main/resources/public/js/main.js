requirejs.config({
    baseUrl: 'js/app',
    paths: {
        'lib': '../lib'
    },
    shim: {
        'lib/sockjs': {
            exports: 'SockJS'
        },
        'lib/stomp': {
            deps: ['lib/sockjs'],
            exports: 'Stomp'
        },
        'lib/phaser': {
            exports: 'Phaser'
        },
        'lib/zepto': {
            exports: 'Zepto'
        }
    }
});

requirejs(["entropy"]);
