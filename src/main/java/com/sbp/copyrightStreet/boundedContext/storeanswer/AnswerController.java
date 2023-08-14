package com.sbp.copyrightStreet.boundedContext.storeanswer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
}
