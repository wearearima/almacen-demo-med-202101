package eu.arima.mejorarTesting.almacen;

import eu.arima.mejorarTesting.almacen.reservas.ReservasFarmaciasController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReservasBase {

    @Autowired
    private ReservasFarmaciasController reservasFarmaciaController;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(reservasFarmaciaController);
    }
}
