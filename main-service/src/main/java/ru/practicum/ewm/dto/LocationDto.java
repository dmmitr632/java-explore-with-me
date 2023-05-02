package ru.practicum.ewm.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private float lat;
    private float lon;
}