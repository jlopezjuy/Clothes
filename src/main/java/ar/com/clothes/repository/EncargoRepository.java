package ar.com.clothes.repository;

import ar.com.clothes.domain.Encargo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Encargo entity.
 */
@SuppressWarnings("unused")
public interface EncargoRepository extends JpaRepository<Encargo,Long> {

}
