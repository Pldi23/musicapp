package by.platonov.music.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * representation of Payment
 *
 * @author Dzmitry Platonov on 2019-08-06.
 * @version 0.0.1
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Payment extends Entity {

    private long id;
    private BigDecimal amount;
    private String cardNumber;
    private LocalDateTime dateTime;
    private String payerName;

}
