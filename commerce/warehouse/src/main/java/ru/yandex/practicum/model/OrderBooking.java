package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "order_booking")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "order_id")
    private UUID orderId;
    @Column(name = "delivery_id")
    private UUID deliveryId;
}
