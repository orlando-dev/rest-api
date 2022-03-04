package br.ce.orlando;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.hamcrest.Matchers;
import org.junit.Test;

public class FileTest {
	
	@Test
	public void deveObrigarEnvioArquivo() {
		given()
			.log().all()
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(404) //deveria ser 400
			.body("error", Matchers.is("Arquivo não enviado"))
		;
	}
	
	@Test
	public void deveFazerUploadArquivo() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/users.pdf"))
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", Matchers.is("users.pdf"))
			.body("md5", Matchers.notNullValue())
			.body("size", Matchers.is(59857))
		;
	}
}
