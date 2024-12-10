package ru.itis.models;

import java.util.List;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Title {

    private Long id;
    private String name;
    private String description;
    private String type;
    private String coverImage;
    private Long authorId;
    private List<Genre> genres;
}


