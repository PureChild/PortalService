import java.sql.*;

public class UserDao {
    private final connectionMaker connectionMaker = new JejuConnectionMaker();//new 키워드를 사용하는 경우 dependency가 생겨버림

    public User get(int id) throws ClassNotFoundException, SQLException {
        //Connection
        Connection connection = connectionMaker.getConnection();

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
        Connection connection = connectionMaker.getConnection();

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
    /*
    요구사항이 들어왔는데 대부분 같고 하나의 메소드가 다를 때
    부분적으로 다르지만 어떻게 다른지 모르면 추상화! → 메소드가 abstract면 클래스도 abstract
    Template Method Pattern = 상위 클래스에서 처리의 흐름을 제어하며, 하위 클래스에서 처리의 내용을 구체화
    Fatory Method Pattern = 인스턴스를 만드는 방법을 상위 클래스 측에서 결정하되, 구체적인 내용은 모두 하위 클래스 측에서 수행
    */
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        //mysql driver load

        return connectionMaker.getConnection();
    }
}
