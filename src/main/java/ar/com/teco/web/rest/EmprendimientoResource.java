package ar.com.teco.web.rest;

import ar.com.teco.domain.Emprendimiento;
import ar.com.teco.repository.EmprendimientoRepository;
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
 * REST controller for managing {@link ar.com.teco.domain.Emprendimiento}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmprendimientoResource {

    private final Logger log = LoggerFactory.getLogger(EmprendimientoResource.class);

    private static final String ENTITY_NAME = "emprendimiento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmprendimientoRepository emprendimientoRepository;

    public EmprendimientoResource(EmprendimientoRepository emprendimientoRepository) {
        this.emprendimientoRepository = emprendimientoRepository;
    }

    /**
     * {@code POST  /emprendimientos} : Create a new emprendimiento.
     *
     * @param emprendimiento the emprendimiento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emprendimiento, or with status {@code 400 (Bad Request)} if the emprendimiento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emprendimientos")
    public ResponseEntity<Emprendimiento> createEmprendimiento(@Valid @RequestBody Emprendimiento emprendimiento)
        throws URISyntaxException {
        log.debug("REST request to save Emprendimiento : {}", emprendimiento);
        if (emprendimiento.getId() != null) {
            throw new BadRequestAlertException("A new emprendimiento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Emprendimiento result = emprendimientoRepository.save(emprendimiento);
        return ResponseEntity
            .created(new URI("/api/emprendimientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emprendimientos/:id} : Updates an existing emprendimiento.
     *
     * @param id the id of the emprendimiento to save.
     * @param emprendimiento the emprendimiento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emprendimiento,
     * or with status {@code 400 (Bad Request)} if the emprendimiento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emprendimiento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emprendimientos/{id}")
    public ResponseEntity<Emprendimiento> updateEmprendimiento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Emprendimiento emprendimiento
    ) throws URISyntaxException {
        log.debug("REST request to update Emprendimiento : {}, {}", id, emprendimiento);
        if (emprendimiento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emprendimiento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emprendimientoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Emprendimiento result = emprendimientoRepository.save(emprendimiento);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, emprendimiento.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /emprendimientos/:id} : Partial updates given fields of an existing emprendimiento, field will ignore if it is null
     *
     * @param id the id of the emprendimiento to save.
     * @param emprendimiento the emprendimiento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emprendimiento,
     * or with status {@code 400 (Bad Request)} if the emprendimiento is not valid,
     * or with status {@code 404 (Not Found)} if the emprendimiento is not found,
     * or with status {@code 500 (Internal Server Error)} if the emprendimiento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/emprendimientos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Emprendimiento> partialUpdateEmprendimiento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Emprendimiento emprendimiento
    ) throws URISyntaxException {
        log.debug("REST request to partial update Emprendimiento partially : {}, {}", id, emprendimiento);
        if (emprendimiento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emprendimiento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emprendimientoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Emprendimiento> result = emprendimientoRepository
            .findById(emprendimiento.getId())
            .map(
                existingEmprendimiento -> {
                    if (emprendimiento.getNombre() != null) {
                        existingEmprendimiento.setNombre(emprendimiento.getNombre());
                    }
                    if (emprendimiento.getContacto() != null) {
                        existingEmprendimiento.setContacto(emprendimiento.getContacto());
                    }
                    if (emprendimiento.getFechaFinObra() != null) {
                        existingEmprendimiento.setFechaFinObra(emprendimiento.getFechaFinObra());
                    }
                    if (emprendimiento.getElementosDeRed() != null) {
                        existingEmprendimiento.setElementosDeRed(emprendimiento.getElementosDeRed());
                    }
                    if (emprendimiento.getClientesCatv() != null) {
                        existingEmprendimiento.setClientesCatv(emprendimiento.getClientesCatv());
                    }
                    if (emprendimiento.getClientesFibertel() != null) {
                        existingEmprendimiento.setClientesFibertel(emprendimiento.getClientesFibertel());
                    }
                    if (emprendimiento.getClientesFibertelLite() != null) {
                        existingEmprendimiento.setClientesFibertelLite(emprendimiento.getClientesFibertelLite());
                    }
                    if (emprendimiento.getClientesFlow() != null) {
                        existingEmprendimiento.setClientesFlow(emprendimiento.getClientesFlow());
                    }
                    if (emprendimiento.getClientesCombo() != null) {
                        existingEmprendimiento.setClientesCombo(emprendimiento.getClientesCombo());
                    }
                    if (emprendimiento.getLineasVoz() != null) {
                        existingEmprendimiento.setLineasVoz(emprendimiento.getLineasVoz());
                    }
                    if (emprendimiento.getMesesDeFinalizado() != null) {
                        existingEmprendimiento.setMesesDeFinalizado(emprendimiento.getMesesDeFinalizado());
                    }
                    if (emprendimiento.getAltasBC() != null) {
                        existingEmprendimiento.setAltasBC(emprendimiento.getAltasBC());
                    }
                    if (emprendimiento.getPenetracionVivLot() != null) {
                        existingEmprendimiento.setPenetracionVivLot(emprendimiento.getPenetracionVivLot());
                    }
                    if (emprendimiento.getPenetracionBC() != null) {
                        existingEmprendimiento.setPenetracionBC(emprendimiento.getPenetracionBC());
                    }
                    if (emprendimiento.getDemanda1() != null) {
                        existingEmprendimiento.setDemanda1(emprendimiento.getDemanda1());
                    }
                    if (emprendimiento.getDemanda2() != null) {
                        existingEmprendimiento.setDemanda2(emprendimiento.getDemanda2());
                    }
                    if (emprendimiento.getDemanda3() != null) {
                        existingEmprendimiento.setDemanda3(emprendimiento.getDemanda3());
                    }
                    if (emprendimiento.getDemanda4() != null) {
                        existingEmprendimiento.setDemanda4(emprendimiento.getDemanda4());
                    }
                    if (emprendimiento.getLotes() != null) {
                        existingEmprendimiento.setLotes(emprendimiento.getLotes());
                    }
                    if (emprendimiento.getViviendas() != null) {
                        existingEmprendimiento.setViviendas(emprendimiento.getViviendas());
                    }
                    if (emprendimiento.getComProf() != null) {
                        existingEmprendimiento.setComProf(emprendimiento.getComProf());
                    }
                    if (emprendimiento.getHabitaciones() != null) {
                        existingEmprendimiento.setHabitaciones(emprendimiento.getHabitaciones());
                    }
                    if (emprendimiento.getManzanas() != null) {
                        existingEmprendimiento.setManzanas(emprendimiento.getManzanas());
                    }
                    if (emprendimiento.getDemanda() != null) {
                        existingEmprendimiento.setDemanda(emprendimiento.getDemanda());
                    }
                    if (emprendimiento.getFechaDeRelevamiento() != null) {
                        existingEmprendimiento.setFechaDeRelevamiento(emprendimiento.getFechaDeRelevamiento());
                    }
                    if (emprendimiento.getTelefono() != null) {
                        existingEmprendimiento.setTelefono(emprendimiento.getTelefono());
                    }
                    if (emprendimiento.getAnoPriorizacion() != null) {
                        existingEmprendimiento.setAnoPriorizacion(emprendimiento.getAnoPriorizacion());
                    }
                    if (emprendimiento.getContratoOpen() != null) {
                        existingEmprendimiento.setContratoOpen(emprendimiento.getContratoOpen());
                    }
                    if (emprendimiento.getNegociacion() != null) {
                        existingEmprendimiento.setNegociacion(emprendimiento.getNegociacion());
                    }
                    if (emprendimiento.getEstadoBC() != null) {
                        existingEmprendimiento.setEstadoBC(emprendimiento.getEstadoBC());
                    }
                    if (emprendimiento.getFecha() != null) {
                        existingEmprendimiento.setFecha(emprendimiento.getFecha());
                    }
                    if (emprendimiento.getCodigoDeFirma() != null) {
                        existingEmprendimiento.setCodigoDeFirma(emprendimiento.getCodigoDeFirma());
                    }
                    if (emprendimiento.getFechaFirma() != null) {
                        existingEmprendimiento.setFechaFirma(emprendimiento.getFechaFirma());
                    }
                    if (emprendimiento.getObservaciones() != null) {
                        existingEmprendimiento.setObservaciones(emprendimiento.getObservaciones());
                    }
                    if (emprendimiento.getComentario() != null) {
                        existingEmprendimiento.setComentario(emprendimiento.getComentario());
                    }
                    if (emprendimiento.getEstadoFirma() != null) {
                        existingEmprendimiento.setEstadoFirma(emprendimiento.getEstadoFirma());
                    }

                    return existingEmprendimiento;
                }
            )
            .map(emprendimientoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, emprendimiento.getId().toString())
        );
    }

    /**
     * {@code GET  /emprendimientos} : get all the emprendimientos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emprendimientos in body.
     */
    @GetMapping("/emprendimientos")
    public List<Emprendimiento> getAllEmprendimientos() {
        log.debug("REST request to get all Emprendimientos");
        return emprendimientoRepository.findAll();
    }

    /**
     * {@code GET  /emprendimientos/:id} : get the "id" emprendimiento.
     *
     * @param id the id of the emprendimiento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emprendimiento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emprendimientos/{id}")
    public ResponseEntity<Emprendimiento> getEmprendimiento(@PathVariable Long id) {
        log.debug("REST request to get Emprendimiento : {}", id);
        Optional<Emprendimiento> emprendimiento = emprendimientoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(emprendimiento);
    }

    /**
     * {@code DELETE  /emprendimientos/:id} : delete the "id" emprendimiento.
     *
     * @param id the id of the emprendimiento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emprendimientos/{id}")
    public ResponseEntity<Void> deleteEmprendimiento(@PathVariable Long id) {
        log.debug("REST request to delete Emprendimiento : {}", id);
        emprendimientoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
