package by.platonov.music.repository.connectionLeak;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-26.
 * @version 0.0.1
 */
public class PostgreSQLIdleConnectionCounter implements IdleConnectionCounter {

    public static final IdleConnectionCounter INSTANCE =
            new PostgreSQLIdleConnectionCounter();

//    @Override
//    public boolean appliesTo(Class<? extends Dialect> dialect) {
//        return PostgreSQL91Dialect.class.isAssignableFrom( dialect );
//    }

    @Override
    public int count(Connection connection) {
        try ( Statement statement = connection.createStatement() ) {
            try ( ResultSet resultSet = statement.executeQuery(
                    "SELECT COUNT(*) " +
                            "FROM pg_stat_activity " +
                            "WHERE state ILIKE '%idle%'" ) ) {
                while ( resultSet.next() ) {
                    return resultSet.getInt( 1 );
                }
                return 0;
            }
        }
        catch ( SQLException e ) {
            throw new IllegalStateException( e );
        }
    }
}
