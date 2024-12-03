package med.voll.api.domain.consulta.validaciones.cancelamiento;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelacionConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidacionConsultaConAnticipacionCancelamiento")
public class ValidacionConsultaConAnticipacion implements ValidacionCancelamientoDeConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;


    public void validar(DatosCancelacionConsulta datosCancelacionConsulta) {
        var consulta = consultaRepository.getReferenceById(datosCancelacionConsulta.idConsulta());
        var ahora = LocalDateTime.now();
        var diferenciaEnHoras = Duration.between(ahora, consulta.getFecha()).toHours();

        if (diferenciaEnHoras < 24) {
            throw new ValidacionException("La consulta debe tener al menos 24 horas de anticipacion");
        }
    }
}
