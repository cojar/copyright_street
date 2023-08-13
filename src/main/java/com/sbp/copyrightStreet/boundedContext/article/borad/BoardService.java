package com.sbp.copyrightStreet.boundedContext.article.borad;

import com.sbp.copyrightStreet.boundedContext.home.controller.DataNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public List<Board> getList() {
        return this.boardRepository.findAll();
    }

    public void create(String title, String content, String category) {
        Board b = new Board();
        b.setTitle(title);
        b.setContent(content);
        b.setCreateDate(LocalDateTime.now());
        b.setCategory(category);
        this.boardRepository.save(b);

    }

    public Board getBoard(Integer id) {
        Optional<Board> board = this.boardRepository.findById(id);
        if (board.isPresent()) {
            Board board1 = board.get();
            board1.setView(board1.getView() + 1);
            this.boardRepository.save(board1);
            return board1;
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public Page<Board> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.boardRepository.findAll(pageable);
    }

    public void modify(Board board, String title, String content) {
        board.setTitle(title);
        board.setContent(content);
        board.setModifyDate(LocalDateTime.now());
        this.boardRepository.save(board);
    }

    public void delete(Board board  ) {
        this.boardRepository.delete(board);
    }






}
