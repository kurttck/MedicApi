package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;

import java.time.Duration;
import java.time.LocalDateTime;

public class ValidacionConsultaConAnticipacion {

    public void validar(DatosReservaConsulta datos){
        var ahora  = LocalDateTime.now();
        var fechaConsulta = datos.fecha();

        var diferenciaEnMinutos = Duration.between(ahora, fechaConsulta).toMinutes();

        if (diferenciaEnMinutos < 30) {
            throw new ValidacionException("La consulta debe tener al menos 30 minutos de anticipacion");
        }
    }
}
