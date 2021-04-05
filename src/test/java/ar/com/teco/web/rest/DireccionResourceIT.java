package ar.com.teco.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.teco.IntegrationTest;
import ar.com.teco.domain.Direccion;
import ar.com.teco.repository.DireccionRepository;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DireccionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DireccionResourceIT {

    private static final String DEFAULT_IDENTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATION = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCIA = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIA = "BBBBBBBBBB";

    private static final String DEFAULT_PARTIDO = "AAAAAAAAAA";
    private static final String UPDATED_PARTIDO = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_CALLE = "AAAAAAAAAA";
    private static final String UPDATED_CALLE = "BBBBBBBBBB";

    private static final Long DEFAULT_ALTURA = 1L;
    private static final Long UPDATED_ALTURA = 2L;

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_SUBREGION = "AAAAAAAAAA";
    private static final String UPDATED_SUBREGION = "BBBBBBBBBB";

    private static final String DEFAULT_HUB = "AAAAAAAAAA";
    private static final String UPDATED_HUB = "BBBBBBBBBB";

    private static final String DEFAULT_BARRIOS_ESPECIALES = "AAAAAAAAAA";
    private static final String UPDATED_BARRIOS_ESPECIALES = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_CALLE = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_CALLE = "BBBBBBBBBB";

    private static final String DEFAULT_ZONA_COMPETENCIA = "AAAAAAAAAA";
    private static final String UPDATED_ZONA_COMPETENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_INTERSECTION_LEFT = "AAAAAAAAAA";
    private static final String UPDATED_INTERSECTION_LEFT = "BBBBBBBBBB";

    private static final String DEFAULT_INTERSECTION_RIGHT = "AAAAAAAAAA";
    private static final String UPDATED_INTERSECTION_RIGHT = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_STREET_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUD = "AAAAAAAAAA";
    private static final String UPDATED_LATITUD = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUD = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUD = "BBBBBBBBBB";

    private static final String DEFAULT_ELEMENTOS_DE_RED = "AAAAAAAAAA";
    private static final String UPDATED_ELEMENTOS_DE_RED = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/direccions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDireccionMockMvc;

    private Direccion direccion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Direccion createEntity(EntityManager em) {
        Direccion direccion = new Direccion()
            .identification(DEFAULT_IDENTIFICATION)
            .pais(DEFAULT_PAIS)
            .provincia(DEFAULT_PROVINCIA)
            .partido(DEFAULT_PARTIDO)
            .localidad(DEFAULT_LOCALIDAD)
            .calle(DEFAULT_CALLE)
            .altura(DEFAULT_ALTURA)
            .region(DEFAULT_REGION)
            .subregion(DEFAULT_SUBREGION)
            .hub(DEFAULT_HUB)
            .barriosEspeciales(DEFAULT_BARRIOS_ESPECIALES)
            .codigoPostal(DEFAULT_CODIGO_POSTAL)
            .tipoCalle(DEFAULT_TIPO_CALLE)
            .zonaCompetencia(DEFAULT_ZONA_COMPETENCIA)
            .intersectionLeft(DEFAULT_INTERSECTION_LEFT)
            .intersectionRight(DEFAULT_INTERSECTION_RIGHT)
            .streetType(DEFAULT_STREET_TYPE)
            .latitud(DEFAULT_LATITUD)
            .longitud(DEFAULT_LONGITUD)
            .elementosDeRed(DEFAULT_ELEMENTOS_DE_RED);
        return direccion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Direccion createUpdatedEntity(EntityManager em) {
        Direccion direccion = new Direccion()
            .identification(UPDATED_IDENTIFICATION)
            .pais(UPDATED_PAIS)
            .provincia(UPDATED_PROVINCIA)
            .partido(UPDATED_PARTIDO)
            .localidad(UPDATED_LOCALIDAD)
            .calle(UPDATED_CALLE)
            .altura(UPDATED_ALTURA)
            .region(UPDATED_REGION)
            .subregion(UPDATED_SUBREGION)
            .hub(UPDATED_HUB)
            .barriosEspeciales(UPDATED_BARRIOS_ESPECIALES)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .tipoCalle(UPDATED_TIPO_CALLE)
            .zonaCompetencia(UPDATED_ZONA_COMPETENCIA)
            .intersectionLeft(UPDATED_INTERSECTION_LEFT)
            .intersectionRight(UPDATED_INTERSECTION_RIGHT)
            .streetType(UPDATED_STREET_TYPE)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .elementosDeRed(UPDATED_ELEMENTOS_DE_RED);
        return direccion;
    }

    @BeforeEach
    public void initTest() {
        direccion = createEntity(em);
    }

    @Test
    @Transactional
    void createDireccion() throws Exception {
        int databaseSizeBeforeCreate = direccionRepository.findAll().size();
        // Create the Direccion
        restDireccionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(direccion)))
            .andExpect(status().isCreated());

        // Validate the Direccion in the database
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeCreate + 1);
        Direccion testDireccion = direccionList.get(direccionList.size() - 1);
        assertThat(testDireccion.getIdentification()).isEqualTo(DEFAULT_IDENTIFICATION);
        assertThat(testDireccion.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testDireccion.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testDireccion.getPartido()).isEqualTo(DEFAULT_PARTIDO);
        assertThat(testDireccion.getLocalidad()).isEqualTo(DEFAULT_LOCALIDAD);
        assertThat(testDireccion.getCalle()).isEqualTo(DEFAULT_CALLE);
        assertThat(testDireccion.getAltura()).isEqualTo(DEFAULT_ALTURA);
        assertThat(testDireccion.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testDireccion.getSubregion()).isEqualTo(DEFAULT_SUBREGION);
        assertThat(testDireccion.getHub()).isEqualTo(DEFAULT_HUB);
        assertThat(testDireccion.getBarriosEspeciales()).isEqualTo(DEFAULT_BARRIOS_ESPECIALES);
        assertThat(testDireccion.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testDireccion.getTipoCalle()).isEqualTo(DEFAULT_TIPO_CALLE);
        assertThat(testDireccion.getZonaCompetencia()).isEqualTo(DEFAULT_ZONA_COMPETENCIA);
        assertThat(testDireccion.getIntersectionLeft()).isEqualTo(DEFAULT_INTERSECTION_LEFT);
        assertThat(testDireccion.getIntersectionRight()).isEqualTo(DEFAULT_INTERSECTION_RIGHT);
        assertThat(testDireccion.getStreetType()).isEqualTo(DEFAULT_STREET_TYPE);
        assertThat(testDireccion.getLatitud()).isEqualTo(DEFAULT_LATITUD);
        assertThat(testDireccion.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
        assertThat(testDireccion.getElementosDeRed()).isEqualTo(DEFAULT_ELEMENTOS_DE_RED);
    }

    @Test
    @Transactional
    void createDireccionWithExistingId() throws Exception {
        // Create the Direccion with an existing ID
        direccion.setId("existing_id");

        int databaseSizeBeforeCreate = direccionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDireccionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(direccion)))
            .andExpect(status().isBadRequest());

        // Validate the Direccion in the database
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPaisIsRequired() throws Exception {
        int databaseSizeBeforeTest = direccionRepository.findAll().size();
        // set the field null
        direccion.setPais(null);

        // Create the Direccion, which fails.

        restDireccionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(direccion)))
            .andExpect(status().isBadRequest());

        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProvinciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = direccionRepository.findAll().size();
        // set the field null
        direccion.setProvincia(null);

        // Create the Direccion, which fails.

        restDireccionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(direccion)))
            .andExpect(status().isBadRequest());

        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPartidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = direccionRepository.findAll().size();
        // set the field null
        direccion.setPartido(null);

        // Create the Direccion, which fails.

        restDireccionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(direccion)))
            .andExpect(status().isBadRequest());

        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLocalidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = direccionRepository.findAll().size();
        // set the field null
        direccion.setLocalidad(null);

        // Create the Direccion, which fails.

        restDireccionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(direccion)))
            .andExpect(status().isBadRequest());

        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCalleIsRequired() throws Exception {
        int databaseSizeBeforeTest = direccionRepository.findAll().size();
        // set the field null
        direccion.setCalle(null);

        // Create the Direccion, which fails.

        restDireccionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(direccion)))
            .andExpect(status().isBadRequest());

        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAlturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = direccionRepository.findAll().size();
        // set the field null
        direccion.setAltura(null);

        // Create the Direccion, which fails.

        restDireccionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(direccion)))
            .andExpect(status().isBadRequest());

        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDireccions() throws Exception {
        // Initialize the database
        direccionRepository.saveAndFlush(direccion);

        // Get all the direccionList
        restDireccionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(direccion.getId())))
            .andExpect(jsonPath("$.[*].identification").value(hasItem(DEFAULT_IDENTIFICATION)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA)))
            .andExpect(jsonPath("$.[*].partido").value(hasItem(DEFAULT_PARTIDO)))
            .andExpect(jsonPath("$.[*].localidad").value(hasItem(DEFAULT_LOCALIDAD)))
            .andExpect(jsonPath("$.[*].calle").value(hasItem(DEFAULT_CALLE)))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA.intValue())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].subregion").value(hasItem(DEFAULT_SUBREGION)))
            .andExpect(jsonPath("$.[*].hub").value(hasItem(DEFAULT_HUB)))
            .andExpect(jsonPath("$.[*].barriosEspeciales").value(hasItem(DEFAULT_BARRIOS_ESPECIALES)))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL)))
            .andExpect(jsonPath("$.[*].tipoCalle").value(hasItem(DEFAULT_TIPO_CALLE)))
            .andExpect(jsonPath("$.[*].zonaCompetencia").value(hasItem(DEFAULT_ZONA_COMPETENCIA)))
            .andExpect(jsonPath("$.[*].intersectionLeft").value(hasItem(DEFAULT_INTERSECTION_LEFT)))
            .andExpect(jsonPath("$.[*].intersectionRight").value(hasItem(DEFAULT_INTERSECTION_RIGHT)))
            .andExpect(jsonPath("$.[*].streetType").value(hasItem(DEFAULT_STREET_TYPE)))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD)))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD)))
            .andExpect(jsonPath("$.[*].elementosDeRed").value(hasItem(DEFAULT_ELEMENTOS_DE_RED)));
    }

    @Test
    @Transactional
    void getDireccion() throws Exception {
        // Initialize the database
        direccionRepository.saveAndFlush(direccion);

        // Get the direccion
        restDireccionMockMvc
            .perform(get(ENTITY_API_URL_ID, direccion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(direccion.getId()))
            .andExpect(jsonPath("$.identification").value(DEFAULT_IDENTIFICATION))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA))
            .andExpect(jsonPath("$.partido").value(DEFAULT_PARTIDO))
            .andExpect(jsonPath("$.localidad").value(DEFAULT_LOCALIDAD))
            .andExpect(jsonPath("$.calle").value(DEFAULT_CALLE))
            .andExpect(jsonPath("$.altura").value(DEFAULT_ALTURA.intValue()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION))
            .andExpect(jsonPath("$.subregion").value(DEFAULT_SUBREGION))
            .andExpect(jsonPath("$.hub").value(DEFAULT_HUB))
            .andExpect(jsonPath("$.barriosEspeciales").value(DEFAULT_BARRIOS_ESPECIALES))
            .andExpect(jsonPath("$.codigoPostal").value(DEFAULT_CODIGO_POSTAL))
            .andExpect(jsonPath("$.tipoCalle").value(DEFAULT_TIPO_CALLE))
            .andExpect(jsonPath("$.zonaCompetencia").value(DEFAULT_ZONA_COMPETENCIA))
            .andExpect(jsonPath("$.intersectionLeft").value(DEFAULT_INTERSECTION_LEFT))
            .andExpect(jsonPath("$.intersectionRight").value(DEFAULT_INTERSECTION_RIGHT))
            .andExpect(jsonPath("$.streetType").value(DEFAULT_STREET_TYPE))
            .andExpect(jsonPath("$.latitud").value(DEFAULT_LATITUD))
            .andExpect(jsonPath("$.longitud").value(DEFAULT_LONGITUD))
            .andExpect(jsonPath("$.elementosDeRed").value(DEFAULT_ELEMENTOS_DE_RED));
    }

    @Test
    @Transactional
    void getNonExistingDireccion() throws Exception {
        // Get the direccion
        restDireccionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDireccion() throws Exception {
        // Initialize the database
        direccionRepository.saveAndFlush(direccion);

        int databaseSizeBeforeUpdate = direccionRepository.findAll().size();

        // Update the direccion
        Direccion updatedDireccion = direccionRepository.findById(direccion.getId()).get();
        // Disconnect from session so that the updates on updatedDireccion are not directly saved in db
        em.detach(updatedDireccion);
        updatedDireccion
            .identification(UPDATED_IDENTIFICATION)
            .pais(UPDATED_PAIS)
            .provincia(UPDATED_PROVINCIA)
            .partido(UPDATED_PARTIDO)
            .localidad(UPDATED_LOCALIDAD)
            .calle(UPDATED_CALLE)
            .altura(UPDATED_ALTURA)
            .region(UPDATED_REGION)
            .subregion(UPDATED_SUBREGION)
            .hub(UPDATED_HUB)
            .barriosEspeciales(UPDATED_BARRIOS_ESPECIALES)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .tipoCalle(UPDATED_TIPO_CALLE)
            .zonaCompetencia(UPDATED_ZONA_COMPETENCIA)
            .intersectionLeft(UPDATED_INTERSECTION_LEFT)
            .intersectionRight(UPDATED_INTERSECTION_RIGHT)
            .streetType(UPDATED_STREET_TYPE)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .elementosDeRed(UPDATED_ELEMENTOS_DE_RED);

        restDireccionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDireccion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDireccion))
            )
            .andExpect(status().isOk());

        // Validate the Direccion in the database
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeUpdate);
        Direccion testDireccion = direccionList.get(direccionList.size() - 1);
        assertThat(testDireccion.getIdentification()).isEqualTo(UPDATED_IDENTIFICATION);
        assertThat(testDireccion.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testDireccion.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testDireccion.getPartido()).isEqualTo(UPDATED_PARTIDO);
        assertThat(testDireccion.getLocalidad()).isEqualTo(UPDATED_LOCALIDAD);
        assertThat(testDireccion.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testDireccion.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testDireccion.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testDireccion.getSubregion()).isEqualTo(UPDATED_SUBREGION);
        assertThat(testDireccion.getHub()).isEqualTo(UPDATED_HUB);
        assertThat(testDireccion.getBarriosEspeciales()).isEqualTo(UPDATED_BARRIOS_ESPECIALES);
        assertThat(testDireccion.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testDireccion.getTipoCalle()).isEqualTo(UPDATED_TIPO_CALLE);
        assertThat(testDireccion.getZonaCompetencia()).isEqualTo(UPDATED_ZONA_COMPETENCIA);
        assertThat(testDireccion.getIntersectionLeft()).isEqualTo(UPDATED_INTERSECTION_LEFT);
        assertThat(testDireccion.getIntersectionRight()).isEqualTo(UPDATED_INTERSECTION_RIGHT);
        assertThat(testDireccion.getStreetType()).isEqualTo(UPDATED_STREET_TYPE);
        assertThat(testDireccion.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testDireccion.getLongitud()).isEqualTo(UPDATED_LONGITUD);
        assertThat(testDireccion.getElementosDeRed()).isEqualTo(UPDATED_ELEMENTOS_DE_RED);
    }

    @Test
    @Transactional
    void putNonExistingDireccion() throws Exception {
        int databaseSizeBeforeUpdate = direccionRepository.findAll().size();
        direccion.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDireccionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, direccion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(direccion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Direccion in the database
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDireccion() throws Exception {
        int databaseSizeBeforeUpdate = direccionRepository.findAll().size();
        direccion.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDireccionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(direccion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Direccion in the database
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDireccion() throws Exception {
        int databaseSizeBeforeUpdate = direccionRepository.findAll().size();
        direccion.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDireccionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(direccion)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Direccion in the database
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDireccionWithPatch() throws Exception {
        // Initialize the database
        direccionRepository.saveAndFlush(direccion);

        int databaseSizeBeforeUpdate = direccionRepository.findAll().size();

        // Update the direccion using partial update
        Direccion partialUpdatedDireccion = new Direccion();
        partialUpdatedDireccion.setId(direccion.getId());

        partialUpdatedDireccion
            .localidad(UPDATED_LOCALIDAD)
            .calle(UPDATED_CALLE)
            .subregion(UPDATED_SUBREGION)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .tipoCalle(UPDATED_TIPO_CALLE)
            .zonaCompetencia(UPDATED_ZONA_COMPETENCIA)
            .intersectionLeft(UPDATED_INTERSECTION_LEFT)
            .intersectionRight(UPDATED_INTERSECTION_RIGHT)
            .streetType(UPDATED_STREET_TYPE)
            .elementosDeRed(UPDATED_ELEMENTOS_DE_RED);

        restDireccionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDireccion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDireccion))
            )
            .andExpect(status().isOk());

        // Validate the Direccion in the database
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeUpdate);
        Direccion testDireccion = direccionList.get(direccionList.size() - 1);
        assertThat(testDireccion.getIdentification()).isEqualTo(DEFAULT_IDENTIFICATION);
        assertThat(testDireccion.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testDireccion.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testDireccion.getPartido()).isEqualTo(DEFAULT_PARTIDO);
        assertThat(testDireccion.getLocalidad()).isEqualTo(UPDATED_LOCALIDAD);
        assertThat(testDireccion.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testDireccion.getAltura()).isEqualTo(DEFAULT_ALTURA);
        assertThat(testDireccion.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testDireccion.getSubregion()).isEqualTo(UPDATED_SUBREGION);
        assertThat(testDireccion.getHub()).isEqualTo(DEFAULT_HUB);
        assertThat(testDireccion.getBarriosEspeciales()).isEqualTo(DEFAULT_BARRIOS_ESPECIALES);
        assertThat(testDireccion.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testDireccion.getTipoCalle()).isEqualTo(UPDATED_TIPO_CALLE);
        assertThat(testDireccion.getZonaCompetencia()).isEqualTo(UPDATED_ZONA_COMPETENCIA);
        assertThat(testDireccion.getIntersectionLeft()).isEqualTo(UPDATED_INTERSECTION_LEFT);
        assertThat(testDireccion.getIntersectionRight()).isEqualTo(UPDATED_INTERSECTION_RIGHT);
        assertThat(testDireccion.getStreetType()).isEqualTo(UPDATED_STREET_TYPE);
        assertThat(testDireccion.getLatitud()).isEqualTo(DEFAULT_LATITUD);
        assertThat(testDireccion.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
        assertThat(testDireccion.getElementosDeRed()).isEqualTo(UPDATED_ELEMENTOS_DE_RED);
    }

    @Test
    @Transactional
    void fullUpdateDireccionWithPatch() throws Exception {
        // Initialize the database
        direccionRepository.saveAndFlush(direccion);

        int databaseSizeBeforeUpdate = direccionRepository.findAll().size();

        // Update the direccion using partial update
        Direccion partialUpdatedDireccion = new Direccion();
        partialUpdatedDireccion.setId(direccion.getId());

        partialUpdatedDireccion
            .identification(UPDATED_IDENTIFICATION)
            .pais(UPDATED_PAIS)
            .provincia(UPDATED_PROVINCIA)
            .partido(UPDATED_PARTIDO)
            .localidad(UPDATED_LOCALIDAD)
            .calle(UPDATED_CALLE)
            .altura(UPDATED_ALTURA)
            .region(UPDATED_REGION)
            .subregion(UPDATED_SUBREGION)
            .hub(UPDATED_HUB)
            .barriosEspeciales(UPDATED_BARRIOS_ESPECIALES)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .tipoCalle(UPDATED_TIPO_CALLE)
            .zonaCompetencia(UPDATED_ZONA_COMPETENCIA)
            .intersectionLeft(UPDATED_INTERSECTION_LEFT)
            .intersectionRight(UPDATED_INTERSECTION_RIGHT)
            .streetType(UPDATED_STREET_TYPE)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .elementosDeRed(UPDATED_ELEMENTOS_DE_RED);

        restDireccionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDireccion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDireccion))
            )
            .andExpect(status().isOk());

        // Validate the Direccion in the database
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeUpdate);
        Direccion testDireccion = direccionList.get(direccionList.size() - 1);
        assertThat(testDireccion.getIdentification()).isEqualTo(UPDATED_IDENTIFICATION);
        assertThat(testDireccion.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testDireccion.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testDireccion.getPartido()).isEqualTo(UPDATED_PARTIDO);
        assertThat(testDireccion.getLocalidad()).isEqualTo(UPDATED_LOCALIDAD);
        assertThat(testDireccion.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testDireccion.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testDireccion.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testDireccion.getSubregion()).isEqualTo(UPDATED_SUBREGION);
        assertThat(testDireccion.getHub()).isEqualTo(UPDATED_HUB);
        assertThat(testDireccion.getBarriosEspeciales()).isEqualTo(UPDATED_BARRIOS_ESPECIALES);
        assertThat(testDireccion.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testDireccion.getTipoCalle()).isEqualTo(UPDATED_TIPO_CALLE);
        assertThat(testDireccion.getZonaCompetencia()).isEqualTo(UPDATED_ZONA_COMPETENCIA);
        assertThat(testDireccion.getIntersectionLeft()).isEqualTo(UPDATED_INTERSECTION_LEFT);
        assertThat(testDireccion.getIntersectionRight()).isEqualTo(UPDATED_INTERSECTION_RIGHT);
        assertThat(testDireccion.getStreetType()).isEqualTo(UPDATED_STREET_TYPE);
        assertThat(testDireccion.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testDireccion.getLongitud()).isEqualTo(UPDATED_LONGITUD);
        assertThat(testDireccion.getElementosDeRed()).isEqualTo(UPDATED_ELEMENTOS_DE_RED);
    }

    @Test
    @Transactional
    void patchNonExistingDireccion() throws Exception {
        int databaseSizeBeforeUpdate = direccionRepository.findAll().size();
        direccion.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDireccionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, direccion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(direccion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Direccion in the database
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDireccion() throws Exception {
        int databaseSizeBeforeUpdate = direccionRepository.findAll().size();
        direccion.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDireccionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(direccion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Direccion in the database
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDireccion() throws Exception {
        int databaseSizeBeforeUpdate = direccionRepository.findAll().size();
        direccion.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDireccionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(direccion))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Direccion in the database
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDireccion() throws Exception {
        // Initialize the database
        direccionRepository.saveAndFlush(direccion);

        int databaseSizeBeforeDelete = direccionRepository.findAll().size();

        // Delete the direccion
        restDireccionMockMvc
            .perform(delete(ENTITY_API_URL_ID, direccion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
