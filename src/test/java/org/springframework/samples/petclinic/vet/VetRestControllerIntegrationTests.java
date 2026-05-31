/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vet;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class VetRestControllerIntegrationTests {

	@LocalServerPort
	int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void getVetsReturnsJsonArray() {
		ResponseEntity<String> response = restTemplate.getForEntity("/api/vets", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getHeaders().getContentType().toString()).contains("application/json");
		String body = response.getBody();
		assertThat(body).isNotNull();
		assertThat(body).contains("\"id\"");
		assertThat(body).contains("\"firstName\"");
		assertThat(body).contains("\"lastName\"");
		assertThat(body).contains("\"specialties\"");
		assertThat(body).contains("James");
		assertThat(body).contains("Carter");
	}

}
