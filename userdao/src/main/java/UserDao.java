import java.sql.*;

public class UserDao {
    private final JdbcContext jdbcContext;

    public UserDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public User get(int id) throws SQLException {
        int id1 = id;
        //람다표현식
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from userinfo where id = ?");
            preparedStatement.setInt(1, id);
            return preparedStatement;
        };
        // 콜백패턴
//       StatementStrategy statementStrategy = new StatementStrategy() {
//            private Integer id = id1;
//
//            @Override
//            public PreparedStatement makeStatement(Connection connection) throws SQLException {
//                PreparedStatement preparedStatement = connection.prepareStatement("select * from userinfo where id = ?");
//                preparedStatement.setInt(1, id);
//                return preparedStatement;
//            }
//        };
        return jdbcContext.jdbcContextForGet(statementStrategy);
    }

    public Integer insert(User user) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into userinfo(name, password) values(?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            return preparedStatement;
        };
        return jdbcContext.jdbcContextForInsert(statementStrategy);
    }

    public void update(User user) throws SQLException {
        StatementStrategy statementStrategy = connection ->  {
            PreparedStatement preparedStatement = connection.prepareStatement("update userinfo set name = ?, password = ? where id = ?");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getId());
            return preparedStatement;
        };
        jdbcContext.jdbcContextForUdate(statementStrategy);
    }

    public void delete(Integer id) throws SQLException {
        StatementStrategy statementStrategy = connection ->  {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from userinfo where id = ?");
            preparedStatement.setInt(1, id);
            return preparedStatement;
        };
        jdbcContext.jdbcContextForUdate(statementStrategy);
    }
}
