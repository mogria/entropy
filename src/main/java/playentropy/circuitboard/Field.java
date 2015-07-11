package org.playentropy.circuitboard;

class Field {
    private final Vector position;
    private Piece content;

    public Field(final Vector position, Piece content) {
        assert content != null;

        this.position = position;
        this.content = content;
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
