import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
Refactor → Extract → Delegate로 여러 곳에서 쓰는 메소드를 하나의 클래스로 빼줌
메소드를 같이 쓰는 경우 abstract
클래스를 같이 쓰는 경우 interface
Strategy Pattern = 알고리즘군을 정의하고 각각을 캡슐화하여 교환해서 사용할 수 있도록 만듦
 */
//public class connectionMaker{
public interface connectionMaker {
//    public connectionMaker() {
//    }
    public Connection getConnection() throws ClassNotFoundException, SQLException;
//    {
//        Class.forName("com.mysql.jdbc.Driver");
//        return DriverManager.getConnection("jdbc:mysql://localhost/spring?characterEncoding=utf-8", "root", "leess911");
//    }
}