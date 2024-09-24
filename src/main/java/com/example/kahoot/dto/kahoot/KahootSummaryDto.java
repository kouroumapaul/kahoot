package com.example.kahoot.dto.kahoot;

import com.example.kahoot.dto.user.UserSummaryDto;
import lombok.Data;
import java.util.Date;

@Data
public class KahootSummaryDto {
    private Long id;
    private String title;
    private String description;
    private boolean isPublic;
    private String coverImageURL;
    private UserSummaryDto creator;
    private int questionCount;
    private Date createdAt;
}
