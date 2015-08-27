package org.playentropy.circuit;

import org.springframework.stereotype.Service;
import org.playentropy.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.playentropy.user.User;
import org.playentropy.player.Player;

@Service
public class BoardService {

    @Autowired
    private UserRepository userRepository;

    private static final Vector MIN_BOARD_SIZE = new Vector(10, 10);

    public void createBoardForUser(User user) {
        Player player = user.getPlayer();
        if(player.getBoard() == null) {
            player.setBoard(new Board(MIN_BOARD_SIZE));
        } else {
            // increase default board size if necessary
            if(player.getBoard().getBoardSize().withinField(new Vector(0, 0), MIN_BOARD_SIZE)) {
                player.setBoard(new Board(player.getBoard(), MIN_BOARD_SIZE));
            }
        }
        userRepository.save(user);
    }

    public void placePieceOnUserBoard(User user, Piece piece, Vector position) {
        try {
            user.getPlayer().getBoard().placePiece(piece, position);
            userRepository.save(user);
        } catch(Board.NoSpaceException ex) {
            throw new RuntimeException("no place on board: " + ex.getMessage(), ex);
        } catch(Board.PieceAlreadyInUseException ex) {
            throw new RuntimeException("piece already in use", ex);
        }
    }

    public void removePieceFromUserBoard(User user, Vector position) {
        Board board = user.getPlayer().getBoard();
        board.removePiece(board.getPieceAt(position));
        userRepository.save(user);
    }
}
