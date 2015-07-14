package org.playentropy.circuit;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.PersistenceConstructor;
import java.io.Serializable;

class Field implements Serializable {
    @Transient
    private final Vector position;

    private Piece content;

    @PersistenceConstructor
    public Field(Piece content) {
        this(new Vector(0, 0), content);
    }

    public Field(final Vector position, Piece content) {
        assert content != null;
        this.content = content;
        this.position = position;
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public Vector getPosition() {
        return position;
    }

    public Piece getContent() {
        return content;
    }

    public void setContent(Piece content) {
        assert content != null;

        this.content = content;
    }
}
