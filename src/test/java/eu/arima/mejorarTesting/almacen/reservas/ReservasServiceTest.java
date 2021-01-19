package eu.arima.mejorarTesting.almacen.reservas;

import eu.arima.mejorarTesting.almacen.medicamentos.Medicamento;
import eu.arima.mejorarTesting.almacen.medicamentos.MedicamentosRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Testcontainers
@Tag("testIntegracion")
class ReservasServiceTest {

    @Container
    private final static PostgreSQLContainer postgresContainer = new PostgreSQLContainer(DockerImageName.parse(
            "postgres:13"));
    @Autowired
    ReservasRepository reservasRepository;
    @Autowired
    MedicamentosRepository medicamentosRepository;
    ReservasService reservasService;

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    private static final int ID_FARMACIA = 44;
    private static final long ID_MEDICAMENTO = 1L;


    @BeforeEach
    void setUp() {
        reservasService = new ReservasService(reservasRepository, medicamentosRepository);
    }

    @Nested
    @DisplayName("reservarMedicamento si hay stock de sobra")
    class SiHayStockSobrante {
        private static final int UNIDADES = 10;

        @Test
        @DisplayName("se hace una reserva con todas las unidades solicitadas")
        void si_stock_reserva_con_unidades_solicitadas() {
            long reservasAntes = reservasRepository.count();

            Optional<Reserva> resultado = reservasService.reservarMedicamento(ID_FARMACIA, ID_MEDICAMENTO, UNIDADES);

            assertAll("Reserva es correcta",
                    () -> assertEquals(reservasAntes + 1, reservasRepository.count()),
                    () -> assertTrue(resultado.isPresent()),
                    () -> assertEquals(UNIDADES, resultado.get().getUnidades()),
                    () -> assertEquals(ID_MEDICAMENTO, resultado.get().getIdMedicamento()),
                    () -> assertEquals(ID_FARMACIA, resultado.get().getIdFarmacia()));
        }

        @Test
        @DisplayName("se reduce el stock del medicamento")
        void si_stock_actualizar_medicamento(){
            reservasService.reservarMedicamento(ID_FARMACIA, ID_MEDICAMENTO, UNIDADES);

            assertEquals(40, medicamentosRepository.findById(ID_MEDICAMENTO).get().getUnidadesStock());
        }
    }

    @Nested
    @DisplayName("reservarMedicamento si hay stock de sobra")
    class SiHayStockPeroMenos {

        private static final int UNIDADES = 100;


        @Test
        @DisplayName("se hace una reserva con las existencias")
        void si_stock_reserva_con_unidades_stock() {
            long reservasAntes = reservasRepository.count();

            Optional<Reserva> resultado = reservasService.reservarMedicamento(ID_FARMACIA, ID_MEDICAMENTO, UNIDADES);

            assertAll("Reserva es correcta",
                    () -> assertEquals(reservasAntes + 1, reservasRepository.count()),
                    () -> assertTrue(resultado.isPresent()),
                    () -> assertEquals(50, resultado.get().getUnidades()),
                    () -> assertEquals(ID_MEDICAMENTO, resultado.get().getIdMedicamento()),
                    () -> assertEquals(ID_FARMACIA, resultado.get().getIdFarmacia()));
        }

        @Test
        @DisplayName("se actualiza el stock del medicamento a 0")
        void si_stock_actualizar_medicamento(){
            reservasService.reservarMedicamento(ID_FARMACIA, ID_MEDICAMENTO, UNIDADES);

            assertEquals(0, medicamentosRepository.findById(ID_MEDICAMENTO).get().getUnidadesStock());
        }
    }



    @Test
    @DisplayName("reservarMedicamento si no hay stock no se hacen reservas")
    void si_no_stock_no_reservas() {
        long reservasAntes = reservasRepository.count();
        Medicamento medicamentoSinStock = new Medicamento();
        long idMedicamentoSinStock = medicamentosRepository.save(medicamentoSinStock).getId();

        Optional<Reserva> resultado = reservasService.reservarMedicamento(ID_FARMACIA, idMedicamentoSinStock, 1);

        assertEquals(reservasAntes, reservasRepository.count());
        assertFalse(resultado.isPresent());
    }
}