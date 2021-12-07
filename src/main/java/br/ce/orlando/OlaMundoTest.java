package br.ce.orlando;

import static io.restassured.RestAssured.given;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {
	
	
	@Test
	public void OlaMundoOTest() {
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");

		Assert.assertTrue((response.getBody().asString().equals("Ola Mundo!")));
		Assert.assertTrue((response.statusCode() == 200));
		Assert.assertTrue("O status code deveria ser 200", (response.statusCode() == 200));
		Assert.assertEquals(200, response.statusCode());
		
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
	}
	
	@Test
	public void devoConhecerOutrasFormasRestAssured() {
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
		
		RestAssured.get("http://restapi.wcaquino.me/ola")
		.then().statusCode(200);
		
		given() //PRE CONDICOES
		.when() //ACAO
			.get("http://restapi.wcaquino.me/ola")
		.then() // ASSERTIVAS
			.statusCode(200);
	}
	
	@Test
	public void devoConhecerMatchersHamcrest() {
		Assert.assertThat("Maria", Matchers.is("Maria"));
		Assert.assertThat(120, Matchers.is(120));
		Assert.assertThat(120, Matchers.isA(Integer.class));
		Assert.assertThat(120d, Matchers.isA(Double.class));
		Assert.assertThat(120d, Matchers.greaterThan(110d));
		Assert.assertThat(120d, Matchers.lessThan(130d));
		
		List<Integer> impares = Arrays.asList(1, 2, 3, 4, 5);
		Assert.assertThat(impares, Matchers.hasSize(5)); // se tem o tamanho 5
		Assert.assertThat(impares, Matchers.contains(1, 2, 3, 4, 5)); //se tem os numeros, se vier mais irra dar falha
		Assert.assertThat(impares, Matchers.containsInAnyOrder(1, 2, 4, 3, 5)); // se tem os numeros em qualquer ordem
		Assert.assertThat(impares, Matchers.hasItem(1)); // se tem o numero que desejo
		Assert.assertThat(impares, Matchers.hasItems(1, 4)); // se tem OS NUMEROS que desejo
		
		Assert.assertThat("Maria", Matchers.is("Maria"));
		Assert.assertThat("Maria", Matchers.not("João"));
		Assert.assertThat("Maria", Matchers.anyOf(Matchers.is("João"), Matchers.is("Maria"))); // se é um ou outro
		Assert.assertThat("Joaquina", Matchers.allOf(Matchers.startsWith("Jo"), Matchers.endsWith("aquina")));
		
	}
	
	@Test
	public void devoValidarBody() {
		given() //PRE CONDICOES
		.when() //ACAO
			.get("http://restapi.wcaquino.me/ola")
		.then() // ASSERTIVAS
			.statusCode(200)
			.body(Matchers.is("Ola Mundo!"))
			.body(Matchers.containsString("Mundo"))
			.body(Matchers.is(Matchers.not(Matchers.nullValue())));
	}
}
