package ar.com.teco.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.teco.IntegrationTest;
import ar.com.teco.domain.Tecnologia;
import ar.com.teco.repository.TecnologiaRepository;
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
 * Integration tests for the {@link TecnologiaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TecnologiaResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tecnologias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TecnologiaRepository tecnologiaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTecnologiaMockMvc;

    private Tecnologia tecnologia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tecnologia createEntity(EntityManager em) {
        Tecnologia tecnologia = new Tecnologia().descripcion(DEFAULT_DESCRIPCION);
        return tecnologia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tecnologia createUpdatedEntity(EntityManager em) {
        Tecnologia tecnologia = new Tecnologia().descripcion(UPDATED_DESCRIPCION);
        return tecnologia;
    }

    @BeforeEach
    public void initTest() {
        tecnologia = createEntity(em);
    }

    @Test
    @Transactional
    void createTecnologia() throws Exception {
        int databaseSizeBeforeCreate = tecnologiaRepository.findAll().size();
        // Create the Tecnologia
        restTecnologiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tecnologia)))
            .andExpect(status().isCreated());

        // Validate the Tecnologia in the database
        List<Tecnologia> tecnologiaList = tecnologiaRepository.findAll();
        assertThat(tecnologiaList).hasSize(databaseSizeBeforeCreate + 1);
        Tecnologia testTecnologia = tecnologiaList.get(tecnologiaList.size() - 1);
        assertThat(testTecnologia.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createTecnologiaWithExistingId() throws Exception {
        // Create the Tecnologia with an existing ID
        tecnologia.setId(1L);

        int databaseSizeBeforeCreate = tecnologiaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTecnologiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tecnologia)))
            .andExpect(status().isBadRequest());

        // Validate the Tecnologia in the database
        List<Tecnologia> tecnologiaList = tecnologiaRepository.findAll();
        assertThat(tecnologiaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTecnologias() throws Exception {
        // Initialize the database
        tecnologiaRepository.saveAndFlush(tecnologia);

        // Get all the tecnologiaList
        restTecnologiaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tecnologia.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getTecnologia() throws Exception {
        // Initialize the database
        tecnologiaRepository.saveAndFlush(tecnologia);

        // Get the tecnologia
        restTecnologiaMockMvc
            .perform(get(ENTITY_API_URL_ID, tecnologia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tecnologia.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingTecnologia() throws Exception {
        // Get the tecnologia
        restTecnologiaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTecnologia() throws Exception {
        // Initialize the database
        tecnologiaRepository.saveAndFlush(tecnologia);

        int databaseSizeBeforeUpdate = tecnologiaRepository.findAll().size();

        // Update the tecnologia
        Tecnologia updatedTecnologia = tecnologiaRepository.findById(tecnologia.getId()).get();
        // Disconnect from session so that the updates on updatedTecnologia are not directly saved in db
        em.detach(updatedTecnologia);
        updatedTecnologia.descripcion(UPDATED_DESCRIPCION);

        restTecnologiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTecnologia.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTecnologia))
            )
            .andExpect(status().isOk());

        // Validate the Tecnologia in the database
        List<Tecnologia> tecnologiaList = tecnologiaRepository.findAll();
        assertThat(tecnologiaList).hasSize(databaseSizeBeforeUpdate);
        Tecnologia testTecnologia = tecnologiaList.get(tecnologiaList.size() - 1);
        assertThat(testTecnologia.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingTecnologia() throws Exception {
        int databaseSizeBeforeUpdate = tecnologiaRepository.findAll().size();
        tecnologia.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTecnologiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tecnologia.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tecnologia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tecnologia in the database
        List<Tecnologia> tecnologiaList = tecnologiaRepository.findAll();
        assertThat(tecnologiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTecnologia() throws Exception {
        int databaseSizeBeforeUpdate = tecnologiaRepository.findAll().size();
        tecnologia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTecnologiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tecnologia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tecnologia in the database
        List<Tecnologia> tecnologiaList = tecnologiaRepository.findAll();
        assertThat(tecnologiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTecnologia() throws Exception {
        int databaseSizeBeforeUpdate = tecnologiaRepository.findAll().size();
        tecnologia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTecnologiaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tecnologia)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tecnologia in the database
        List<Tecnologia> tecnologiaList = tecnologiaRepository.findAll();
        assertThat(tecnologiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTecnologiaWithPatch() throws Exception {
        // Initialize the database
        tecnologiaRepository.saveAndFlush(tecnologia);

        int databaseSizeBeforeUpdate = tecnologiaRepository.findAll().size();

        // Update the tecnologia using partial update
        Tecnologia partialUpdatedTecnologia = new Tecnologia();
        partialUpdatedTecnologia.setId(tecnologia.getId());

        partialUpdatedTecnologia.descripcion(UPDATED_DESCRIPCION);

        restTecnologiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTecnologia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTecnologia))
            )
            .andExpect(status().isOk());

        // Validate the Tecnologia in the database
        List<Tecnologia> tecnologiaList = tecnologiaRepository.findAll();
        assertThat(tecnologiaList).hasSize(databaseSizeBeforeUpdate);
        Tecnologia testTecnologia = tecnologiaList.get(tecnologiaList.size() - 1);
        assertThat(testTecnologia.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateTecnologiaWithPatch() throws Exception {
        // Initialize the database
        tecnologiaRepository.saveAndFlush(tecnologia);

        int databaseSizeBeforeUpdate = tecnologiaRepository.findAll().size();

        // Update the tecnologia using partial update
        Tecnologia partialUpdatedTecnologia = new Tecnologia();
        partialUpdatedTecnologia.setId(tecnologia.getId());

        partialUpdatedTecnologia.descripcion(UPDATED_DESCRIPCION);

        restTecnologiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTecnologia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTecnologia))
            )
            .andExpect(status().isOk());

        // Validate the Tecnologia in the database
        List<Tecnologia> tecnologiaList = tecnologiaRepository.findAll();
        assertThat(tecnologiaList).hasSize(databaseSizeBeforeUpdate);
        Tecnologia testTecnologia = tecnologiaList.get(tecnologiaList.size() - 1);
        assertThat(testTecnologia.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingTecnologia() throws Exception {
        int databaseSizeBeforeUpdate = tecnologiaRepository.findAll().size();
        tecnologia.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTecnologiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tecnologia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tecnologia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tecnologia in the database
        List<Tecnologia> tecnologiaList = tecnologiaRepository.findAll();
        assertThat(tecnologiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTecnologia() throws Exception {
        int databaseSizeBeforeUpdate = tecnologiaRepository.findAll().size();
        tecnologia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTecnologiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tecnologia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tecnologia in the database
        List<Tecnologia> tecnologiaList = tecnologiaRepository.findAll();
        assertThat(tecnologiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTecnologia() throws Exception {
        int databaseSizeBeforeUpdate = tecnologiaRepository.findAll().size();
        tecnologia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTecnologiaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tecnologia))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tecnologia in the database
        List<Tecnologia> tecnologiaList = tecnologiaRepository.findAll();
        assertThat(tecnologiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTecnologia() throws Exception {
        // Initialize the database
        tecnologiaRepository.saveAndFlush(tecnologia);

        int databaseSizeBeforeDelete = tecnologiaRepository.findAll().size();

        // Delete the tecnologia
        restTecnologiaMockMvc
            .perform(delete(ENTITY_API_URL_ID, tecnologia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tecnologia> tecnologiaList = tecnologiaRepository.findAll();
        assertThat(tecnologiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
