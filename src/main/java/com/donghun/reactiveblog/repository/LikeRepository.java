package com.donghun.reactiveblog.repository;

import com.donghun.reactiveblog.domain.Like;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface LikeRepository extends ReactiveMongoRepository<Like, String> {
}
