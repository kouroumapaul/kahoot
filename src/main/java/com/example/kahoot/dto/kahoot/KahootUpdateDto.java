package com.example.kahoot.dto.kahoot;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class KahootUpdateDto {
    private String title;

    private String description;

    private Boolean isPublic;

    private String coverImageURL;
}
