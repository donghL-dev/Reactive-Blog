package com.donghun.reactiveblog.domain;


import com.donghun.reactiveblog.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-02
 */
public class CommentTest {

    @Test
    @DisplayName("Comment 객체 생성 테스트")
    public void commentCreateTest() {

        // given
        String body = "게시글이 멋집니다.";
        String author = "Test_USER";
        String slug = "Hello-World";

        // when
        Comment comment = Comment.builder()
                .id(UUID.randomUUID().toString())
                .body(body)
                .author(author)
                .slug(slug)
                .createdAt(LocalDateTime.now())
                .build();

        // then
        then(comment).isNotNull();
        then(comment.getBody()).isEqualTo(body);
        then(comment.getAuthor()).isEqualTo(author);
        then(comment.getSlug()).isEqualTo(slug);
    }
}