import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PostgreDbConfigTest {

    private Connection getNewConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:3309/controltest";
        String user = "root";
        String passwd = "root";
        return DriverManager.getConnection(url, user, passwd);
    }

    @Test
    public void shouldGetJdbcConnection() throws SQLException, ClassNotFoundException {
        try(Connection connection = getNewConnection()) {
            assertTrue(connection.isValid(1));
            assertFalse(connection.isClosed());
        }
    }
}
