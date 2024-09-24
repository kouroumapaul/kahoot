package com.example.kahoot.dto.kahoot;

import com.example.kahoot.dto.question.QuestionSummaryDto;
import com.example.kahoot.dto.user.UserSummaryDto;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class KahootDto {
    private Long id;
    private String title;
    private String description;
    private boolean isPublic;
    private String coverImageURL;
    private UserSummaryDto creator;
    private List<QuestionSummaryDto> questions;
    private Date createdAt;
}
