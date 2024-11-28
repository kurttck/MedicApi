package med.voll.api.domain.consulta;

import java.time.LocalDateTime;
import java.util.UUID;

public record DatosDetalleConsulta(
        Long id,
        UUID idMedico,
        Long idPaciente,
        LocalDateTime fecha
) {

    public DatosDetalleConsulta(DatosReservaConsulta datosReservaConsulta) {
        this(null, datosReservaConsulta.idMedico(), datosReservaConsulta.idPaciente(), datosReservaConsulta.fecha());
    }
}
