package org.playentropy.test.circuit;

import org.playentropy.circuit.Piece;
import org.playentropy.circuit.Connector;
import org.playentropy.circuit.NullPiece;
import org.playentropy.circuit.Board;
import org.playentropy.circuit.Board.NoSpaceException;
import org.playentropy.circuit.Board.PieceAlreadyInUseException;
import org.playentropy.circuit.Vector;
import java.util.Collection;
import java.util.HashSet;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class BoardTest {
    static int SIZE_X = 5;
    static int SIZE_Y= 7;
    private Board board;
    private Piece p11;
    private Piece p21;
    private Piece p22;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    abstract class MockPiece implements Piece {
        public Collection<Connector> getConnectors() {
            return new HashSet<Connector>();
        }
        public void update() {}
        public String getIdentifier() { return "Mock"; }
    }

    @Before
    public void setUp() {
        board = new Board(SIZE_X, SIZE_Y);
        p11 = new MockPiece() {
            public Vector getSize() { return new Vector(1, 1); }
        };

        p22 = new MockPiece() {
            public Vector getSize() { return new Vector(2, 2); }
        };

        p21 = new MockPiece() {
            public Vector getSize() { return new Vector(2, 1); }
        };
    }

    @After
    public void tearDown() {
        board = null;
        p11 = null;
        p22 = null;
        p21 = null;
    }

    @Test
    public void testEmptyBoard() {
        for(int x = 0; x < SIZE_X; x++) {
            for(int y = 0; y < SIZE_Y; y++) {
                Piece p = board.getPieceAt(x, y);
                Assert.assertNotNull(p);
                Assert.assertTrue(p instanceof NullPiece);
            }
        }
    }

    @Test
    public void testPlace()
        throws NoSpaceException,
               PieceAlreadyInUseException {
        board.placePiece(p11, 0, 0);
        Assert.assertEquals(p11, board.getPieceAt(0, 0));
        Assert.assertTrue(board.getPieceAt(1, 0) instanceof NullPiece);
        Assert.assertTrue(board.getPieceAt(0, 1) instanceof NullPiece);
    }

    @Test
    public void testPlaceNear()
        throws NoSpaceException,
               PieceAlreadyInUseException {
        board.placePiece(p11, 0, 0);
        board.placePiece(p22, 0, 1);
        board.placePiece(p21, 1, 0);
    }

    @Test(expected= NoSpaceException.class)
    public void testPlaceNoSpaceException()
        throws NoSpaceException,
               PieceAlreadyInUseException {
        board.placePiece(p22, 0, 0);
        board.placePiece(p11, 1, 1);
    }

    @Test
    public void testIsWithinBoard() {
        Assert.assertTrue(board.isWithinBoard(0, 0, 1, 1));
        Assert.assertTrue(board.isWithinBoard(SIZE_X - 1, SIZE_Y - 1, 1, 1));
        Assert.assertTrue(board.isWithinBoard(0, 0, SIZE_X, SIZE_Y));
        Assert.assertTrue(board.isWithinBoard(SIZE_X/2, 0, 1, 2));
        Assert.assertTrue(board.isWithinBoard(0, SIZE_Y/2, 1, 2));

        Assert.assertFalse(board.isWithinBoard(SIZE_X, SIZE_Y - 1, 1, 1));
        Assert.assertFalse(board.isWithinBoard(SIZE_X - 1, SIZE_Y, 1, 1));
        Assert.assertFalse(board.isWithinBoard(SIZE_X - 1, SIZE_Y - 1, 2, 1));
        Assert.assertFalse(board.isWithinBoard(SIZE_X - 1, SIZE_Y - 1, 1, 2));
    }

    @Test
    public void testRemovePiece()
        throws NoSpaceException,
               PieceAlreadyInUseException {
        board.placePiece(p11, 0, 0);
        board.placePiece(p22, 1, 1);

        board.removePiece(p11);
        Assert.assertTrue(board.getPieceAt(0, 0) instanceof NullPiece);
        Assert.assertEquals(p22, board.getPieceAt(1, 1));

        board.removePiece(p22);
        Assert.assertTrue(board.getPieceAt(1, 1) instanceof NullPiece);
    }

    @Test(expected = Board.PieceAlreadyInUseException.class)
    public void testDoublePiecePlacement()
        throws Board.NoSpaceException,
               Board.PieceAlreadyInUseException {
        board.placePiece(p11, 0, 0);
        board.placePiece(p11, 1, 1);
    }

 
}
