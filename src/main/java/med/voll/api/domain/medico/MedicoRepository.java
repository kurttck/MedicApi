package med.voll.api.domain.medico;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, UUID> {
    Page<Medico> findByActivoTrue(Pageable page);


    @Query("""
            select m from Medico m
            where m.activo = true
            and m.especialidad = :especialidad
            and m.id not in (
                select c.medico.id from Consulta c
                where c.fecha = :fecha
                and
                c.motivoCancelacion is null
            )
            order by rand()
            limit 1
            """)
    Medico elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad especialidad, @NotNull @Future LocalDateTime fecha);


    Boolean existsActivoById(UUID uuid);
}
