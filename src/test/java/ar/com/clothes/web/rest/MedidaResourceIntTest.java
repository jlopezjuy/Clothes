package ar.com.clothes.web.rest;

import ar.com.clothes.ClothesApp;

import ar.com.clothes.domain.Medida;
import ar.com.clothes.repository.MedidaRepository;
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
 * Test class for the MedidaResource REST controller.
 *
 * @see MedidaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothesApp.class)
public class MedidaResourceIntTest {

    private static final Double DEFAULT_CONTORNO_BUSTO = 1D;
    private static final Double UPDATED_CONTORNO_BUSTO = 2D;

    private static final Double DEFAULT_ANCHO_PECHO = 1D;
    private static final Double UPDATED_ANCHO_PECHO = 2D;

    private static final Double DEFAULT_ALTO_BUSTO = 1D;
    private static final Double UPDATED_ALTO_BUSTO = 2D;

    private static final Double DEFAULT_BAJO_BUSTO = 1D;
    private static final Double UPDATED_BAJO_BUSTO = 2D;

    private static final Double DEFAULT_ALTURA_PINZA = 1D;
    private static final Double UPDATED_ALTURA_PINZA = 2D;

    private static final Double DEFAULT_SEPARACION_BUSTO = 1D;
    private static final Double UPDATED_SEPARACION_BUSTO = 2D;

    private static final Double DEFAULT_TALLE_DELTANTERO = 1D;
    private static final Double UPDATED_TALLE_DELTANTERO = 2D;

    private static final Double DEFAULT_TALLE_ESPALDA = 1D;
    private static final Double UPDATED_TALLE_ESPALDA = 2D;

    private static final Double DEFAULT_LARGO_CORSET = 1D;
    private static final Double UPDATED_LARGO_CORSET = 2D;

    private static final Double DEFAULT_COSTADO = 1D;
    private static final Double UPDATED_COSTADO = 2D;

    private static final Double DEFAULT_HOMBRO = 1D;
    private static final Double UPDATED_HOMBRO = 2D;

    private static final Double DEFAULT_ANCHO_HOMBRO = 1D;
    private static final Double UPDATED_ANCHO_HOMBRO = 2D;

    private static final Double DEFAULT_LARGO_MANGA = 1D;
    private static final Double UPDATED_LARGO_MANGA = 2D;

    private static final Double DEFAULT_SISA = 1D;
    private static final Double UPDATED_SISA = 2D;

    private static final Double DEFAULT_CINTURA = 1D;
    private static final Double UPDATED_CINTURA = 2D;

    private static final Double DEFAULT_ANTE_CADERA = 1D;
    private static final Double UPDATED_ANTE_CADERA = 2D;

    private static final Double DEFAULT_CADERA = 1D;
    private static final Double UPDATED_CADERA = 2D;

    private static final Double DEFAULT_LARGO_POLLERA = 1D;
    private static final Double UPDATED_LARGO_POLLERA = 2D;

    private static final LocalDate DEFAULT_FECHA_MEDIDA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MEDIDA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MedidaRepository medidaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedidaMockMvc;

    private Medida medida;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            MedidaResource medidaResource = new MedidaResource(medidaRepository);
        this.restMedidaMockMvc = MockMvcBuilders.standaloneSetup(medidaResource)
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
    public static Medida createEntity(EntityManager em) {
        Medida medida = new Medida()
                .contornoBusto(DEFAULT_CONTORNO_BUSTO)
                .anchoPecho(DEFAULT_ANCHO_PECHO)
                .altoBusto(DEFAULT_ALTO_BUSTO)
                .bajoBusto(DEFAULT_BAJO_BUSTO)
                .alturaPinza(DEFAULT_ALTURA_PINZA)
                .separacionBusto(DEFAULT_SEPARACION_BUSTO)
                .talleDeltantero(DEFAULT_TALLE_DELTANTERO)
                .talleEspalda(DEFAULT_TALLE_ESPALDA)
                .largoCorset(DEFAULT_LARGO_CORSET)
                .costado(DEFAULT_COSTADO)
                .hombro(DEFAULT_HOMBRO)
                .anchoHombro(DEFAULT_ANCHO_HOMBRO)
                .largoManga(DEFAULT_LARGO_MANGA)
                .sisa(DEFAULT_SISA)
                .cintura(DEFAULT_CINTURA)
                .anteCadera(DEFAULT_ANTE_CADERA)
                .cadera(DEFAULT_CADERA)
                .largoPollera(DEFAULT_LARGO_POLLERA)
                .fechaMedida(DEFAULT_FECHA_MEDIDA);
        return medida;
    }

    @Before
    public void initTest() {
        medida = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedida() throws Exception {
        int databaseSizeBeforeCreate = medidaRepository.findAll().size();

        // Create the Medida

        restMedidaMockMvc.perform(post("/api/medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medida)))
            .andExpect(status().isCreated());

        // Validate the Medida in the database
        List<Medida> medidaList = medidaRepository.findAll();
        assertThat(medidaList).hasSize(databaseSizeBeforeCreate + 1);
        Medida testMedida = medidaList.get(medidaList.size() - 1);
        assertThat(testMedida.getContornoBusto()).isEqualTo(DEFAULT_CONTORNO_BUSTO);
        assertThat(testMedida.getAnchoPecho()).isEqualTo(DEFAULT_ANCHO_PECHO);
        assertThat(testMedida.getAltoBusto()).isEqualTo(DEFAULT_ALTO_BUSTO);
        assertThat(testMedida.getBajoBusto()).isEqualTo(DEFAULT_BAJO_BUSTO);
        assertThat(testMedida.getAlturaPinza()).isEqualTo(DEFAULT_ALTURA_PINZA);
        assertThat(testMedida.getSeparacionBusto()).isEqualTo(DEFAULT_SEPARACION_BUSTO);
        assertThat(testMedida.getTalleDeltantero()).isEqualTo(DEFAULT_TALLE_DELTANTERO);
        assertThat(testMedida.getTalleEspalda()).isEqualTo(DEFAULT_TALLE_ESPALDA);
        assertThat(testMedida.getLargoCorset()).isEqualTo(DEFAULT_LARGO_CORSET);
        assertThat(testMedida.getCostado()).isEqualTo(DEFAULT_COSTADO);
        assertThat(testMedida.getHombro()).isEqualTo(DEFAULT_HOMBRO);
        assertThat(testMedida.getAnchoHombro()).isEqualTo(DEFAULT_ANCHO_HOMBRO);
        assertThat(testMedida.getLargoManga()).isEqualTo(DEFAULT_LARGO_MANGA);
        assertThat(testMedida.getSisa()).isEqualTo(DEFAULT_SISA);
        assertThat(testMedida.getCintura()).isEqualTo(DEFAULT_CINTURA);
        assertThat(testMedida.getAnteCadera()).isEqualTo(DEFAULT_ANTE_CADERA);
        assertThat(testMedida.getCadera()).isEqualTo(DEFAULT_CADERA);
        assertThat(testMedida.getLargoPollera()).isEqualTo(DEFAULT_LARGO_POLLERA);
        assertThat(testMedida.getFechaMedida()).isEqualTo(DEFAULT_FECHA_MEDIDA);
    }

    @Test
    @Transactional
    public void createMedidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medidaRepository.findAll().size();

        // Create the Medida with an existing ID
        Medida existingMedida = new Medida();
        existingMedida.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedidaMockMvc.perform(post("/api/medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingMedida)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Medida> medidaList = medidaRepository.findAll();
        assertThat(medidaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMedidas() throws Exception {
        // Initialize the database
        medidaRepository.saveAndFlush(medida);

        // Get all the medidaList
        restMedidaMockMvc.perform(get("/api/medidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medida.getId().intValue())))
            .andExpect(jsonPath("$.[*].contornoBusto").value(hasItem(DEFAULT_CONTORNO_BUSTO.doubleValue())))
            .andExpect(jsonPath("$.[*].anchoPecho").value(hasItem(DEFAULT_ANCHO_PECHO.doubleValue())))
            .andExpect(jsonPath("$.[*].altoBusto").value(hasItem(DEFAULT_ALTO_BUSTO.doubleValue())))
            .andExpect(jsonPath("$.[*].bajoBusto").value(hasItem(DEFAULT_BAJO_BUSTO.doubleValue())))
            .andExpect(jsonPath("$.[*].alturaPinza").value(hasItem(DEFAULT_ALTURA_PINZA.doubleValue())))
            .andExpect(jsonPath("$.[*].separacionBusto").value(hasItem(DEFAULT_SEPARACION_BUSTO.doubleValue())))
            .andExpect(jsonPath("$.[*].talleDeltantero").value(hasItem(DEFAULT_TALLE_DELTANTERO.doubleValue())))
            .andExpect(jsonPath("$.[*].talleEspalda").value(hasItem(DEFAULT_TALLE_ESPALDA.doubleValue())))
            .andExpect(jsonPath("$.[*].largoCorset").value(hasItem(DEFAULT_LARGO_CORSET.doubleValue())))
            .andExpect(jsonPath("$.[*].costado").value(hasItem(DEFAULT_COSTADO.doubleValue())))
            .andExpect(jsonPath("$.[*].hombro").value(hasItem(DEFAULT_HOMBRO.doubleValue())))
            .andExpect(jsonPath("$.[*].anchoHombro").value(hasItem(DEFAULT_ANCHO_HOMBRO.doubleValue())))
            .andExpect(jsonPath("$.[*].largoManga").value(hasItem(DEFAULT_LARGO_MANGA.doubleValue())))
            .andExpect(jsonPath("$.[*].sisa").value(hasItem(DEFAULT_SISA.doubleValue())))
            .andExpect(jsonPath("$.[*].cintura").value(hasItem(DEFAULT_CINTURA.doubleValue())))
            .andExpect(jsonPath("$.[*].anteCadera").value(hasItem(DEFAULT_ANTE_CADERA.doubleValue())))
            .andExpect(jsonPath("$.[*].cadera").value(hasItem(DEFAULT_CADERA.doubleValue())))
            .andExpect(jsonPath("$.[*].largoPollera").value(hasItem(DEFAULT_LARGO_POLLERA.doubleValue())))
            .andExpect(jsonPath("$.[*].fechaMedida").value(hasItem(DEFAULT_FECHA_MEDIDA.toString())));
    }

    @Test
    @Transactional
    public void getMedida() throws Exception {
        // Initialize the database
        medidaRepository.saveAndFlush(medida);

        // Get the medida
        restMedidaMockMvc.perform(get("/api/medidas/{id}", medida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medida.getId().intValue()))
            .andExpect(jsonPath("$.contornoBusto").value(DEFAULT_CONTORNO_BUSTO.doubleValue()))
            .andExpect(jsonPath("$.anchoPecho").value(DEFAULT_ANCHO_PECHO.doubleValue()))
            .andExpect(jsonPath("$.altoBusto").value(DEFAULT_ALTO_BUSTO.doubleValue()))
            .andExpect(jsonPath("$.bajoBusto").value(DEFAULT_BAJO_BUSTO.doubleValue()))
            .andExpect(jsonPath("$.alturaPinza").value(DEFAULT_ALTURA_PINZA.doubleValue()))
            .andExpect(jsonPath("$.separacionBusto").value(DEFAULT_SEPARACION_BUSTO.doubleValue()))
            .andExpect(jsonPath("$.talleDeltantero").value(DEFAULT_TALLE_DELTANTERO.doubleValue()))
            .andExpect(jsonPath("$.talleEspalda").value(DEFAULT_TALLE_ESPALDA.doubleValue()))
            .andExpect(jsonPath("$.largoCorset").value(DEFAULT_LARGO_CORSET.doubleValue()))
            .andExpect(jsonPath("$.costado").value(DEFAULT_COSTADO.doubleValue()))
            .andExpect(jsonPath("$.hombro").value(DEFAULT_HOMBRO.doubleValue()))
            .andExpect(jsonPath("$.anchoHombro").value(DEFAULT_ANCHO_HOMBRO.doubleValue()))
            .andExpect(jsonPath("$.largoManga").value(DEFAULT_LARGO_MANGA.doubleValue()))
            .andExpect(jsonPath("$.sisa").value(DEFAULT_SISA.doubleValue()))
            .andExpect(jsonPath("$.cintura").value(DEFAULT_CINTURA.doubleValue()))
            .andExpect(jsonPath("$.anteCadera").value(DEFAULT_ANTE_CADERA.doubleValue()))
            .andExpect(jsonPath("$.cadera").value(DEFAULT_CADERA.doubleValue()))
            .andExpect(jsonPath("$.largoPollera").value(DEFAULT_LARGO_POLLERA.doubleValue()))
            .andExpect(jsonPath("$.fechaMedida").value(DEFAULT_FECHA_MEDIDA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedida() throws Exception {
        // Get the medida
        restMedidaMockMvc.perform(get("/api/medidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedida() throws Exception {
        // Initialize the database
        medidaRepository.saveAndFlush(medida);
        int databaseSizeBeforeUpdate = medidaRepository.findAll().size();

        // Update the medida
        Medida updatedMedida = medidaRepository.findOne(medida.getId());
        updatedMedida
                .contornoBusto(UPDATED_CONTORNO_BUSTO)
                .anchoPecho(UPDATED_ANCHO_PECHO)
                .altoBusto(UPDATED_ALTO_BUSTO)
                .bajoBusto(UPDATED_BAJO_BUSTO)
                .alturaPinza(UPDATED_ALTURA_PINZA)
                .separacionBusto(UPDATED_SEPARACION_BUSTO)
                .talleDeltantero(UPDATED_TALLE_DELTANTERO)
                .talleEspalda(UPDATED_TALLE_ESPALDA)
                .largoCorset(UPDATED_LARGO_CORSET)
                .costado(UPDATED_COSTADO)
                .hombro(UPDATED_HOMBRO)
                .anchoHombro(UPDATED_ANCHO_HOMBRO)
                .largoManga(UPDATED_LARGO_MANGA)
                .sisa(UPDATED_SISA)
                .cintura(UPDATED_CINTURA)
                .anteCadera(UPDATED_ANTE_CADERA)
                .cadera(UPDATED_CADERA)
                .largoPollera(UPDATED_LARGO_POLLERA)
                .fechaMedida(UPDATED_FECHA_MEDIDA);

        restMedidaMockMvc.perform(put("/api/medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedida)))
            .andExpect(status().isOk());

        // Validate the Medida in the database
        List<Medida> medidaList = medidaRepository.findAll();
        assertThat(medidaList).hasSize(databaseSizeBeforeUpdate);
        Medida testMedida = medidaList.get(medidaList.size() - 1);
        assertThat(testMedida.getContornoBusto()).isEqualTo(UPDATED_CONTORNO_BUSTO);
        assertThat(testMedida.getAnchoPecho()).isEqualTo(UPDATED_ANCHO_PECHO);
        assertThat(testMedida.getAltoBusto()).isEqualTo(UPDATED_ALTO_BUSTO);
        assertThat(testMedida.getBajoBusto()).isEqualTo(UPDATED_BAJO_BUSTO);
        assertThat(testMedida.getAlturaPinza()).isEqualTo(UPDATED_ALTURA_PINZA);
        assertThat(testMedida.getSeparacionBusto()).isEqualTo(UPDATED_SEPARACION_BUSTO);
        assertThat(testMedida.getTalleDeltantero()).isEqualTo(UPDATED_TALLE_DELTANTERO);
        assertThat(testMedida.getTalleEspalda()).isEqualTo(UPDATED_TALLE_ESPALDA);
        assertThat(testMedida.getLargoCorset()).isEqualTo(UPDATED_LARGO_CORSET);
        assertThat(testMedida.getCostado()).isEqualTo(UPDATED_COSTADO);
        assertThat(testMedida.getHombro()).isEqualTo(UPDATED_HOMBRO);
        assertThat(testMedida.getAnchoHombro()).isEqualTo(UPDATED_ANCHO_HOMBRO);
        assertThat(testMedida.getLargoManga()).isEqualTo(UPDATED_LARGO_MANGA);
        assertThat(testMedida.getSisa()).isEqualTo(UPDATED_SISA);
        assertThat(testMedida.getCintura()).isEqualTo(UPDATED_CINTURA);
        assertThat(testMedida.getAnteCadera()).isEqualTo(UPDATED_ANTE_CADERA);
        assertThat(testMedida.getCadera()).isEqualTo(UPDATED_CADERA);
        assertThat(testMedida.getLargoPollera()).isEqualTo(UPDATED_LARGO_POLLERA);
        assertThat(testMedida.getFechaMedida()).isEqualTo(UPDATED_FECHA_MEDIDA);
    }

    @Test
    @Transactional
    public void updateNonExistingMedida() throws Exception {
        int databaseSizeBeforeUpdate = medidaRepository.findAll().size();

        // Create the Medida

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedidaMockMvc.perform(put("/api/medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medida)))
            .andExpect(status().isCreated());

        // Validate the Medida in the database
        List<Medida> medidaList = medidaRepository.findAll();
        assertThat(medidaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedida() throws Exception {
        // Initialize the database
        medidaRepository.saveAndFlush(medida);
        int databaseSizeBeforeDelete = medidaRepository.findAll().size();

        // Get the medida
        restMedidaMockMvc.perform(delete("/api/medidas/{id}", medida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Medida> medidaList = medidaRepository.findAll();
        assertThat(medidaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medida.class);
    }
}
