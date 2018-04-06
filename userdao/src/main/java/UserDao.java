import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private final ConnectionMaker ConnectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.ConnectionMaker = connectionMaker; //의존성을 클라이언트에게 넘김
    }

    public User get(int id) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user;
        try {
            //Connection
            connection = ConnectionMaker.getConnection();

            //sql 작성 (PreparedStatement = statement를 상속받는 인터페이스로 SQL구문을 실행시키는 기능을 갖는 객체)
            preparedStatement = connection.prepareStatement("select * from userinfo where id = ?");
            preparedStatement.setInt(1, id);

            //sql 실행
            resultSet = preparedStatement.executeQuery();

            //결과를 User 에 매핑
            resultSet.next();
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
        } finally {
            //자원 해지
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                try {
                    connection.close(); //예외가 throws된 상태면 close가 일어나지 않아서 반복될 경우 서버가 멈출 수 있음 → try-finally
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        //결과 리턴
        return user;
    }


    public Integer insert(User user) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer id;
        try {
            connection = ConnectionMaker.getConnection();

            preparedStatement = connection.prepareStatement("insert into userinfo(name, password) values(?,?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("select last_insert_id()");

            resultSet = preparedStatement.executeQuery();

            resultSet.next();

            id = resultSet.getInt(1);
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return id;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        return ConnectionMaker.getConnection();
    }
}
