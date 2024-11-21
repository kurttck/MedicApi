package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

import java.util.UUID;

@Entity
@Table(name="medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_medico")
    private UUID id;

    private String nombre;
    private String email;
    private String documento;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;

    private String telefono;
    private Boolean activo;

    public Medico(DatosRegistroMedico datosRegistroMedico) {
        this.nombre = datosRegistroMedico.nombre();
        this.email = datosRegistroMedico.email();
        this.documento = datosRegistroMedico.documento();
        this.especialidad = datosRegistroMedico.especialidad();
        this.direccion =  new Direccion(datosRegistroMedico.direccion());
        this.telefono = datosRegistroMedico.telefono();
        this.activo = true;
    }

    public void actualizarDatos(DatosActualizarMedico datosActualizarMedico) {

        if(datosActualizarMedico.nombre() != null){
            this.nombre = datosActualizarMedico.nombre();
        }

        if (datosActualizarMedico.documento() != null){
            this.documento = datosActualizarMedico.documento();
        }

        if(datosActualizarMedico.direccion() != null){
            this.direccion = direccion.actualizarDatos(datosActualizarMedico.direccion());
        }


    }

    public void desactivarMedico() {
        this.activo = false;
    }
}
