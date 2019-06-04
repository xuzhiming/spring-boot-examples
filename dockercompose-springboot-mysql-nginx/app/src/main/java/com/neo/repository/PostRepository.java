package com.neo.repository;

import com.neo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by xzm on 2019/6/4.
 */
public interface PostRepository extends ElasticsearchRepository<Post, String> {
    Page<Post> findByTagsName(String name, Pageable pageable);
}

