package ar.com.teco.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.teco.IntegrationTest;
import ar.com.teco.domain.Competencia;
import ar.com.teco.repository.CompetenciaRepository;
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
 * Integration tests for the {@link CompetenciaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompetenciaResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/competencias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompetenciaRepository competenciaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompetenciaMockMvc;

    private Competencia competencia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competencia createEntity(EntityManager em) {
        Competencia competencia = new Competencia().descripcion(DEFAULT_DESCRIPCION);
        return competencia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competencia createUpdatedEntity(EntityManager em) {
        Competencia competencia = new Competencia().descripcion(UPDATED_DESCRIPCION);
        return competencia;
    }

    @BeforeEach
    public void initTest() {
        competencia = createEntity(em);
    }

    @Test
    @Transactional
    void createCompetencia() throws Exception {
        int databaseSizeBeforeCreate = competenciaRepository.findAll().size();
        // Create the Competencia
        restCompetenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(competencia)))
            .andExpect(status().isCreated());

        // Validate the Competencia in the database
        List<Competencia> competenciaList = competenciaRepository.findAll();
        assertThat(competenciaList).hasSize(databaseSizeBeforeCreate + 1);
        Competencia testCompetencia = competenciaList.get(competenciaList.size() - 1);
        assertThat(testCompetencia.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createCompetenciaWithExistingId() throws Exception {
        // Create the Competencia with an existing ID
        competencia.setId(1L);

        int databaseSizeBeforeCreate = competenciaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(competencia)))
            .andExpect(status().isBadRequest());

        // Validate the Competencia in the database
        List<Competencia> competenciaList = competenciaRepository.findAll();
        assertThat(competenciaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompetencias() throws Exception {
        // Initialize the database
        competenciaRepository.saveAndFlush(competencia);

        // Get all the competenciaList
        restCompetenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getCompetencia() throws Exception {
        // Initialize the database
        competenciaRepository.saveAndFlush(competencia);

        // Get the competencia
        restCompetenciaMockMvc
            .perform(get(ENTITY_API_URL_ID, competencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(competencia.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingCompetencia() throws Exception {
        // Get the competencia
        restCompetenciaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCompetencia() throws Exception {
        // Initialize the database
        competenciaRepository.saveAndFlush(competencia);

        int databaseSizeBeforeUpdate = competenciaRepository.findAll().size();

        // Update the competencia
        Competencia updatedCompetencia = competenciaRepository.findById(competencia.getId()).get();
        // Disconnect from session so that the updates on updatedCompetencia are not directly saved in db
        em.detach(updatedCompetencia);
        updatedCompetencia.descripcion(UPDATED_DESCRIPCION);

        restCompetenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCompetencia.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCompetencia))
            )
            .andExpect(status().isOk());

        // Validate the Competencia in the database
        List<Competencia> competenciaList = competenciaRepository.findAll();
        assertThat(competenciaList).hasSize(databaseSizeBeforeUpdate);
        Competencia testCompetencia = competenciaList.get(competenciaList.size() - 1);
        assertThat(testCompetencia.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingCompetencia() throws Exception {
        int databaseSizeBeforeUpdate = competenciaRepository.findAll().size();
        competencia.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, competencia.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competencia in the database
        List<Competencia> competenciaList = competenciaRepository.findAll();
        assertThat(competenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompetencia() throws Exception {
        int databaseSizeBeforeUpdate = competenciaRepository.findAll().size();
        competencia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competencia in the database
        List<Competencia> competenciaList = competenciaRepository.findAll();
        assertThat(competenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompetencia() throws Exception {
        int databaseSizeBeforeUpdate = competenciaRepository.findAll().size();
        competencia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetenciaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(competencia)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Competencia in the database
        List<Competencia> competenciaList = competenciaRepository.findAll();
        assertThat(competenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompetenciaWithPatch() throws Exception {
        // Initialize the database
        competenciaRepository.saveAndFlush(competencia);

        int databaseSizeBeforeUpdate = competenciaRepository.findAll().size();

        // Update the competencia using partial update
        Competencia partialUpdatedCompetencia = new Competencia();
        partialUpdatedCompetencia.setId(competencia.getId());

        partialUpdatedCompetencia.descripcion(UPDATED_DESCRIPCION);

        restCompetenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetencia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetencia))
            )
            .andExpect(status().isOk());

        // Validate the Competencia in the database
        List<Competencia> competenciaList = competenciaRepository.findAll();
        assertThat(competenciaList).hasSize(databaseSizeBeforeUpdate);
        Competencia testCompetencia = competenciaList.get(competenciaList.size() - 1);
        assertThat(testCompetencia.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateCompetenciaWithPatch() throws Exception {
        // Initialize the database
        competenciaRepository.saveAndFlush(competencia);

        int databaseSizeBeforeUpdate = competenciaRepository.findAll().size();

        // Update the competencia using partial update
        Competencia partialUpdatedCompetencia = new Competencia();
        partialUpdatedCompetencia.setId(competencia.getId());

        partialUpdatedCompetencia.descripcion(UPDATED_DESCRIPCION);

        restCompetenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetencia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetencia))
            )
            .andExpect(status().isOk());

        // Validate the Competencia in the database
        List<Competencia> competenciaList = competenciaRepository.findAll();
        assertThat(competenciaList).hasSize(databaseSizeBeforeUpdate);
        Competencia testCompetencia = competenciaList.get(competenciaList.size() - 1);
        assertThat(testCompetencia.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingCompetencia() throws Exception {
        int databaseSizeBeforeUpdate = competenciaRepository.findAll().size();
        competencia.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, competencia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competencia in the database
        List<Competencia> competenciaList = competenciaRepository.findAll();
        assertThat(competenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompetencia() throws Exception {
        int databaseSizeBeforeUpdate = competenciaRepository.findAll().size();
        competencia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competencia in the database
        List<Competencia> competenciaList = competenciaRepository.findAll();
        assertThat(competenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompetencia() throws Exception {
        int databaseSizeBeforeUpdate = competenciaRepository.findAll().size();
        competencia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetenciaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(competencia))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Competencia in the database
        List<Competencia> competenciaList = competenciaRepository.findAll();
        assertThat(competenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompetencia() throws Exception {
        // Initialize the database
        competenciaRepository.saveAndFlush(competencia);

        int databaseSizeBeforeDelete = competenciaRepository.findAll().size();

        // Delete the competencia
        restCompetenciaMockMvc
            .perform(delete(ENTITY_API_URL_ID, competencia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Competencia> competenciaList = competenciaRepository.findAll();
        assertThat(competenciaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
