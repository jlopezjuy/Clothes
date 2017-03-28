package ar.com.clothes.web.rest;

import ar.com.clothes.ClothesApp;

import ar.com.clothes.domain.ValorDominio;
import ar.com.clothes.repository.ValorDominioRepository;
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
 * Test class for the ValorDominioResource REST controller.
 *
 * @see ValorDominioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothesApp.class)
public class ValorDominioResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private ValorDominioRepository valorDominioRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restValorDominioMockMvc;

    private ValorDominio valorDominio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ValorDominioResource valorDominioResource = new ValorDominioResource(valorDominioRepository);
        this.restValorDominioMockMvc = MockMvcBuilders.standaloneSetup(valorDominioResource)
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
    public static ValorDominio createEntity(EntityManager em) {
        ValorDominio valorDominio = new ValorDominio()
            .descripcion(DEFAULT_DESCRIPCION);
        return valorDominio;
    }

    @Before
    public void initTest() {
        valorDominio = createEntity(em);
    }

    @Test
    @Transactional
    public void createValorDominio() throws Exception {
        int databaseSizeBeforeCreate = valorDominioRepository.findAll().size();

        // Create the ValorDominio
        restValorDominioMockMvc.perform(post("/api/valor-dominios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valorDominio)))
            .andExpect(status().isCreated());

        // Validate the ValorDominio in the database
        List<ValorDominio> valorDominioList = valorDominioRepository.findAll();
        assertThat(valorDominioList).hasSize(databaseSizeBeforeCreate + 1);
        ValorDominio testValorDominio = valorDominioList.get(valorDominioList.size() - 1);
        assertThat(testValorDominio.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createValorDominioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = valorDominioRepository.findAll().size();

        // Create the ValorDominio with an existing ID
        valorDominio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restValorDominioMockMvc.perform(post("/api/valor-dominios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valorDominio)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ValorDominio> valorDominioList = valorDominioRepository.findAll();
        assertThat(valorDominioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllValorDominios() throws Exception {
        // Initialize the database
        valorDominioRepository.saveAndFlush(valorDominio);

        // Get all the valorDominioList
        restValorDominioMockMvc.perform(get("/api/valor-dominios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valorDominio.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getValorDominio() throws Exception {
        // Initialize the database
        valorDominioRepository.saveAndFlush(valorDominio);

        // Get the valorDominio
        restValorDominioMockMvc.perform(get("/api/valor-dominios/{id}", valorDominio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(valorDominio.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingValorDominio() throws Exception {
        // Get the valorDominio
        restValorDominioMockMvc.perform(get("/api/valor-dominios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValorDominio() throws Exception {
        // Initialize the database
        valorDominioRepository.saveAndFlush(valorDominio);
        int databaseSizeBeforeUpdate = valorDominioRepository.findAll().size();

        // Update the valorDominio
        ValorDominio updatedValorDominio = valorDominioRepository.findOne(valorDominio.getId());
        updatedValorDominio
            .descripcion(UPDATED_DESCRIPCION);

        restValorDominioMockMvc.perform(put("/api/valor-dominios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedValorDominio)))
            .andExpect(status().isOk());

        // Validate the ValorDominio in the database
        List<ValorDominio> valorDominioList = valorDominioRepository.findAll();
        assertThat(valorDominioList).hasSize(databaseSizeBeforeUpdate);
        ValorDominio testValorDominio = valorDominioList.get(valorDominioList.size() - 1);
        assertThat(testValorDominio.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingValorDominio() throws Exception {
        int databaseSizeBeforeUpdate = valorDominioRepository.findAll().size();

        // Create the ValorDominio

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restValorDominioMockMvc.perform(put("/api/valor-dominios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valorDominio)))
            .andExpect(status().isCreated());

        // Validate the ValorDominio in the database
        List<ValorDominio> valorDominioList = valorDominioRepository.findAll();
        assertThat(valorDominioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteValorDominio() throws Exception {
        // Initialize the database
        valorDominioRepository.saveAndFlush(valorDominio);
        int databaseSizeBeforeDelete = valorDominioRepository.findAll().size();

        // Get the valorDominio
        restValorDominioMockMvc.perform(delete("/api/valor-dominios/{id}", valorDominio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ValorDominio> valorDominioList = valorDominioRepository.findAll();
        assertThat(valorDominioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValorDominio.class);
    }
}
