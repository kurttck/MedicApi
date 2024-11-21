package med.voll.api.domain.medico;

import java.util.UUID;

public record DatosListadoMedico(
        UUID id,
        String nombre,
        String especialidad,
        String documento,
        String email
) {
    public DatosListadoMedico(Medico m) {
        this(m.getId(),m.getNombre(), m.getEspecialidad().toString(), m.getDocumento(), m.getEmail());

    }
}
