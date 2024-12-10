package ru.itis.models;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Comment {
    private Long id;
    private Long chapterId;
    private Long userId;
    private String content;
}
