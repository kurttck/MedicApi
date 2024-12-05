package med.voll.api.controller;

import med.voll.api.domain.consulta.DatosDetalleConsulta;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.consulta.ReservaDeConsultas;
import med.voll.api.domain.medico.Especialidad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DatosReservaConsulta> datosReservaConsultaJacksonTester;

    @Autowired
    private JacksonTester<DatosDetalleConsulta> datosDetalleConsultaJacksonTester;

    @MockBean
    private ReservaDeConsultas reservaDeConsultas;

    @Test
    @DisplayName("Deberia devolver http 400 cuando la request no tenga datos")
    @WithMockUser
    void reservar_escenario1() throws Exception {
       var response = mvc.perform(post("/consultas")).andReturn().getResponse();

       assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deberia devolver http 200 cuando la request reciba un json valido")
    @WithMockUser
    void reservar_escenario2() throws Exception {

        var fecha = LocalDateTime.now().plusHours(1);
        var especialidad = Especialidad.ORTOPEDIA;
        var datosDetalle = new DatosDetalleConsulta(null, UUID.fromString("1c1e60df-6671-4308-89d7-0160d756db02"), 1L, fecha);

        when(reservaDeConsultas.reservar(any())).thenReturn(datosDetalle);


        var response = mvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(datosReservaConsultaJacksonTester.write(new DatosReservaConsulta(UUID.fromString("1c1e60df-6671-4308-89d7-0160d756db02"), 1L, fecha, especialidad)).getJson()))
                .andReturn().getResponse();

        var jsonEsperado = datosDetalleConsultaJacksonTester.write(datosDetalle).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }



}