package eu.arima.mejorarTesting.almacen.reservas;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservasFarmaciasController.class)
@Tag("testIntegracion")
class ReservasFarmaciasControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ReservasService reservasService;

    @Test
    @DisplayName("/reservas (post) con la información para una reserva realiza la reserva devuelviendo el id de la misma")
    void post_reservas_devuelve_informacion_recogida_reserva() throws Exception {

        Reserva reserva = new Reserva(1,2,3);
        reserva.setId(88L);
        Optional<Reserva> reservaOptional = Optional.of(reserva);
        when(reservasService.reservarMedicamento(1, 2, 3)).thenReturn(reservaOptional);

        ResultActions resultado = mockMvc.perform(post("/reservas")
                .contentType(APPLICATION_JSON)
                .content("{\"idFarmacia\": 1, \"idMedicamento\": 2, \"unidadesReservar\": 3}"));

        assertAll("La respuesta tiene el id de la reserva",
                () -> resultado.andExpect(status().isCreated()),
                () -> assertEquals(88L, Long.valueOf(resultado.andReturn().getResponse().getContentAsString())));
    }
}