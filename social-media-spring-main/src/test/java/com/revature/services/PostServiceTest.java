package com.revature.services;

import com.revature.exceptions.NoSuchRecordException;
import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.PostRepository;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private PostService postService;
    private Post post;
    private User user;
    private Comment comment;

    @BeforeEach
    public void setup() {

        post = Post.builder()
                .postId(1)
                .text("Test String")
                .author(user)
                .build();
    }

    @Test
    public void PostService_UpsertPost_ReturnUpsertPost() {

        when(postRepository.save(Mockito.any(Post.class))).thenReturn(post);

        Post savedPost = postService.upsert(post);

        Assertions.assertThat(savedPost).isNotNull();
    }

    @Test
    void PostService_DeletePost_ReturnDeletedPost() {

        int postId = 1;

        willDoNothing().given(postRepository).delete(post);

        postService.deletePost(post);

        verify(postRepository, times(1)).delete(post);
    }

    @Test
    void PostService_GetAll_ReturnAllPosts() {
        // given - precondition or setup

        Post post1 = Post.builder()
                .postId(1)
                .text("Test String")
                .author(user)
                .build();



        given(postRepository.findAll()).willReturn(List.of(post, post1));

        // when -  action or the behaviour that we are going test
