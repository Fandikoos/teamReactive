package com.svalero.teamreactive.service;

import com.svalero.teamreactive.domain.Team;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TeamService {

    Flux<Team> findAll();
    Mono<Team> findByName(String name);
    Mono<Team> findById(String id);
    Mono<Team> addTeam(Team team);
    Mono<Void> deleteTeam(String id);
}
