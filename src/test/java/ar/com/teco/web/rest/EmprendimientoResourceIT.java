package ar.com.teco.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.teco.IntegrationTest;
import ar.com.teco.domain.Emprendimiento;
import ar.com.teco.repository.EmprendimientoRepository;
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
 * Integration tests for the {@link EmprendimientoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmprendimientoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_FIN_OBRA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FIN_OBRA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ELEMENTOS_DE_RED = "AAAAAAAAAA";
    private static final String UPDATED_ELEMENTOS_DE_RED = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENTES_CATV = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTES_CATV = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENTES_FIBERTEL = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTES_FIBERTEL = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENTES_FIBERTEL_LITE = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTES_FIBERTEL_LITE = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENTES_FLOW = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTES_FLOW = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENTES_COMBO = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTES_COMBO = "BBBBBBBBBB";

    private static final String DEFAULT_LINEAS_VOZ = "AAAAAAAAAA";
    private static final String UPDATED_LINEAS_VOZ = "BBBBBBBBBB";

    private static final String DEFAULT_MESES_DE_FINALIZADO = "AAAAAAAAAA";
    private static final String UPDATED_MESES_DE_FINALIZADO = "BBBBBBBBBB";

    private static final String DEFAULT_ALTAS_BC = "AAAAAAAAAA";
    private static final String UPDATED_ALTAS_BC = "BBBBBBBBBB";

    private static final String DEFAULT_PENETRACION_VIV_LOT = "AAAAAAAAAA";
    private static final String UPDATED_PENETRACION_VIV_LOT = "BBBBBBBBBB";

    private static final String DEFAULT_PENETRACION_BC = "AAAAAAAAAA";
    private static final String UPDATED_PENETRACION_BC = "BBBBBBBBBB";

    private static final String DEFAULT_DEMANDA_1 = "AAAAAAAAAA";
    private static final String UPDATED_DEMANDA_1 = "BBBBBBBBBB";

    private static final String DEFAULT_DEMANDA_2 = "AAAAAAAAAA";
    private static final String UPDATED_DEMANDA_2 = "BBBBBBBBBB";

    private static final String DEFAULT_DEMANDA_3 = "AAAAAAAAAA";
    private static final String UPDATED_DEMANDA_3 = "BBBBBBBBBB";

    private static final String DEFAULT_DEMANDA_4 = "AAAAAAAAAA";
    private static final String UPDATED_DEMANDA_4 = "BBBBBBBBBB";

    private static final String DEFAULT_LOTES = "AAAAAAAAAA";
    private static final String UPDATED_LOTES = "BBBBBBBBBB";

    private static final String DEFAULT_VIVIENDAS = "AAAAAAAAAA";
    private static final String UPDATED_VIVIENDAS = "BBBBBBBBBB";

    private static final String DEFAULT_COM_PROF = "AAAAAAAAAA";
    private static final String UPDATED_COM_PROF = "BBBBBBBBBB";

    private static final String DEFAULT_HABITACIONES = "AAAAAAAAAA";
    private static final String UPDATED_HABITACIONES = "BBBBBBBBBB";

    private static final String DEFAULT_MANZANAS = "AAAAAAAAAA";
    private static final String UPDATED_MANZANAS = "BBBBBBBBBB";

    private static final String DEFAULT_DEMANDA = "AAAAAAAAAA";
    private static final String UPDATED_DEMANDA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_DE_RELEVAMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_DE_RELEVAMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ANO_PRIORIZACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANO_PRIORIZACION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CONTRATO_OPEN = "AAAAAAAAAA";
    private static final String UPDATED_CONTRATO_OPEN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NEGOCIACION = false;
    private static final Boolean UPDATED_NEGOCIACION = true;

    private static final String DEFAULT_ESTADO_BC = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO_BC = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CODIGO_DE_FIRMA = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_DE_FIRMA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_FIRMA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FIRMA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO_FIRMA = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO_FIRMA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/emprendimientos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmprendimientoRepository emprendimientoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmprendimientoMockMvc;

    private Emprendimiento emprendimiento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emprendimiento createEntity(EntityManager em) {
        Emprendimiento emprendimiento = new Emprendimiento()
            .nombre(DEFAULT_NOMBRE)
            .contacto(DEFAULT_CONTACTO)
            .fechaFinObra(DEFAULT_FECHA_FIN_OBRA)
            .elementosDeRed(DEFAULT_ELEMENTOS_DE_RED)
            .clientesCatv(DEFAULT_CLIENTES_CATV)
            .clientesFibertel(DEFAULT_CLIENTES_FIBERTEL)
            .clientesFibertelLite(DEFAULT_CLIENTES_FIBERTEL_LITE)
            .clientesFlow(DEFAULT_CLIENTES_FLOW)
            .clientesCombo(DEFAULT_CLIENTES_COMBO)
            .lineasVoz(DEFAULT_LINEAS_VOZ)
            .mesesDeFinalizado(DEFAULT_MESES_DE_FINALIZADO)
            .altasBC(DEFAULT_ALTAS_BC)
            .penetracionVivLot(DEFAULT_PENETRACION_VIV_LOT)
            .penetracionBC(DEFAULT_PENETRACION_BC)
            .demanda1(DEFAULT_DEMANDA_1)
            .demanda2(DEFAULT_DEMANDA_2)
            .demanda3(DEFAULT_DEMANDA_3)
            .demanda4(DEFAULT_DEMANDA_4)
            .lotes(DEFAULT_LOTES)
            .viviendas(DEFAULT_VIVIENDAS)
            .comProf(DEFAULT_COM_PROF)
            .habitaciones(DEFAULT_HABITACIONES)
            .manzanas(DEFAULT_MANZANAS)
            .demanda(DEFAULT_DEMANDA)
            .fechaDeRelevamiento(DEFAULT_FECHA_DE_RELEVAMIENTO)
            .telefono(DEFAULT_TELEFONO)
            .anoPriorizacion(DEFAULT_ANO_PRIORIZACION)
            .contratoOpen(DEFAULT_CONTRATO_OPEN)
            .negociacion(DEFAULT_NEGOCIACION)
            .estadoBC(DEFAULT_ESTADO_BC)
            .fecha(DEFAULT_FECHA)
            .codigoDeFirma(DEFAULT_CODIGO_DE_FIRMA)
            .fechaFirma(DEFAULT_FECHA_FIRMA)
            .observaciones(DEFAULT_OBSERVACIONES)
            .comentario(DEFAULT_COMENTARIO)
            .estadoFirma(DEFAULT_ESTADO_FIRMA);
        return emprendimiento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emprendimiento createUpdatedEntity(EntityManager em) {
        Emprendimiento emprendimiento = new Emprendimiento()
            .nombre(UPDATED_NOMBRE)
            .contacto(UPDATED_CONTACTO)
            .fechaFinObra(UPDATED_FECHA_FIN_OBRA)
            .elementosDeRed(UPDATED_ELEMENTOS_DE_RED)
            .clientesCatv(UPDATED_CLIENTES_CATV)
            .clientesFibertel(UPDATED_CLIENTES_FIBERTEL)
            .clientesFibertelLite(UPDATED_CLIENTES_FIBERTEL_LITE)
            .clientesFlow(UPDATED_CLIENTES_FLOW)
            .clientesCombo(UPDATED_CLIENTES_COMBO)
            .lineasVoz(UPDATED_LINEAS_VOZ)
            .mesesDeFinalizado(UPDATED_MESES_DE_FINALIZADO)
            .altasBC(UPDATED_ALTAS_BC)
            .penetracionVivLot(UPDATED_PENETRACION_VIV_LOT)
            .penetracionBC(UPDATED_PENETRACION_BC)
            .demanda1(UPDATED_DEMANDA_1)
            .demanda2(UPDATED_DEMANDA_2)
            .demanda3(UPDATED_DEMANDA_3)
            .demanda4(UPDATED_DEMANDA_4)
            .lotes(UPDATED_LOTES)
            .viviendas(UPDATED_VIVIENDAS)
            .comProf(UPDATED_COM_PROF)
            .habitaciones(UPDATED_HABITACIONES)
            .manzanas(UPDATED_MANZANAS)
            .demanda(UPDATED_DEMANDA)
            .fechaDeRelevamiento(UPDATED_FECHA_DE_RELEVAMIENTO)
            .telefono(UPDATED_TELEFONO)
            .anoPriorizacion(UPDATED_ANO_PRIORIZACION)
            .contratoOpen(UPDATED_CONTRATO_OPEN)
            .negociacion(UPDATED_NEGOCIACION)
            .estadoBC(UPDATED_ESTADO_BC)
            .fecha(UPDATED_FECHA)
            .codigoDeFirma(UPDATED_CODIGO_DE_FIRMA)
            .fechaFirma(UPDATED_FECHA_FIRMA)
            .observaciones(UPDATED_OBSERVACIONES)
            .comentario(UPDATED_COMENTARIO)
            .estadoFirma(UPDATED_ESTADO_FIRMA);
        return emprendimiento;
    }

    @BeforeEach
    public void initTest() {
        emprendimiento = createEntity(em);
    }

    @Test
    @Transactional
    void createEmprendimiento() throws Exception {
        int databaseSizeBeforeCreate = emprendimientoRepository.findAll().size();
        // Create the Emprendimiento
        restEmprendimientoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emprendimiento))
            )
            .andExpect(status().isCreated());

        // Validate the Emprendimiento in the database
        List<Emprendimiento> emprendimientoList = emprendimientoRepository.findAll();
        assertThat(emprendimientoList).hasSize(databaseSizeBeforeCreate + 1);
        Emprendimiento testEmprendimiento = emprendimientoList.get(emprendimientoList.size() - 1);
        assertThat(testEmprendimiento.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEmprendimiento.getContacto()).isEqualTo(DEFAULT_CONTACTO);
        assertThat(testEmprendimiento.getFechaFinObra()).isEqualTo(DEFAULT_FECHA_FIN_OBRA);
        assertThat(testEmprendimiento.getElementosDeRed()).isEqualTo(DEFAULT_ELEMENTOS_DE_RED);
        assertThat(testEmprendimiento.getClientesCatv()).isEqualTo(DEFAULT_CLIENTES_CATV);
        assertThat(testEmprendimiento.getClientesFibertel()).isEqualTo(DEFAULT_CLIENTES_FIBERTEL);
        assertThat(testEmprendimiento.getClientesFibertelLite()).isEqualTo(DEFAULT_CLIENTES_FIBERTEL_LITE);
        assertThat(testEmprendimiento.getClientesFlow()).isEqualTo(DEFAULT_CLIENTES_FLOW);
        assertThat(testEmprendimiento.getClientesCombo()).isEqualTo(DEFAULT_CLIENTES_COMBO);
        assertThat(testEmprendimiento.getLineasVoz()).isEqualTo(DEFAULT_LINEAS_VOZ);
        assertThat(testEmprendimiento.getMesesDeFinalizado()).isEqualTo(DEFAULT_MESES_DE_FINALIZADO);
        assertThat(testEmprendimiento.getAltasBC()).isEqualTo(DEFAULT_ALTAS_BC);
        assertThat(testEmprendimiento.getPenetracionVivLot()).isEqualTo(DEFAULT_PENETRACION_VIV_LOT);
        assertThat(testEmprendimiento.getPenetracionBC()).isEqualTo(DEFAULT_PENETRACION_BC);
        assertThat(testEmprendimiento.getDemanda1()).isEqualTo(DEFAULT_DEMANDA_1);
        assertThat(testEmprendimiento.getDemanda2()).isEqualTo(DEFAULT_DEMANDA_2);
        assertThat(testEmprendimiento.getDemanda3()).isEqualTo(DEFAULT_DEMANDA_3);
        assertThat(testEmprendimiento.getDemanda4()).isEqualTo(DEFAULT_DEMANDA_4);
        assertThat(testEmprendimiento.getLotes()).isEqualTo(DEFAULT_LOTES);
        assertThat(testEmprendimiento.getViviendas()).isEqualTo(DEFAULT_VIVIENDAS);
        assertThat(testEmprendimiento.getComProf()).isEqualTo(DEFAULT_COM_PROF);
        assertThat(testEmprendimiento.getHabitaciones()).isEqualTo(DEFAULT_HABITACIONES);
        assertThat(testEmprendimiento.getManzanas()).isEqualTo(DEFAULT_MANZANAS);
        assertThat(testEmprendimiento.getDemanda()).isEqualTo(DEFAULT_DEMANDA);
        assertThat(testEmprendimiento.getFechaDeRelevamiento()).isEqualTo(DEFAULT_FECHA_DE_RELEVAMIENTO);
        assertThat(testEmprendimiento.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testEmprendimiento.getAnoPriorizacion()).isEqualTo(DEFAULT_ANO_PRIORIZACION);
        assertThat(testEmprendimiento.getContratoOpen()).isEqualTo(DEFAULT_CONTRATO_OPEN);
        assertThat(testEmprendimiento.getNegociacion()).isEqualTo(DEFAULT_NEGOCIACION);
        assertThat(testEmprendimiento.getEstadoBC()).isEqualTo(DEFAULT_ESTADO_BC);
        assertThat(testEmprendimiento.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testEmprendimiento.getCodigoDeFirma()).isEqualTo(DEFAULT_CODIGO_DE_FIRMA);
        assertThat(testEmprendimiento.getFechaFirma()).isEqualTo(DEFAULT_FECHA_FIRMA);
        assertThat(testEmprendimiento.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
        assertThat(testEmprendimiento.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testEmprendimiento.getEstadoFirma()).isEqualTo(DEFAULT_ESTADO_FIRMA);
    }

    @Test
    @Transactional
    void createEmprendimientoWithExistingId() throws Exception {
        // Create the Emprendimiento with an existing ID
        emprendimiento.setId(1L);

        int databaseSizeBeforeCreate = emprendimientoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmprendimientoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emprendimiento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emprendimiento in the database
        List<Emprendimiento> emprendimientoList = emprendimientoRepository.findAll();
        assertThat(emprendimientoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmprendimientos() throws Exception {
        // Initialize the database
        emprendimientoRepository.saveAndFlush(emprendimiento);

        // Get all the emprendimientoList
        restEmprendimientoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emprendimiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].contacto").value(hasItem(DEFAULT_CONTACTO)))
            .andExpect(jsonPath("$.[*].fechaFinObra").value(hasItem(DEFAULT_FECHA_FIN_OBRA.toString())))
            .andExpect(jsonPath("$.[*].elementosDeRed").value(hasItem(DEFAULT_ELEMENTOS_DE_RED)))
            .andExpect(jsonPath("$.[*].clientesCatv").value(hasItem(DEFAULT_CLIENTES_CATV)))
            .andExpect(jsonPath("$.[*].clientesFibertel").value(hasItem(DEFAULT_CLIENTES_FIBERTEL)))
            .andExpect(jsonPath("$.[*].clientesFibertelLite").value(hasItem(DEFAULT_CLIENTES_FIBERTEL_LITE)))
            .andExpect(jsonPath("$.[*].clientesFlow").value(hasItem(DEFAULT_CLIENTES_FLOW)))
            .andExpect(jsonPath("$.[*].clientesCombo").value(hasItem(DEFAULT_CLIENTES_COMBO)))
            .andExpect(jsonPath("$.[*].lineasVoz").value(hasItem(DEFAULT_LINEAS_VOZ)))
            .andExpect(jsonPath("$.[*].mesesDeFinalizado").value(hasItem(DEFAULT_MESES_DE_FINALIZADO)))
            .andExpect(jsonPath("$.[*].altasBC").value(hasItem(DEFAULT_ALTAS_BC)))
            .andExpect(jsonPath("$.[*].penetracionVivLot").value(hasItem(DEFAULT_PENETRACION_VIV_LOT)))
            .andExpect(jsonPath("$.[*].penetracionBC").value(hasItem(DEFAULT_PENETRACION_BC)))
            .andExpect(jsonPath("$.[*].demanda1").value(hasItem(DEFAULT_DEMANDA_1)))
            .andExpect(jsonPath("$.[*].demanda2").value(hasItem(DEFAULT_DEMANDA_2)))
            .andExpect(jsonPath("$.[*].demanda3").value(hasItem(DEFAULT_DEMANDA_3)))
            .andExpect(jsonPath("$.[*].demanda4").value(hasItem(DEFAULT_DEMANDA_4)))
            .andExpect(jsonPath("$.[*].lotes").value(hasItem(DEFAULT_LOTES)))
            .andExpect(jsonPath("$.[*].viviendas").value(hasItem(DEFAULT_VIVIENDAS)))
            .andExpect(jsonPath("$.[*].comProf").value(hasItem(DEFAULT_COM_PROF)))
            .andExpect(jsonPath("$.[*].habitaciones").value(hasItem(DEFAULT_HABITACIONES)))
            .andExpect(jsonPath("$.[*].manzanas").value(hasItem(DEFAULT_MANZANAS)))
            .andExpect(jsonPath("$.[*].demanda").value(hasItem(DEFAULT_DEMANDA)))
            .andExpect(jsonPath("$.[*].fechaDeRelevamiento").value(hasItem(DEFAULT_FECHA_DE_RELEVAMIENTO.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].anoPriorizacion").value(hasItem(DEFAULT_ANO_PRIORIZACION.toString())))
            .andExpect(jsonPath("$.[*].contratoOpen").value(hasItem(DEFAULT_CONTRATO_OPEN)))
            .andExpect(jsonPath("$.[*].negociacion").value(hasItem(DEFAULT_NEGOCIACION.booleanValue())))
            .andExpect(jsonPath("$.[*].estadoBC").value(hasItem(DEFAULT_ESTADO_BC)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].codigoDeFirma").value(hasItem(DEFAULT_CODIGO_DE_FIRMA)))
            .andExpect(jsonPath("$.[*].fechaFirma").value(hasItem(DEFAULT_FECHA_FIRMA.toString())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)))
            .andExpect(jsonPath("$.[*].estadoFirma").value(hasItem(DEFAULT_ESTADO_FIRMA)));
    }

    @Test
    @Transactional
    void getEmprendimiento() throws Exception {
        // Initialize the database
        emprendimientoRepository.saveAndFlush(emprendimiento);

        // Get the emprendimiento
        restEmprendimientoMockMvc
            .perform(get(ENTITY_API_URL_ID, emprendimiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emprendimiento.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.contacto").value(DEFAULT_CONTACTO))
            .andExpect(jsonPath("$.fechaFinObra").value(DEFAULT_FECHA_FIN_OBRA.toString()))
            .andExpect(jsonPath("$.elementosDeRed").value(DEFAULT_ELEMENTOS_DE_RED))
            .andExpect(jsonPath("$.clientesCatv").value(DEFAULT_CLIENTES_CATV))
            .andExpect(jsonPath("$.clientesFibertel").value(DEFAULT_CLIENTES_FIBERTEL))
            .andExpect(jsonPath("$.clientesFibertelLite").value(DEFAULT_CLIENTES_FIBERTEL_LITE))
            .andExpect(jsonPath("$.clientesFlow").value(DEFAULT_CLIENTES_FLOW))
            .andExpect(jsonPath("$.clientesCombo").value(DEFAULT_CLIENTES_COMBO))
            .andExpect(jsonPath("$.lineasVoz").value(DEFAULT_LINEAS_VOZ))
            .andExpect(jsonPath("$.mesesDeFinalizado").value(DEFAULT_MESES_DE_FINALIZADO))
            .andExpect(jsonPath("$.altasBC").value(DEFAULT_ALTAS_BC))
            .andExpect(jsonPath("$.penetracionVivLot").value(DEFAULT_PENETRACION_VIV_LOT))
            .andExpect(jsonPath("$.penetracionBC").value(DEFAULT_PENETRACION_BC))
            .andExpect(jsonPath("$.demanda1").value(DEFAULT_DEMANDA_1))
            .andExpect(jsonPath("$.demanda2").value(DEFAULT_DEMANDA_2))
            .andExpect(jsonPath("$.demanda3").value(DEFAULT_DEMANDA_3))
            .andExpect(jsonPath("$.demanda4").value(DEFAULT_DEMANDA_4))
            .andExpect(jsonPath("$.lotes").value(DEFAULT_LOTES))
            .andExpect(jsonPath("$.viviendas").value(DEFAULT_VIVIENDAS))
            .andExpect(jsonPath("$.comProf").value(DEFAULT_COM_PROF))
            .andExpect(jsonPath("$.habitaciones").value(DEFAULT_HABITACIONES))
            .andExpect(jsonPath("$.manzanas").value(DEFAULT_MANZANAS))
            .andExpect(jsonPath("$.demanda").value(DEFAULT_DEMANDA))
            .andExpect(jsonPath("$.fechaDeRelevamiento").value(DEFAULT_FECHA_DE_RELEVAMIENTO.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.anoPriorizacion").value(DEFAULT_ANO_PRIORIZACION.toString()))
            .andExpect(jsonPath("$.contratoOpen").value(DEFAULT_CONTRATO_OPEN))
            .andExpect(jsonPath("$.negociacion").value(DEFAULT_NEGOCIACION.booleanValue()))
            .andExpect(jsonPath("$.estadoBC").value(DEFAULT_ESTADO_BC))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.codigoDeFirma").value(DEFAULT_CODIGO_DE_FIRMA))
            .andExpect(jsonPath("$.fechaFirma").value(DEFAULT_FECHA_FIRMA.toString()))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO))
            .andExpect(jsonPath("$.estadoFirma").value(DEFAULT_ESTADO_FIRMA));
    }

    @Test
    @Transactional
    void getNonExistingEmprendimiento() throws Exception {
        // Get the emprendimiento
        restEmprendimientoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmprendimiento() throws Exception {
        // Initialize the database
        emprendimientoRepository.saveAndFlush(emprendimiento);

        int databaseSizeBeforeUpdate = emprendimientoRepository.findAll().size();

        // Update the emprendimiento
        Emprendimiento updatedEmprendimiento = emprendimientoRepository.findById(emprendimiento.getId()).get();
        // Disconnect from session so that the updates on updatedEmprendimiento are not directly saved in db
        em.detach(updatedEmprendimiento);
        updatedEmprendimiento
            .nombre(UPDATED_NOMBRE)
            .contacto(UPDATED_CONTACTO)
            .fechaFinObra(UPDATED_FECHA_FIN_OBRA)
            .elementosDeRed(UPDATED_ELEMENTOS_DE_RED)
            .clientesCatv(UPDATED_CLIENTES_CATV)
            .clientesFibertel(UPDATED_CLIENTES_FIBERTEL)
            .clientesFibertelLite(UPDATED_CLIENTES_FIBERTEL_LITE)
            .clientesFlow(UPDATED_CLIENTES_FLOW)
            .clientesCombo(UPDATED_CLIENTES_COMBO)
            .lineasVoz(UPDATED_LINEAS_VOZ)
            .mesesDeFinalizado(UPDATED_MESES_DE_FINALIZADO)
            .altasBC(UPDATED_ALTAS_BC)
            .penetracionVivLot(UPDATED_PENETRACION_VIV_LOT)
            .penetracionBC(UPDATED_PENETRACION_BC)
            .demanda1(UPDATED_DEMANDA_1)
            .demanda2(UPDATED_DEMANDA_2)
            .demanda3(UPDATED_DEMANDA_3)
            .demanda4(UPDATED_DEMANDA_4)
            .lotes(UPDATED_LOTES)
            .viviendas(UPDATED_VIVIENDAS)
            .comProf(UPDATED_COM_PROF)
            .habitaciones(UPDATED_HABITACIONES)
            .manzanas(UPDATED_MANZANAS)
            .demanda(UPDATED_DEMANDA)
            .fechaDeRelevamiento(UPDATED_FECHA_DE_RELEVAMIENTO)
            .telefono(UPDATED_TELEFONO)
            .anoPriorizacion(UPDATED_ANO_PRIORIZACION)
            .contratoOpen(UPDATED_CONTRATO_OPEN)
            .negociacion(UPDATED_NEGOCIACION)
            .estadoBC(UPDATED_ESTADO_BC)
            .fecha(UPDATED_FECHA)
            .codigoDeFirma(UPDATED_CODIGO_DE_FIRMA)
            .fechaFirma(UPDATED_FECHA_FIRMA)
            .observaciones(UPDATED_OBSERVACIONES)
            .comentario(UPDATED_COMENTARIO)
            .estadoFirma(UPDATED_ESTADO_FIRMA);

        restEmprendimientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmprendimiento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmprendimiento))
            )
            .andExpect(status().isOk());

        // Validate the Emprendimiento in the database
        List<Emprendimiento> emprendimientoList = emprendimientoRepository.findAll();
        assertThat(emprendimientoList).hasSize(databaseSizeBeforeUpdate);
        Emprendimiento testEmprendimiento = emprendimientoList.get(emprendimientoList.size() - 1);
        assertThat(testEmprendimiento.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEmprendimiento.getContacto()).isEqualTo(UPDATED_CONTACTO);
        assertThat(testEmprendimiento.getFechaFinObra()).isEqualTo(UPDATED_FECHA_FIN_OBRA);
        assertThat(testEmprendimiento.getElementosDeRed()).isEqualTo(UPDATED_ELEMENTOS_DE_RED);
        assertThat(testEmprendimiento.getClientesCatv()).isEqualTo(UPDATED_CLIENTES_CATV);
        assertThat(testEmprendimiento.getClientesFibertel()).isEqualTo(UPDATED_CLIENTES_FIBERTEL);
        assertThat(testEmprendimiento.getClientesFibertelLite()).isEqualTo(UPDATED_CLIENTES_FIBERTEL_LITE);
        assertThat(testEmprendimiento.getClientesFlow()).isEqualTo(UPDATED_CLIENTES_FLOW);
        assertThat(testEmprendimiento.getClientesCombo()).isEqualTo(UPDATED_CLIENTES_COMBO);
        assertThat(testEmprendimiento.getLineasVoz()).isEqualTo(UPDATED_LINEAS_VOZ);
        assertThat(testEmprendimiento.getMesesDeFinalizado()).isEqualTo(UPDATED_MESES_DE_FINALIZADO);
        assertThat(testEmprendimiento.getAltasBC()).isEqualTo(UPDATED_ALTAS_BC);
        assertThat(testEmprendimiento.getPenetracionVivLot()).isEqualTo(UPDATED_PENETRACION_VIV_LOT);
        assertThat(testEmprendimiento.getPenetracionBC()).isEqualTo(UPDATED_PENETRACION_BC);
        assertThat(testEmprendimiento.getDemanda1()).isEqualTo(UPDATED_DEMANDA_1);
        assertThat(testEmprendimiento.getDemanda2()).isEqualTo(UPDATED_DEMANDA_2);
        assertThat(testEmprendimiento.getDemanda3()).isEqualTo(UPDATED_DEMANDA_3);
        assertThat(testEmprendimiento.getDemanda4()).isEqualTo(UPDATED_DEMANDA_4);
        assertThat(testEmprendimiento.getLotes()).isEqualTo(UPDATED_LOTES);
        assertThat(testEmprendimiento.getViviendas()).isEqualTo(UPDATED_VIVIENDAS);
        assertThat(testEmprendimiento.getComProf()).isEqualTo(UPDATED_COM_PROF);
        assertThat(testEmprendimiento.getHabitaciones()).isEqualTo(UPDATED_HABITACIONES);
        assertThat(testEmprendimiento.getManzanas()).isEqualTo(UPDATED_MANZANAS);
        assertThat(testEmprendimiento.getDemanda()).isEqualTo(UPDATED_DEMANDA);
        assertThat(testEmprendimiento.getFechaDeRelevamiento()).isEqualTo(UPDATED_FECHA_DE_RELEVAMIENTO);
        assertThat(testEmprendimiento.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testEmprendimiento.getAnoPriorizacion()).isEqualTo(UPDATED_ANO_PRIORIZACION);
        assertThat(testEmprendimiento.getContratoOpen()).isEqualTo(UPDATED_CONTRATO_OPEN);
        assertThat(testEmprendimiento.getNegociacion()).isEqualTo(UPDATED_NEGOCIACION);
        assertThat(testEmprendimiento.getEstadoBC()).isEqualTo(UPDATED_ESTADO_BC);
        assertThat(testEmprendimiento.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testEmprendimiento.getCodigoDeFirma()).isEqualTo(UPDATED_CODIGO_DE_FIRMA);
        assertThat(testEmprendimiento.getFechaFirma()).isEqualTo(UPDATED_FECHA_FIRMA);
        assertThat(testEmprendimiento.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testEmprendimiento.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testEmprendimiento.getEstadoFirma()).isEqualTo(UPDATED_ESTADO_FIRMA);
    }

    @Test
    @Transactional
    void putNonExistingEmprendimiento() throws Exception {
        int databaseSizeBeforeUpdate = emprendimientoRepository.findAll().size();
        emprendimiento.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmprendimientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emprendimiento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emprendimiento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emprendimiento in the database
        List<Emprendimiento> emprendimientoList = emprendimientoRepository.findAll();
        assertThat(emprendimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmprendimiento() throws Exception {
        int databaseSizeBeforeUpdate = emprendimientoRepository.findAll().size();
        emprendimiento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmprendimientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emprendimiento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emprendimiento in the database
        List<Emprendimiento> emprendimientoList = emprendimientoRepository.findAll();
        assertThat(emprendimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmprendimiento() throws Exception {
        int databaseSizeBeforeUpdate = emprendimientoRepository.findAll().size();
        emprendimiento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmprendimientoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emprendimiento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Emprendimiento in the database
        List<Emprendimiento> emprendimientoList = emprendimientoRepository.findAll();
        assertThat(emprendimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmprendimientoWithPatch() throws Exception {
        // Initialize the database
        emprendimientoRepository.saveAndFlush(emprendimiento);

        int databaseSizeBeforeUpdate = emprendimientoRepository.findAll().size();

        // Update the emprendimiento using partial update
        Emprendimiento partialUpdatedEmprendimiento = new Emprendimiento();
        partialUpdatedEmprendimiento.setId(emprendimiento.getId());

        partialUpdatedEmprendimiento
            .nombre(UPDATED_NOMBRE)
            .fechaFinObra(UPDATED_FECHA_FIN_OBRA)
            .elementosDeRed(UPDATED_ELEMENTOS_DE_RED)
            .clientesFibertelLite(UPDATED_CLIENTES_FIBERTEL_LITE)
            .clientesFlow(UPDATED_CLIENTES_FLOW)
            .clientesCombo(UPDATED_CLIENTES_COMBO)
            .penetracionVivLot(UPDATED_PENETRACION_VIV_LOT)
            .penetracionBC(UPDATED_PENETRACION_BC)
            .demanda2(UPDATED_DEMANDA_2)
            .lotes(UPDATED_LOTES)
            .habitaciones(UPDATED_HABITACIONES)
            .demanda(UPDATED_DEMANDA)
            .contratoOpen(UPDATED_CONTRATO_OPEN)
            .observaciones(UPDATED_OBSERVACIONES)
            .estadoFirma(UPDATED_ESTADO_FIRMA);

        restEmprendimientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmprendimiento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmprendimiento))
            )
            .andExpect(status().isOk());

        // Validate the Emprendimiento in the database
        List<Emprendimiento> emprendimientoList = emprendimientoRepository.findAll();
        assertThat(emprendimientoList).hasSize(databaseSizeBeforeUpdate);
        Emprendimiento testEmprendimiento = emprendimientoList.get(emprendimientoList.size() - 1);
        assertThat(testEmprendimiento.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEmprendimiento.getContacto()).isEqualTo(DEFAULT_CONTACTO);
        assertThat(testEmprendimiento.getFechaFinObra()).isEqualTo(UPDATED_FECHA_FIN_OBRA);
        assertThat(testEmprendimiento.getElementosDeRed()).isEqualTo(UPDATED_ELEMENTOS_DE_RED);
        assertThat(testEmprendimiento.getClientesCatv()).isEqualTo(DEFAULT_CLIENTES_CATV);
        assertThat(testEmprendimiento.getClientesFibertel()).isEqualTo(DEFAULT_CLIENTES_FIBERTEL);
        assertThat(testEmprendimiento.getClientesFibertelLite()).isEqualTo(UPDATED_CLIENTES_FIBERTEL_LITE);
        assertThat(testEmprendimiento.getClientesFlow()).isEqualTo(UPDATED_CLIENTES_FLOW);
        assertThat(testEmprendimiento.getClientesCombo()).isEqualTo(UPDATED_CLIENTES_COMBO);
        assertThat(testEmprendimiento.getLineasVoz()).isEqualTo(DEFAULT_LINEAS_VOZ);
        assertThat(testEmprendimiento.getMesesDeFinalizado()).isEqualTo(DEFAULT_MESES_DE_FINALIZADO);
        assertThat(testEmprendimiento.getAltasBC()).isEqualTo(DEFAULT_ALTAS_BC);
        assertThat(testEmprendimiento.getPenetracionVivLot()).isEqualTo(UPDATED_PENETRACION_VIV_LOT);
        assertThat(testEmprendimiento.getPenetracionBC()).isEqualTo(UPDATED_PENETRACION_BC);
        assertThat(testEmprendimiento.getDemanda1()).isEqualTo(DEFAULT_DEMANDA_1);
        assertThat(testEmprendimiento.getDemanda2()).isEqualTo(UPDATED_DEMANDA_2);
        assertThat(testEmprendimiento.getDemanda3()).isEqualTo(DEFAULT_DEMANDA_3);
        assertThat(testEmprendimiento.getDemanda4()).isEqualTo(DEFAULT_DEMANDA_4);
        assertThat(testEmprendimiento.getLotes()).isEqualTo(UPDATED_LOTES);
        assertThat(testEmprendimiento.getViviendas()).isEqualTo(DEFAULT_VIVIENDAS);
        assertThat(testEmprendimiento.getComProf()).isEqualTo(DEFAULT_COM_PROF);
        assertThat(testEmprendimiento.getHabitaciones()).isEqualTo(UPDATED_HABITACIONES);
        assertThat(testEmprendimiento.getManzanas()).isEqualTo(DEFAULT_MANZANAS);
        assertThat(testEmprendimiento.getDemanda()).isEqualTo(UPDATED_DEMANDA);
        assertThat(testEmprendimiento.getFechaDeRelevamiento()).isEqualTo(DEFAULT_FECHA_DE_RELEVAMIENTO);
        assertThat(testEmprendimiento.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testEmprendimiento.getAnoPriorizacion()).isEqualTo(DEFAULT_ANO_PRIORIZACION);
        assertThat(testEmprendimiento.getContratoOpen()).isEqualTo(UPDATED_CONTRATO_OPEN);
        assertThat(testEmprendimiento.getNegociacion()).isEqualTo(DEFAULT_NEGOCIACION);
        assertThat(testEmprendimiento.getEstadoBC()).isEqualTo(DEFAULT_ESTADO_BC);
        assertThat(testEmprendimiento.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testEmprendimiento.getCodigoDeFirma()).isEqualTo(DEFAULT_CODIGO_DE_FIRMA);
        assertThat(testEmprendimiento.getFechaFirma()).isEqualTo(DEFAULT_FECHA_FIRMA);
        assertThat(testEmprendimiento.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testEmprendimiento.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testEmprendimiento.getEstadoFirma()).isEqualTo(UPDATED_ESTADO_FIRMA);
    }

    @Test
    @Transactional
    void fullUpdateEmprendimientoWithPatch() throws Exception {
        // Initialize the database
        emprendimientoRepository.saveAndFlush(emprendimiento);

        int databaseSizeBeforeUpdate = emprendimientoRepository.findAll().size();

        // Update the emprendimiento using partial update
        Emprendimiento partialUpdatedEmprendimiento = new Emprendimiento();
        partialUpdatedEmprendimiento.setId(emprendimiento.getId());

        partialUpdatedEmprendimiento
            .nombre(UPDATED_NOMBRE)
            .contacto(UPDATED_CONTACTO)
            .fechaFinObra(UPDATED_FECHA_FIN_OBRA)
            .elementosDeRed(UPDATED_ELEMENTOS_DE_RED)
            .clientesCatv(UPDATED_CLIENTES_CATV)
            .clientesFibertel(UPDATED_CLIENTES_FIBERTEL)
            .clientesFibertelLite(UPDATED_CLIENTES_FIBERTEL_LITE)
            .clientesFlow(UPDATED_CLIENTES_FLOW)
            .clientesCombo(UPDATED_CLIENTES_COMBO)
            .lineasVoz(UPDATED_LINEAS_VOZ)
            .mesesDeFinalizado(UPDATED_MESES_DE_FINALIZADO)
            .altasBC(UPDATED_ALTAS_BC)
            .penetracionVivLot(UPDATED_PENETRACION_VIV_LOT)
            .penetracionBC(UPDATED_PENETRACION_BC)
            .demanda1(UPDATED_DEMANDA_1)
            .demanda2(UPDATED_DEMANDA_2)
            .demanda3(UPDATED_DEMANDA_3)
            .demanda4(UPDATED_DEMANDA_4)
            .lotes(UPDATED_LOTES)
            .viviendas(UPDATED_VIVIENDAS)
            .comProf(UPDATED_COM_PROF)
            .habitaciones(UPDATED_HABITACIONES)
            .manzanas(UPDATED_MANZANAS)
            .demanda(UPDATED_DEMANDA)
            .fechaDeRelevamiento(UPDATED_FECHA_DE_RELEVAMIENTO)
            .telefono(UPDATED_TELEFONO)
            .anoPriorizacion(UPDATED_ANO_PRIORIZACION)
            .contratoOpen(UPDATED_CONTRATO_OPEN)
            .negociacion(UPDATED_NEGOCIACION)
            .estadoBC(UPDATED_ESTADO_BC)
            .fecha(UPDATED_FECHA)
            .codigoDeFirma(UPDATED_CODIGO_DE_FIRMA)
            .fechaFirma(UPDATED_FECHA_FIRMA)
            .observaciones(UPDATED_OBSERVACIONES)
            .comentario(UPDATED_COMENTARIO)
            .estadoFirma(UPDATED_ESTADO_FIRMA);

        restEmprendimientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmprendimiento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmprendimiento))
            )
            .andExpect(status().isOk());

        // Validate the Emprendimiento in the database
        List<Emprendimiento> emprendimientoList = emprendimientoRepository.findAll();
        assertThat(emprendimientoList).hasSize(databaseSizeBeforeUpdate);
        Emprendimiento testEmprendimiento = emprendimientoList.get(emprendimientoList.size() - 1);
        assertThat(testEmprendimiento.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEmprendimiento.getContacto()).isEqualTo(UPDATED_CONTACTO);
        assertThat(testEmprendimiento.getFechaFinObra()).isEqualTo(UPDATED_FECHA_FIN_OBRA);
        assertThat(testEmprendimiento.getElementosDeRed()).isEqualTo(UPDATED_ELEMENTOS_DE_RED);
        assertThat(testEmprendimiento.getClientesCatv()).isEqualTo(UPDATED_CLIENTES_CATV);
        assertThat(testEmprendimiento.getClientesFibertel()).isEqualTo(UPDATED_CLIENTES_FIBERTEL);
        assertThat(testEmprendimiento.getClientesFibertelLite()).isEqualTo(UPDATED_CLIENTES_FIBERTEL_LITE);
        assertThat(testEmprendimiento.getClientesFlow()).isEqualTo(UPDATED_CLIENTES_FLOW);
        assertThat(testEmprendimiento.getClientesCombo()).isEqualTo(UPDATED_CLIENTES_COMBO);
        assertThat(testEmprendimiento.getLineasVoz()).isEqualTo(UPDATED_LINEAS_VOZ);
        assertThat(testEmprendimiento.getMesesDeFinalizado()).isEqualTo(UPDATED_MESES_DE_FINALIZADO);
        assertThat(testEmprendimiento.getAltasBC()).isEqualTo(UPDATED_ALTAS_BC);
        assertThat(testEmprendimiento.getPenetracionVivLot()).isEqualTo(UPDATED_PENETRACION_VIV_LOT);
        assertThat(testEmprendimiento.getPenetracionBC()).isEqualTo(UPDATED_PENETRACION_BC);
        assertThat(testEmprendimiento.getDemanda1()).isEqualTo(UPDATED_DEMANDA_1);
        assertThat(testEmprendimiento.getDemanda2()).isEqualTo(UPDATED_DEMANDA_2);
        assertThat(testEmprendimiento.getDemanda3()).isEqualTo(UPDATED_DEMANDA_3);
        assertThat(testEmprendimiento.getDemanda4()).isEqualTo(UPDATED_DEMANDA_4);
        assertThat(testEmprendimiento.getLotes()).isEqualTo(UPDATED_LOTES);
        assertThat(testEmprendimiento.getViviendas()).isEqualTo(UPDATED_VIVIENDAS);
        assertThat(testEmprendimiento.getComProf()).isEqualTo(UPDATED_COM_PROF);
        assertThat(testEmprendimiento.getHabitaciones()).isEqualTo(UPDATED_HABITACIONES);
        assertThat(testEmprendimiento.getManzanas()).isEqualTo(UPDATED_MANZANAS);
        assertThat(testEmprendimiento.getDemanda()).isEqualTo(UPDATED_DEMANDA);
        assertThat(testEmprendimiento.getFechaDeRelevamiento()).isEqualTo(UPDATED_FECHA_DE_RELEVAMIENTO);
        assertThat(testEmprendimiento.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testEmprendimiento.getAnoPriorizacion()).isEqualTo(UPDATED_ANO_PRIORIZACION);
        assertThat(testEmprendimiento.getContratoOpen()).isEqualTo(UPDATED_CONTRATO_OPEN);
        assertThat(testEmprendimiento.getNegociacion()).isEqualTo(UPDATED_NEGOCIACION);
        assertThat(testEmprendimiento.getEstadoBC()).isEqualTo(UPDATED_ESTADO_BC);
        assertThat(testEmprendimiento.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testEmprendimiento.getCodigoDeFirma()).isEqualTo(UPDATED_CODIGO_DE_FIRMA);
        assertThat(testEmprendimiento.getFechaFirma()).isEqualTo(UPDATED_FECHA_FIRMA);
        assertThat(testEmprendimiento.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testEmprendimiento.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testEmprendimiento.getEstadoFirma()).isEqualTo(UPDATED_ESTADO_FIRMA);
    }

    @Test
    @Transactional
    void patchNonExistingEmprendimiento() throws Exception {
        int databaseSizeBeforeUpdate = emprendimientoRepository.findAll().size();
        emprendimiento.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmprendimientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, emprendimiento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emprendimiento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emprendimiento in the database
        List<Emprendimiento> emprendimientoList = emprendimientoRepository.findAll();
        assertThat(emprendimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmprendimiento() throws Exception {
        int databaseSizeBeforeUpdate = emprendimientoRepository.findAll().size();
        emprendimiento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmprendimientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emprendimiento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emprendimiento in the database
        List<Emprendimiento> emprendimientoList = emprendimientoRepository.findAll();
        assertThat(emprendimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmprendimiento() throws Exception {
        int databaseSizeBeforeUpdate = emprendimientoRepository.findAll().size();
        emprendimiento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmprendimientoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(emprendimiento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Emprendimiento in the database
        List<Emprendimiento> emprendimientoList = emprendimientoRepository.findAll();
        assertThat(emprendimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmprendimiento() throws Exception {
        // Initialize the database
        emprendimientoRepository.saveAndFlush(emprendimiento);

        int databaseSizeBeforeDelete = emprendimientoRepository.findAll().size();

        // Delete the emprendimiento
        restEmprendimientoMockMvc
            .perform(delete(ENTITY_API_URL_ID, emprendimiento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Emprendimiento> emprendimientoList = emprendimientoRepository.findAll();
        assertThat(emprendimientoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
