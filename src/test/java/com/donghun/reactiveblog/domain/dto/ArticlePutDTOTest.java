package com.donghun.reactiveblog.domain.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-12
 */
class ArticlePutDTOTest {

    @Test
    @DisplayName("ArticlePutDTO 객체 생성 테스트")
    public void articlePutVODTOCreateTest() {

        // given
        String title = "test_title";
        String description = "TEST_DES";
        String body = "TEXT_BODY";
        Set<String> set = Collections.emptySet();

        // when
        ArticlePutDTO articlePutDTO = ArticlePutDTO.builder()
                .body(body)
                .description(description)
                .tagList(set)
                .title(title).build();

        // then
        then(articlePutDTO).isNotNull();
        then(articlePutDTO.getBody()).isEqualTo(body);
        then(articlePutDTO.getDescription()).isEqualTo(description);
        then(articlePutDTO.getTagList()).isEqualTo(set);
        then(articlePutDTO.getTitle()).isEqualTo(title);
    }
}