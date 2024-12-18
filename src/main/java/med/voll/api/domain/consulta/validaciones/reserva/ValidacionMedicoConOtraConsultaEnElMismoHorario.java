package med.voll.api.domain.consulta.validaciones.reserva;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionMedicoConOtraConsultaEnElMismoHorario implements ValidadorDeConsultas{

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DatosReservaConsulta datos){


        var medicoTieneOtraConsultaEnElHorario = consultaRepository.existsByMedicoIdAndFechaAndMotivoCancelacionIsNull(datos.idMedico(), datos.fecha());


        if (medicoTieneOtraConsultaEnElHorario) {
            throw new ValidacionException("El medico tiene otra consulta en el mismo horario");
        }

    }

}
