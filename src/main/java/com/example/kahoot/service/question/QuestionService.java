package com.example.kahoot.service.question;

import com.example.kahoot.dto.answer.AnswerDto;
import com.example.kahoot.dto.question.QuestionDto;
import com.example.kahoot.dto.question.QuestionSummaryDto;
import com.example.kahoot.exception.ResourceNotFoundException;
import com.example.kahoot.exception.InvalidQuestionTypeException;
import com.example.kahoot.mapper.QuestionMapper;
import com.example.kahoot.model.Answer;
import com.example.kahoot.model.Kahoot;
import com.example.kahoot.model.question.Question;
import com.example.kahoot.model.question.MultiChoiceQuestion;
import com.example.kahoot.model.question.TrueFalseQuestion;
import com.example.kahoot.repository.AnswerRepository;
import com.example.kahoot.repository.KahootRepository;
import com.example.kahoot.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final AnswerRepository answerRepository;
    private final KahootRepository kahootRepository;

    public QuestionService(QuestionRepository questionRepository,
                           QuestionMapper questionMapper,
                           AnswerRepository answerRepository,
                           KahootRepository kahootRepository) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.answerRepository = answerRepository;
        this.kahootRepository = kahootRepository;
    }

    public QuestionSummaryDto createQuestion(QuestionDto questionDto) {
        if (!kahootRepository.existsById(questionDto.getKahootId())) {
            throw new ResourceNotFoundException("Kahoot", "id", questionDto.getKahootId());
        }

        Question question = switch (questionDto.getQuestionType()) {
            case "CHOIX_MULTIPLE" -> createMultiChoiceQuestion(questionDto);
            case "VRAI_FAUX" -> createTrueFalseQuestion(questionDto);
            default -> throw new InvalidQuestionTypeException("Question type " + questionDto.getQuestionType() + " is not supported");
        };

        Question questionSaved = questionRepository.save(question);
        return questionMapper.INSTANCE.toDto(questionSaved);
    }

    private MultiChoiceQuestion createMultiChoiceQuestion(QuestionDto dto) {
        Kahoot kahoot = kahootRepository.getReferenceById(dto.getKahootId());

        MultiChoiceQuestion question = new MultiChoiceQuestion();
        question.setKahoot(kahoot);
        question.setCreatedAt(new Date());
        question.setContent(dto.getContent());
        question.setQuestionOrder(dto.getQuestionOrder());
        question.setPoints(dto.getPoints());

        List<Answer> answers = new ArrayList<>();
        for (AnswerDto answerDTO : dto.getAnswers()) {
            Answer answer = new Answer();
            answer.setContent(answerDTO.getContent());
            answer.setIsCorrect(answerDTO.getIsCorrect());
            answer.setQuestion(question);
            question.addAnswer(answer);
        }

        answerRepository.saveAll(answers);
        return questionRepository.save(question);
    }

    private TrueFalseQuestion createTrueFalseQuestion(QuestionDto dto) {
        Kahoot kahoot = kahootRepository.getReferenceById(dto.getKahootId());

        TrueFalseQuestion question = new TrueFalseQuestion();
        question.setKahoot(kahoot);
        question.setIsCorrect(dto.getIsCorrect());
        question.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : new Date());
        question.setContent(dto.getContent());
        question.setQuestionOrder(dto.getQuestionOrder());

        return question;
    }

    public boolean checkAnswer(Long questionId, Object userAnswer) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));

        return question.checkAnswer(userAnswer);
    }
}