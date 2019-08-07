package by.platonov.music.repository.mapper;

import by.platonov.music.entity.Payment;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to set payment fields to prepared statement
 *
 * @author Dzmitry Platonov on 2019-08-06.
 * @version 0.0.1
 */
public class SetPaymentInsertMapper implements PreparedStatementMapper<Payment> {
    @Override
    public void map(PreparedStatement preparedStatement, Payment payment) throws SQLException {
        preparedStatement.setBigDecimal(1, payment.getAmount());
        preparedStatement.setString(2, payment.getCardNumber());
        preparedStatement.setString(3, payment.getPayerName());
    }
}
