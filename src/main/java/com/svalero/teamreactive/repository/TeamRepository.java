package com.svalero.teamreactive.repository;

import com.svalero.teamreactive.domain.Team;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TeamRepository extends ReactiveMongoRepository<Team, String> {

    Flux<Team> findAll();

    Mono<Team> findByName(String name);


}
