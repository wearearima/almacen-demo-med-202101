package eu.arima.mejorarTesting.almacen.medicamentos;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicamentosService {
    private MedicamentosRepository medicamentoRepository;

    public MedicamentosService(MedicamentosRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public List<Medicamento> getAllMedicamentos() {
        return medicamentoRepository.findAll();
    }
}
