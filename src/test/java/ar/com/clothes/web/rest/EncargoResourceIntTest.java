package ar.com.clothes.web.rest;

import ar.com.clothes.ClothesApp;

import ar.com.clothes.domain.Encargo;
import ar.com.clothes.repository.EncargoRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EncargoResource REST controller.
 *
 * @see EncargoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothesApp.class)
public class EncargoResourceIntTest {

    private static final Double DEFAULT_IMPORTE_TOTAL = 1D;
    private static final Double UPDATED_IMPORTE_TOTAL = 2D;

    private static final LocalDate DEFAULT_FECHA_ENCARGO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ENCARGO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_ENTREGA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ENTREGA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DETALLE_VESTIDO = "AAAAAAAAAA";
    private static final String UPDATED_DETALLE_VESTIDO = "BBBBBBBBBB";

    @Autowired
    private EncargoRepository encargoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEncargoMockMvc;

    private Encargo encargo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EncargoResource encargoResource = new EncargoResource(encargoRepository);
        this.restEncargoMockMvc = MockMvcBuilders.standaloneSetup(encargoResource)
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
    public static Encargo createEntity(EntityManager em) {
        Encargo encargo = new Encargo()
            .importeTotal(DEFAULT_IMPORTE_TOTAL)
            .fechaEncargo(DEFAULT_FECHA_ENCARGO)
            .fechaEntrega(DEFAULT_FECHA_ENTREGA)
            .detalleVestido(DEFAULT_DETALLE_VESTIDO);
        return encargo;
    }

    @Before
    public void initTest() {
        encargo = createEntity(em);
    }

    @Test
    @Transactional
    public void createEncargo() throws Exception {
        int databaseSizeBeforeCreate = encargoRepository.findAll().size();

        // Create the Encargo
        restEncargoMockMvc.perform(post("/api/encargos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encargo)))
            .andExpect(status().isCreated());

        // Validate the Encargo in the database
        List<Encargo> encargoList = encargoRepository.findAll();
        assertThat(encargoList).hasSize(databaseSizeBeforeCreate + 1);
        Encargo testEncargo = encargoList.get(encargoList.size() - 1);
        assertThat(testEncargo.getImporteTotal()).isEqualTo(DEFAULT_IMPORTE_TOTAL);
        assertThat(testEncargo.getFechaEncargo()).isEqualTo(DEFAULT_FECHA_ENCARGO);
        assertThat(testEncargo.getFechaEntrega()).isEqualTo(DEFAULT_FECHA_ENTREGA);
        assertThat(testEncargo.getDetalleVestido()).isEqualTo(DEFAULT_DETALLE_VESTIDO);
    }

    @Test
    @Transactional
    public void createEncargoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = encargoRepository.findAll().size();

        // Create the Encargo with an existing ID
        encargo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEncargoMockMvc.perform(post("/api/encargos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encargo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Encargo> encargoList = encargoRepository.findAll();
        assertThat(encargoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEncargos() throws Exception {
        // Initialize the database
        encargoRepository.saveAndFlush(encargo);

        // Get all the encargoList
        restEncargoMockMvc.perform(get("/api/encargos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encargo.getId().intValue())))
            .andExpect(jsonPath("$.[*].importeTotal").value(hasItem(DEFAULT_IMPORTE_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].fechaEncargo").value(hasItem(DEFAULT_FECHA_ENCARGO.toString())))
            .andExpect(jsonPath("$.[*].fechaEntrega").value(hasItem(DEFAULT_FECHA_ENTREGA.toString())))
            .andExpect(jsonPath("$.[*].detalleVestido").value(hasItem(DEFAULT_DETALLE_VESTIDO.toString())));
    }

    @Test
    @Transactional
    public void getEncargo() throws Exception {
        // Initialize the database
        encargoRepository.saveAndFlush(encargo);

        // Get the encargo
        restEncargoMockMvc.perform(get("/api/encargos/{id}", encargo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(encargo.getId().intValue()))
            .andExpect(jsonPath("$.importeTotal").value(DEFAULT_IMPORTE_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.fechaEncargo").value(DEFAULT_FECHA_ENCARGO.toString()))
            .andExpect(jsonPath("$.fechaEntrega").value(DEFAULT_FECHA_ENTREGA.toString()))
            .andExpect(jsonPath("$.detalleVestido").value(DEFAULT_DETALLE_VESTIDO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEncargo() throws Exception {
        // Get the encargo
        restEncargoMockMvc.perform(get("/api/encargos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEncargo() throws Exception {
        // Initialize the database
        encargoRepository.saveAndFlush(encargo);
        int databaseSizeBeforeUpdate = encargoRepository.findAll().size();

        // Update the encargo
        Encargo updatedEncargo = encargoRepository.findOne(encargo.getId());
        updatedEncargo
            .importeTotal(UPDATED_IMPORTE_TOTAL)
            .fechaEncargo(UPDATED_FECHA_ENCARGO)
            .fechaEntrega(UPDATED_FECHA_ENTREGA)
            .detalleVestido(UPDATED_DETALLE_VESTIDO);

        restEncargoMockMvc.perform(put("/api/encargos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEncargo)))
            .andExpect(status().isOk());

        // Validate the Encargo in the database
        List<Encargo> encargoList = encargoRepository.findAll();
        assertThat(encargoList).hasSize(databaseSizeBeforeUpdate);
        Encargo testEncargo = encargoList.get(encargoList.size() - 1);
        assertThat(testEncargo.getImporteTotal()).isEqualTo(UPDATED_IMPORTE_TOTAL);
        assertThat(testEncargo.getFechaEncargo()).isEqualTo(UPDATED_FECHA_ENCARGO);
        assertThat(testEncargo.getFechaEntrega()).isEqualTo(UPDATED_FECHA_ENTREGA);
        assertThat(testEncargo.getDetalleVestido()).isEqualTo(UPDATED_DETALLE_VESTIDO);
    }

    @Test
    @Transactional
    public void updateNonExistingEncargo() throws Exception {
        int databaseSizeBeforeUpdate = encargoRepository.findAll().size();

        // Create the Encargo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEncargoMockMvc.perform(put("/api/encargos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encargo)))
            .andExpect(status().isCreated());

        // Validate the Encargo in the database
        List<Encargo> encargoList = encargoRepository.findAll();
        assertThat(encargoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEncargo() throws Exception {
        // Initialize the database
        encargoRepository.saveAndFlush(encargo);
        int databaseSizeBeforeDelete = encargoRepository.findAll().size();

        // Get the encargo
        restEncargoMockMvc.perform(delete("/api/encargos/{id}", encargo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Encargo> encargoList = encargoRepository.findAll();
        assertThat(encargoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Encargo.class);
    }
}
