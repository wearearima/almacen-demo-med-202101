package eu.arima.mejorarTesting.almacen.reservas;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long idFarmacia;
    private long idMedicamento;
    private int unidades;
    private LocalDate fechaReserva;

    public Reserva() {
    }

    public Reserva(long idFarmacia, long idMedicamento, int unidades) {
        this.idFarmacia = idFarmacia;
        this.idMedicamento = idMedicamento;
        this.unidades = unidades;
    }

    public long getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(long idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(long idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    @PrePersist
    void prePersist() {
        this.fechaReserva = LocalDate.now();
    }
}
