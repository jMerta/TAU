package io.jmerta.tau.domain.taskManagment.transport;

import io.jmerta.tau.domain.taskManagment.entity.Board;
import io.jmerta.tau.domain.taskManagment.service.ManageBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private ManageBoard manageBoard;

    @Autowired
    public BoardController(ManageBoard manageBoard) {
        this.manageBoard = manageBoard;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Board> createNewBoard(@RequestBody Board board) {
        manageBoard.createNewBoard(board);

        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteBoard(@PathVariable("id") long id) {
        manageBoard.deleteBoard(id);
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Board> editBoard(@RequestBody Board board) {
        manageBoard.updateBoard(board);

        return new ResponseEntity<>(board, HttpStatus.OK);
    }
}
