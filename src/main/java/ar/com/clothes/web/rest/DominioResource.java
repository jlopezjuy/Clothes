package ar.com.clothes.web.rest;

import com.codahale.metrics.annotation.Timed;
import ar.com.clothes.domain.Dominio;

import ar.com.clothes.repository.DominioRepository;
import ar.com.clothes.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Dominio.
 */
@RestController
@RequestMapping("/api")
public class DominioResource {

    private final Logger log = LoggerFactory.getLogger(DominioResource.class);

    private static final String ENTITY_NAME = "dominio";
        
    private final DominioRepository dominioRepository;

    public DominioResource(DominioRepository dominioRepository) {
        this.dominioRepository = dominioRepository;
    }

    /**
     * POST  /dominios : Create a new dominio.
     *
     * @param dominio the dominio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dominio, or with status 400 (Bad Request) if the dominio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dominios")
    @Timed
    public ResponseEntity<Dominio> createDominio(@RequestBody Dominio dominio) throws URISyntaxException {
        log.debug("REST request to save Dominio : {}", dominio);
        if (dominio.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new dominio cannot already have an ID")).body(null);
        }
        Dominio result = dominioRepository.save(dominio);
        return ResponseEntity.created(new URI("/api/dominios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dominios : Updates an existing dominio.
     *
     * @param dominio the dominio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dominio,
     * or with status 400 (Bad Request) if the dominio is not valid,
     * or with status 500 (Internal Server Error) if the dominio couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dominios")
    @Timed
    public ResponseEntity<Dominio> updateDominio(@RequestBody Dominio dominio) throws URISyntaxException {
        log.debug("REST request to update Dominio : {}", dominio);
        if (dominio.getId() == null) {
            return createDominio(dominio);
        }
        Dominio result = dominioRepository.save(dominio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dominio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dominios : get all the dominios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dominios in body
     */
    @GetMapping("/dominios")
    @Timed
    public List<Dominio> getAllDominios() {
        log.debug("REST request to get all Dominios");
        List<Dominio> dominios = dominioRepository.findAll();
        return dominios;
    }

    /**
     * GET  /dominios/:id : get the "id" dominio.
     *
     * @param id the id of the dominio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dominio, or with status 404 (Not Found)
     */
    @GetMapping("/dominios/{id}")
    @Timed
    public ResponseEntity<Dominio> getDominio(@PathVariable Long id) {
        log.debug("REST request to get Dominio : {}", id);
        Dominio dominio = dominioRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dominio));
    }

    /**
     * DELETE  /dominios/:id : delete the "id" dominio.
     *
     * @param id the id of the dominio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dominios/{id}")
    @Timed
    public ResponseEntity<Void> deleteDominio(@PathVariable Long id) {
        log.debug("REST request to delete Dominio : {}", id);
        dominioRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
