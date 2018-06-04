package com.example.project_practice;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 웹서버를 띄우고 테스트
public class UserTest {
    public static final String PATH = "/api/user";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void get(){
        Integer id = 1;
        String name = "이승수";
        String password = "1111";
        User user = restTemplate.getForObject(PATH + "/" + id, User.class);
        assertThat(user.getId(), is(id));
        assertThat(user.getName(), is(name));
        assertThat(user.getPassword(), is(password));
    }

    @Test
    public void list(){
        List<User> users = restTemplate.getForObject(PATH + "/list", List.class);
        assertThat(users, not(IsEmptyCollection.empty()));
    }

    @Test
    public void create(){
        String name = "이름";
        String password = "1111";
        User createdUser = createUser(name, password);
        validate(name, password, createdUser);
    }

    @Test
    public void modify(){
        String name = "이름";
        String password = "1111";
        User createUser = createUser(name, password);
        createUser.setPassword("12345");
        restTemplate.put(PATH, createUser);
        validate(name, "1234", createUser);
    }

    @Test
    public void delete(){
        String name = "이름";
        String password = "1111";
        User createUser = createUser(name, password);
        validate(name, password, createUser);
        restTemplate.delete(PATH + "/" + createUser.getId());
        User user = restTemplate.getForObject(PATH + "/" + createUser.getId(), User.class);
        assertThat(user.getId(), is(nullValue()));
        assertThat(user.getName(), is(nullValue()));
        assertThat(user.getPassword(), is(nullValue()));
    }

    private void validate(String name, String password, User createdUser) {
        User resultUser =  restTemplate.getForObject(PATH + "/" + createdUser.getId(), User.class);
        assertThat(resultUser.getName(), is(name));
        assertThat(resultUser.getPassword(), is(password));
    }

    private User createUser(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        return restTemplate.postForObject(PATH, user, User.class);
    }
}
