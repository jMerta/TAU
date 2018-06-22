package io.jmerta.tau.domain.taskManagment.transport;

import io.jmerta.tau.domain.taskManagment.service.ManageBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private ManageBoard manageBoard;

    @Autowired
    public BoardController(ManageBoard manageBoard) {
        this.manageBoard = manageBoard;
    }


    

}
