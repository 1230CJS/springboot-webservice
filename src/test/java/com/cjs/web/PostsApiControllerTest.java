package com.cjs.web;

import com.cjs.web.dto.PostsResponseDto;
import com.cjs.web.dto.PostsSaveRequestDto;
import com.cjs.web.entity.Posts;
import com.cjs.web.repository.PostsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }


    @Test
    @DisplayName("Posts 등록된다.")
    public void test_postCreate() {
        // given
        String expectedTitle = "title";
        String expectedContent = "content";

        PostsSaveRequestDto requestDto
                = PostsSaveRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .author("author")
                .build();

        String url = "http://localhost:"+port+"/api/v1/posts";
        System.out.println("test CJS  url : "+url);

        //when
        ResponseEntity<Long> responseEntity
                = restTemplate.postForEntity(url,requestDto,Long.class);

        //then
        assertThat( responseEntity.getStatusCode() ).isEqualTo(HttpStatus.OK);
        assertThat( responseEntity.getBody() ).isGreaterThan( 0L );

        List<Posts> postsList = postsRepository.findAll();

        assertThat( postsList.get(0).getTitle() ).isEqualTo(expectedTitle);
        assertThat( postsList.get(0).getContent() ).isEqualTo(expectedContent);
    }

    @Test
    @DisplayName("Posts 수정된다.")
    public void Posts_modify() throws Exception {

        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "corrtent2";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder().title(expectedTitle).content(expectedContent).build();
        String url = "http://localhost:" + port + "/api/v1/posts/"+ updateId;
        HttpEntity<PostsSaveRequestDto> requestEntity = new HttpEntity<>(requestDto);
        System.out.println("test CJS  url : "+url);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //than
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();

        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

    @Test
    @DisplayName("Posts 조회된다.")
    public void Posts_inquiry() throws Exception {
        // given
        String expectedTitle = "title";
        String expectedContent = "content";

        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .author("author")
                .build());

        Long inquiryId = savedPosts.getId();
        String url = "http://localhost:" + port + "/api/v1/posts/"+ inquiryId;
        System.out.println("test CJS  url : "+url);

        //when
        ResponseEntity<PostsResponseDto> responseEntity = restTemplate.getForEntity(url, PostsResponseDto.class);
        System.out.println("test CJS  responseEntity : "+responseEntity);
        System.out.println("test CJS  responseEntity.getBody().getContent() : "+responseEntity.getBody().getContent());
        System.out.println("test CJS  responseEntity.getBody().getTitle() : "+responseEntity.getBody().getTitle());

        //than
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(expectedTitle).isEqualTo(responseEntity.getBody().getTitle());
        assertThat(expectedContent).isEqualTo(responseEntity.getBody().getContent());
    }

    @Test
    @DisplayName("Posts 모두 조회된다.")
    public void Posts_inquirAll() throws Exception {
        // given
        String expectedTitle = "title";
        String expectedContent = "content";

        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .author("author")
                .build());

        String url = "http://localhost:" + port + "/api/v1/postsAll";
        System.out.println("test CJS  url : "+url);

        //when
        ResponseEntity<List<PostsResponseDto>> responseEntity = restTemplate.exchange(url,HttpMethod.GET, null, new ParameterizedTypeReference<List<PostsResponseDto>>() {});

        System.out.println("test CJS  responseEntity : "+responseEntity);
        System.out.println("test CJS  responseEntity.getBody().getContent() : "+responseEntity.getBody().get(0).getContent());
        System.out.println("test CJS  responseEntity.getBody().getTitle() : "+responseEntity.getBody().get(0).getTitle());

        //than
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(expectedTitle).isEqualTo(responseEntity.getBody().get(0).getTitle());
        assertThat(expectedContent).isEqualTo(responseEntity.getBody().get(0).getContent());
    }
}
