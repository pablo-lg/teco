package ar.com.teco.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.teco.IntegrationTest;
import ar.com.teco.domain.TipoEmp;
import ar.com.teco.repository.TipoEmpRepository;
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
 * Integration tests for the {@link TipoEmpResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoEmpResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-emps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoEmpRepository tipoEmpRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoEmpMockMvc;

    private TipoEmp tipoEmp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoEmp createEntity(EntityManager em) {
        TipoEmp tipoEmp = new TipoEmp().descripcion(DEFAULT_DESCRIPCION).valor(DEFAULT_VALOR);
        return tipoEmp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoEmp createUpdatedEntity(EntityManager em) {
        TipoEmp tipoEmp = new TipoEmp().descripcion(UPDATED_DESCRIPCION).valor(UPDATED_VALOR);
        return tipoEmp;
    }

    @BeforeEach
    public void initTest() {
        tipoEmp = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoEmp() throws Exception {
        int databaseSizeBeforeCreate = tipoEmpRepository.findAll().size();
        // Create the TipoEmp
        restTipoEmpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoEmp)))
            .andExpect(status().isCreated());

        // Validate the TipoEmp in the database
        List<TipoEmp> tipoEmpList = tipoEmpRepository.findAll();
        assertThat(tipoEmpList).hasSize(databaseSizeBeforeCreate + 1);
        TipoEmp testTipoEmp = tipoEmpList.get(tipoEmpList.size() - 1);
        assertThat(testTipoEmp.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testTipoEmp.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void createTipoEmpWithExistingId() throws Exception {
        // Create the TipoEmp with an existing ID
        tipoEmp.setId(1L);

        int databaseSizeBeforeCreate = tipoEmpRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoEmpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoEmp)))
            .andExpect(status().isBadRequest());

        // Validate the TipoEmp in the database
        List<TipoEmp> tipoEmpList = tipoEmpRepository.findAll();
        assertThat(tipoEmpList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTipoEmps() throws Exception {
        // Initialize the database
        tipoEmpRepository.saveAndFlush(tipoEmp);

        // Get all the tipoEmpList
        restTipoEmpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoEmp.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }

    @Test
    @Transactional
    void getTipoEmp() throws Exception {
        // Initialize the database
        tipoEmpRepository.saveAndFlush(tipoEmp);

        // Get the tipoEmp
        restTipoEmpMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoEmp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoEmp.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }

    @Test
    @Transactional
    void getNonExistingTipoEmp() throws Exception {
        // Get the tipoEmp
        restTipoEmpMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTipoEmp() throws Exception {
        // Initialize the database
        tipoEmpRepository.saveAndFlush(tipoEmp);

        int databaseSizeBeforeUpdate = tipoEmpRepository.findAll().size();

        // Update the tipoEmp
        TipoEmp updatedTipoEmp = tipoEmpRepository.findById(tipoEmp.getId()).get();
        // Disconnect from session so that the updates on updatedTipoEmp are not directly saved in db
        em.detach(updatedTipoEmp);
        updatedTipoEmp.descripcion(UPDATED_DESCRIPCION).valor(UPDATED_VALOR);

        restTipoEmpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTipoEmp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipoEmp))
            )
            .andExpect(status().isOk());

        // Validate the TipoEmp in the database
        List<TipoEmp> tipoEmpList = tipoEmpRepository.findAll();
        assertThat(tipoEmpList).hasSize(databaseSizeBeforeUpdate);
        TipoEmp testTipoEmp = tipoEmpList.get(tipoEmpList.size() - 1);
        assertThat(testTipoEmp.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTipoEmp.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void putNonExistingTipoEmp() throws Exception {
        int databaseSizeBeforeUpdate = tipoEmpRepository.findAll().size();
        tipoEmp.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoEmpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoEmp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoEmp))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoEmp in the database
        List<TipoEmp> tipoEmpList = tipoEmpRepository.findAll();
        assertThat(tipoEmpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoEmp() throws Exception {
        int databaseSizeBeforeUpdate = tipoEmpRepository.findAll().size();
        tipoEmp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoEmpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoEmp))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoEmp in the database
        List<TipoEmp> tipoEmpList = tipoEmpRepository.findAll();
        assertThat(tipoEmpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoEmp() throws Exception {
        int databaseSizeBeforeUpdate = tipoEmpRepository.findAll().size();
        tipoEmp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoEmpMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoEmp)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoEmp in the database
        List<TipoEmp> tipoEmpList = tipoEmpRepository.findAll();
        assertThat(tipoEmpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoEmpWithPatch() throws Exception {
        // Initialize the database
        tipoEmpRepository.saveAndFlush(tipoEmp);

        int databaseSizeBeforeUpdate = tipoEmpRepository.findAll().size();

        // Update the tipoEmp using partial update
        TipoEmp partialUpdatedTipoEmp = new TipoEmp();
        partialUpdatedTipoEmp.setId(tipoEmp.getId());

        partialUpdatedTipoEmp.valor(UPDATED_VALOR);

        restTipoEmpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoEmp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoEmp))
            )
            .andExpect(status().isOk());

        // Validate the TipoEmp in the database
        List<TipoEmp> tipoEmpList = tipoEmpRepository.findAll();
        assertThat(tipoEmpList).hasSize(databaseSizeBeforeUpdate);
        TipoEmp testTipoEmp = tipoEmpList.get(tipoEmpList.size() - 1);
        assertThat(testTipoEmp.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testTipoEmp.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void fullUpdateTipoEmpWithPatch() throws Exception {
        // Initialize the database
        tipoEmpRepository.saveAndFlush(tipoEmp);

        int databaseSizeBeforeUpdate = tipoEmpRepository.findAll().size();

        // Update the tipoEmp using partial update
        TipoEmp partialUpdatedTipoEmp = new TipoEmp();
        partialUpdatedTipoEmp.setId(tipoEmp.getId());

        partialUpdatedTipoEmp.descripcion(UPDATED_DESCRIPCION).valor(UPDATED_VALOR);

        restTipoEmpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoEmp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoEmp))
            )
            .andExpect(status().isOk());

        // Validate the TipoEmp in the database
        List<TipoEmp> tipoEmpList = tipoEmpRepository.findAll();
        assertThat(tipoEmpList).hasSize(databaseSizeBeforeUpdate);
        TipoEmp testTipoEmp = tipoEmpList.get(tipoEmpList.size() - 1);
        assertThat(testTipoEmp.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTipoEmp.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void patchNonExistingTipoEmp() throws Exception {
        int databaseSizeBeforeUpdate = tipoEmpRepository.findAll().size();
        tipoEmp.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoEmpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoEmp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoEmp))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoEmp in the database
        List<TipoEmp> tipoEmpList = tipoEmpRepository.findAll();
        assertThat(tipoEmpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoEmp() throws Exception {
        int databaseSizeBeforeUpdate = tipoEmpRepository.findAll().size();
        tipoEmp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoEmpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoEmp))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoEmp in the database
        List<TipoEmp> tipoEmpList = tipoEmpRepository.findAll();
        assertThat(tipoEmpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoEmp() throws Exception {
        int databaseSizeBeforeUpdate = tipoEmpRepository.findAll().size();
        tipoEmp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoEmpMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoEmp)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoEmp in the database
        List<TipoEmp> tipoEmpList = tipoEmpRepository.findAll();
        assertThat(tipoEmpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoEmp() throws Exception {
        // Initialize the database
        tipoEmpRepository.saveAndFlush(tipoEmp);

        int databaseSizeBeforeDelete = tipoEmpRepository.findAll().size();

        // Delete the tipoEmp
        restTipoEmpMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoEmp.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoEmp> tipoEmpList = tipoEmpRepository.findAll();
        assertThat(tipoEmpList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
