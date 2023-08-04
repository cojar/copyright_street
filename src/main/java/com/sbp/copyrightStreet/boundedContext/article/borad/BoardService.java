package com.sbp.copyrightStreet.boundedContext.article.borad;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public List<Board> getList(){
        return this.boardRepository.findAll();
    }

    public void create(String title, String content){
        Board b = new Board();
        b.setTitle(title);
        b.setContent(content);
        b.setCreateDate(LocalDateTime.now());
        this.boardRepository.save(b);

    }
    public Board getBoard(Long id) {
        Optional<Board> board = this.boardRepository.findById(id);
        return board.get();
    }

}
