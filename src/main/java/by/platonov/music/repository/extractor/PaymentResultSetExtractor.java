package by.platonov.music.repository.extractor;

import by.platonov.music.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * to disassemble result set to list of {@link Payment}
 *
 * @author Dzmitry Platonov on 2019-08-06.
 * @version 0.0.1
 */
public class PaymentResultSetExtractor implements AbstractResultSetExtractor<Payment> {
    @Override
    public List<Payment> extract(ResultSet resultSet) throws SQLException {
        List<Payment> result = new ArrayList<>();
        while (resultSet.next()) {
            Payment payment = Payment.builder()
                    .id(resultSet.getLong(ExtractConstant.ID))
                    .amount(resultSet.getBigDecimal(ExtractConstant.AMOUNT))
                    .cardNumber(resultSet.getString(ExtractConstant.CARD_NUMBER))
                    .dateTime(resultSet.getTimestamp(ExtractConstant.CREATED_AT).toLocalDateTime())
                    .payerName(resultSet.getString(ExtractConstant.USER_LOGIN))
                    .build();
            result.add(payment);
        }
        return result;
    }
}
