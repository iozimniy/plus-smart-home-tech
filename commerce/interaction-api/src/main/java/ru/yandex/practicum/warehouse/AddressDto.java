package ru.yandex.practicum.warehouse;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    String country;
    String city;
    String street;
    String house;
    String flat;
}
