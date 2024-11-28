package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidacionMedicoActivo {

    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DatosReservaConsulta datos) {
        if(datos.idMedico() == null){
            return;
        }

        var medicoEstado = medicoRepository.findActivoById(datos.idMedico());

        if(!medicoEstado){
            throw new ValidacionException("El medico esta inactivo");
        }

    }
}
