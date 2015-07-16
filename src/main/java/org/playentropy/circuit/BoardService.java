package org.playentropy.circuit;

import org.springframework.stereotype.Service;
import org.playentropy.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.playentropy.user.User;

@Service
public class BoardService {

    @Autowired
    private UserRepository userRepository;

    private static final Vector MIN_BOARD_SIZE = new Vector(10, 10);

    public void createBoardForUser(User user) {
        if(user.getBoard() == null) {
            user.setBoard(new Board(MIN_BOARD_SIZE));
        } else {
            if(user.getBoard().getBoardSize().withinField(new Vector(0, 0), MIN_BOARD_SIZE)) {
                user.setBoard(new Board(user.getBoard().getBoard(), MIN_BOARD_SIZE));
            }
        }
        userRepository.save(user);
    }

    public void createBoardForUser() {
    }

    public void placePieceOnUserBoard(User user, Piece piece, Vector position) {
        try {
            user.getBoard().placePiece(piece, position);
            userRepository.save(user);
        } catch(Board.NoSpaceException ex) {
            throw new RuntimeException("no place on baord: " + ex.getMessage(), ex);
        } catch(Board.PieceAlreadyInUseException ex) {
            throw new RuntimeException("piece already in use", ex);
        }
    }

    public void removePieceFromUserBoard(User user, Vector position) {
        Board board = user.getBoard();
        board.removePiece(board.getPieceAt(position));
        userRepository.save(user);
    }
}
