package eu.arima.mejorarTesting.almacen.reservas;

public class SolicitudReserva {
    private long idFarmacia;
    private long idMedicamento;
    private int unidadesReservar;

    public long getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(long idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public long getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(long idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public int getUnidadesReservar() {
        return unidadesReservar;
    }

    public void setUnidadesReservar(int unidadesReservar) {
        this.unidadesReservar = unidadesReservar;
    }
}
