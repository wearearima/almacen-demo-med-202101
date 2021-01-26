package eu.arima.mejorarTesting.almacen;

import eu.arima.mejorarTesting.almacen.medicamentos.MedicamentosRepository;
import eu.arima.mejorarTesting.almacen.reservas.ReservasFarmaciasController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
public class ReservasTestContainerBase {

    @Container
    private final static PostgreSQLContainer postgresContainer = new PostgreSQLContainer(DockerImageName.parse(
            "postgres:13"));

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @Autowired
    private ReservasFarmaciasController reservasFarmaciaController;
    @Autowired
    MedicamentosRepository repo;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(reservasFarmaciaController);
    }
}
