package ar.com.clothes.web.rest;

import com.codahale.metrics.annotation.Timed;

import ar.com.clothes.domain.Dominio;
import ar.com.clothes.domain.ValorDominio;
import ar.com.clothes.repository.DominioRepository;
import ar.com.clothes.repository.ValorDominioRepository;
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
 * REST controller for managing ValorDominio.
 */
@RestController
@RequestMapping("/api")
public class ValorDominioResource {

    private final Logger log = LoggerFactory.getLogger(ValorDominioResource.class);

    private static final String ENTITY_NAME = "valorDominio";
        
    private final ValorDominioRepository valorDominioRepository;
    private final DominioRepository dominioRepository;

    public ValorDominioResource(ValorDominioRepository valorDominioRepository,DominioRepository dominioRepository) {
        this.valorDominioRepository = valorDominioRepository;
        this.dominioRepository = dominioRepository;
    }

    /**
     * POST  /valor-dominios : Create a new valorDominio.
     *
     * @param valorDominio the valorDominio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new valorDominio, or with status 400 (Bad Request) if the valorDominio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/valor-dominios")
    @Timed
    public ResponseEntity<ValorDominio> createValorDominio(@RequestBody ValorDominio valorDominio) throws URISyntaxException {
        log.debug("REST request to save ValorDominio : {}", valorDominio);
        if (valorDominio.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new valorDominio cannot already have an ID")).body(null);
        }
        ValorDominio result = valorDominioRepository.save(valorDominio);
        return ResponseEntity.created(new URI("/api/valor-dominios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /valor-dominios : Updates an existing valorDominio.
     *
     * @param valorDominio the valorDominio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated valorDominio,
     * or with status 400 (Bad Request) if the valorDominio is not valid,
     * or with status 500 (Internal Server Error) if the valorDominio couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/valor-dominios")
    @Timed
    public ResponseEntity<ValorDominio> updateValorDominio(@RequestBody ValorDominio valorDominio) throws URISyntaxException {
        log.debug("REST request to update ValorDominio : {}", valorDominio);
        if (valorDominio.getId() == null) {
            return createValorDominio(valorDominio);
        }
        ValorDominio result = valorDominioRepository.save(valorDominio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, valorDominio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /valor-dominios : get all the valorDominios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of valorDominios in body
     */
    @GetMapping("/valor-dominios")
    @Timed
    public List<ValorDominio> getAllValorDominios() {
        log.debug("REST request to get all ValorDominios");
        List<ValorDominio> valorDominios = valorDominioRepository.findAll();
        return valorDominios;
    }
    
    /**
     * GET  /valor-dominios : get all the valorDominios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of valorDominios in body
     */
    @GetMapping("/valor-dominios-estado")
    @Timed
    public List<ValorDominio> getAllValorDominioEstado() {
        log.debug("REST request to get all ValorDominios");
        Dominio dominio = dominioRepository.findOne(Long.valueOf(1));
        List<ValorDominio> valorDominios = valorDominioRepository.findByDominio(dominio);
        return valorDominios;
    }
    
    /**
     * GET  /valor-dominios : get all the valorDominios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of valorDominios in body
     */
    @GetMapping("/valor-dominios-tipo-evento")
    @Timed
    public List<ValorDominio> getAllValorDominioTipoEvento() {
        log.debug("REST request to get all ValorDominios");
        Dominio dominio = dominioRepository.findOne(Long.valueOf(2));
        List<ValorDominio> valorDominios = valorDominioRepository.findByDominio(dominio);
        return valorDominios;
    }

    /**
     * GET  /valor-dominios/:id : get the "id" valorDominio.
     *
     * @param id the id of the valorDominio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the valorDominio, or with status 404 (Not Found)
     */
    @GetMapping("/valor-dominios/{id}")
    @Timed
    public ResponseEntity<ValorDominio> getValorDominio(@PathVariable Long id) {
        log.debug("REST request to get ValorDominio : {}", id);
        ValorDominio valorDominio = valorDominioRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(valorDominio));
    }

    /**
     * DELETE  /valor-dominios/:id : delete the "id" valorDominio.
     *
     * @param id the id of the valorDominio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/valor-dominios/{id}")
    @Timed
    public ResponseEntity<Void> deleteValorDominio(@PathVariable Long id) {
        log.debug("REST request to delete ValorDominio : {}", id);
        valorDominioRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
