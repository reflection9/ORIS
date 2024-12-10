package ru.itis.models;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTitle {
    private Long userId;
    private Long titleId;
    private String status;
}
