import static org.hamcrest.MatcherAssert.*; //비교 동작 지원, static으로 import하면 클래스 없이 라이브러리 안의 스태틱메소드 사용 가능
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test; //TestCase지원

import java.sql.SQLException;


public class UserDaoTest {
    private UserDao userDao;

    @Before //Test하기 전에 수행
    public void setup(){
        userDao = new UserDao();
    }

    @Test //메소드 단위로도 테스트 가능
    public void get() throws SQLException, ClassNotFoundException { //Excaption throw시킨 이유 = 처리는 자신이 제일 잘 알 때 처리하는 것.
        int id= 1;
        User user = userDao.get(id);
        assertThat(user.getId(), is(1));
        assertThat(user.getName(), is("허윤호"));
        assertThat(user.getPassword(), is("1234"));
    }

    @Test
    public void add() throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setName("승수");
        user.setPassword("1111");
        //id는 오토인크리먼트기 때문에 따로 지정해줄 필요 없음
        Integer id = userDao.insert(user);
//        User insertedUser = userDao.insert(user);

        User insertedUser = userDao.get(id);
        assertThat(insertedUser.getId(),is(id));
        assertThat(insertedUser.getName(),is(user.getName()));
        assertThat(insertedUser.getPassword(),is(user.getPassword()));

    }
}
