package com.quiz.quiz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Question {

    @Id
    // id se refiere a que este campo sea de registro unico para la db
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // indica que la base de datos se encargará de generar automáticamente valores únicos para la clave primaria
    private Integer id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String rightAnswer;
    private String difficultylevel;
    private String category;

}
