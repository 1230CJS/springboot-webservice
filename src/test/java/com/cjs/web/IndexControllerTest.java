package com.cjs.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    //@Test
    @DisplayName("메인페이지로 로딩")
    public void test_index(){
        //given
        String url = "/temp";
        String expected = "스트링 부트로 시작하는 웹서비스";

        //when
        String body = testRestTemplate.getForObject( url, String.class);

        //then
        assertThat(body).contains(expected);
    }
}

