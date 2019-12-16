package com.donghun.reactiveblog.handler;

import com.donghun.reactiveblog.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

/**
 * @author donghL-dev
 * @since  2019-12-14
 */
class TagHandlerTest extends BaseHandlerTest {

    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    public void init() {
        tagRepository.deleteAll().subscribe();
    }

    @Test
    @DisplayName("태그 목록을 조회하여 가져오는 API 테스트")
    public void getTagsRouteTest() {
        webTestClient.get()
                .uri("/api/tags")
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isOk();
    }

}