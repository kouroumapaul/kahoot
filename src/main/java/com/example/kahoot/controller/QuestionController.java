package com.example.kahoot.controller;

import com.example.kahoot.dto.question.QuestionDto;
import com.example.kahoot.dto.question.QuestionSummaryDto;
import com.example.kahoot.model.question.Question;
import com.example.kahoot.repository.KahootRepository;
import com.example.kahoot.service.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final KahootRepository kahootRepository;

    @Autowired
    public QuestionController(QuestionService questionService, KahootRepository kahootRepository){
        this.questionService = questionService;
        this.kahootRepository = kahootRepository;
    }

    @PostMapping
    public ResponseEntity<QuestionSummaryDto> createQuestion(@RequestBody QuestionDto questionDTO) {
        if(!kahootRepository.existsById(questionDTO.getKahootId())){
            throw new IllegalArgumentException("Kahoot does not exist");
        }

        QuestionSummaryDto question = questionService.createQuestion(questionDTO);
        return ResponseEntity.ok(question);
    }
}
