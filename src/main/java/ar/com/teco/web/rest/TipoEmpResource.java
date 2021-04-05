package ar.com.teco.web.rest;

import ar.com.teco.domain.TipoEmp;
import ar.com.teco.repository.TipoEmpRepository;
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
 * REST controller for managing {@link ar.com.teco.domain.TipoEmp}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TipoEmpResource {

    private final Logger log = LoggerFactory.getLogger(TipoEmpResource.class);

    private static final String ENTITY_NAME = "tipoEmp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoEmpRepository tipoEmpRepository;

    public TipoEmpResource(TipoEmpRepository tipoEmpRepository) {
        this.tipoEmpRepository = tipoEmpRepository;
    }

    /**
     * {@code POST  /tipo-emps} : Create a new tipoEmp.
     *
     * @param tipoEmp the tipoEmp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoEmp, or with status {@code 400 (Bad Request)} if the tipoEmp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-emps")
    public ResponseEntity<TipoEmp> createTipoEmp(@Valid @RequestBody TipoEmp tipoEmp) throws URISyntaxException {
        log.debug("REST request to save TipoEmp : {}", tipoEmp);
        if (tipoEmp.getId() != null) {
            throw new BadRequestAlertException("A new tipoEmp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoEmp result = tipoEmpRepository.save(tipoEmp);
        return ResponseEntity
            .created(new URI("/api/tipo-emps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-emps/:id} : Updates an existing tipoEmp.
     *
     * @param id the id of the tipoEmp to save.
     * @param tipoEmp the tipoEmp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoEmp,
     * or with status {@code 400 (Bad Request)} if the tipoEmp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoEmp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-emps/{id}")
    public ResponseEntity<TipoEmp> updateTipoEmp(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoEmp tipoEmp
    ) throws URISyntaxException {
        log.debug("REST request to update TipoEmp : {}, {}", id, tipoEmp);
        if (tipoEmp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoEmp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoEmpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoEmp result = tipoEmpRepository.save(tipoEmp);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoEmp.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-emps/:id} : Partial updates given fields of an existing tipoEmp, field will ignore if it is null
     *
     * @param id the id of the tipoEmp to save.
     * @param tipoEmp the tipoEmp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoEmp,
     * or with status {@code 400 (Bad Request)} if the tipoEmp is not valid,
     * or with status {@code 404 (Not Found)} if the tipoEmp is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoEmp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-emps/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TipoEmp> partialUpdateTipoEmp(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoEmp tipoEmp
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoEmp partially : {}, {}", id, tipoEmp);
        if (tipoEmp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoEmp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoEmpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoEmp> result = tipoEmpRepository
            .findById(tipoEmp.getId())
            .map(
                existingTipoEmp -> {
                    if (tipoEmp.getDescripcion() != null) {
                        existingTipoEmp.setDescripcion(tipoEmp.getDescripcion());
                    }
                    if (tipoEmp.getValor() != null) {
                        existingTipoEmp.setValor(tipoEmp.getValor());
                    }

                    return existingTipoEmp;
                }
            )
            .map(tipoEmpRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoEmp.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-emps} : get all the tipoEmps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoEmps in body.
     */
    @GetMapping("/tipo-emps")
    public List<TipoEmp> getAllTipoEmps() {
        log.debug("REST request to get all TipoEmps");
        return tipoEmpRepository.findAll();
    }

    /**
     * {@code GET  /tipo-emps/:id} : get the "id" tipoEmp.
     *
     * @param id the id of the tipoEmp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoEmp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-emps/{id}")
    public ResponseEntity<TipoEmp> getTipoEmp(@PathVariable Long id) {
        log.debug("REST request to get TipoEmp : {}", id);
        Optional<TipoEmp> tipoEmp = tipoEmpRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoEmp);
    }

    /**
     * {@code DELETE  /tipo-emps/:id} : delete the "id" tipoEmp.
     *
     * @param id the id of the tipoEmp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-emps/{id}")
    public ResponseEntity<Void> deleteTipoEmp(@PathVariable Long id) {
        log.debug("REST request to delete TipoEmp : {}", id);
        tipoEmpRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
