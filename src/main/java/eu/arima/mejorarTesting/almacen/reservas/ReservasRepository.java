package eu.arima.mejorarTesting.almacen.reservas;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservasRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByIdFarmacia(Long idFarmacia);
}
