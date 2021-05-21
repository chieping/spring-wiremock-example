package com.example.demo;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.hamcrest.Matchers.equalTo;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
class DemoApplicationTests {

	@RegisterExtension
	WireMockExtension mockServer1 = new WireMockExtension(8081);

	@Test
	void test() {
		mockServer1.stubFor(get("/api").willReturn(
				aResponse()
						.withHeader("Content-type", "application/json")
						.withBodyFile("DemoApplicationTests_test_1.json")));

		RestAssured.get("/hello")
				.then()
				.assertThat()
				.statusCode(200)
				.body("data", equalTo("this is wiremock's data"));
	}
}

class WireMockExtension extends WireMockServer implements BeforeEachCallback, AfterEachCallback {

	public WireMockExtension(int port) {
		super(port);
	}

	@Override
	public void beforeEach(ExtensionContext context) {
		this.start();
	}

	@Override
	public void afterEach(ExtensionContext context) {
		this.stop();
		this.resetAll();
	}
}
