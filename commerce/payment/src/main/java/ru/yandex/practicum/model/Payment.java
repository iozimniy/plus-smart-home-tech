package ru.yandex.practicum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import ru.yandex.practicum.payment.PaymentState;

import java.util.UUID;

@Entity
@Table(name = "payments")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Payment {
    @Id
    @Column(name = "payment_id")
    private UUID id;
    @Column(name = "order_id")
    private UUID orderId;
    private Double total;
    @Column(name = "products_total")
    private Double productsTotal;
    @Column(name = "delivery_total")
    private Double deliveryTotal;
    @Column(name = "fee_total")
    private Double feeTotal;
    private PaymentState state;
}
