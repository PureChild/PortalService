import java.sql.*;

public class UserDao {
    public User get(int id) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();

        //sql 작성
        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from userinfo where id = ?");
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
        //mysql driver load
        Connection connection = getConnection();
        //sql 작성
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into userinfo(name, password) values(?,?)");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());

        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement("select last_insert_id()");
        //sql 실행
        ResultSet resultSet = preparedStatement.executeQuery();
        //결과를 User 에 매핑
        resultSet.next();

        Integer id = resultSet.getInt(1);
        //자원 해지
        resultSet.close();
        preparedStatement.close();
        connection.close();

        //결과 리턴
        return id;
    }

    //개발자는 죽을 때까지 반복을 싫어해야 한다. copy & paste로 코딩 후 Test 수행 후 반복되는 동작을 Refactoring. 인텔리제이는 같은 부분을 알아서 다 바꿔줘서 좋음
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        //mysql driver load
        Class.forName("com.mysql.jdbc.Driver");
        //Connection
        return DriverManager.getConnection("jdbc:mysql://localhost/spring?characterEncoding=utf-8"
                , "root", "leess911");
    }
}
