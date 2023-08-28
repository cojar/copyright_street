package com.sbp.copyrightStreet.boundedContext.storequestion;



import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public void create(Product product, Member member, String content) {
        Question question = Question.builder()
                .member(member)
                .product(product)
                .content(content)
                .build();
        this.questionRepository.save(question);
    }

    public Question getQuestion(Long id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new RuntimeException("question not found");
        }
    }

    public void modify(Question question, String content) {
        Question modifyQuestion =
                question.toBuilder()
                        .content(content)
                        .build();
        this.questionRepository.save(modifyQuestion);
    }

    public void delete(Question question) {
        this.questionRepository.delete(question);
    }
}