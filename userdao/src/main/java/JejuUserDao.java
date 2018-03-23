import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JejuUserDao extends UserDao {
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
//        mysql driver load
        Class.forName("com.mysql.jdbc.Driver"); //JDBC = Java Database Connectivity, 데이터베이스에 접근하여 SQL문을 실행하기 위한 자바 라이브러리

        return DriverManager.getConnection("jdbc:mysql://localhost/spring?characterEncoding=utf-8", "root", "leess911");
    }
}
