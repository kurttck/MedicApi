package med.voll.api.domain.medico;

import med.voll.api.domain.direccion.DatosDireccion;

import java.util.UUID;

public record DatosRespuestaMedicos(
        UUID id,
        String nombre,
        String email,
        String telefono,
        String documento,
        String especialidad,
        DatosDireccion datosDireccion
) {
}
