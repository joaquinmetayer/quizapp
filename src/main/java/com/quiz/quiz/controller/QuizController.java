package com.quiz.quiz.controller;

import com.quiz.quiz.model.QuestionWrapper;
import com.quiz.quiz.model.Response;
// importo los modelos como su nombre lo dice, son como las interfaces de ts
import com.quiz.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// controlador para gestionar operaciones relacionadas con quizzes.
@RestController
// esta api es a partir de /quiz/...
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    // inyectando dependencia con autowired
    QuizService quizService;
    @PostMapping("create")
    // este metodo crea una quiz y retorna un string
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title){
        return quizService.createQuiz(category, numQ, title);
    }

    @GetMapping("get/{id}")
    // obtengo las quiz acorde al id en forma de lista usando la "plantilla" questionwrapper
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    // este metodo es para enviar las respuestas y obtener un puntaje / resultado
    // me responde con un integer o sea un numero, recibe como parametro en ruta un id en body un la lista de modelo response
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id, responses);
    }
}

