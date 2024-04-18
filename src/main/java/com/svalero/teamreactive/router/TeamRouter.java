package com.svalero.teamreactive.router;

import com.svalero.teamreactive.handler.TeamHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class TeamRouter {

    @Bean
    public RouterFunction<ServerResponse> teamsRoute(TeamHandler teamHandler){
        return RouterFunctions
                .route(GET("/teams"). and(accept(MediaType.APPLICATION_JSON)), teamHandler::getAllTeams)
                .andRoute(GET("/team/{id}").and(accept(MediaType.APPLICATION_JSON)), teamHandler::getTeam)
                .andRoute(POST("/teams"). and(accept(MediaType.APPLICATION_JSON)), teamHandler::addTeam)
                .andRoute(DELETE("/team/{id}").and(accept(MediaType.APPLICATION_JSON)), teamHandler::deleteTeam)
                .andRoute(PUT("/team/{id}").and(accept(MediaType.APPLICATION_JSON)), teamHandler::updateTeam);
    }
}
