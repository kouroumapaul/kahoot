package com.example.kahoot.dto.user;

import com.example.kahoot.dto.kahoot.KahootSummaryDto;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
        private String username;
        private String email;
        private String createdAt;
        private List<KahootSummaryDto> kahoots;
}
