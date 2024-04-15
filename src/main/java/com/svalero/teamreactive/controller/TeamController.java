package com.svalero.teamreactive.controller;

import com.svalero.teamreactive.domain.Team;
import com.svalero.teamreactive.exception.TeamNotFoundException;
import com.svalero.teamreactive.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping(value = "/teams", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<Team> getTeams(){
        return teamService.findAll().delayElements(Duration.of(1, ChronoUnit.SECONDS));
    }

    @PostMapping(value = "/teams")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Team> addTeam(@RequestBody Team team){
        return teamService.addTeam(team);
    }

    @DeleteMapping(value = "/team/{teamId}")
    public Mono<ResponseEntity<String>> deleteTeam(@PathVariable String teamId){
        return teamService.deleteTeam(teamId)
                .then(Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).body("Equipo eliminado correctamente")))
                .switchIfEmpty(Mono.error(new TeamNotFoundException("Team not found")));
    }

    @PutMapping(value = "/team/{teamId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Team> updateTeam(@PathVariable String teamId, @RequestBody Team team) {
        return teamService.findById(teamId)
                .flatMap(existingTeam -> {
                    existingTeam.setName(team.getName());
                    existingTeam.setCity(team.getCity());
                    existingTeam.setStadium(team.getStadium());
                    existingTeam.setCoach(team.getCoach());
                    existingTeam.setFoundationYear(team.getFoundationYear());
                    return teamService.addTeam(existingTeam);
                })
                .switchIfEmpty(Mono.error(new TeamNotFoundException("Team not found")));
    }

    @ExceptionHandler
    public ResponseEntity<String> teamNotFoundException (TeamNotFoundException tnfe){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(tnfe.getMessage());
    }
}
