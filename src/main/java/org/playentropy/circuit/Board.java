package org.playentropy.circuit;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.PersistenceConstructor;
import java.io.Serializable;

public class Board implements Serializable {
    public class PieceAlreadyInUseException extends Exception {
    }
    public class NoSpaceException extends Exception {
    }

    private ArrayList<ArrayList<Field>> board;

    private final Vector boardSize;

    @Transient
    private HashMap<Piece, Vector> pieces = new HashMap<Piece, Vector>();

    @Transient
    private static final NullPiece NULL_PIECE = new NullPiece();

    @PersistenceConstructor
    public Board(ArrayList<ArrayList<Field>> fields, Vector boardSize) {
        this(boardSize);
        for(int x = 0; x < boardSize.getX(); x++) {
            for(int y = 0; y < boardSize.getY(); y++) {
                Field field = fields.get(x).get(y);
                Piece content = field.getContent();

                board.get(x).get(y).setContent(content);

                if(!(content instanceof NullPiece)) {
                    pieces.put(content, new Vector(x, y));
                }
            }
        }
    }

    public Board(int x, int y) {
        this(new Vector(x, y));
    }

    public Board(final Vector size) {
        assert size.withinField(new Vector(0, 0), size);

        this.board = new ArrayList<ArrayList<Field>>(size.getX());
        for(int x = 0; x < size.getX(); x++) {
            this.board.add(new ArrayList<Field>(size.getY()));
            for(int y = 0; y < size.getY(); y++) {
                Field f = new Field(new Vector(x, y), NULL_PIECE);
                this.board.get(x).add(f);
            }
        }

        this.boardSize = size;
    }

    private Stream<Field> streamBoard() {
        return board.stream().flatMap(column -> column.stream());
    }

    private Stream<Field> streamPartialBoard(final Vector position, final Vector size) {
        assert position != null;
        assert size != null;

        final Vector upperBound = position.add(size)
                                          .add(new Vector(-1, -1));
        return streamBoard()
                   .filter(field -> field.getPosition().withinField(position, upperBound));
    }

    public boolean isWithinBoard(int x, int y, int sizeX, int sizeY) {
        return isWithinBoard(new Vector(x, y), new Vector(sizeX, sizeY));
    }

    public boolean isWithinBoard(final Vector position, final Vector size) {
        assert position != null;
        assert size != null;

        return position.add(size).withinField(new Vector(0, 0), boardSize)
            && position.withinField(new Vector(0, 0), boardSize.add(new Vector(-1, -1)));
    }

    public boolean isPlaceFree(int x, int y, int sizeX, int sizeY) {
        return isPlaceFree(new Vector(x, y), new Vector(sizeX, sizeY));
    }

    public boolean isPlaceFree(final Vector position, final Vector size) {
        assert position != null;
        assert size != null;

        return streamPartialBoard(position, size)
               .filter(f -> f.getContent() == NULL_PIECE)
               .count() == size.area();

    }

    public void placePiece(Piece piece, int x, int y)
        throws NoSpaceException,
               PieceAlreadyInUseException {
        placePiece(piece, new Vector(x, y));
    }

    public void placePiece(Piece piece, final Vector position)
        throws NoSpaceException,
               PieceAlreadyInUseException {
        assert piece != null;
        assert !(piece instanceof NullPiece);
        assert position != null;

        final Vector size = piece.getSize();

        if(!isPlaceFree(position, size)) throw new NoSpaceException();
        if(!isWithinBoard(position, size)) throw new NoSpaceException();
        if(pieces.containsKey(piece)) throw new PieceAlreadyInUseException();
        pieces.put(piece, position);

        streamPartialBoard(position, size)
            .forEach(f -> f.setContent(piece));
    }

    public void removePiece(Piece piece) {
        assert piece != null;
        assert !(piece instanceof NullPiece);

        if(!pieces.containsKey(piece)) return;

        streamPartialBoard(pieces.get(piece), piece.getSize())
            .forEach(f -> f.setContent(NULL_PIECE));

        pieces.remove(piece);
    }

    public Piece getPieceAt(int x, int y) {
        return getPieceAt(new Vector(x, y));
    }

    public Piece getPieceAt(final Vector position) {
        assert position != null;

        if(isWithinBoard(position, new Vector(1, 1))) {
            return board.get(position.getX())
                        .get(position.getY())
                        .getContent();
        } else {
            return NULL_PIECE;
        }
    }

    public Vector getPiecePosition(Piece piece) {
        return pieces.get(piece);
    }

    public Collection<Piece> getPieces() {
        return pieces.keySet();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int y = 0; y < boardSize.getY(); y++) {
            for(int x = 0; x < boardSize.getX(); x++) {
                builder.append(board.get(x).get(y).getContent().toString());
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
