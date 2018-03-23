import java.sql.*;

public abstract class UserDao {
    public User get(int id) throws ClassNotFoundException, SQLException {
        //Connection
        Connection connection = getConnection();

        //sql 작성 (PreparedStatement = statement를 상속받는 인터페이스로 SQL구문을 실행시키는 기능을 갖는 객체)
        PreparedStatement preparedStatement = connection.prepareStatement("select * from userinfo where id = ?");
        preparedStatement.setInt(1, id);

        //sql 실행
        ResultSet resultSet = preparedStatement.executeQuery();

        //결과를 User 에 매핑
        resultSet.next();
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));

        //자원 해지
        resultSet.close();
        preparedStatement.close();
        connection.close();

        //결과 리턴
        return user;
    }


    public Integer insert(User user) throws ClassNotFoundException, SQLException {
        //Connection
        Connection connection = getConnection();

        //sql 작성
        PreparedStatement preparedStatement = connection.prepareStatement("insert into userinfo(name, password) values(?,?)");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());

        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement("select last_insert_id()");

        //sql 실행
        ResultSet resultSet = preparedStatement.executeQuery();

        //return해주기 위해 id 변수 선언
        resultSet.next();

        Integer id = resultSet.getInt(1);

        //자원 해지
        resultSet.close();
        preparedStatement.close();
        connection.close();

        //insert 함수의 경우 기능으로만 보면 void로 선언해도 되지만 test를 위해 integer로 선언
        return id;
    }

    //개발자는 죽을 때까지 반복을 싫어해야 한다. copy & paste로 코딩 후 Test 수행 후 반복되는 동작을 Refactoring. 인텔리제이는 같은 부분을 알아서 다 바꿔줘서 좋음
    //Refactor → Extract → Method : Ctrl + Alt + M
    //모르면 추상화! → 메소드가 abstract면 클래스도 abstract
    abstract public Connection getConnection() throws ClassNotFoundException, SQLException;
        //mysql driver load
//        Class.forName("com.mysql.jdbc.Driver"); //JDBC = Java Database Connectivity, 데이터베이스에 접근하여 SQL문을 실행하기 위한 자바 라이브러리
//
//        return DriverManager.getConnection("jdbc:mysql://localhost/spring?characterEncoding=utf-8", "root", "leess911");
}
