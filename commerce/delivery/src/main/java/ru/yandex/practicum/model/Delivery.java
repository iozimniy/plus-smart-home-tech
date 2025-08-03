package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.delivery.DeliveryState;

import java.util.UUID;

@Entity
@Table(name = "deliveries")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "delivery_id")
    private UUID id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "from_address_id", referencedColumnName = "address_id")
    private Address fromAddress;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "to_address_id", referencedColumnName = "address_id")
    private Address toAddress;
    @Column(name = "order_id")
    private UUID orderId;
    @Enumerated(EnumType.STRING)
    private DeliveryState state;
}
