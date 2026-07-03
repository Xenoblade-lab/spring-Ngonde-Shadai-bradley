package edu.upc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApplicationIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	private static Long professionId;
	private static Long formationId;
	private static Long candidatId;
	private static Long inscriptionId;

	private static String suffix;

	private static long readId(MvcResult result) throws Exception {
		Object id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
		return ((Number) id).longValue();
	}

	@Test
	@Order(0)
	void initSuffix() {
		suffix = String.valueOf(System.currentTimeMillis());
	}

	@Test
	@Order(1)
	void professionsCrud() throws Exception {
		mockMvc.perform(get("/api/professions"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));

		MvcResult created = mockMvc.perform(post("/api/professions")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"description\":\"Test Profession API " + suffix + "\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andReturn();

		professionId = readId(created);

		mockMvc.perform(get("/api/professions/" + professionId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.description").value("Test Profession API " + suffix));

		mockMvc.perform(put("/api/professions/" + professionId)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"description\":\"Test Profession MAJ " + suffix + "\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.description").value("Test Profession MAJ " + suffix));
	}

	@Test
	@Order(2)
	void formationsApiStillWorks() throws Exception {
		mockMvc.perform(get("/api/formations"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));

		MvcResult created = mockMvc.perform(post("/api/formations")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"description\":\"Test Formation API " + suffix + "\",\"duree\":4}"))
				.andExpect(status().isCreated())
				.andReturn();

		formationId = readId(created);

		mockMvc.perform(get("/api/formations/" + formationId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.duree").value(4));
	}

	@Test
	@Order(3)
	void candidatsWithProfessionJoin() throws Exception {
		mockMvc.perform(get("/api/candidats"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].profession.description").exists());

		mockMvc.perform(get("/api/candidats?keyword=Bradley"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].noms").value("Ngonde Shadai Bradley"))
				.andExpect(jsonPath("$[0].profession.description").value("Informaticien"));

		MvcResult created = mockMvc.perform(post("/api/candidats")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "professionPk": 1,
								  "noms": "Test Candidat API",
								  "genre": "M",
								  "etatCivil": "Celibataire",
								  "lieuNais": "Kinshasa",
								  "dateNais": "2001-01-10"
								}
								"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.profession.description").value("Informaticien"))
				.andReturn();

		candidatId = readId(created);

		mockMvc.perform(get("/api/candidats/" + candidatId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.formations").isArray());
	}

	@Test
	@Order(4)
	void candidatFormationEnrollment() throws Exception {
		mockMvc.perform(get("/api/candidats-formations"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));

		MvcResult created = mockMvc.perform(post("/api/candidats-formations")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"candidatPk\":" + candidatId + ",\"formationPk\":" + formationId + "}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.candidat.noms").value("Test Candidat API"))
				.andExpect(jsonPath("$.formation.description").value("Test Formation API " + suffix))
				.andReturn();

		inscriptionId = readId(created);

		mockMvc.perform(delete("/api/candidats-formations/" + inscriptionId))
				.andExpect(status().isOk());
	}

	@Test
	@Order(5)
	void webPagesAndLocale() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/?locale=fr"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/professions"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/formations"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/candidats"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/candidats?keyword=Bradley"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/candidats-formations"))
				.andExpect(status().isOk());
	}

	@AfterAll
	void cleanupTestData() throws Exception {
		cleanupCreatedEntities();
	}

	private void cleanupCreatedEntities() throws Exception {
		if (inscriptionId != null) {
			mockMvc.perform(delete("/api/candidats-formations/" + inscriptionId));
			inscriptionId = null;
		}
		if (candidatId != null) {
			mockMvc.perform(delete("/api/candidats/" + candidatId));
			candidatId = null;
		}
		if (formationId != null) {
			mockMvc.perform(delete("/api/formations/" + formationId));
			formationId = null;
		}
		if (professionId != null) {
			mockMvc.perform(delete("/api/professions/" + professionId));
			professionId = null;
		}
	}

}
