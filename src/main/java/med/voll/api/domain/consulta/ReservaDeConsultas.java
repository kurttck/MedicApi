package med.voll.api.domain.consulta;

import jakarta.validation.Valid;
import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public void reservar(DatosReservaConsulta datos){

        if(!pacienteRepository.existsById(datos.idPaciente())){
            throw new ValidacionException("El paciente no existe");
        }

        if(datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionException("El medico no existe");
        }




        var medico = elegirMedico(datos);
        var paciente = pacienteRepository.getReferenceById(datos.idPaciente());

        var consulta = new Consulta(null, medico, paciente,null, datos.fecha());

    }

    public Medico elegirMedico(DatosReservaConsulta datos) {
        if (datos.idMedico() != null) {
            return medicoRepository.getReferenceById(datos.idMedico());
        }

        if(datos.especialidad() == null){
            throw new ValidacionException("La especialidad es obligatoria cuando no elige medico");
        }

        return medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(datos.especialidad(), datos.fecha());
    }


    public void cancelar(@Valid DatosCancelacionConsulta datosCancelacionConsulta) {
        if(!consultaRepository.existsById(datosCancelacionConsulta.idConsulta())) {
            throw new ValidacionException("La consulta no existe");
        }
        var consulta = consultaRepository.getReferenceById(datosCancelacionConsulta.idConsulta());
        consulta.cancelar(datosCancelacionConsulta.motivo());
    }
}
