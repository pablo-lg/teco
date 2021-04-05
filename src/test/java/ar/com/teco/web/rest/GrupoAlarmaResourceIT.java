package ar.com.teco.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.teco.IntegrationTest;
import ar.com.teco.domain.GrupoAlarma;
import ar.com.teco.repository.GrupoAlarmaRepository;
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
 * Integration tests for the {@link GrupoAlarmaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GrupoAlarmaResourceIT {

    private static final String DEFAULT_NOMBRE_GRUPO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_GRUPO = "BBBBBBBBBB";

    private static final Long DEFAULT_ALARMA_TIEMPO = 1L;
    private static final Long UPDATED_ALARMA_TIEMPO = 2L;

    private static final Long DEFAULT_ALARMA_SVA = 1L;
    private static final Long UPDATED_ALARMA_SVA = 2L;

    private static final Long DEFAULT_ALARMA_BUSINESSCASE = 1L;
    private static final Long UPDATED_ALARMA_BUSINESSCASE = 2L;

    private static final String ENTITY_API_URL = "/api/grupo-alarmas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GrupoAlarmaRepository grupoAlarmaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrupoAlarmaMockMvc;

    private GrupoAlarma grupoAlarma;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoAlarma createEntity(EntityManager em) {
        GrupoAlarma grupoAlarma = new GrupoAlarma()
            .nombreGrupo(DEFAULT_NOMBRE_GRUPO)
            .alarmaTiempo(DEFAULT_ALARMA_TIEMPO)
            .alarmaSva(DEFAULT_ALARMA_SVA)
            .alarmaBusinesscase(DEFAULT_ALARMA_BUSINESSCASE);
        return grupoAlarma;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoAlarma createUpdatedEntity(EntityManager em) {
        GrupoAlarma grupoAlarma = new GrupoAlarma()
            .nombreGrupo(UPDATED_NOMBRE_GRUPO)
            .alarmaTiempo(UPDATED_ALARMA_TIEMPO)
            .alarmaSva(UPDATED_ALARMA_SVA)
            .alarmaBusinesscase(UPDATED_ALARMA_BUSINESSCASE);
        return grupoAlarma;
    }

    @BeforeEach
    public void initTest() {
        grupoAlarma = createEntity(em);
    }

    @Test
    @Transactional
    void createGrupoAlarma() throws Exception {
        int databaseSizeBeforeCreate = grupoAlarmaRepository.findAll().size();
        // Create the GrupoAlarma
        restGrupoAlarmaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupoAlarma)))
            .andExpect(status().isCreated());

        // Validate the GrupoAlarma in the database
        List<GrupoAlarma> grupoAlarmaList = grupoAlarmaRepository.findAll();
        assertThat(grupoAlarmaList).hasSize(databaseSizeBeforeCreate + 1);
        GrupoAlarma testGrupoAlarma = grupoAlarmaList.get(grupoAlarmaList.size() - 1);
        assertThat(testGrupoAlarma.getNombreGrupo()).isEqualTo(DEFAULT_NOMBRE_GRUPO);
        assertThat(testGrupoAlarma.getAlarmaTiempo()).isEqualTo(DEFAULT_ALARMA_TIEMPO);
        assertThat(testGrupoAlarma.getAlarmaSva()).isEqualTo(DEFAULT_ALARMA_SVA);
        assertThat(testGrupoAlarma.getAlarmaBusinesscase()).isEqualTo(DEFAULT_ALARMA_BUSINESSCASE);
    }

    @Test
    @Transactional
    void createGrupoAlarmaWithExistingId() throws Exception {
        // Create the GrupoAlarma with an existing ID
        grupoAlarma.setId(1L);

        int databaseSizeBeforeCreate = grupoAlarmaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoAlarmaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupoAlarma)))
            .andExpect(status().isBadRequest());

        // Validate the GrupoAlarma in the database
        List<GrupoAlarma> grupoAlarmaList = grupoAlarmaRepository.findAll();
        assertThat(grupoAlarmaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreGrupoIsRequired() throws Exception {
        int databaseSizeBeforeTest = grupoAlarmaRepository.findAll().size();
        // set the field null
        grupoAlarma.setNombreGrupo(null);

        // Create the GrupoAlarma, which fails.

        restGrupoAlarmaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupoAlarma)))
            .andExpect(status().isBadRequest());

        List<GrupoAlarma> grupoAlarmaList = grupoAlarmaRepository.findAll();
        assertThat(grupoAlarmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGrupoAlarmas() throws Exception {
        // Initialize the database
        grupoAlarmaRepository.saveAndFlush(grupoAlarma);

        // Get all the grupoAlarmaList
        restGrupoAlarmaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoAlarma.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreGrupo").value(hasItem(DEFAULT_NOMBRE_GRUPO)))
            .andExpect(jsonPath("$.[*].alarmaTiempo").value(hasItem(DEFAULT_ALARMA_TIEMPO.intValue())))
            .andExpect(jsonPath("$.[*].alarmaSva").value(hasItem(DEFAULT_ALARMA_SVA.intValue())))
            .andExpect(jsonPath("$.[*].alarmaBusinesscase").value(hasItem(DEFAULT_ALARMA_BUSINESSCASE.intValue())));
    }

    @Test
    @Transactional
    void getGrupoAlarma() throws Exception {
        // Initialize the database
        grupoAlarmaRepository.saveAndFlush(grupoAlarma);

        // Get the grupoAlarma
        restGrupoAlarmaMockMvc
            .perform(get(ENTITY_API_URL_ID, grupoAlarma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grupoAlarma.getId().intValue()))
            .andExpect(jsonPath("$.nombreGrupo").value(DEFAULT_NOMBRE_GRUPO))
            .andExpect(jsonPath("$.alarmaTiempo").value(DEFAULT_ALARMA_TIEMPO.intValue()))
            .andExpect(jsonPath("$.alarmaSva").value(DEFAULT_ALARMA_SVA.intValue()))
            .andExpect(jsonPath("$.alarmaBusinesscase").value(DEFAULT_ALARMA_BUSINESSCASE.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingGrupoAlarma() throws Exception {
        // Get the grupoAlarma
        restGrupoAlarmaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGrupoAlarma() throws Exception {
        // Initialize the database
        grupoAlarmaRepository.saveAndFlush(grupoAlarma);

        int databaseSizeBeforeUpdate = grupoAlarmaRepository.findAll().size();

        // Update the grupoAlarma
        GrupoAlarma updatedGrupoAlarma = grupoAlarmaRepository.findById(grupoAlarma.getId()).get();
        // Disconnect from session so that the updates on updatedGrupoAlarma are not directly saved in db
        em.detach(updatedGrupoAlarma);
        updatedGrupoAlarma
            .nombreGrupo(UPDATED_NOMBRE_GRUPO)
            .alarmaTiempo(UPDATED_ALARMA_TIEMPO)
            .alarmaSva(UPDATED_ALARMA_SVA)
            .alarmaBusinesscase(UPDATED_ALARMA_BUSINESSCASE);

        restGrupoAlarmaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGrupoAlarma.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGrupoAlarma))
            )
            .andExpect(status().isOk());

        // Validate the GrupoAlarma in the database
        List<GrupoAlarma> grupoAlarmaList = grupoAlarmaRepository.findAll();
        assertThat(grupoAlarmaList).hasSize(databaseSizeBeforeUpdate);
        GrupoAlarma testGrupoAlarma = grupoAlarmaList.get(grupoAlarmaList.size() - 1);
        assertThat(testGrupoAlarma.getNombreGrupo()).isEqualTo(UPDATED_NOMBRE_GRUPO);
        assertThat(testGrupoAlarma.getAlarmaTiempo()).isEqualTo(UPDATED_ALARMA_TIEMPO);
        assertThat(testGrupoAlarma.getAlarmaSva()).isEqualTo(UPDATED_ALARMA_SVA);
        assertThat(testGrupoAlarma.getAlarmaBusinesscase()).isEqualTo(UPDATED_ALARMA_BUSINESSCASE);
    }

    @Test
    @Transactional
    void putNonExistingGrupoAlarma() throws Exception {
        int databaseSizeBeforeUpdate = grupoAlarmaRepository.findAll().size();
        grupoAlarma.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoAlarmaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, grupoAlarma.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(grupoAlarma))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAlarma in the database
        List<GrupoAlarma> grupoAlarmaList = grupoAlarmaRepository.findAll();
        assertThat(grupoAlarmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGrupoAlarma() throws Exception {
        int databaseSizeBeforeUpdate = grupoAlarmaRepository.findAll().size();
        grupoAlarma.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAlarmaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(grupoAlarma))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAlarma in the database
        List<GrupoAlarma> grupoAlarmaList = grupoAlarmaRepository.findAll();
        assertThat(grupoAlarmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGrupoAlarma() throws Exception {
        int databaseSizeBeforeUpdate = grupoAlarmaRepository.findAll().size();
        grupoAlarma.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAlarmaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupoAlarma)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoAlarma in the database
        List<GrupoAlarma> grupoAlarmaList = grupoAlarmaRepository.findAll();
        assertThat(grupoAlarmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGrupoAlarmaWithPatch() throws Exception {
        // Initialize the database
        grupoAlarmaRepository.saveAndFlush(grupoAlarma);

        int databaseSizeBeforeUpdate = grupoAlarmaRepository.findAll().size();

        // Update the grupoAlarma using partial update
        GrupoAlarma partialUpdatedGrupoAlarma = new GrupoAlarma();
        partialUpdatedGrupoAlarma.setId(grupoAlarma.getId());

        partialUpdatedGrupoAlarma
            .nombreGrupo(UPDATED_NOMBRE_GRUPO)
            .alarmaSva(UPDATED_ALARMA_SVA)
            .alarmaBusinesscase(UPDATED_ALARMA_BUSINESSCASE);

        restGrupoAlarmaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoAlarma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGrupoAlarma))
            )
            .andExpect(status().isOk());

        // Validate the GrupoAlarma in the database
        List<GrupoAlarma> grupoAlarmaList = grupoAlarmaRepository.findAll();
        assertThat(grupoAlarmaList).hasSize(databaseSizeBeforeUpdate);
        GrupoAlarma testGrupoAlarma = grupoAlarmaList.get(grupoAlarmaList.size() - 1);
        assertThat(testGrupoAlarma.getNombreGrupo()).isEqualTo(UPDATED_NOMBRE_GRUPO);
        assertThat(testGrupoAlarma.getAlarmaTiempo()).isEqualTo(DEFAULT_ALARMA_TIEMPO);
        assertThat(testGrupoAlarma.getAlarmaSva()).isEqualTo(UPDATED_ALARMA_SVA);
        assertThat(testGrupoAlarma.getAlarmaBusinesscase()).isEqualTo(UPDATED_ALARMA_BUSINESSCASE);
    }

    @Test
    @Transactional
    void fullUpdateGrupoAlarmaWithPatch() throws Exception {
        // Initialize the database
        grupoAlarmaRepository.saveAndFlush(grupoAlarma);

        int databaseSizeBeforeUpdate = grupoAlarmaRepository.findAll().size();

        // Update the grupoAlarma using partial update
        GrupoAlarma partialUpdatedGrupoAlarma = new GrupoAlarma();
        partialUpdatedGrupoAlarma.setId(grupoAlarma.getId());

        partialUpdatedGrupoAlarma
            .nombreGrupo(UPDATED_NOMBRE_GRUPO)
            .alarmaTiempo(UPDATED_ALARMA_TIEMPO)
            .alarmaSva(UPDATED_ALARMA_SVA)
            .alarmaBusinesscase(UPDATED_ALARMA_BUSINESSCASE);

        restGrupoAlarmaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoAlarma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGrupoAlarma))
            )
            .andExpect(status().isOk());

        // Validate the GrupoAlarma in the database
        List<GrupoAlarma> grupoAlarmaList = grupoAlarmaRepository.findAll();
        assertThat(grupoAlarmaList).hasSize(databaseSizeBeforeUpdate);
        GrupoAlarma testGrupoAlarma = grupoAlarmaList.get(grupoAlarmaList.size() - 1);
        assertThat(testGrupoAlarma.getNombreGrupo()).isEqualTo(UPDATED_NOMBRE_GRUPO);
        assertThat(testGrupoAlarma.getAlarmaTiempo()).isEqualTo(UPDATED_ALARMA_TIEMPO);
        assertThat(testGrupoAlarma.getAlarmaSva()).isEqualTo(UPDATED_ALARMA_SVA);
        assertThat(testGrupoAlarma.getAlarmaBusinesscase()).isEqualTo(UPDATED_ALARMA_BUSINESSCASE);
    }

    @Test
    @Transactional
    void patchNonExistingGrupoAlarma() throws Exception {
        int databaseSizeBeforeUpdate = grupoAlarmaRepository.findAll().size();
        grupoAlarma.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoAlarmaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, grupoAlarma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(grupoAlarma))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAlarma in the database
        List<GrupoAlarma> grupoAlarmaList = grupoAlarmaRepository.findAll();
        assertThat(grupoAlarmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGrupoAlarma() throws Exception {
        int databaseSizeBeforeUpdate = grupoAlarmaRepository.findAll().size();
        grupoAlarma.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAlarmaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(grupoAlarma))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAlarma in the database
        List<GrupoAlarma> grupoAlarmaList = grupoAlarmaRepository.findAll();
        assertThat(grupoAlarmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGrupoAlarma() throws Exception {
        int databaseSizeBeforeUpdate = grupoAlarmaRepository.findAll().size();
        grupoAlarma.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAlarmaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(grupoAlarma))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoAlarma in the database
        List<GrupoAlarma> grupoAlarmaList = grupoAlarmaRepository.findAll();
        assertThat(grupoAlarmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGrupoAlarma() throws Exception {
        // Initialize the database
        grupoAlarmaRepository.saveAndFlush(grupoAlarma);

        int databaseSizeBeforeDelete = grupoAlarmaRepository.findAll().size();

        // Delete the grupoAlarma
        restGrupoAlarmaMockMvc
            .perform(delete(ENTITY_API_URL_ID, grupoAlarma.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GrupoAlarma> grupoAlarmaList = grupoAlarmaRepository.findAll();
        assertThat(grupoAlarmaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
