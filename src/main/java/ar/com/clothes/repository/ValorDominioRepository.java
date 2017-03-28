package ar.com.clothes.repository;

import ar.com.clothes.domain.ValorDominio;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ValorDominio entity.
 */
@SuppressWarnings("unused")
public interface ValorDominioRepository extends JpaRepository<ValorDominio,Long> {

}
