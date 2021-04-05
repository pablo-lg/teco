package ar.com.teco.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.teco.IntegrationTest;
import ar.com.teco.domain.Obra;
import ar.com.teco.repository.ObraRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link ObraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ObraResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HABILITADA = false;
    private static final Boolean UPDATED_HABILITADA = true;

    private static final LocalDate DEFAULT_FECHA_FIN_OBRA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FIN_OBRA = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/obras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObraRepository obraRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restObraMockMvc;

    private Obra obra;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Obra createEntity(EntityManager em) {
        Obra obra = new Obra().descripcion(DEFAULT_DESCRIPCION).habilitada(DEFAULT_HABILITADA).fechaFinObra(DEFAULT_FECHA_FIN_OBRA);
        return obra;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Obra createUpdatedEntity(EntityManager em) {
        Obra obra = new Obra().descripcion(UPDATED_DESCRIPCION).habilitada(UPDATED_HABILITADA).fechaFinObra(UPDATED_FECHA_FIN_OBRA);
        return obra;
    }

    @BeforeEach
    public void initTest() {
        obra = createEntity(em);
    }

    @Test
    @Transactional
    void createObra() throws Exception {
        int databaseSizeBeforeCreate = obraRepository.findAll().size();
        // Create the Obra
        restObraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(obra)))
            .andExpect(status().isCreated());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeCreate + 1);
        Obra testObra = obraList.get(obraList.size() - 1);
        assertThat(testObra.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testObra.getHabilitada()).isEqualTo(DEFAULT_HABILITADA);
        assertThat(testObra.getFechaFinObra()).isEqualTo(DEFAULT_FECHA_FIN_OBRA);
    }

    @Test
    @Transactional
    void createObraWithExistingId() throws Exception {
        // Create the Obra with an existing ID
        obra.setId(1L);

        int databaseSizeBeforeCreate = obraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restObraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(obra)))
            .andExpect(status().isBadRequest());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllObras() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList
        restObraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(obra.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].habilitada").value(hasItem(DEFAULT_HABILITADA.booleanValue())))
            .andExpect(jsonPath("$.[*].fechaFinObra").value(hasItem(DEFAULT_FECHA_FIN_OBRA.toString())));
    }

    @Test
    @Transactional
    void getObra() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get the obra
        restObraMockMvc
            .perform(get(ENTITY_API_URL_ID, obra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(obra.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.habilitada").value(DEFAULT_HABILITADA.booleanValue()))
            .andExpect(jsonPath("$.fechaFinObra").value(DEFAULT_FECHA_FIN_OBRA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingObra() throws Exception {
        // Get the obra
        restObraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewObra() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        int databaseSizeBeforeUpdate = obraRepository.findAll().size();

        // Update the obra
        Obra updatedObra = obraRepository.findById(obra.getId()).get();
        // Disconnect from session so that the updates on updatedObra are not directly saved in db
        em.detach(updatedObra);
        updatedObra.descripcion(UPDATED_DESCRIPCION).habilitada(UPDATED_HABILITADA).fechaFinObra(UPDATED_FECHA_FIN_OBRA);

        restObraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedObra.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedObra))
            )
            .andExpect(status().isOk());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
        Obra testObra = obraList.get(obraList.size() - 1);
        assertThat(testObra.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testObra.getHabilitada()).isEqualTo(UPDATED_HABILITADA);
        assertThat(testObra.getFechaFinObra()).isEqualTo(UPDATED_FECHA_FIN_OBRA);
    }

    @Test
    @Transactional
    void putNonExistingObra() throws Exception {
        int databaseSizeBeforeUpdate = obraRepository.findAll().size();
        obra.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, obra.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(obra))
            )
            .andExpect(status().isBadRequest());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchObra() throws Exception {
        int databaseSizeBeforeUpdate = obraRepository.findAll().size();
        obra.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(obra))
            )
            .andExpect(status().isBadRequest());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamObra() throws Exception {
        int databaseSizeBeforeUpdate = obraRepository.findAll().size();
        obra.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObraMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(obra)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateObraWithPatch() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        int databaseSizeBeforeUpdate = obraRepository.findAll().size();

        // Update the obra using partial update
        Obra partialUpdatedObra = new Obra();
        partialUpdatedObra.setId(obra.getId());

        partialUpdatedObra.descripcion(UPDATED_DESCRIPCION).habilitada(UPDATED_HABILITADA);

        restObraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObra))
            )
            .andExpect(status().isOk());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
        Obra testObra = obraList.get(obraList.size() - 1);
        assertThat(testObra.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testObra.getHabilitada()).isEqualTo(UPDATED_HABILITADA);
        assertThat(testObra.getFechaFinObra()).isEqualTo(DEFAULT_FECHA_FIN_OBRA);
    }

    @Test
    @Transactional
    void fullUpdateObraWithPatch() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        int databaseSizeBeforeUpdate = obraRepository.findAll().size();

        // Update the obra using partial update
        Obra partialUpdatedObra = new Obra();
        partialUpdatedObra.setId(obra.getId());

        partialUpdatedObra.descripcion(UPDATED_DESCRIPCION).habilitada(UPDATED_HABILITADA).fechaFinObra(UPDATED_FECHA_FIN_OBRA);

        restObraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObra))
            )
            .andExpect(status().isOk());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
        Obra testObra = obraList.get(obraList.size() - 1);
        assertThat(testObra.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testObra.getHabilitada()).isEqualTo(UPDATED_HABILITADA);
        assertThat(testObra.getFechaFinObra()).isEqualTo(UPDATED_FECHA_FIN_OBRA);
    }

    @Test
    @Transactional
    void patchNonExistingObra() throws Exception {
        int databaseSizeBeforeUpdate = obraRepository.findAll().size();
        obra.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, obra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(obra))
            )
            .andExpect(status().isBadRequest());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchObra() throws Exception {
        int databaseSizeBeforeUpdate = obraRepository.findAll().size();
        obra.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(obra))
            )
            .andExpect(status().isBadRequest());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamObra() throws Exception {
        int databaseSizeBeforeUpdate = obraRepository.findAll().size();
        obra.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObraMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(obra)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteObra() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        int databaseSizeBeforeDelete = obraRepository.findAll().size();

        // Delete the obra
        restObraMockMvc
            .perform(delete(ENTITY_API_URL_ID, obra.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
