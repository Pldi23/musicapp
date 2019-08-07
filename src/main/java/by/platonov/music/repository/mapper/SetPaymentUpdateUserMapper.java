package by.platonov.music.repository.mapper;

import by.platonov.music.entity.Payment;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * to set payments fields to prepared statement which update User's paid-period
 *
 * @author Dzmitry Platonov on 2019-08-06.
 * @version 0.0.1
 */
public class SetPaymentUpdateUserMapper implements PreparedStatementMapper<Payment> {

    private static final ResourceBundle APP_PAYMENT_POLICY = ResourceBundle.getBundle("payment");

    @Override
    public void map(PreparedStatement preparedStatement, Payment payment) throws SQLException {

        int value;
        if (payment.getAmount().equals(new BigDecimal(APP_PAYMENT_POLICY.getString("light.price")))) {
            value = Integer.parseInt(APP_PAYMENT_POLICY.getString("light.period"));
        } else if (payment.getAmount().equals(new BigDecimal(APP_PAYMENT_POLICY.getString("medium.price")))) {
            value = Integer.parseInt(APP_PAYMENT_POLICY.getString("medium.period"));
        } else if (payment.getAmount().equals(new BigDecimal(APP_PAYMENT_POLICY.getString("long.price")))){
            value = Integer.parseInt(APP_PAYMENT_POLICY.getString("long.period"));
        } else {
            value =Integer.parseInt(APP_PAYMENT_POLICY.getString("default.period"));
        }

        preparedStatement.setInt(1, value);
        preparedStatement.setString(2, payment.getPayerName());
    }
}
