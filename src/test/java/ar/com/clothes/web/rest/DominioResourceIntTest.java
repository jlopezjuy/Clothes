package ar.com.clothes.web.rest;

import ar.com.clothes.ClothesApp;

import ar.com.clothes.domain.Dominio;
import ar.com.clothes.repository.DominioRepository;
import ar.com.clothes.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DominioResource REST controller.
 *
 * @see DominioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothesApp.class)
public class DominioResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private DominioRepository dominioRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDominioMockMvc;

    private Dominio dominio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            DominioResource dominioResource = new DominioResource(dominioRepository);
        this.restDominioMockMvc = MockMvcBuilders.standaloneSetup(dominioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dominio createEntity(EntityManager em) {
        Dominio dominio = new Dominio()
                .descripcion(DEFAULT_DESCRIPCION);
        return dominio;
    }

    @Before
    public void initTest() {
        dominio = createEntity(em);
    }

    @Test
    @Transactional
    public void createDominio() throws Exception {
        int databaseSizeBeforeCreate = dominioRepository.findAll().size();

        // Create the Dominio

        restDominioMockMvc.perform(post("/api/dominios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dominio)))
            .andExpect(status().isCreated());

        // Validate the Dominio in the database
        List<Dominio> dominioList = dominioRepository.findAll();
        assertThat(dominioList).hasSize(databaseSizeBeforeCreate + 1);
        Dominio testDominio = dominioList.get(dominioList.size() - 1);
        assertThat(testDominio.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createDominioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dominioRepository.findAll().size();

        // Create the Dominio with an existing ID
        Dominio existingDominio = new Dominio();
        existingDominio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDominioMockMvc.perform(post("/api/dominios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingDominio)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Dominio> dominioList = dominioRepository.findAll();
        assertThat(dominioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDominios() throws Exception {
        // Initialize the database
        dominioRepository.saveAndFlush(dominio);

        // Get all the dominioList
        restDominioMockMvc.perform(get("/api/dominios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dominio.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getDominio() throws Exception {
        // Initialize the database
        dominioRepository.saveAndFlush(dominio);

        // Get the dominio
        restDominioMockMvc.perform(get("/api/dominios/{id}", dominio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dominio.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDominio() throws Exception {
        // Get the dominio
        restDominioMockMvc.perform(get("/api/dominios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDominio() throws Exception {
        // Initialize the database
        dominioRepository.saveAndFlush(dominio);
        int databaseSizeBeforeUpdate = dominioRepository.findAll().size();

        // Update the dominio
        Dominio updatedDominio = dominioRepository.findOne(dominio.getId());
        updatedDominio
                .descripcion(UPDATED_DESCRIPCION);

        restDominioMockMvc.perform(put("/api/dominios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDominio)))
            .andExpect(status().isOk());

        // Validate the Dominio in the database
        List<Dominio> dominioList = dominioRepository.findAll();
        assertThat(dominioList).hasSize(databaseSizeBeforeUpdate);
        Dominio testDominio = dominioList.get(dominioList.size() - 1);
        assertThat(testDominio.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingDominio() throws Exception {
        int databaseSizeBeforeUpdate = dominioRepository.findAll().size();

        // Create the Dominio

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDominioMockMvc.perform(put("/api/dominios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dominio)))
            .andExpect(status().isCreated());

        // Validate the Dominio in the database
        List<Dominio> dominioList = dominioRepository.findAll();
        assertThat(dominioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDominio() throws Exception {
        // Initialize the database
        dominioRepository.saveAndFlush(dominio);
        int databaseSizeBeforeDelete = dominioRepository.findAll().size();

        // Get the dominio
        restDominioMockMvc.perform(delete("/api/dominios/{id}", dominio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dominio> dominioList = dominioRepository.findAll();
        assertThat(dominioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dominio.class);
    }
}
