package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    MedicoRepository medicoRepository;

    //Return 201 "Created"
    //URL donde encontrar al medico creado
    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaMedicos> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder){
       Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));

       DatosRespuestaMedicos datosRespuestaMedicos = new DatosRespuestaMedicos(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(), medico.getDireccion().getComplemento()));

       //URL donde encontrar al medico creado
       URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

       return ResponseEntity.created(url).body(datosRespuestaMedicos);

    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> listaMedico(@PageableDefault(size = 10, sort = {"nombre"}) Pageable page) {
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(page).map(m -> new DatosListadoMedico(m)));
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
            Optional<Medico> respuesta = medicoRepository.findById(datosActualizarMedico.id());

            if(respuesta.isPresent()){
                var medico = respuesta.get();
                medico.actualizarDatos(datosActualizarMedico);
                return ResponseEntity.ok(new DatosRespuestaMedicos(medico.getId(), medico.getNombre(),
                        medico.getEmail(), medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad().toString(),
                        new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                                medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(), medico.getDireccion().getComplemento())));
            }else{
                //Retorna un 404 "Not Found"
                return ResponseEntity.notFound().build();
            }
    }

    //Delete logico, cambiar el estado.
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity borrarMedico(@PathVariable UUID id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    //delete borra de verdad en la base de datos
    /*public void borrarMedico(@PathVariable UUID id){
        Medico medico = medicoRepository.getReferenceById(id);
        medicoRepository.delete(medico);
    }*/


    //CONSULTA DE MEDICO POR ID
    @GetMapping("/{id}")
    public ResponseEntity retornaDatosMedico(@PathVariable UUID id){
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedicos(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(), medico.getDireccion().getComplemento()));

        return ResponseEntity.ok(datosMedico);
    }


}
