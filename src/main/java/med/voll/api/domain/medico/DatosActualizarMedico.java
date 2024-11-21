package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;

import java.util.UUID;

public record DatosActualizarMedico(
        @NotNull UUID id,
        String nombre,
        String documento,
        DatosDireccion direccion
) {
}
