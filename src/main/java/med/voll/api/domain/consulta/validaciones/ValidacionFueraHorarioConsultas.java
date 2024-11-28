package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;

import java.time.DayOfWeek;

public class ValidacionFueraHorarioConsultas {

    public void validar(DatosReservaConsulta datos) {
        var fechaConsulta = datos.fecha();

        var domingo = fechaConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);

        var horarioAntesAperturaClinica =  fechaConsulta.getHour() < 7;
        var horarioDespuesCierreClinica =  fechaConsulta.getHour() > 18;

        if (domingo || horarioAntesAperturaClinica || horarioDespuesCierreClinica) {
            throw new ValidacionException("La consulta esta fuera de horario de atencion");
        }



    }
}
