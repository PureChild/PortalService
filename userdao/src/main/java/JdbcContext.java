import javax.sql.DataSource;
import java.sql.*;

public class JdbcContext {
    final DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    User jdbcContextForGet(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            //Connection
            connection = dataSource.getConnection();

            preparedStatement = statementStrategy.makeStatement(connection);

            //sql 실행
            resultSet = preparedStatement.executeQuery();

            //결과를 User 에 매핑
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }
        } finally {
            //자원 해지
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
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

    Integer jdbcContextForInsert(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer id;
        try {
            connection = dataSource.getConnection();

            preparedStatement = statementStrategy.makeStatement(connection);

            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            id = resultSet.getInt(1);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return id;
    }

    void jdbcContextForUdate(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();

            preparedStatement = statementStrategy.makeStatement(connection);

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("select last_insert_id()");


        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void update(String sql, Object[] params) throws SQLException {
        StatementStrategy statementStrategy = connection ->  {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<params.length;i++){
                preparedStatement.setObject(i+1,params[i]);
            }
            return preparedStatement;
        };
        jdbcContextForUdate(statementStrategy);
    }

    public Integer insert(String sql, Object[] params) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for(int i=0;i<params.length;i++){
                preparedStatement.setObject(i+1,params[i]);
            }
            return preparedStatement;
        };
        return jdbcContextForInsert(statementStrategy);
    }

    public User queryForObject(String sql, Object[] params) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<params.length;i++){
                preparedStatement.setObject(i+1,params[i]);
            }
            return preparedStatement;
        };
        return jdbcContextForGet(statementStrategy);
    }
}