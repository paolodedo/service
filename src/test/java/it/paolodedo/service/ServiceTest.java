package it.paolodedo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceTest {
	@Container
	private static final Neo4jContainer<?> neo4j = new Neo4jContainer<>("neo4j:4.4")
		.withReuse(true);

	@DynamicPropertySource
	static void neo4jProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.neo4j.uri", neo4j::getBoltUrl);
		registry.add("spring.neo4j.authentication.username", () -> "neo4j");
		registry.add("spring.neo4j.authentication.password", neo4j::getAdminPassword);
	}

	@BeforeAll
	public static void prepareDatabase(@Autowired Driver driver) {
		try (var session = driver.session()) {
			session.run("MATCH (n) DETACH DELETE n").consume();
			session.run("CREATE (d:Device {id: 1, name:'Testdevice', version:0})").consume();
		}
	}

	@Test
	void f(@Autowired TestRestTemplate restTemplate, @Autowired Driver driver) {

		Map<String, Object> data = new HashMap<>();
		data.put("name", "test");
		data.put("devices", new String[] { "/1" });

		var response = restTemplate.exchange(
			"/service/groups",
			HttpMethod.POST,
			new HttpEntity<>(Map.of("name", "test", "devices", new String[] { "/1" })),
			Void.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		try (var session = driver.session()) {
			var cnt = session.run("MATCH (g:Group {name: $name}) <-[:BELONGS_TO]- (d:Device {id: $id}) RETURN count(*)",
					Map.of("name", "test", "id", 1))
				.single().get(0).asLong();
			assertThat(cnt).isOne();
		}
	}
}
