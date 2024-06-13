package com.cjs.web;

import com.cjs.web.dto.PostsResponseDto;
import com.cjs.web.dto.PostsSaveRequestDto;
import com.cjs.web.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class PostsApiController {
    @Autowired
    private PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto ){
        return postsService.save(requestDto);
    }


    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsSaveRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto inquiry(@PathVariable Long id) {
        System.out.println("PostsApiController findById id : "+id);
        return postsService.findById(id);
    }

    @GetMapping("/api/v1/postsAll")
    public List<PostsResponseDto> inquiryList() {
        return postsService.findAll();
    }


}
