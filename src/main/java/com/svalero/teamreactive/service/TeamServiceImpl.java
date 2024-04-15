package com.svalero.teamreactive.service;

import com.svalero.teamreactive.domain.Team;
import com.svalero.teamreactive.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TeamServiceImpl implements TeamService{

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Flux<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public Mono<Team> findByName(String name) {
        return teamRepository.findByName(name);
    }

    @Override
    public Mono<Team> findById(String id) {
        return teamRepository.findById(id);
    }

    @Override
    public Mono<Team> addTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Mono<Void> deleteTeam(String id) {
        return teamRepository.deleteById(id);
    }
}
