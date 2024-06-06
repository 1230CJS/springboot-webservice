package com.cjs.web.repository;

import com.cjs.web.entity.Posts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest{
    @Autowired
    private PostsRepository postsRepository;
    @Test
    @DisplayName("생성시간/수정시간 자동화하기 테스트")
    public void test_baseTimeEntityPost(){

        //given
        LocalDateTime now = LocalDateTime.now();

        String title = "테스트 제목";
        String content = "테스트 본문";
        String author = "테스트 저자";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts post = postsList.get(0);

        System.out.println(">>>>>>>>>> nowDate="+now);
        //System.out.println(">>>>>>>>>> CreateDate="+post.getCreatedDate()+", modifiedDate="+post.getModifiedDate() );

        //assertThat( post.getCreatedDate() ).isAfter(now);
        //assertThat( post.getModifiedDate() ).isAfter(now);
    }


}


