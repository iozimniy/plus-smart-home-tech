package ru.yandex.practicum.warehouse;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DimensionDto {
    private Double width;
    private Double height;
    private Double depth;
}
