package ar.com.teco.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.teco.IntegrationTest;
import ar.com.teco.domain.GrupoEmprendimiento;
import ar.com.teco.repository.GrupoEmprendimientoRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
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
 * Integration tests for the {@link GrupoEmprendimientoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GrupoEmprendimientoResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ES_PROTEGIDO = false;
    private static final Boolean UPDATED_ES_PROTEGIDO = true;

    private static final String ENTITY_API_URL = "/api/grupo-emprendimientos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GrupoEmprendimientoRepository grupoEmprendimientoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrupoEmprendimientoMockMvc;

    private GrupoEmprendimiento grupoEmprendimiento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoEmprendimiento createEntity(EntityManager em) {
        GrupoEmprendimiento grupoEmprendimiento = new GrupoEmprendimiento()
            .descripcion(DEFAULT_DESCRIPCION)
            .esProtegido(DEFAULT_ES_PROTEGIDO);
        return grupoEmprendimiento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoEmprendimiento createUpdatedEntity(EntityManager em) {
        GrupoEmprendimiento grupoEmprendimiento = new GrupoEmprendimiento()
            .descripcion(UPDATED_DESCRIPCION)
            .esProtegido(UPDATED_ES_PROTEGIDO);
        return grupoEmprendimiento;
    }

    @BeforeEach
    public void initTest() {
        grupoEmprendimiento = createEntity(em);
    }

    @Test
    @Transactional
    void createGrupoEmprendimiento() throws Exception {
        int databaseSizeBeforeCreate = grupoEmprendimientoRepository.findAll().size();
        // Create the GrupoEmprendimiento
        restGrupoEmprendimientoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupoEmprendimiento))
            )
            .andExpect(status().isCreated());

        // Validate the GrupoEmprendimiento in the database
        List<GrupoEmprendimiento> grupoEmprendimientoList = grupoEmprendimientoRepository.findAll();
        assertThat(grupoEmprendimientoList).hasSize(databaseSizeBeforeCreate + 1);
        GrupoEmprendimiento testGrupoEmprendimiento = grupoEmprendimientoList.get(grupoEmprendimientoList.size() - 1);
        assertThat(testGrupoEmprendimiento.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testGrupoEmprendimiento.getEsProtegido()).isEqualTo(DEFAULT_ES_PROTEGIDO);
    }

    @Test
    @Transactional
    void createGrupoEmprendimientoWithExistingId() throws Exception {
        // Create the GrupoEmprendimiento with an existing ID
        grupoEmprendimiento.setId(1L);

        int databaseSizeBeforeCreate = grupoEmprendimientoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoEmprendimientoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupoEmprendimiento))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoEmprendimiento in the database
        List<GrupoEmprendimiento> grupoEmprendimientoList = grupoEmprendimientoRepository.findAll();
        assertThat(grupoEmprendimientoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGrupoEmprendimientos() throws Exception {
        // Initialize the database
        grupoEmprendimientoRepository.saveAndFlush(grupoEmprendimiento);

        // Get all the grupoEmprendimientoList
        restGrupoEmprendimientoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoEmprendimiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].esProtegido").value(hasItem(DEFAULT_ES_PROTEGIDO.booleanValue())));
    }

    @Test
    @Transactional
    void getGrupoEmprendimiento() throws Exception {
        // Initialize the database
        grupoEmprendimientoRepository.saveAndFlush(grupoEmprendimiento);

        // Get the grupoEmprendimiento
        restGrupoEmprendimientoMockMvc
            .perform(get(ENTITY_API_URL_ID, grupoEmprendimiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grupoEmprendimiento.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.esProtegido").value(DEFAULT_ES_PROTEGIDO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingGrupoEmprendimiento() throws Exception {
        // Get the grupoEmprendimiento
        restGrupoEmprendimientoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGrupoEmprendimiento() throws Exception {
        // Initialize the database
        grupoEmprendimientoRepository.saveAndFlush(grupoEmprendimiento);

        int databaseSizeBeforeUpdate = grupoEmprendimientoRepository.findAll().size();

        // Update the grupoEmprendimiento
        GrupoEmprendimiento updatedGrupoEmprendimiento = grupoEmprendimientoRepository.findById(grupoEmprendimiento.getId()).get();
        // Disconnect from session so that the updates on updatedGrupoEmprendimiento are not directly saved in db
        em.detach(updatedGrupoEmprendimiento);
        updatedGrupoEmprendimiento.descripcion(UPDATED_DESCRIPCION).esProtegido(UPDATED_ES_PROTEGIDO);

        restGrupoEmprendimientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGrupoEmprendimiento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGrupoEmprendimiento))
            )
            .andExpect(status().isOk());

        // Validate the GrupoEmprendimiento in the database
        List<GrupoEmprendimiento> grupoEmprendimientoList = grupoEmprendimientoRepository.findAll();
        assertThat(grupoEmprendimientoList).hasSize(databaseSizeBeforeUpdate);
        GrupoEmprendimiento testGrupoEmprendimiento = grupoEmprendimientoList.get(grupoEmprendimientoList.size() - 1);
        assertThat(testGrupoEmprendimiento.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testGrupoEmprendimiento.getEsProtegido()).isEqualTo(UPDATED_ES_PROTEGIDO);
    }

    @Test
    @Transactional
    void putNonExistingGrupoEmprendimiento() throws Exception {
        int databaseSizeBeforeUpdate = grupoEmprendimientoRepository.findAll().size();
        grupoEmprendimiento.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoEmprendimientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, grupoEmprendimiento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(grupoEmprendimiento))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoEmprendimiento in the database
        List<GrupoEmprendimiento> grupoEmprendimientoList = grupoEmprendimientoRepository.findAll();
        assertThat(grupoEmprendimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGrupoEmprendimiento() throws Exception {
        int databaseSizeBeforeUpdate = grupoEmprendimientoRepository.findAll().size();
        grupoEmprendimiento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoEmprendimientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(grupoEmprendimiento))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoEmprendimiento in the database
        List<GrupoEmprendimiento> grupoEmprendimientoList = grupoEmprendimientoRepository.findAll();
        assertThat(grupoEmprendimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGrupoEmprendimiento() throws Exception {
        int databaseSizeBeforeUpdate = grupoEmprendimientoRepository.findAll().size();
        grupoEmprendimiento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoEmprendimientoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupoEmprendimiento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoEmprendimiento in the database
        List<GrupoEmprendimiento> grupoEmprendimientoList = grupoEmprendimientoRepository.findAll();
        assertThat(grupoEmprendimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGrupoEmprendimientoWithPatch() throws Exception {
        // Initialize the database
        grupoEmprendimientoRepository.saveAndFlush(grupoEmprendimiento);

        int databaseSizeBeforeUpdate = grupoEmprendimientoRepository.findAll().size();

        // Update the grupoEmprendimiento using partial update
        GrupoEmprendimiento partialUpdatedGrupoEmprendimiento = new GrupoEmprendimiento();
        partialUpdatedGrupoEmprendimiento.setId(grupoEmprendimiento.getId());

        partialUpdatedGrupoEmprendimiento.descripcion(UPDATED_DESCRIPCION);

        restGrupoEmprendimientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoEmprendimiento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGrupoEmprendimiento))
            )
            .andExpect(status().isOk());

        // Validate the GrupoEmprendimiento in the database
        List<GrupoEmprendimiento> grupoEmprendimientoList = grupoEmprendimientoRepository.findAll();
        assertThat(grupoEmprendimientoList).hasSize(databaseSizeBeforeUpdate);
        GrupoEmprendimiento testGrupoEmprendimiento = grupoEmprendimientoList.get(grupoEmprendimientoList.size() - 1);
        assertThat(testGrupoEmprendimiento.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testGrupoEmprendimiento.getEsProtegido()).isEqualTo(DEFAULT_ES_PROTEGIDO);
    }

    @Test
    @Transactional
    void fullUpdateGrupoEmprendimientoWithPatch() throws Exception {
        // Initialize the database
        grupoEmprendimientoRepository.saveAndFlush(grupoEmprendimiento);

        int databaseSizeBeforeUpdate = grupoEmprendimientoRepository.findAll().size();

        // Update the grupoEmprendimiento using partial update
        GrupoEmprendimiento partialUpdatedGrupoEmprendimiento = new GrupoEmprendimiento();
        partialUpdatedGrupoEmprendimiento.setId(grupoEmprendimiento.getId());

        partialUpdatedGrupoEmprendimiento.descripcion(UPDATED_DESCRIPCION).esProtegido(UPDATED_ES_PROTEGIDO);

        restGrupoEmprendimientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoEmprendimiento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGrupoEmprendimiento))
            )
            .andExpect(status().isOk());

        // Validate the GrupoEmprendimiento in the database
        List<GrupoEmprendimiento> grupoEmprendimientoList = grupoEmprendimientoRepository.findAll();
        assertThat(grupoEmprendimientoList).hasSize(databaseSizeBeforeUpdate);
        GrupoEmprendimiento testGrupoEmprendimiento = grupoEmprendimientoList.get(grupoEmprendimientoList.size() - 1);
        assertThat(testGrupoEmprendimiento.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testGrupoEmprendimiento.getEsProtegido()).isEqualTo(UPDATED_ES_PROTEGIDO);
    }

    @Test
    @Transactional
    void patchNonExistingGrupoEmprendimiento() throws Exception {
        int databaseSizeBeforeUpdate = grupoEmprendimientoRepository.findAll().size();
        grupoEmprendimiento.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoEmprendimientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, grupoEmprendimiento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(grupoEmprendimiento))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoEmprendimiento in the database
        List<GrupoEmprendimiento> grupoEmprendimientoList = grupoEmprendimientoRepository.findAll();
        assertThat(grupoEmprendimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGrupoEmprendimiento() throws Exception {
        int databaseSizeBeforeUpdate = grupoEmprendimientoRepository.findAll().size();
        grupoEmprendimiento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoEmprendimientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(grupoEmprendimiento))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoEmprendimiento in the database
        List<GrupoEmprendimiento> grupoEmprendimientoList = grupoEmprendimientoRepository.findAll();
        assertThat(grupoEmprendimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGrupoEmprendimiento() throws Exception {
        int databaseSizeBeforeUpdate = grupoEmprendimientoRepository.findAll().size();
        grupoEmprendimiento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoEmprendimientoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(grupoEmprendimiento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoEmprendimiento in the database
        List<GrupoEmprendimiento> grupoEmprendimientoList = grupoEmprendimientoRepository.findAll();
        assertThat(grupoEmprendimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGrupoEmprendimiento() throws Exception {
        // Initialize the database
        grupoEmprendimientoRepository.saveAndFlush(grupoEmprendimiento);

        int databaseSizeBeforeDelete = grupoEmprendimientoRepository.findAll().size();

        // Delete the grupoEmprendimiento
        restGrupoEmprendimientoMockMvc
            .perform(delete(ENTITY_API_URL_ID, grupoEmprendimiento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GrupoEmprendimiento> grupoEmprendimientoList = grupoEmprendimientoRepository.findAll();
        assertThat(grupoEmprendimientoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
