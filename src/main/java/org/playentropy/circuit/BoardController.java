package org.playentropy.circuit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.playentropy.user.User;
import org.playentropy.user.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.Collection;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestParam;
import org.playentropy.circuit.VectorEditor;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;


@Controller
public class BoardController {

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Vector.class, new VectorEditor());
    }

    @ModelAttribute("availablePieces")
    public Collection<Piece> availablePieces() {
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        pieces.add(new PowerSourcePiece(new Energy(100, 100)));
        pieces.add(new WirePiece());
        return pieces;
    }

    @RequestMapping(value="/users/{id}/board", method=RequestMethod.GET)
    public String showBoard(@PathVariable String id, Model model) {
        User user = userService.findById(id);
        boardService.createBoardForUser(user);
        model.addAttribute("user", user);
        model.addAttribute("board", user.getBoard());
        return "circuit/board";
    }

    @RequestMapping(value="/users/{id}/board", method=RequestMethod.POST)
    public String updateBoard(@PathVariable String id, Model model,
                              @RequestParam(value="position") Vector position) {
        User user = userService.findById(id);
        boardService.createBoardForUser(user);
        boardService.placePieceOnUserBoard(user, new WirePiece(), position);
        model.addAttribute("user", user);
        model.addAttribute("board", user.getBoard());
        return "circuit/board";
    }
}
