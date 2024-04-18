package com.svalero.teamreactive.service;

import com.svalero.teamreactive.domain.Team;
import com.svalero.teamreactive.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public Flux<Team> getAllTeams(){
        return teamRepository.findAll();
    }

    public Mono<Team> getTeam(String id){
        return teamRepository.findById(id);
    }

    public Mono<Team> saveTeam(Team team){
        Team newTeam = new Team();
        newTeam.setName(team.getName());
        newTeam.setCoach(team.getCoach());
        newTeam.setCity(team.getCity());
        newTeam.setStadium(team.getStadium());
        newTeam.setFoundationYear(team.getFoundationYear());
        return teamRepository.save(newTeam);
    }

    public Mono<Void> deleteTeam(String id){
        return teamRepository.deleteById(id);
    }

    public Mono<Team> updateTeam(String id, Mono<Team> teamMono) {
        return teamMono.flatMap(team -> {
            if (id == null) {
                return Mono.error(new IllegalArgumentException("Team ID cannot be null"));
            } else {
                return teamRepository.findById(id)
                        .flatMap(existingTeam -> {
                            existingTeam.setName(team.getName());
                            existingTeam.setStadium(team.getStadium());
                            existingTeam.setCity(team.getCity());
                            existingTeam.setCoach(team.getCoach());
                            existingTeam.setFoundationYear(team.getFoundationYear());
                            return teamRepository.save(existingTeam);
                        });
            }
        });
    }
}
