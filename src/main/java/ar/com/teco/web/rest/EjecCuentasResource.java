package ar.com.teco.web.rest;

import ar.com.teco.domain.EjecCuentas;
import ar.com.teco.repository.EjecCuentasRepository;
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
 * REST controller for managing {@link ar.com.teco.domain.EjecCuentas}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EjecCuentasResource {

    private final Logger log = LoggerFactory.getLogger(EjecCuentasResource.class);

    private static final String ENTITY_NAME = "ejecCuentas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EjecCuentasRepository ejecCuentasRepository;

    public EjecCuentasResource(EjecCuentasRepository ejecCuentasRepository) {
        this.ejecCuentasRepository = ejecCuentasRepository;
    }

    /**
     * {@code POST  /ejec-cuentas} : Create a new ejecCuentas.
     *
     * @param ejecCuentas the ejecCuentas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ejecCuentas, or with status {@code 400 (Bad Request)} if the ejecCuentas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ejec-cuentas")
    public ResponseEntity<EjecCuentas> createEjecCuentas(@Valid @RequestBody EjecCuentas ejecCuentas) throws URISyntaxException {
        log.debug("REST request to save EjecCuentas : {}", ejecCuentas);
        if (ejecCuentas.getId() != null) {
            throw new BadRequestAlertException("A new ejecCuentas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EjecCuentas result = ejecCuentasRepository.save(ejecCuentas);
        return ResponseEntity
            .created(new URI("/api/ejec-cuentas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ejec-cuentas/:id} : Updates an existing ejecCuentas.
     *
     * @param id the id of the ejecCuentas to save.
     * @param ejecCuentas the ejecCuentas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ejecCuentas,
     * or with status {@code 400 (Bad Request)} if the ejecCuentas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ejecCuentas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ejec-cuentas/{id}")
    public ResponseEntity<EjecCuentas> updateEjecCuentas(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EjecCuentas ejecCuentas
    ) throws URISyntaxException {
        log.debug("REST request to update EjecCuentas : {}, {}", id, ejecCuentas);
        if (ejecCuentas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ejecCuentas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ejecCuentasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EjecCuentas result = ejecCuentasRepository.save(ejecCuentas);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ejecCuentas.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ejec-cuentas/:id} : Partial updates given fields of an existing ejecCuentas, field will ignore if it is null
     *
     * @param id the id of the ejecCuentas to save.
     * @param ejecCuentas the ejecCuentas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ejecCuentas,
     * or with status {@code 400 (Bad Request)} if the ejecCuentas is not valid,
     * or with status {@code 404 (Not Found)} if the ejecCuentas is not found,
     * or with status {@code 500 (Internal Server Error)} if the ejecCuentas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ejec-cuentas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EjecCuentas> partialUpdateEjecCuentas(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EjecCuentas ejecCuentas
    ) throws URISyntaxException {
        log.debug("REST request to partial update EjecCuentas partially : {}, {}", id, ejecCuentas);
        if (ejecCuentas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ejecCuentas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ejecCuentasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EjecCuentas> result = ejecCuentasRepository
            .findById(ejecCuentas.getId())
            .map(
                existingEjecCuentas -> {
                    if (ejecCuentas.getTelefono() != null) {
                        existingEjecCuentas.setTelefono(ejecCuentas.getTelefono());
                    }
                    if (ejecCuentas.getApellido() != null) {
                        existingEjecCuentas.setApellido(ejecCuentas.getApellido());
                    }
                    if (ejecCuentas.getCelular() != null) {
                        existingEjecCuentas.setCelular(ejecCuentas.getCelular());
                    }
                    if (ejecCuentas.getMail() != null) {
                        existingEjecCuentas.setMail(ejecCuentas.getMail());
                    }
                    if (ejecCuentas.getNombre() != null) {
                        existingEjecCuentas.setNombre(ejecCuentas.getNombre());
                    }
                    if (ejecCuentas.getRepcom1() != null) {
                        existingEjecCuentas.setRepcom1(ejecCuentas.getRepcom1());
                    }
                    if (ejecCuentas.getRepcom2() != null) {
                        existingEjecCuentas.setRepcom2(ejecCuentas.getRepcom2());
                    }

                    return existingEjecCuentas;
                }
            )
            .map(ejecCuentasRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ejecCuentas.getId().toString())
        );
    }

    /**
     * {@code GET  /ejec-cuentas} : get all the ejecCuentas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ejecCuentas in body.
     */
    @GetMapping("/ejec-cuentas")
    public List<EjecCuentas> getAllEjecCuentas() {
        log.debug("REST request to get all EjecCuentas");
        return ejecCuentasRepository.findAll();
    }

    /**
     * {@code GET  /ejec-cuentas/:id} : get the "id" ejecCuentas.
     *
     * @param id the id of the ejecCuentas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ejecCuentas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ejec-cuentas/{id}")
    public ResponseEntity<EjecCuentas> getEjecCuentas(@PathVariable Long id) {
        log.debug("REST request to get EjecCuentas : {}", id);
        Optional<EjecCuentas> ejecCuentas = ejecCuentasRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ejecCuentas);
    }

    /**
     * {@code DELETE  /ejec-cuentas/:id} : delete the "id" ejecCuentas.
     *
     * @param id the id of the ejecCuentas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ejec-cuentas/{id}")
    public ResponseEntity<Void> deleteEjecCuentas(@PathVariable Long id) {
        log.debug("REST request to delete EjecCuentas : {}", id);
        ejecCuentasRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
