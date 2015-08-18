package org.playentropy.circuit;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.PersistenceConstructor;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.Iterator;

public class Board implements Serializable {
    public class PieceAlreadyInUseException extends Exception {
        public PieceAlreadyInUseException(String string) {
            super(string);
        }
        public PieceAlreadyInUseException(String string, Exception wrap) {
            super(string, wrap);
        }
    }
    public class NoSpaceException extends Exception {
        public NoSpaceException(String string) {
            super(string);
        }
        public NoSpaceException(String string, Exception wrap) {
            super(string, wrap);
        }
    }

    @Transient
    private ArrayList<ArrayList<Field>> board;

    private final Vector boardSize;

    private ArrayList<Piece> piecesList = new ArrayList<Piece>();

    private ArrayList<Vector> positionList = new ArrayList<Vector>();

    @Transient
    private static final NullPiece NULL_PIECE = new NullPiece();

    @PersistenceConstructor
    public Board(ArrayList<Piece> piecesList, ArrayList<Vector> positionList, Vector boardSize)
        throws Board.NoSpaceException,
               Board.PieceAlreadyInUseException {
        this(boardSize);

        assert piecesList.size() == positionList.size();

        int size = piecesList.size();
        for(int i = 0; i < size; i++) {
            placePiece(piecesList.get(i), positionList.get(i));
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


        long emptyFieldCount = streamPartialBoard(position, size)
           .filter(f -> f.getContent().equals(NULL_PIECE))
           .count();

        return  emptyFieldCount == size.area();

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

        if(!isPlaceFree(position, size)) throw new NoSpaceException("place already used by other piece");
        if(!isWithinBoard(position, size)) throw new NoSpaceException("piece not within board");
        if(piecesList.contains(piece)) throw new PieceAlreadyInUseException("the same piece is already on the board");
        piecesList.add(piece);
        positionList.add(position);

        streamPartialBoard(position, size)
            .forEach(f -> f.setContent(piece));
    }

    public void removePiece(Piece piece) {
        assert piece != null;
        assert !(piece instanceof NullPiece);

        int index = piecesList.indexOf(piece);
        if(index == -1) return;

        Vector position = positionList.get(index);
        streamPartialBoard(position, piece.getSize())
            .forEach(f -> f.setContent(NULL_PIECE));

        piecesList.remove(index);
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
        int index = piecesList.indexOf(piece);
        if(index == -1) return null;
        else            return positionList.get(index);
    }

    public Collection<Piece> getPieces() {
        return piecesList;
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

    public ArrayList<ArrayList<Field>> getBoard() {
        return board;
    }

    public Vector getBoardSize() {
        return boardSize;
    }
}
