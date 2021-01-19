package eu.arima.mejorarTesting.almacen.reservas;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/reservas")
public class ReservasFarmaciasController {

    ReservasService reservasService;

    public ReservasFarmaciasController(ReservasService reservasService) {
        this.reservasService = reservasService;
    }

    @GetMapping
    List<Reserva> listarReservas() {
        return reservasService.getAllReservas();
    }

    @GetMapping("/{idFarmacia}")
    List<Reserva> listarReservasFarmacia(@PathVariable Long idFarmacia) {
        return reservasService.getAllReservasFarmacia(idFarmacia);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    Long reservar(@Validated @RequestBody SolicitudReserva solicitudReserva) {
        Optional<Reserva> reserva = reservasService
                .reservarMedicamento(solicitudReserva.getIdFarmacia(), solicitudReserva
                        .getIdMedicamento(), solicitudReserva.getUnidadesReservar());
        return reserva.orElseThrow().getId();
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(BAD_REQUEST)
    void handleExceptions() {

    }

}
