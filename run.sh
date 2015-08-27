#!/bin/bash

start() {
    mvn spring-boot:run -Drun.arguments="--spring.output.ansi.enabled=ALWAYS" &
}

stop() {
    PIDFILE="playentropy.pid"
    if [ -f "$PIDFILE" ]; then
        pid="`cat "$PIDFILE"`"
        if [ "`uname -o`" = "Cygwin" ]; then
            # cygwin can't simply kill win32 processes (which java in most
            # cases is). So we need to avoid the built-in kill and use
            # the one provided by cygwin so the -f option is available
            /bin/kill -f "$pid"
        else
            kill "$pid"
        fi
        rm "$PIDFILE"
    fi
}

reload() {
        mvn compile
}

stop
start
while read line; do
    if [ "$line" = "r" ]; then
        stop
        start
    elif [ "$line" = "q" ]; then
        stop
        exit 0
    else
        reload
    fi
done
