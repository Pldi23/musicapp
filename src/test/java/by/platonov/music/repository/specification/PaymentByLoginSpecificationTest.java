package by.platonov.music.repository.specification;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Payment;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-08-08.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class PaymentByLoginSpecificationTest {

    @Test
    void toPreparedStatement() throws RepositoryException {
        List<Payment> actual = PaymentRepository.getInstance().query(new PaymentByLoginSpecification("pldi"));
        List<Payment> expected = List.of(Payment.builder()
                .id(1)
                .amount(new BigDecimal("4.99"))
                .payerName("pldi")
                .cardNumber("0123456789123456")
                .dateTime(LocalDateTime.now())
                .build());

        assertEquals(expected, actual);

    }
}