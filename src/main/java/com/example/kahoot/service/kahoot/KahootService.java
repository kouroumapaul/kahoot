package com.example.kahoot.service.kahoot;

import com.example.kahoot.dto.kahoot.KahootCreateDto;
import com.example.kahoot.dto.kahoot.KahootDto;
import com.example.kahoot.exception.ResourceNotFoundException;
import com.example.kahoot.mapper.KahootMapper;
import com.example.kahoot.model.Kahoot;
import com.example.kahoot.model.User;
import com.example.kahoot.repository.KahootRepository;
import com.example.kahoot.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class KahootService {

    private final KahootRepository kahootRepository;
    private final KahootMapper kahootMapper;
    private final UserRepository userRepository;


    public KahootService(KahootRepository kahootRepository, KahootMapper kahootMapper, UserRepository userRepository) {
        this.kahootRepository = kahootRepository;
        this.kahootMapper = kahootMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    public KahootDto createKahoot(KahootCreateDto kahootCreateDto) {
        User creator = userRepository.findById(kahootCreateDto.getCreatorId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", kahootCreateDto.getCreatorId()));

        Kahoot kahoot = kahootMapper.toKahoot(kahootCreateDto);
        kahoot.setCreator(creator);

        Kahoot savedKahoot = kahootRepository.save(kahoot);
        return kahootMapper.toKahootDto(savedKahoot);
    }
}
