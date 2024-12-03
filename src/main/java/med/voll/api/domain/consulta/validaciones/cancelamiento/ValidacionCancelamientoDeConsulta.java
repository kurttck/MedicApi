package med.voll.api.domain.consulta.validaciones.cancelamiento;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.DatosCancelacionConsulta;

public interface ValidacionCancelamientoDeConsulta {


    void validar(@Valid DatosCancelacionConsulta datosCancelacionConsulta);


}
