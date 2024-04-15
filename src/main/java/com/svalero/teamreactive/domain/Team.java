package com.svalero.teamreactive.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(value = "teams")
public class Team {

    @Id
    private String id;
    @Field
    private String name;
    @Field
    private String city;
    @Field
    private String stadium;
    @Field
    private String coach;
    @Field
    private LocalDate foundationYear;

}
