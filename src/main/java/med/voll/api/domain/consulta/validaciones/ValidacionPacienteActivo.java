package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidacionPacienteActivo {

    @Autowired
    private PacienteRepository repository;

    public void validar(DatosReservaConsulta datos) {
        var pacienteActivo = repository.findActivoById(datos.idPaciente());


        if (!pacienteActivo) {
            throw new ValidacionException("El paciente esta inactivo");
        }
    }
}
