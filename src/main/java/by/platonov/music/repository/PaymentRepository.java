package by.platonov.music.repository;

import by.platonov.music.entity.Payment;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.extractor.PaymentResultSetExtractor;
import by.platonov.music.repository.mapper.SetPaymentInsertMapper;
import by.platonov.music.repository.mapper.SetPaymentUpdateUserMapper;
import by.platonov.music.repository.specification.SqlSpecification;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * a class that interacts with the database and accumulates in itself methods to add or select a {@link Payment}
 * methods update and remove throws {@link UnsupportedOperationException}
 * @author Dzmitry Platonov on 2019-08-06.
 * @version 0.0.1
 */
@Log4j2
public class PaymentRepository implements Repository<Payment> {

    @Language("SQL")
    private static final String SQL_INSERT_PAYMENT = "INSERT INTO payment(amount, card_number, user_login) VALUES (?, ?, ?) ";
    @Language("SQL")
    private static final String SQL_UPDATE_USER =
            "UPDATE application_user SET paid_period = paid_period + ? * INTERVAL '1' MONTH WHERE application_user.login = ? ";
    @Language("SQL")
    private static final String SQL_SELECT_PAYMENT = "SELECT id, amount, card_number, user_login, created_at FROM payment ";

    private static PaymentRepository instance;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean create = new AtomicBoolean(false);

    private TransactionHandler transactionHandler;
    private JdbcHelper jdbcHelper;

    private PaymentRepository(TransactionHandler transactionHandler, JdbcHelper jdbcHelper) {
        this.transactionHandler = transactionHandler;
        this.jdbcHelper = jdbcHelper;
    }

    public static PaymentRepository getInstance() {
        if (!create.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new PaymentRepository(new TransactionHandler(), new JdbcHelper());
                    create.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    public boolean add(Payment payment) throws RepositoryException {
        return transactionHandler.transactional(connection -> {
            jdbcHelper.execute(connection, SQL_INSERT_PAYMENT, payment, new SetPaymentInsertMapper());
            jdbcHelper.execute(connection, SQL_UPDATE_USER, payment, new SetPaymentUpdateUserMapper());
            return true;
        });
    }

    @Override
    public boolean remove(Payment payment){
        throw new UnsupportedOperationException("Removing payments unsupported");
    }

    @Override
    public boolean update(Payment payment){
        throw new UnsupportedOperationException("Updating payments unsupported");
    }

    @Override
    public List<Payment> query(SqlSpecification specification) throws RepositoryException {
        return transactionHandler.transactional(connection ->
                jdbcHelper.query(connection, SQL_SELECT_PAYMENT, specification, new PaymentResultSetExtractor()));
    }

    @Override
    public long count(SqlSpecification specification) throws RepositoryException {
        return transactionHandler.transactional(connection ->
                jdbcHelper.count(connection, SQL_SELECT_PAYMENT, specification));
    }
}
