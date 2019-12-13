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
class ArticlePostDTOTest {

    @Test
    @DisplayName("ArticlePostDTO 객체 생성 테스트")
    public void articlePostVODTOCreateTest() {

        // given
        String title = "test_title";
        String description = "TEST_DES";
        String body = "TEXT_BODY";
        Set<String> set = Collections.emptySet();

        // when
        ArticlePostDTO articlePostDTO = ArticlePostDTO.builder()
                                        .body(body)
                                        .description(description)
                                        .tagList(set)
                                        .title(title).build();

        // then
        then(articlePostDTO).isNotNull();
        then(articlePostDTO.getBody()).isEqualTo(body);
        then(articlePostDTO.getDescription()).isEqualTo(description);
        then(articlePostDTO.getTagList()).isEqualTo(set);
        then(articlePostDTO.getTitle()).isEqualTo(title);
    }
}