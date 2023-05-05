package ru.practicum.ewm.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@ToString
public class UserDto {
    private String email;
    private Integer id;
    private String name;
}