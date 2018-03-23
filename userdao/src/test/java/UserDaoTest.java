import static org.hamcrest.MatcherAssert.*; //비교 동작 지원, static으로 import하면 클래스 없이 라이브러리 안의 스태틱메소드 사용 가능
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test; //TestCase지원

import java.sql.SQLException;


public class UserDaoTest {

    private UserDao userDao;
    private UserDao hallaUserDao;

    @Before //Test하기 전에 수행
    public void setup(){
        userDao = new JejuUserDao();
        hallaUserDao = new HallaUserDao();
    }

    @Test //메소드 단위로도 테스트 가능
    public void get() throws SQLException, ClassNotFoundException { //Excaption throw시킨 이유 = 예외처리는 자신이 제일 잘 알 때 처리하는 것.
        int id= 1;
        User user = userDao.get(id);
        //assertThat(T actual, Matcher<? super T> matcher)의 형태로 두 값을 비교. 첫번째 파라미터에는 비교대상 값, 두번째 파라미터로는 비교로직이 담긴 Matcher.
        assertThat(user.getId(), is(1));
        assertThat(user.getName(), is("이승수"));
        assertThat(user.getPassword(), is("1111"));
    }

    @Test
    public void add() throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setName("이용자");
        user.setPassword("1234");
        //id는 auto_increment기 때문에 따로 지정해줄 필요 없음
        Integer id = userDao.insert(user);
//        User insertedUser = userDao.insert(user);

        User insertedUser = userDao.get(id);
        assertThat(insertedUser.getId(),is(id));
        assertThat(insertedUser.getName(),is(user.getName()));
        assertThat(insertedUser.getPassword(),is(user.getPassword()));

    }

    @Test //메소드 단위로도 테스트 가능
    public void hallaGet() throws SQLException, ClassNotFoundException { //Excaption throw시킨 이유 = 예외처리는 자신이 제일 잘 알 때 처리하는 것.
        int id= 1;
        User user = hallaUserDao.get(id);
        //assertThat(T actual, Matcher<? super T> matcher)의 형태로 두 값을 비교. 첫번째 파라미터에는 비교대상 값, 두번째 파라미터로는 비교로직이 담긴 Matcher.
        assertThat(user.getId(), is(1));
        assertThat(user.getName(), is("이승수"));
        assertThat(user.getPassword(), is("1111"));
    }

    @Test
    public void hallaAdd() throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setName("이용자");
        user.setPassword("1234");
        //id는 auto_increment기 때문에 따로 지정해줄 필요 없음
        Integer id = hallaUserDao.insert(user);
//        User insertedUser = userDao.insert(user);

        User insertedUser = hallaUserDao.get(id);
        assertThat(insertedUser.getId(),is(id));
        assertThat(insertedUser.getName(),is(user.getName()));
        assertThat(insertedUser.getPassword(),is(user.getPassword()));

    }
}