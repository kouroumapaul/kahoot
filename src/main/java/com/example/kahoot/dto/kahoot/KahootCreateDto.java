package com.example.kahoot.dto.kahoot;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class KahootCreateDto {
    @NotBlank(message = "Le titre est obligatoire")
    private String title;

    @NotNull(message =" La description est obligatoire")
    private String description;

    @NotNull(message = "Veuillez préciser si le kahoot est public ou non")
    private Boolean isPublic;

    private String coverImageURL;

    @NotNull(message = "L'identifiant de l'utilisateur est obligatoire")
    private Long creatorId;
}
