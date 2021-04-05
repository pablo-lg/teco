package ar.com.teco.web.rest;

import ar.com.teco.domain.GrupoUsuario;
import ar.com.teco.repository.GrupoUsuarioRepository;
import ar.com.teco.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ar.com.teco.domain.GrupoUsuario}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GrupoUsuarioResource {

    private final Logger log = LoggerFactory.getLogger(GrupoUsuarioResource.class);

    private static final String ENTITY_NAME = "grupoUsuario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrupoUsuarioRepository grupoUsuarioRepository;

    public GrupoUsuarioResource(GrupoUsuarioRepository grupoUsuarioRepository) {
        this.grupoUsuarioRepository = grupoUsuarioRepository;
    }

    /**
     * {@code POST  /grupo-usuarios} : Create a new grupoUsuario.
     *
     * @param grupoUsuario the grupoUsuario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupoUsuario, or with status {@code 400 (Bad Request)} if the grupoUsuario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grupo-usuarios")
    public ResponseEntity<GrupoUsuario> createGrupoUsuario(@Valid @RequestBody GrupoUsuario grupoUsuario) throws URISyntaxException {
        log.debug("REST request to save GrupoUsuario : {}", grupoUsuario);
        if (grupoUsuario.getId() != null) {
            throw new BadRequestAlertException("A new grupoUsuario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrupoUsuario result = grupoUsuarioRepository.save(grupoUsuario);
        return ResponseEntity
            .created(new URI("/api/grupo-usuarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grupo-usuarios/:id} : Updates an existing grupoUsuario.
     *
     * @param id the id of the grupoUsuario to save.
     * @param grupoUsuario the grupoUsuario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoUsuario,
     * or with status {@code 400 (Bad Request)} if the grupoUsuario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupoUsuario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grupo-usuarios/{id}")
    public ResponseEntity<GrupoUsuario> updateGrupoUsuario(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GrupoUsuario grupoUsuario
    ) throws URISyntaxException {
        log.debug("REST request to update GrupoUsuario : {}, {}", id, grupoUsuario);
        if (grupoUsuario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoUsuario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoUsuarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GrupoUsuario result = grupoUsuarioRepository.save(grupoUsuario);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, grupoUsuario.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /grupo-usuarios/:id} : Partial updates given fields of an existing grupoUsuario, field will ignore if it is null
     *
     * @param id the id of the grupoUsuario to save.
     * @param grupoUsuario the grupoUsuario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoUsuario,
     * or with status {@code 400 (Bad Request)} if the grupoUsuario is not valid,
     * or with status {@code 404 (Not Found)} if the grupoUsuario is not found,
     * or with status {@code 500 (Internal Server Error)} if the grupoUsuario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/grupo-usuarios/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<GrupoUsuario> partialUpdateGrupoUsuario(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GrupoUsuario grupoUsuario
    ) throws URISyntaxException {
        log.debug("REST request to partial update GrupoUsuario partially : {}, {}", id, grupoUsuario);
        if (grupoUsuario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoUsuario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoUsuarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GrupoUsuario> result = grupoUsuarioRepository
            .findById(grupoUsuario.getId())
            .map(
                existingGrupoUsuario -> {
                    if (grupoUsuario.getUsuario() != null) {
                        existingGrupoUsuario.setUsuario(grupoUsuario.getUsuario());
                    }

                    return existingGrupoUsuario;
                }
            )
            .map(grupoUsuarioRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, grupoUsuario.getId().toString())
        );
    }

    /**
     * {@code GET  /grupo-usuarios} : get all the grupoUsuarios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupoUsuarios in body.
     */
    @GetMapping("/grupo-usuarios")
    public List<GrupoUsuario> getAllGrupoUsuarios() {
        log.debug("REST request to get all GrupoUsuarios");
        return grupoUsuarioRepository.findAll();
    }

    /**
     * {@code GET  /grupo-usuarios/:id} : get the "id" grupoUsuario.
     *
     * @param id the id of the grupoUsuario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupoUsuario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grupo-usuarios/{id}")
    public ResponseEntity<GrupoUsuario> getGrupoUsuario(@PathVariable Long id) {
        log.debug("REST request to get GrupoUsuario : {}", id);
        Optional<GrupoUsuario> grupoUsuario = grupoUsuarioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(grupoUsuario);
    }

    /**
     * {@code DELETE  /grupo-usuarios/:id} : delete the "id" grupoUsuario.
     *
     * @param id the id of the grupoUsuario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grupo-usuarios/{id}")
    public ResponseEntity<Void> deleteGrupoUsuario(@PathVariable Long id) {
        log.debug("REST request to delete GrupoUsuario : {}", id);
        grupoUsuarioRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
