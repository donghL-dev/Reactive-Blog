package com.donghun.reactiveblog.repository;

import com.donghun.reactiveblog.domain.Fallow;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FallowRepository extends ReactiveMongoRepository<Fallow, String> {
}
