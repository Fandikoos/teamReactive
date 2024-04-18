package com.svalero.teamreactive.handler;

import com.svalero.teamreactive.domain.Team;
import com.svalero.teamreactive.service.TeamService;
import com.svalero.teamreactive.util.ErrorResponse;
import com.svalero.teamreactive.validator.TeamValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class TeamHandler {

    @Autowired
    private TeamService teamService;

    static Mono<ServerResponse> notFound =ServerResponse.notFound().build();
    private final Validator validator = new TeamValidator();

    public Mono<ServerResponse> getAllTeams(ServerRequest serverRequest){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(teamService.getAllTeams(), Team.class);
    }

    public Mono<ServerResponse> getTeam(ServerRequest serverRequest){
        String id = serverRequest.pathVariable("id");
        return teamService.getTeam(id)
                .flatMap(t -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(t), Team.class))
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(new ErrorResponse(404, "Team not found")), ErrorResponse.class));
    }

    public Mono<ServerResponse> addTeam(ServerRequest serverRequest){
        Mono<Team> teamToSave = serverRequest.bodyToMono(Team.class)
                .doOnNext(this::validate);

        return teamToSave.flatMap(team ->
            ServerResponse.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(teamService.saveTeam(team), Team.class));
    }

    private void validate(Team team) {
        Errors errors = new BeanPropertyBindingResult(team, "team");
        validator.validate(team, errors);
        if (errors.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    errors.getAllErrors().toString());
        }
    }

    public Mono<ServerResponse> deleteTeam(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return teamService.deleteTeam(id)
                .then(ServerResponse.noContent().build())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found")));
    }

    public Mono<ServerResponse> updateTeam(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        Mono<Team> teamMono = serverRequest.bodyToMono(Team.class);

        return teamService.updateTeam(id, teamMono)
                .flatMap(team ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(fromObject(team)))
                .switchIfEmpty(notFound);
    }
}
