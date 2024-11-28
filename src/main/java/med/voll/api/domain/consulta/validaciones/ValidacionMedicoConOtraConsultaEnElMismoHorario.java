package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidacionMedicoConOtraConsultaEnElMismoHorario {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DatosReservaConsulta datos){
        var medicoTieneOtraConsultaEnElHorario = consultaRepository.existsByMedicoIdAndFecha(datos.idMedico(), datos.fecha());

        if (medicoTieneOtraConsultaEnElHorario) {
            throw new ValidacionException("El medico tiene otra consulta en el mismo horario");
        }

    }

}
