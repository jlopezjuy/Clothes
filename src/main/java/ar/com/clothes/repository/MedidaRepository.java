package ar.com.clothes.repository;

import ar.com.clothes.domain.Medida;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Medida entity.
 */
@SuppressWarnings("unused")
public interface MedidaRepository extends JpaRepository<Medida,Long> {

}
