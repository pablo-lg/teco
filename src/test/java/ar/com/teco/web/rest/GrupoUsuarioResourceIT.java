package ar.com.teco.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.teco.IntegrationTest;
import ar.com.teco.domain.GrupoUsuario;
import ar.com.teco.repository.GrupoUsuarioRepository;
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
 * Integration tests for the {@link GrupoUsuarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GrupoUsuarioResourceIT {

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/grupo-usuarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GrupoUsuarioRepository grupoUsuarioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrupoUsuarioMockMvc;

    private GrupoUsuario grupoUsuario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoUsuario createEntity(EntityManager em) {
        GrupoUsuario grupoUsuario = new GrupoUsuario().usuario(DEFAULT_USUARIO);
        return grupoUsuario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoUsuario createUpdatedEntity(EntityManager em) {
        GrupoUsuario grupoUsuario = new GrupoUsuario().usuario(UPDATED_USUARIO);
        return grupoUsuario;
    }

    @BeforeEach
    public void initTest() {
        grupoUsuario = createEntity(em);
    }

    @Test
    @Transactional
    void createGrupoUsuario() throws Exception {
        int databaseSizeBeforeCreate = grupoUsuarioRepository.findAll().size();
        // Create the GrupoUsuario
        restGrupoUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupoUsuario)))
            .andExpect(status().isCreated());

        // Validate the GrupoUsuario in the database
        List<GrupoUsuario> grupoUsuarioList = grupoUsuarioRepository.findAll();
        assertThat(grupoUsuarioList).hasSize(databaseSizeBeforeCreate + 1);
        GrupoUsuario testGrupoUsuario = grupoUsuarioList.get(grupoUsuarioList.size() - 1);
        assertThat(testGrupoUsuario.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    void createGrupoUsuarioWithExistingId() throws Exception {
        // Create the GrupoUsuario with an existing ID
        grupoUsuario.setId(1L);

        int databaseSizeBeforeCreate = grupoUsuarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupoUsuario)))
            .andExpect(status().isBadRequest());

        // Validate the GrupoUsuario in the database
        List<GrupoUsuario> grupoUsuarioList = grupoUsuarioRepository.findAll();
        assertThat(grupoUsuarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGrupoUsuarios() throws Exception {
        // Initialize the database
        grupoUsuarioRepository.saveAndFlush(grupoUsuario);

        // Get all the grupoUsuarioList
        restGrupoUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)));
    }

    @Test
    @Transactional
    void getGrupoUsuario() throws Exception {
        // Initialize the database
        grupoUsuarioRepository.saveAndFlush(grupoUsuario);

        // Get the grupoUsuario
        restGrupoUsuarioMockMvc
            .perform(get(ENTITY_API_URL_ID, grupoUsuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grupoUsuario.getId().intValue()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO));
    }

    @Test
    @Transactional
    void getNonExistingGrupoUsuario() throws Exception {
        // Get the grupoUsuario
        restGrupoUsuarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGrupoUsuario() throws Exception {
        // Initialize the database
        grupoUsuarioRepository.saveAndFlush(grupoUsuario);

        int databaseSizeBeforeUpdate = grupoUsuarioRepository.findAll().size();

        // Update the grupoUsuario
        GrupoUsuario updatedGrupoUsuario = grupoUsuarioRepository.findById(grupoUsuario.getId()).get();
        // Disconnect from session so that the updates on updatedGrupoUsuario are not directly saved in db
        em.detach(updatedGrupoUsuario);
        updatedGrupoUsuario.usuario(UPDATED_USUARIO);

        restGrupoUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGrupoUsuario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGrupoUsuario))
            )
            .andExpect(status().isOk());

        // Validate the GrupoUsuario in the database
        List<GrupoUsuario> grupoUsuarioList = grupoUsuarioRepository.findAll();
        assertThat(grupoUsuarioList).hasSize(databaseSizeBeforeUpdate);
        GrupoUsuario testGrupoUsuario = grupoUsuarioList.get(grupoUsuarioList.size() - 1);
        assertThat(testGrupoUsuario.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingGrupoUsuario() throws Exception {
        int databaseSizeBeforeUpdate = grupoUsuarioRepository.findAll().size();
        grupoUsuario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, grupoUsuario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(grupoUsuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoUsuario in the database
        List<GrupoUsuario> grupoUsuarioList = grupoUsuarioRepository.findAll();
        assertThat(grupoUsuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGrupoUsuario() throws Exception {
        int databaseSizeBeforeUpdate = grupoUsuarioRepository.findAll().size();
        grupoUsuario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(grupoUsuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoUsuario in the database
        List<GrupoUsuario> grupoUsuarioList = grupoUsuarioRepository.findAll();
        assertThat(grupoUsuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGrupoUsuario() throws Exception {
        int databaseSizeBeforeUpdate = grupoUsuarioRepository.findAll().size();
        grupoUsuario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoUsuarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupoUsuario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoUsuario in the database
        List<GrupoUsuario> grupoUsuarioList = grupoUsuarioRepository.findAll();
        assertThat(grupoUsuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGrupoUsuarioWithPatch() throws Exception {
        // Initialize the database
        grupoUsuarioRepository.saveAndFlush(grupoUsuario);

        int databaseSizeBeforeUpdate = grupoUsuarioRepository.findAll().size();

        // Update the grupoUsuario using partial update
        GrupoUsuario partialUpdatedGrupoUsuario = new GrupoUsuario();
        partialUpdatedGrupoUsuario.setId(grupoUsuario.getId());

        restGrupoUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGrupoUsuario))
            )
            .andExpect(status().isOk());

        // Validate the GrupoUsuario in the database
        List<GrupoUsuario> grupoUsuarioList = grupoUsuarioRepository.findAll();
        assertThat(grupoUsuarioList).hasSize(databaseSizeBeforeUpdate);
        GrupoUsuario testGrupoUsuario = grupoUsuarioList.get(grupoUsuarioList.size() - 1);
        assertThat(testGrupoUsuario.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateGrupoUsuarioWithPatch() throws Exception {
        // Initialize the database
        grupoUsuarioRepository.saveAndFlush(grupoUsuario);

        int databaseSizeBeforeUpdate = grupoUsuarioRepository.findAll().size();

        // Update the grupoUsuario using partial update
        GrupoUsuario partialUpdatedGrupoUsuario = new GrupoUsuario();
        partialUpdatedGrupoUsuario.setId(grupoUsuario.getId());

        partialUpdatedGrupoUsuario.usuario(UPDATED_USUARIO);

        restGrupoUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGrupoUsuario))
            )
            .andExpect(status().isOk());

        // Validate the GrupoUsuario in the database
        List<GrupoUsuario> grupoUsuarioList = grupoUsuarioRepository.findAll();
        assertThat(grupoUsuarioList).hasSize(databaseSizeBeforeUpdate);
        GrupoUsuario testGrupoUsuario = grupoUsuarioList.get(grupoUsuarioList.size() - 1);
        assertThat(testGrupoUsuario.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingGrupoUsuario() throws Exception {
        int databaseSizeBeforeUpdate = grupoUsuarioRepository.findAll().size();
        grupoUsuario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, grupoUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(grupoUsuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoUsuario in the database
        List<GrupoUsuario> grupoUsuarioList = grupoUsuarioRepository.findAll();
        assertThat(grupoUsuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGrupoUsuario() throws Exception {
        int databaseSizeBeforeUpdate = grupoUsuarioRepository.findAll().size();
        grupoUsuario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(grupoUsuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoUsuario in the database
        List<GrupoUsuario> grupoUsuarioList = grupoUsuarioRepository.findAll();
        assertThat(grupoUsuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGrupoUsuario() throws Exception {
        int databaseSizeBeforeUpdate = grupoUsuarioRepository.findAll().size();
        grupoUsuario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(grupoUsuario))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoUsuario in the database
        List<GrupoUsuario> grupoUsuarioList = grupoUsuarioRepository.findAll();
        assertThat(grupoUsuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGrupoUsuario() throws Exception {
        // Initialize the database
        grupoUsuarioRepository.saveAndFlush(grupoUsuario);

        int databaseSizeBeforeDelete = grupoUsuarioRepository.findAll().size();

        // Delete the grupoUsuario
        restGrupoUsuarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, grupoUsuario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GrupoUsuario> grupoUsuarioList = grupoUsuarioRepository.findAll();
        assertThat(grupoUsuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
