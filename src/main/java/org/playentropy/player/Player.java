package org.playentropy.player;

import org.playentropy.circuit.Board;
import org.playentropy.circuit.Vector;
import org.playentropy.map.Map;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Player {
    private Vector position;

    private Board board;

    @DBRef
    private Map currentMap;

    public Vector getPosition() {
        return this.position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Map getCurrentMap() {
        return this.currentMap;
    }

    public void setCurrentMap(Map map) {
        this.currentMap = map;
    }

}
