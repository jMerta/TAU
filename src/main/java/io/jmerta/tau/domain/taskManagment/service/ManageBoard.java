package io.jmerta.tau.domain.taskManagment.service;

import io.jmerta.tau.domain.accountManagment.entity.Account;
import io.jmerta.tau.domain.taskManagment.entity.Board;
import io.jmerta.tau.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageBoard {

    private BoardRepository boardRepository;
    private ManageCategory manageCategory;
    private ManageTask manageTask;

    @Autowired
    public ManageBoard(BoardRepository boardRepository, ManageCategory manageCategory, ManageTask manageTask) {
        this.boardRepository = boardRepository;
        this.manageCategory = manageCategory;
        this.manageTask = manageTask;
    }


    public List<Board> getAllBoardsByAccountId(long accountId) {

        return boardRepository.getAllBoardsByUser(accountId);
    }


    public void createNewBoard(Board board) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (board.getAccountId() == account.getId()) {
            boardRepository.createNewBoard(board);
        }
    }

    public void deleteBoard(long id) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (boardRepository.getBoardById(id).getAccountId() == account.getId()) {
            boardRepository.deleteBoard(id);
        }
    }

    public void updateBoard(Board board) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (board.getAccountId() == account.getId()) {
            boardRepository.updateBoard(board);
        }
    }
}
