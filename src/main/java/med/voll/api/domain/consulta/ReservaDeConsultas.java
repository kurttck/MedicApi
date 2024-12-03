package med.voll.api.domain.consulta;

import jakarta.validation.Valid;
import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.validaciones.cancelamiento.ValidacionCancelamientoDeConsulta;
import med.voll.api.domain.consulta.validaciones.reserva.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private List<ValidadorDeConsultas> validadores;

    @Autowired
    private List<ValidacionCancelamientoDeConsulta> validacionCancelamientoDeConsulta;

    public DatosDetalleConsulta reservar(DatosReservaConsulta datos){



        if(!pacienteRepository.existsById(datos.idPaciente())){
            throw new ValidacionException("El paciente no existe");
        }


        if(datos.idMedico() != null && !medicoRepository.existsActivoById(datos.idMedico())){
            throw new ValidacionException("El medico no existe");
        }

        System.out.println("PASO LA PRUEBA");

        validadores.forEach(v -> v.validar(datos));

        System.out.println("PASO LA PRUEBA 2");

        var medico = elegirMedico(datos);

        if(medico == null){
            throw new ValidacionException("No existe medico");
        }

        System.out.println("PASO LA PRUEBA 3");

        var paciente = pacienteRepository.getReferenceById(datos.idPaciente());

         Consulta consulta = consultaRepository.save(new Consulta(null, medico, paciente,null, datos.fecha()));




        return new DatosDetalleConsulta(consulta);

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

        validacionCancelamientoDeConsulta.forEach(v -> v.validar(datosCancelacionConsulta));

        var consulta = consultaRepository.getReferenceById(datosCancelacionConsulta.idConsulta());
        consulta.cancelar(datosCancelacionConsulta.motivo());
    }


}
