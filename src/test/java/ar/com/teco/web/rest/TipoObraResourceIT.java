package ar.com.teco.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.teco.IntegrationTest;
import ar.com.teco.domain.TipoObra;
import ar.com.teco.repository.TipoObraRepository;
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
 * Integration tests for the {@link TipoObraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoObraResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-obras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoObraRepository tipoObraRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoObraMockMvc;

    private TipoObra tipoObra;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoObra createEntity(EntityManager em) {
        TipoObra tipoObra = new TipoObra().descripcion(DEFAULT_DESCRIPCION);
        return tipoObra;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoObra createUpdatedEntity(EntityManager em) {
        TipoObra tipoObra = new TipoObra().descripcion(UPDATED_DESCRIPCION);
        return tipoObra;
    }

    @BeforeEach
    public void initTest() {
        tipoObra = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoObra() throws Exception {
        int databaseSizeBeforeCreate = tipoObraRepository.findAll().size();
        // Create the TipoObra
        restTipoObraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoObra)))
            .andExpect(status().isCreated());

        // Validate the TipoObra in the database
        List<TipoObra> tipoObraList = tipoObraRepository.findAll();
        assertThat(tipoObraList).hasSize(databaseSizeBeforeCreate + 1);
        TipoObra testTipoObra = tipoObraList.get(tipoObraList.size() - 1);
        assertThat(testTipoObra.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createTipoObraWithExistingId() throws Exception {
        // Create the TipoObra with an existing ID
        tipoObra.setId(1L);

        int databaseSizeBeforeCreate = tipoObraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoObraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoObra)))
            .andExpect(status().isBadRequest());

        // Validate the TipoObra in the database
        List<TipoObra> tipoObraList = tipoObraRepository.findAll();
        assertThat(tipoObraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTipoObras() throws Exception {
        // Initialize the database
        tipoObraRepository.saveAndFlush(tipoObra);

        // Get all the tipoObraList
        restTipoObraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoObra.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getTipoObra() throws Exception {
        // Initialize the database
        tipoObraRepository.saveAndFlush(tipoObra);

        // Get the tipoObra
        restTipoObraMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoObra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoObra.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingTipoObra() throws Exception {
        // Get the tipoObra
        restTipoObraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTipoObra() throws Exception {
        // Initialize the database
        tipoObraRepository.saveAndFlush(tipoObra);

        int databaseSizeBeforeUpdate = tipoObraRepository.findAll().size();

        // Update the tipoObra
        TipoObra updatedTipoObra = tipoObraRepository.findById(tipoObra.getId()).get();
        // Disconnect from session so that the updates on updatedTipoObra are not directly saved in db
        em.detach(updatedTipoObra);
        updatedTipoObra.descripcion(UPDATED_DESCRIPCION);

        restTipoObraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTipoObra.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipoObra))
            )
            .andExpect(status().isOk());

        // Validate the TipoObra in the database
        List<TipoObra> tipoObraList = tipoObraRepository.findAll();
        assertThat(tipoObraList).hasSize(databaseSizeBeforeUpdate);
        TipoObra testTipoObra = tipoObraList.get(tipoObraList.size() - 1);
        assertThat(testTipoObra.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingTipoObra() throws Exception {
        int databaseSizeBeforeUpdate = tipoObraRepository.findAll().size();
        tipoObra.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoObraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoObra.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoObra))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoObra in the database
        List<TipoObra> tipoObraList = tipoObraRepository.findAll();
        assertThat(tipoObraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoObra() throws Exception {
        int databaseSizeBeforeUpdate = tipoObraRepository.findAll().size();
        tipoObra.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoObraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoObra))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoObra in the database
        List<TipoObra> tipoObraList = tipoObraRepository.findAll();
        assertThat(tipoObraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoObra() throws Exception {
        int databaseSizeBeforeUpdate = tipoObraRepository.findAll().size();
        tipoObra.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoObraMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoObra)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoObra in the database
        List<TipoObra> tipoObraList = tipoObraRepository.findAll();
        assertThat(tipoObraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoObraWithPatch() throws Exception {
        // Initialize the database
        tipoObraRepository.saveAndFlush(tipoObra);

        int databaseSizeBeforeUpdate = tipoObraRepository.findAll().size();

        // Update the tipoObra using partial update
        TipoObra partialUpdatedTipoObra = new TipoObra();
        partialUpdatedTipoObra.setId(tipoObra.getId());

        restTipoObraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoObra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoObra))
            )
            .andExpect(status().isOk());

        // Validate the TipoObra in the database
        List<TipoObra> tipoObraList = tipoObraRepository.findAll();
        assertThat(tipoObraList).hasSize(databaseSizeBeforeUpdate);
        TipoObra testTipoObra = tipoObraList.get(tipoObraList.size() - 1);
        assertThat(testTipoObra.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateTipoObraWithPatch() throws Exception {
        // Initialize the database
        tipoObraRepository.saveAndFlush(tipoObra);

        int databaseSizeBeforeUpdate = tipoObraRepository.findAll().size();

        // Update the tipoObra using partial update
        TipoObra partialUpdatedTipoObra = new TipoObra();
        partialUpdatedTipoObra.setId(tipoObra.getId());

        partialUpdatedTipoObra.descripcion(UPDATED_DESCRIPCION);

        restTipoObraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoObra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoObra))
            )
            .andExpect(status().isOk());

        // Validate the TipoObra in the database
        List<TipoObra> tipoObraList = tipoObraRepository.findAll();
        assertThat(tipoObraList).hasSize(databaseSizeBeforeUpdate);
        TipoObra testTipoObra = tipoObraList.get(tipoObraList.size() - 1);
        assertThat(testTipoObra.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingTipoObra() throws Exception {
        int databaseSizeBeforeUpdate = tipoObraRepository.findAll().size();
        tipoObra.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoObraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoObra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoObra))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoObra in the database
        List<TipoObra> tipoObraList = tipoObraRepository.findAll();
        assertThat(tipoObraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoObra() throws Exception {
        int databaseSizeBeforeUpdate = tipoObraRepository.findAll().size();
        tipoObra.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoObraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoObra))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoObra in the database
        List<TipoObra> tipoObraList = tipoObraRepository.findAll();
        assertThat(tipoObraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoObra() throws Exception {
        int databaseSizeBeforeUpdate = tipoObraRepository.findAll().size();
        tipoObra.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoObraMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoObra)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoObra in the database
        List<TipoObra> tipoObraList = tipoObraRepository.findAll();
        assertThat(tipoObraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoObra() throws Exception {
        // Initialize the database
        tipoObraRepository.saveAndFlush(tipoObra);

        int databaseSizeBeforeDelete = tipoObraRepository.findAll().size();

        // Delete the tipoObra
        restTipoObraMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoObra.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoObra> tipoObraList = tipoObraRepository.findAll();
        assertThat(tipoObraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
