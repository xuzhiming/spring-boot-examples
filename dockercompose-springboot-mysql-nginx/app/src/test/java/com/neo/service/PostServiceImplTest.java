package com.neo.service;

import com.neo.ESApplication;
import com.neo.entity.Post;
import com.neo.entity.Tag;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.*;

/**
 * Created by xzm on 2019/6/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = ESApplication.class)
@SpringBootTest(classes = ESApplication.class)
public class PostServiceImplTest {
    @Autowired
    private PostService postService;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Before
    public void before() {
        elasticsearchTemplate.deleteIndex(Post.class);
        elasticsearchTemplate.createIndex(Post.class);
        elasticsearchTemplate.putMapping(Post.class);
        elasticsearchTemplate.refresh(Post.class);
    }

//    @Test
    public void testSave() throws Exception {
        Tag tag = new Tag();
        tag.setId("1");
        tag.setName("tech");
        Tag tag2 = new Tag();
        tag2.setId("2");
        tag2.setName("elasticsearch");

        Post post = new Post();
        post.setId("1");
        post.setTitle("Bigining with spring boot application and elasticsearch");
        post.setTags(Arrays.asList(tag, tag2));
        Post ret = postService.save(post);

        assertThat(ret.getId(), notNullValue());

        Post post2 = new Post();
        post2.setId("1");
        post2.setTitle("Bigining with spring boot application");
        post2.setTags(Arrays.asList(tag));
        ret = postService.save(post2);
        assertThat(ret.getId(), notNullValue());




    }

    @Test
    public void testFindOne() throws Exception {
        testSave();
        assertThat(postService.findOne("none"), is(Optional.empty()));
        assertThat(postService.findOne("1"), notNullValue());
    }

    public void testFindAll() throws Exception {

    }
    @Test
    public void testFindByTagsName() throws Exception {
        Tag tag = new Tag();
        tag.setId("1");
        tag.setName("tech");
        Tag tag2 = new Tag();
        tag2.setId("2");
        tag2.setName("elasticsearch");

        Post post = new Post();
        post.setId("1");
        post.setTitle("Bigining with spring boot application and elasticsearch");
        post.setTags(Arrays.asList(tag, tag2));
        post = postService.save(post);



        Post post2 = new Post();
        post2.setId("1");
        post2.setTitle("Bigining with spring boot application");
        post2.setTags(Arrays.asList(tag));
        post2 = postService.save(post2);

        Page<Post> posts  = postService.findByTagsName("tech", PageRequest.of(0,10));
        Page<Post> posts2  = postService.findByTagsName("tech", PageRequest.of(0,10));
        Page<Post> posts3  = postService.findByTagsName("maz", PageRequest.of(0,10));


        assertThat(posts.getTotalElements(), is(1L));
        assertThat(posts2.getTotalElements(), is(1L));
        assertThat(posts3.getTotalElements(), is(0L));
    }
}