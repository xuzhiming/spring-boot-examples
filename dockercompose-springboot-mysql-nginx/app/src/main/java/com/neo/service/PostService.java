package com.neo.service;

import com.neo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface PostService {
    Post save(Post post);

    Optional<Post> findOne(String id);

    Iterable<Post> findAll();

    Page<Post> findByTagsName(String tagName, PageRequest pageRequest);
}
