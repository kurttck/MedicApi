package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ReservaDeConsultas reservaDeConsultas;

    @PostMapping
    @Transactional
    public ResponseEntity reservar(@RequestBody @Valid DatosReservaConsulta datosReservaConsulta) {
        System.out.println(datosReservaConsulta);

        var consulta = reservaDeConsultas.reservar(datosReservaConsulta);

        return ResponseEntity.ok(consulta);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaConsulta>> listarConsultas(@PageableDefault(size = 10, sort = {"fecha"}) Pageable page) {
        return ResponseEntity.ok(consultaRepository.findAll(page).map(DatosListaConsulta::new));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DatosCancelacionConsulta datosCancelacionConsulta) {
        reservaDeConsultas.cancelar(datosCancelacionConsulta);
        return ResponseEntity.noContent().build();
    }
}
