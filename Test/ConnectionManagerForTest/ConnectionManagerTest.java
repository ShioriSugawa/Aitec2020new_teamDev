package ConnectionManagerForTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerTest {
    /**
     * データベース接続URL
     */
    private static final String URL = "jdbc:postgresql://localhost:5432/orion";
    //private static final String URL = "jdbc:postgresql://192.168.10.10:5432/orion";
    /**
     * ユーザー名
     */
    private static final String USER = "postgres";
    /**
     * パスワード
     */
    private static final String PASSWORD = "sgwaitec";

    /**
     * データベースへの接続オブジェクトを取得
     *
     * @return データベースへの接続オブジェクト
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {

        Connection test_connection = DriverManager.getConnection(URL, USER, PASSWORD);
        test_connection.setAutoCommit(false);
        return test_connection;
    }

}
