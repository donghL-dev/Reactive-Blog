package com.donghun.reactiveblog.repository;

import com.donghun.reactiveblog.domain.Fallow;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author donghL-dev
 * @since  2019-12-03
 */
public interface FallowRepository extends ReactiveMongoRepository<Fallow, String> {
}
