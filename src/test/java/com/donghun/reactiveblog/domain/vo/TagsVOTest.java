package com.donghun.reactiveblog.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-14
 */
class TagsVOTest {

    @Test
    @DisplayName("TagsVO 객체 생성 테스트")
    public void tagsVOCreateTest() {

        // given
        List<String> tags = Collections.singletonList("react.js");

        // when
        TagsVO tagsVo = new TagsVO(tags);

        // then
        then(tagsVo).isNotNull();
        then(tagsVo.getTags()).isEqualTo(tags);
    }

}