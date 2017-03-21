package ar.com.clothes.repository;

import ar.com.clothes.domain.Dominio;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Dominio entity.
 */
@SuppressWarnings("unused")
public interface DominioRepository extends JpaRepository<Dominio,Long> {

}
