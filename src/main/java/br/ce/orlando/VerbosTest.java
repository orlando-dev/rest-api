package br.ce.orlando;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.*;
import org.junit.Test;


public class VerbosTest {
	
	@Test
	public void deveSalvarUsuario() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"name\": \"Orlando\", \"age\": 190}")
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Orlando"))
			.body("age", is(190))
		;
		
	}
	
	@Test
	public void naoDeveSalvarUsuario() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{ \"age\": 190}")
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(400)
			.body("id", is(nullValue()))
			.body("error", is("Name é um atributo obrigatório"))
			;
		
	}
}
