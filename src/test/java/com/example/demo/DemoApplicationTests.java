package com.example.demo;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	@RegisterExtension
	WireMockExtension mockServer1 = new WireMockExtension(8081);

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void test() {
		mockServer1.stubFor(get("/api").willReturn(
				aResponse()
						.withHeader("Content-type", "application/json")
						.withBodyFile("DemoApplicationTests_test_1.json")));

		String result = this.restTemplate.getForObject("http://localhost:" + port + "/hello", String.class);

		assertThat(result).isEqualTo("api result is: this is wiremock's data");
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
