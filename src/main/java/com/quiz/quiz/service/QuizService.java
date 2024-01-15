package com.quiz.quiz.service;

import com.quiz.quiz.dao.QuestionDao;
import com.quiz.quiz.dao.QuizDao;
import com.quiz.quiz.model.Question;
import com.quiz.quiz.model.QuestionWrapper;
import com.quiz.quiz.model.Quiz;
import com.quiz.quiz.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        // creo quiz nueva el cual recibo como parametro la categoria que quiero crear
        // el numero de quiz y el titulo que quiero ponerle

        // traigo y guardo en una lista de tipo question cantidad de preguntas random de la categoria especifica
        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);
        // declaro la nueva quiz y luego voy configurando con los setters cada uno
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        // ya creada la quiz la guardo en la db
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        // este metodo es mas simple de lo que parece, traigo por id de la base de datos distintas preguntas las cuales respondo con interface de question wrapper
        Optional<Quiz> quizOptional = quizDao.findById(id);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            List<Question> questionsFromDB = quiz.getQuestions();
            if (questionsFromDB != null && !questionsFromDB.isEmpty()) {
                List<QuestionWrapper> questionsForUser = new ArrayList<>();
                for (Question q : questionsFromDB) {
                    QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
                    questionsForUser.add(qw);
                }
                return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        // mapeo lo que me envio el usuario por su id que corresponde a una quiz de preguntas y en base a lo que me da el body
        // obtengo un resultado de si es correcta o no devolviendole un puntaje al usuario
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int right = 0;
        int i = 0;
        for(Response response : responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer()))
                right++;
            i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}