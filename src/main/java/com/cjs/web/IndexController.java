package com.cjs.web;

import com.cjs.web.dto.PostsResponseDto;
import com.cjs.web.dto.SessionUser;
import com.cjs.web.entity.User;
import com.cjs.web.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        //, @LoginUser SessionUser user
        model.addAttribute("posts", postsService.findAllDesc());
        SessionUser user = (SessionUser) httpSession.getAttribute("user"); // @
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }


    @GetMapping("/posts/save")
    public String save(){
        return "posts-save";   // posts-save.mustache 화면 표시
    }



    @GetMapping("/temp2")
    public String index(){
        System.out.println("IndexController");
        return "index_temp2";
    }

    @GetMapping("/temp")
    public String index_temp(){
        System.out.println("IndexController");
        return "index_temp";
    }

}

