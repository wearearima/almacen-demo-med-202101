package eu.arima.mejorarTesting.almacen.reservas;


import eu.arima.mejorarTesting.almacen.medicamentos.Medicamento;
import eu.arima.mejorarTesting.almacen.medicamentos.MedicamentosRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ReservasService {

    private final ReservasRepository reservasRepository;
    private final MedicamentosRepository medicamentosRepository;

    public ReservasService(ReservasRepository reservasRepository,
                           MedicamentosRepository medicamentosRepository) {
        this.reservasRepository = reservasRepository;
        this.medicamentosRepository = medicamentosRepository;
    }

    @Transactional
    public Optional<Reserva> reservarMedicamento(long idFarmacia, long idMedicamento, int unidades) {
        Medicamento medicamento = medicamentosRepository.findById(idMedicamento).orElseThrow();
        int stockReservable = medicamento.getStockReservable(unidades);
        Optional<Reserva> reserva = Optional.empty();
        if (stockReservable > 0){
            medicamento.disminuirStock(stockReservable);
            medicamentosRepository.save(medicamento);
            reserva = Optional.of(reservasRepository.save(new Reserva(idFarmacia, idMedicamento,stockReservable)));
        }
        return reserva;
    }

    public List<Reserva> getAllReservas() {
        return reservasRepository.findAll();
    }

    public List<Reserva> getAllReservasFarmacia(Long idFarmacia) {
        return reservasRepository.findByIdFarmacia(idFarmacia);
    }
}
