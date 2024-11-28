package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record DatosCancelacionConsulta(
        @NotNull
        Long idConsulta,
        @NotNull
        MotivoCancelacion motivo
) {
}
