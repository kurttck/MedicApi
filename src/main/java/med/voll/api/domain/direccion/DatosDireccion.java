package med.voll.api.domain.direccion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosDireccion(
        @NotBlank(message = "La calle es obligatoria") String calle,
        @NotBlank String distrito,
        @NotBlank String ciudad,
        @NotNull int numero,
        @NotBlank String complemento
) {

}
