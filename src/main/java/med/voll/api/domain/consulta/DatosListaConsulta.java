package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

public record DatosListaConsulta(
        String nombreMedico,
        String nombrePaciente,
        LocalDateTime fecha
) {

    public DatosListaConsulta(Consulta consulta){
        this(consulta.getMedico().getNombre(), consulta.getPaciente().getNombre(), consulta.getFecha());
    }

}
