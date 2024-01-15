package com.quiz.quiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizApplication {

	// aca solo inicializamos la app, no va nada mas
	// los controladores se encargan de resibir las solicitudes del usuario
	// los controladores envian esta solicitud del usuario hacia el servicio
	// el servicio se encarga de hacer la consulta a la db o lo que sea que tenga que hacer de logica para retornarle la respuesta al controlador
	// la capa dao es donde esta to do lo que tenga que ver con las interacciones con la db
	//

	public static void main(String[] args) {
		SpringApplication.run(QuizApplication.class, args);
	}

}
