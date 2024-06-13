package com.cjs.web.repository;

import com.cjs.web.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Query(value = "SELECT p.* FROM Posts p ORDER BY p.id DESC", nativeQuery = true)
    List<Posts> findAllDesc();
}

