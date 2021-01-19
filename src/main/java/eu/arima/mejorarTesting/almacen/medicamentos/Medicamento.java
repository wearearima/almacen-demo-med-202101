package eu.arima.mejorarTesting.almacen.medicamentos;

import javax.persistence.*;

@Entity
@Table(name = "medicamentos")
public class Medicamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String codigo;
    private int unidadesStock;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getUnidadesStock() {
        return unidadesStock;
    }

    public void setUnidadesStock(int unidadesStock) {
        this.unidadesStock = unidadesStock;
    }


    public void disminuirStock(int unidades) {
        this.unidadesStock -= unidades;
    }

    public int getStockReservable(int unidades) {
        return Math.min(unidadesStock, unidades);
    }
}
