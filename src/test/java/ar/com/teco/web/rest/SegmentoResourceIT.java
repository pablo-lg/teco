package ar.com.teco.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.teco.IntegrationTest;
import ar.com.teco.domain.Segmento;
import ar.com.teco.repository.SegmentoRepository;
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
 * Integration tests for the {@link SegmentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SegmentoResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/segmentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SegmentoRepository segmentoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSegmentoMockMvc;

    private Segmento segmento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Segmento createEntity(EntityManager em) {
        Segmento segmento = new Segmento().descripcion(DEFAULT_DESCRIPCION);
        return segmento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Segmento createUpdatedEntity(EntityManager em) {
        Segmento segmento = new Segmento().descripcion(UPDATED_DESCRIPCION);
        return segmento;
    }

    @BeforeEach
    public void initTest() {
        segmento = createEntity(em);
    }

    @Test
    @Transactional
    void createSegmento() throws Exception {
        int databaseSizeBeforeCreate = segmentoRepository.findAll().size();
        // Create the Segmento
        restSegmentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(segmento)))
            .andExpect(status().isCreated());

        // Validate the Segmento in the database
        List<Segmento> segmentoList = segmentoRepository.findAll();
        assertThat(segmentoList).hasSize(databaseSizeBeforeCreate + 1);
        Segmento testSegmento = segmentoList.get(segmentoList.size() - 1);
        assertThat(testSegmento.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createSegmentoWithExistingId() throws Exception {
        // Create the Segmento with an existing ID
        segmento.setId(1L);

        int databaseSizeBeforeCreate = segmentoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSegmentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(segmento)))
            .andExpect(status().isBadRequest());

        // Validate the Segmento in the database
        List<Segmento> segmentoList = segmentoRepository.findAll();
        assertThat(segmentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSegmentos() throws Exception {
        // Initialize the database
        segmentoRepository.saveAndFlush(segmento);

        // Get all the segmentoList
        restSegmentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(segmento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getSegmento() throws Exception {
        // Initialize the database
        segmentoRepository.saveAndFlush(segmento);

        // Get the segmento
        restSegmentoMockMvc
            .perform(get(ENTITY_API_URL_ID, segmento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(segmento.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingSegmento() throws Exception {
        // Get the segmento
        restSegmentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSegmento() throws Exception {
        // Initialize the database
        segmentoRepository.saveAndFlush(segmento);

        int databaseSizeBeforeUpdate = segmentoRepository.findAll().size();

        // Update the segmento
        Segmento updatedSegmento = segmentoRepository.findById(segmento.getId()).get();
        // Disconnect from session so that the updates on updatedSegmento are not directly saved in db
        em.detach(updatedSegmento);
        updatedSegmento.descripcion(UPDATED_DESCRIPCION);

        restSegmentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSegmento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSegmento))
            )
            .andExpect(status().isOk());

        // Validate the Segmento in the database
        List<Segmento> segmentoList = segmentoRepository.findAll();
        assertThat(segmentoList).hasSize(databaseSizeBeforeUpdate);
        Segmento testSegmento = segmentoList.get(segmentoList.size() - 1);
        assertThat(testSegmento.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingSegmento() throws Exception {
        int databaseSizeBeforeUpdate = segmentoRepository.findAll().size();
        segmento.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSegmentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, segmento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Segmento in the database
        List<Segmento> segmentoList = segmentoRepository.findAll();
        assertThat(segmentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSegmento() throws Exception {
        int databaseSizeBeforeUpdate = segmentoRepository.findAll().size();
        segmento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Segmento in the database
        List<Segmento> segmentoList = segmentoRepository.findAll();
        assertThat(segmentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSegmento() throws Exception {
        int databaseSizeBeforeUpdate = segmentoRepository.findAll().size();
        segmento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(segmento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Segmento in the database
        List<Segmento> segmentoList = segmentoRepository.findAll();
        assertThat(segmentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSegmentoWithPatch() throws Exception {
        // Initialize the database
        segmentoRepository.saveAndFlush(segmento);

        int databaseSizeBeforeUpdate = segmentoRepository.findAll().size();

        // Update the segmento using partial update
        Segmento partialUpdatedSegmento = new Segmento();
        partialUpdatedSegmento.setId(segmento.getId());

        restSegmentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSegmento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSegmento))
            )
            .andExpect(status().isOk());

        // Validate the Segmento in the database
        List<Segmento> segmentoList = segmentoRepository.findAll();
        assertThat(segmentoList).hasSize(databaseSizeBeforeUpdate);
        Segmento testSegmento = segmentoList.get(segmentoList.size() - 1);
        assertThat(testSegmento.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateSegmentoWithPatch() throws Exception {
        // Initialize the database
        segmentoRepository.saveAndFlush(segmento);

        int databaseSizeBeforeUpdate = segmentoRepository.findAll().size();

        // Update the segmento using partial update
        Segmento partialUpdatedSegmento = new Segmento();
        partialUpdatedSegmento.setId(segmento.getId());

        partialUpdatedSegmento.descripcion(UPDATED_DESCRIPCION);

        restSegmentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSegmento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSegmento))
            )
            .andExpect(status().isOk());

        // Validate the Segmento in the database
        List<Segmento> segmentoList = segmentoRepository.findAll();
        assertThat(segmentoList).hasSize(databaseSizeBeforeUpdate);
        Segmento testSegmento = segmentoList.get(segmentoList.size() - 1);
        assertThat(testSegmento.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingSegmento() throws Exception {
        int databaseSizeBeforeUpdate = segmentoRepository.findAll().size();
        segmento.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSegmentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, segmento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(segmento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Segmento in the database
        List<Segmento> segmentoList = segmentoRepository.findAll();
        assertThat(segmentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSegmento() throws Exception {
        int databaseSizeBeforeUpdate = segmentoRepository.findAll().size();
        segmento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(segmento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Segmento in the database
        List<Segmento> segmentoList = segmentoRepository.findAll();
        assertThat(segmentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSegmento() throws Exception {
        int databaseSizeBeforeUpdate = segmentoRepository.findAll().size();
        segmento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(segmento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Segmento in the database
        List<Segmento> segmentoList = segmentoRepository.findAll();
        assertThat(segmentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSegmento() throws Exception {
        // Initialize the database
        segmentoRepository.saveAndFlush(segmento);

        int databaseSizeBeforeDelete = segmentoRepository.findAll().size();

        // Delete the segmento
        restSegmentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, segmento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Segmento> segmentoList = segmentoRepository.findAll();
        assertThat(segmentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
