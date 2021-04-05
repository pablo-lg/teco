package ar.com.teco.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.teco.IntegrationTest;
import ar.com.teco.domain.NSE;
import ar.com.teco.repository.NSERepository;
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
 * Integration tests for the {@link NSEResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NSEResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final String ENTITY_API_URL = "/api/nses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NSERepository nSERepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNSEMockMvc;

    private NSE nSE;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NSE createEntity(EntityManager em) {
        NSE nSE = new NSE().descripcion(DEFAULT_DESCRIPCION).activo(DEFAULT_ACTIVO);
        return nSE;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NSE createUpdatedEntity(EntityManager em) {
        NSE nSE = new NSE().descripcion(UPDATED_DESCRIPCION).activo(UPDATED_ACTIVO);
        return nSE;
    }

    @BeforeEach
    public void initTest() {
        nSE = createEntity(em);
    }

    @Test
    @Transactional
    void createNSE() throws Exception {
        int databaseSizeBeforeCreate = nSERepository.findAll().size();
        // Create the NSE
        restNSEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nSE)))
            .andExpect(status().isCreated());

        // Validate the NSE in the database
        List<NSE> nSEList = nSERepository.findAll();
        assertThat(nSEList).hasSize(databaseSizeBeforeCreate + 1);
        NSE testNSE = nSEList.get(nSEList.size() - 1);
        assertThat(testNSE.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testNSE.getActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    void createNSEWithExistingId() throws Exception {
        // Create the NSE with an existing ID
        nSE.setId(1L);

        int databaseSizeBeforeCreate = nSERepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNSEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nSE)))
            .andExpect(status().isBadRequest());

        // Validate the NSE in the database
        List<NSE> nSEList = nSERepository.findAll();
        assertThat(nSEList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNSES() throws Exception {
        // Initialize the database
        nSERepository.saveAndFlush(nSE);

        // Get all the nSEList
        restNSEMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nSE.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getNSE() throws Exception {
        // Initialize the database
        nSERepository.saveAndFlush(nSE);

        // Get the nSE
        restNSEMockMvc
            .perform(get(ENTITY_API_URL_ID, nSE.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nSE.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingNSE() throws Exception {
        // Get the nSE
        restNSEMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNSE() throws Exception {
        // Initialize the database
        nSERepository.saveAndFlush(nSE);

        int databaseSizeBeforeUpdate = nSERepository.findAll().size();

        // Update the nSE
        NSE updatedNSE = nSERepository.findById(nSE.getId()).get();
        // Disconnect from session so that the updates on updatedNSE are not directly saved in db
        em.detach(updatedNSE);
        updatedNSE.descripcion(UPDATED_DESCRIPCION).activo(UPDATED_ACTIVO);

        restNSEMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNSE.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNSE))
            )
            .andExpect(status().isOk());

        // Validate the NSE in the database
        List<NSE> nSEList = nSERepository.findAll();
        assertThat(nSEList).hasSize(databaseSizeBeforeUpdate);
        NSE testNSE = nSEList.get(nSEList.size() - 1);
        assertThat(testNSE.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testNSE.getActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    void putNonExistingNSE() throws Exception {
        int databaseSizeBeforeUpdate = nSERepository.findAll().size();
        nSE.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNSEMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nSE.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nSE))
            )
            .andExpect(status().isBadRequest());

        // Validate the NSE in the database
        List<NSE> nSEList = nSERepository.findAll();
        assertThat(nSEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNSE() throws Exception {
        int databaseSizeBeforeUpdate = nSERepository.findAll().size();
        nSE.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNSEMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nSE))
            )
            .andExpect(status().isBadRequest());

        // Validate the NSE in the database
        List<NSE> nSEList = nSERepository.findAll();
        assertThat(nSEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNSE() throws Exception {
        int databaseSizeBeforeUpdate = nSERepository.findAll().size();
        nSE.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNSEMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nSE)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NSE in the database
        List<NSE> nSEList = nSERepository.findAll();
        assertThat(nSEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNSEWithPatch() throws Exception {
        // Initialize the database
        nSERepository.saveAndFlush(nSE);

        int databaseSizeBeforeUpdate = nSERepository.findAll().size();

        // Update the nSE using partial update
        NSE partialUpdatedNSE = new NSE();
        partialUpdatedNSE.setId(nSE.getId());

        partialUpdatedNSE.descripcion(UPDATED_DESCRIPCION);

        restNSEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNSE.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNSE))
            )
            .andExpect(status().isOk());

        // Validate the NSE in the database
        List<NSE> nSEList = nSERepository.findAll();
        assertThat(nSEList).hasSize(databaseSizeBeforeUpdate);
        NSE testNSE = nSEList.get(nSEList.size() - 1);
        assertThat(testNSE.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testNSE.getActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    void fullUpdateNSEWithPatch() throws Exception {
        // Initialize the database
        nSERepository.saveAndFlush(nSE);

        int databaseSizeBeforeUpdate = nSERepository.findAll().size();

        // Update the nSE using partial update
        NSE partialUpdatedNSE = new NSE();
        partialUpdatedNSE.setId(nSE.getId());

        partialUpdatedNSE.descripcion(UPDATED_DESCRIPCION).activo(UPDATED_ACTIVO);

        restNSEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNSE.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNSE))
            )
            .andExpect(status().isOk());

        // Validate the NSE in the database
        List<NSE> nSEList = nSERepository.findAll();
        assertThat(nSEList).hasSize(databaseSizeBeforeUpdate);
        NSE testNSE = nSEList.get(nSEList.size() - 1);
        assertThat(testNSE.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testNSE.getActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    void patchNonExistingNSE() throws Exception {
        int databaseSizeBeforeUpdate = nSERepository.findAll().size();
        nSE.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNSEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nSE.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nSE))
            )
            .andExpect(status().isBadRequest());

        // Validate the NSE in the database
        List<NSE> nSEList = nSERepository.findAll();
        assertThat(nSEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNSE() throws Exception {
        int databaseSizeBeforeUpdate = nSERepository.findAll().size();
        nSE.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNSEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nSE))
            )
            .andExpect(status().isBadRequest());

        // Validate the NSE in the database
        List<NSE> nSEList = nSERepository.findAll();
        assertThat(nSEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNSE() throws Exception {
        int databaseSizeBeforeUpdate = nSERepository.findAll().size();
        nSE.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNSEMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nSE)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NSE in the database
        List<NSE> nSEList = nSERepository.findAll();
        assertThat(nSEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNSE() throws Exception {
        // Initialize the database
        nSERepository.saveAndFlush(nSE);

        int databaseSizeBeforeDelete = nSERepository.findAll().size();

        // Delete the nSE
        restNSEMockMvc.perform(delete(ENTITY_API_URL_ID, nSE.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NSE> nSEList = nSERepository.findAll();
        assertThat(nSEList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
