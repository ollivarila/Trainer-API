package com.example.trainerapi.unit;

import com.example.trainerapi.models.entities.User;
import com.example.trainerapi.security.util.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JwtTokenUtilTest {

    @Test
    public void generatesToken(){
        String token = JwtTokenUtil.generate("test");
        String user = JwtTokenUtil.getUsername(token);
        assertThat(user).isEqualTo("test");
    }

    @Test
    public void validatesToken(){
        String token = JwtTokenUtil.generate("test");
        User user = new User();
        user.setUsername("test");
        boolean valid = JwtTokenUtil.validate(token, user);
        assertThat(valid).isTrue();
    }

    @Test
    public void validatesTokenWithWrongUser(){
        String token = JwtTokenUtil.generate("test");
        User user = new User();
        user.setUsername("wrong");
        boolean valid = JwtTokenUtil.validate(token, user);
        assertThat(valid).isFalse();
    }

    @Test
    public void getsUsername(){
        String token = JwtTokenUtil.generate("test");
        String user = JwtTokenUtil.getUsername(token);
        assertThat(user).isEqualTo("test");
    }
}
