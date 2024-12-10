package ru.itis.models;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chapter {

    private Long id;
    private String name;
    private String content;
    private Long titleId;
    private int chapterNumber;

}
