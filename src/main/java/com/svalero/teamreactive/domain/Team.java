package com.svalero.teamreactive.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "teams")
public class Team implements Serializable {

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
