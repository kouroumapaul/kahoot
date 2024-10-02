package com.example.kahoot.service.kahoot;

import com.example.kahoot.dto.kahoot.KahootCreateDto;
import com.example.kahoot.dto.kahoot.KahootDto;
import com.example.kahoot.dto.kahoot.KahootSummaryDto;
import com.example.kahoot.exception.ResourceNotFoundException;
import com.example.kahoot.mapper.KahootMapper;
import com.example.kahoot.model.Kahoot;
import com.example.kahoot.model.User;
import com.example.kahoot.repository.KahootRepository;
import com.example.kahoot.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KahootService {

    private final KahootRepository kahootRepository;
    private final KahootMapper kahootMapper;
    private final UserRepository userRepository;


    @Autowired
    public KahootService(KahootRepository kahootRepository, KahootMapper kahootMapper, UserRepository userRepository) {
        this.kahootRepository = kahootRepository;
        this.kahootMapper = kahootMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    public KahootDto createKahoot(KahootCreateDto kahootCreateDto) {
        User user = userRepository.findById(kahootCreateDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", kahootCreateDto.getUserId()));

        Kahoot kahoot = kahootMapper.INSTANCE.toKahoot(kahootCreateDto);
        user.addKahoot(kahoot);

        Kahoot savedKahoot = kahootRepository.save(kahoot);
        return kahootMapper.INSTANCE.toKahootDto(savedKahoot);
    }

    public KahootDto getKahoot(Long kahootId) {
        Kahoot kahoot = kahootRepository.findById(kahootId)
                .orElseThrow(() -> new ResourceNotFoundException("Kahoot", "id", kahootId));
        return kahootMapper.INSTANCE.toKahootDto(kahoot);
    }

    public Iterable<KahootSummaryDto> findAllKahoots() {
        Iterable<Kahoot> kahoots = kahootRepository.findAll();
        return kahootMapper.INSTANCE.toKahootDtos(kahoots);
    }
}
