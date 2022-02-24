package br.ce.orlando;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.http.ContentType;


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
	public void deveSalvarUsuarioUsandoMap() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "Orlando via map");
		params.put("age", 689);
		
		given()
			.log().all()
			.contentType("application/json")
			.body(params)
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Orlando via map"))
			.body("age", is(689))
		;
		
	}
	
	@Test
	public void deveSalvarUsuarioUsandoObjeto() {
		User user = new User("Usuario Orlando via objeto", 850);
		
		given()
			.log().all()
			.contentType("application/json")
			.body(user)
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Usuario Orlando via objeto"))
			.body("age", is(850))
		;
		
	}
	
	@Test
	public void deveDeserialziarObjetoAoSalvarUsuario() {
		User user = new User("Usuario Orlando deserializado", 850);
		
		User usuarioInserido = given()
			.log().all()
			.contentType("application/json")
			.body(user)
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.extract().body().as(User.class)
		;
		
		System.out.println("usuarioInserido");
		Assert.assertThat(usuarioInserido.getId(), notNullValue());
		Assert.assertEquals("Usuario Orlando deserializado", usuarioInserido.getName());
		Assert.assertThat(usuarioInserido.getAge(), is(850));
		
		
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
	
	@Test
	public void deveSalvarUsuarioViaXML() {
		given()
			.log().all()
			.contentType(ContentType.XML)
			.body("<user><name>Orlando</name><age>50</age></user>")
		.when()
			.post("https://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.body("user.@id", is(notNullValue()))
			.body("user.name", is("Orlando"))
			.body("user.age", is("50"))
		;
		
	}
	
	@Test
	public void deveAlterarUsuario() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"name\": \"Usuario alterado\", \"age\": 195}")
		.when()
			.put("https://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuario alterado"))
			.body("age", is(195))
			.body("salary", is(1234.5678f))
		;
		
	}
	
	@Test
	public void devoCustomizarURL() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"name\": \"Usuario alterado\", \"age\": 195}")
		.when()
			.put("https://restapi.wcaquino.me/{entidade}/{userId}", "users", "1")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuario alterado"))
			.body("age", is(195))
			.body("salary", is(1234.5678f))
		;
		
	}
	
	@Test
	public void devoCustomizarURLParte2() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"name\": \"Usuario alterado\", \"age\": 195}")
			.pathParam("entidade", "users")
			.pathParam("userId", 1)
		.when()
			.put("https://restapi.wcaquino.me/{entidade}/{userId}", "users", "1")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuario alterado"))
			.body("age", is(195))
			.body("salary", is(1234.5678f))
		;
		
	}
	
	@Test
	public void deveRemoverUsuario() {
		given()
			.log().all()
		.when()
			.delete("https://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(204)
		;
	}
	
	@Test
	public void naoDeveRemoverUsuarioInexistente() {
		given()
			.log().all()
		.when()
			.delete("https://restapi.wcaquino.me/users/1000")
		.then()
			.log().all()
			.statusCode(400)
			.body("error", is("Registro inexistente"))
		;
	}
	
	@Test
	public void deveSalvarUsurarioViaXMLUsandoObjeto() {
		User user = new User("Usuario Orlando XML", 40);
		
				given()
			.log().all()
			.contentType(ContentType.XML)
			.body(user)
		.when()
			.post("https://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.body("user.@id", is(notNullValue()))
			.body("user.name", is("Usuario Orlando XML"))
			.body("user.age", is("40"));
	}
}
