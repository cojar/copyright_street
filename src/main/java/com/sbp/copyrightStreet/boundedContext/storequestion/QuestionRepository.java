package com.sbp.copyrightStreet.boundedContext.storequestion;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}