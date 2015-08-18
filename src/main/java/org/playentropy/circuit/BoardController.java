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
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;
import org.playentropy.user.UserPropertyEditor;
import java.util.Map;
import java.util.HashMap;


@Controller
public class BoardController {

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserPropertyEditor userEditor;

    private Map<String, Piece> piecesMap = new HashMap<>();

    public void addPiece(Piece piece) {
        piecesMap.put(piece.getIdentifier(), piece);
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Vector.class, new VectorEditor());
        binder.registerCustomEditor(User.class, userEditor);

        addPiece(new PowerSourcePiece(new Energy(100, 100)));
        addPiece(new WirePiece());
    }

    @ModelAttribute("piecesMap")
    public Map<String, Piece> piecesMap() {
        return piecesMap;
    }

    @ModelAttribute("selectedPiece") 
    public String getSelectedPiece() {
        return "WirePiece";
    }

    @RequestMapping(value="/users/{id}/board", method=RequestMethod.GET)
    public String showBoard(@PathVariable(value="id") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("board", user.getPlayer().getBoard());
        return "circuit/board";
    }

    @RequestMapping(value="/users/{id}/board", method=RequestMethod.POST)
    public String updateBoard(@PathVariable(value="id") User user, Model model,
                              @RequestParam(value="position") Vector position,
                              @RequestParam(value="piece") String identifier) {
        boardService.placePieceOnUserBoard(user, piecesMap.get(identifier), position);
        model.addAttribute("user", user);
        model.addAttribute("board", user.getPlayer().getBoard());
        model.addAttribute("selectedPiece", identifier);
        return "circuit/board";
    }
}
