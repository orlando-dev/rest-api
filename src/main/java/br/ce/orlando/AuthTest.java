package br.ce.orlando;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.junit.Test;

public class AuthTest {
	
	@Test
	public void deveAcessarSWAPI() {
		given()
			.log().all()
		.when()
			.get("https://swapi.dev/api/people/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", Matchers.is("Luke Skywalker"))
		;
	}
	
	
	/**
	 * Usei essa endpoint para a requisição do teste abaixo
	 *https://api.openweathermap.org/data/2.5/weather?q=Fortaleza,BR&appid=18fdd7513e8a8ddcbba2631eaf1c9373&units=metric
	 */
	@Test
	public void deveObterClima() {
		given()
			.log().all()
			.queryParam("q", "Fortaleza,BR")
			.queryParam("appid", "18fdd7513e8a8ddcbba2631eaf1c9373")
			.queryParam("units", "metric")
		.when()
			.get("https://api.openweathermap.org/data/2.5/weather")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", Matchers.is("Fortaleza"))
			.body("coord.lon", Matchers.is(-38.5247f))
			.body("main.temp", Matchers.greaterThan(25f))
	;
	}
		
	@Test
	public void naoDeveAcessarSemSenha() {
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/basicAuth")
		.then()
			.log().all()
			.statusCode(401)
		;
	}
	
	@Test
	public void deveFazerAutenticacaoBasica() {
		given()
			.log().all()
		.when()
			.get("https://admin:senha@restapi.wcaquino.me/basicAuth")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", Matchers.is("logado"))
		;
	}
	
	@Test
	public void deveFazerAutenticacaoBasica2() {
		given()
			.log().all()
			.auth().basic("admin", "senha")
		.when()
			.get("https://restapi.wcaquino.me/basicAuth")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", Matchers.is("logado"))
		;
	}
	
	@Test
	public void deveFazerAutenticacaoBasicaChallenge() {
		given()
			.log().all()
			.auth().preemptive().basic("admin", "senha")
		.when()
			.get("https://restapi.wcaquino.me/basicAuth2")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", Matchers.is("logado"))
		;
	}
}
