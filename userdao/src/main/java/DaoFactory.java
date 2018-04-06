import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//얘가 스프링
@Configuration
public class DaoFactory {
    @Bean
    public UserDao UserDao() {
        return new UserDao(ConnectionMaker());
    }

    @Bean
    //반복되는 것은 Refactor + Extract + Method
    public ConnectionMaker ConnectionMaker() {
        return new JejuConnectionMaker();
    }
}
