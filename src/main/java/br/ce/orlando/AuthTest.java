package br.ce.orlando;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.http.ContentType;

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
	
	@Test
	public void deveFazerAutenticacaoComTokenJWT() {
		
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "orlando.dev@hotmail.com");
		login.put("senha", "1234");
		
		//Login na API e Receber o TOKEN do login
		String token = given()
			.log().all()
			.body(login)
			.contentType(ContentType.JSON)
		.when()
			.post("https://barrigarest.wcaquino.me/signin")
		.then()
			.log().all()
			.statusCode(200)
			.extract().path("token")
		;
		
		//Obter as contas
		given()
			.log().all()
			.header("Authorization", "JWT " + token)
		.when()
			.get("https://barrigarest.wcaquino.me/contas")
		.then()
			.log().all()
			.statusCode(200)
			.body("nome", Matchers.hasItem("Conta de teste"))
		;
	}
}
