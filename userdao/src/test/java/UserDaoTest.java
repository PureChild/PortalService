import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;


public class UserDaoTest {

    private UserDao userDao;
    private DaoFactory daoFactory;

    @Before
    public void setup(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        userDao = applicationContext.getBean(UserDao.class);
    }

    @Test //메소드 단위로도 테스트 가능
    public void get() throws SQLException, ClassNotFoundException {
        int id= 1;
        User user = userDao.get(id);
        assertThat(user.getId(), is(1));
        assertThat(user.getName(), is("이승수"));
        assertThat(user.getPassword(), is("1111"));
    }

    @Test
    public void add() throws SQLException, ClassNotFoundException {
        User user = new User();
        Integer id = insertUserTest(user);
        User insertedUser = userDao.get(id);
        assertThat(insertedUser.getId(),is(id));
        assertThat(insertedUser.getName(),is(user.getName()));
        assertThat(insertedUser.getPassword(),is(user.getPassword()));
    }

    @Test
    public void update() throws SQLException, ClassNotFoundException {
        User user = new User();
        Integer id = insertUserTest(user);

        user.setId(id);
        user.setName("이름수정");
        user.setPassword("00000");
        userDao.update(user);

        User updatedUser = userDao.get(id);

        assertThat(updatedUser.getId(), is(user.getId()));
        assertThat(updatedUser.getName(), is(user.getName()));
        assertThat(updatedUser.getPassword(), is(user.getPassword()));
    }

    private Integer insertUserTest(User user) throws ClassNotFoundException, SQLException {
        user.setName("이용자");
        user.setPassword("1234");
        return userDao.insert(user);
    }

    @Test
    public void delete() throws SQLException, ClassNotFoundException {
        User user = new User();
        Integer id = insertUserTest(user);

        userDao.delete(id);

        User deletedUser = userDao.get(id);

        assertThat(deletedUser, nullValue());
    }
}