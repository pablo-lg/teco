package ar.com.teco.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Emprendimiento.
 */
@Entity
@Table(name = "emprendimiento")
public class Emprendimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "contacto")
    private String contacto;

    @Column(name = "fecha_fin_obra")
    private LocalDate fechaFinObra;

    @Column(name = "elementos_de_red")
    private String elementosDeRed;

    @Column(name = "clientes_catv")
    private String clientesCatv;

    @Column(name = "clientes_fibertel")
    private String clientesFibertel;

    @Column(name = "clientes_fibertel_lite")
    private String clientesFibertelLite;

    @Column(name = "clientes_flow")
    private String clientesFlow;

    @Column(name = "clientes_combo")
    private String clientesCombo;

    @Column(name = "lineas_voz")
    private String lineasVoz;

    @Column(name = "meses_de_finalizado")
    private String mesesDeFinalizado;

    @Column(name = "altas_bc")
    private String altasBC;

    @Column(name = "penetracion_viv_lot")
    private String penetracionVivLot;

    @Column(name = "penetracion_bc")
    private String penetracionBC;

    @Column(name = "demanda_1")
    private String demanda1;

    @Column(name = "demanda_2")
    private String demanda2;

    @Column(name = "demanda_3")
    private String demanda3;

    @Column(name = "demanda_4")
    private String demanda4;

    @Column(name = "lotes")
    private String lotes;

    @Column(name = "viviendas")
    private String viviendas;

    @Column(name = "com_prof")
    private String comProf;

    @Column(name = "habitaciones")
    private String habitaciones;

    @Column(name = "manzanas")
    private String manzanas;

    @Column(name = "demanda")
    private String demanda;

    @Column(name = "fecha_de_relevamiento")
    private LocalDate fechaDeRelevamiento;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "ano_priorizacion")
    private LocalDate anoPriorizacion;

    @Column(name = "contrato_open")
    private String contratoOpen;

    @Column(name = "negociacion")
    private Boolean negociacion;

    @Column(name = "estado_bc")
    private String estadoBC;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "codigo_de_firma")
    private String codigoDeFirma;

    @Column(name = "fecha_firma")
    private LocalDate fechaFirma;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "estado_firma")
    private String estadoFirma;

    @ManyToOne
    private GrupoEmprendimiento grupoEmprendimiento;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tipoObra" }, allowSetters = true)
    private Obra obra;

    @ManyToOne
    @JsonIgnoreProperties(value = { "segmento" }, allowSetters = true)
    private TipoObra tipoObra;

    @ManyToOne
    @JsonIgnoreProperties(value = { "masterTipoEmp" }, allowSetters = true)
    private TipoEmp tipoEmp;

    @ManyToOne
    private Estado estado;

    @ManyToOne
    private Competencia competencia;

    @ManyToOne
    private Despliegue despliegue;

    @ManyToOne
    private NSE nSE;

    @ManyToOne
    private Segmento segmento;

    @ManyToOne
    private Tecnologia tecnologia;

    @ManyToOne
    @JsonIgnoreProperties(value = { "segmento" }, allowSetters = true)
    private EjecCuentas ejecCuentas;

    @ManyToOne
    private Direccion direccion;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Emprendimiento id(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Emprendimiento nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return this.contacto;
    }

    public Emprendimiento contacto(String contacto) {
        this.contacto = contacto;
        return this;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public LocalDate getFechaFinObra() {
        return this.fechaFinObra;
    }

    public Emprendimiento fechaFinObra(LocalDate fechaFinObra) {
        this.fechaFinObra = fechaFinObra;
        return this;
    }

    public void setFechaFinObra(LocalDate fechaFinObra) {
        this.fechaFinObra = fechaFinObra;
    }

    public String getElementosDeRed() {
        return this.elementosDeRed;
    }

    public Emprendimiento elementosDeRed(String elementosDeRed) {
        this.elementosDeRed = elementosDeRed;
        return this;
    }

    public void setElementosDeRed(String elementosDeRed) {
        this.elementosDeRed = elementosDeRed;
    }

    public String getClientesCatv() {
        return this.clientesCatv;
    }

    public Emprendimiento clientesCatv(String clientesCatv) {
        this.clientesCatv = clientesCatv;
        return this;
    }

    public void setClientesCatv(String clientesCatv) {
        this.clientesCatv = clientesCatv;
    }

    public String getClientesFibertel() {
        return this.clientesFibertel;
    }

    public Emprendimiento clientesFibertel(String clientesFibertel) {
        this.clientesFibertel = clientesFibertel;
        return this;
    }

    public void setClientesFibertel(String clientesFibertel) {
        this.clientesFibertel = clientesFibertel;
    }

    public String getClientesFibertelLite() {
        return this.clientesFibertelLite;
    }

    public Emprendimiento clientesFibertelLite(String clientesFibertelLite) {
        this.clientesFibertelLite = clientesFibertelLite;
        return this;
    }

    public void setClientesFibertelLite(String clientesFibertelLite) {
        this.clientesFibertelLite = clientesFibertelLite;
    }

    public String getClientesFlow() {
        return this.clientesFlow;
    }

    public Emprendimiento clientesFlow(String clientesFlow) {
        this.clientesFlow = clientesFlow;
        return this;
    }

    public void setClientesFlow(String clientesFlow) {
        this.clientesFlow = clientesFlow;
    }

    public String getClientesCombo() {
        return this.clientesCombo;
    }

    public Emprendimiento clientesCombo(String clientesCombo) {
        this.clientesCombo = clientesCombo;
        return this;
    }

    public void setClientesCombo(String clientesCombo) {
        this.clientesCombo = clientesCombo;
    }

    public String getLineasVoz() {
        return this.lineasVoz;
    }

    public Emprendimiento lineasVoz(String lineasVoz) {
        this.lineasVoz = lineasVoz;
        return this;
    }

    public void setLineasVoz(String lineasVoz) {
        this.lineasVoz = lineasVoz;
    }

    public String getMesesDeFinalizado() {
        return this.mesesDeFinalizado;
    }

    public Emprendimiento mesesDeFinalizado(String mesesDeFinalizado) {
        this.mesesDeFinalizado = mesesDeFinalizado;
        return this;
    }

    public void setMesesDeFinalizado(String mesesDeFinalizado) {
        this.mesesDeFinalizado = mesesDeFinalizado;
    }

    public String getAltasBC() {
        return this.altasBC;
    }

    public Emprendimiento altasBC(String altasBC) {
        this.altasBC = altasBC;
        return this;
    }

    public void setAltasBC(String altasBC) {
        this.altasBC = altasBC;
    }

    public String getPenetracionVivLot() {
        return this.penetracionVivLot;
    }

    public Emprendimiento penetracionVivLot(String penetracionVivLot) {
        this.penetracionVivLot = penetracionVivLot;
        return this;
    }

    public void setPenetracionVivLot(String penetracionVivLot) {
        this.penetracionVivLot = penetracionVivLot;
    }

    public String getPenetracionBC() {
        return this.penetracionBC;
    }

    public Emprendimiento penetracionBC(String penetracionBC) {
        this.penetracionBC = penetracionBC;
        return this;
    }

    public void setPenetracionBC(String penetracionBC) {
        this.penetracionBC = penetracionBC;
    }

    public String getDemanda1() {
        return this.demanda1;
    }

    public Emprendimiento demanda1(String demanda1) {
        this.demanda1 = demanda1;
        return this;
    }

    public void setDemanda1(String demanda1) {
        this.demanda1 = demanda1;
    }

    public String getDemanda2() {
        return this.demanda2;
    }

    public Emprendimiento demanda2(String demanda2) {
        this.demanda2 = demanda2;
        return this;
    }

    public void setDemanda2(String demanda2) {
        this.demanda2 = demanda2;
    }

    public String getDemanda3() {
        return this.demanda3;
    }

    public Emprendimiento demanda3(String demanda3) {
        this.demanda3 = demanda3;
        return this;
    }

    public void setDemanda3(String demanda3) {
        this.demanda3 = demanda3;
    }

    public String getDemanda4() {
        return this.demanda4;
    }

    public Emprendimiento demanda4(String demanda4) {
        this.demanda4 = demanda4;
        return this;
    }

    public void setDemanda4(String demanda4) {
        this.demanda4 = demanda4;
    }

    public String getLotes() {
        return this.lotes;
    }

    public Emprendimiento lotes(String lotes) {
        this.lotes = lotes;
        return this;
    }

    public void setLotes(String lotes) {
        this.lotes = lotes;
    }

    public String getViviendas() {
        return this.viviendas;
    }

    public Emprendimiento viviendas(String viviendas) {
        this.viviendas = viviendas;
        return this;
    }

    public void setViviendas(String viviendas) {
        this.viviendas = viviendas;
    }

    public String getComProf() {
        return this.comProf;
    }

    public Emprendimiento comProf(String comProf) {
        this.comProf = comProf;
        return this;
    }

    public void setComProf(String comProf) {
        this.comProf = comProf;
    }

    public String getHabitaciones() {
        return this.habitaciones;
    }

    public Emprendimiento habitaciones(String habitaciones) {
        this.habitaciones = habitaciones;
        return this;
    }

    public void setHabitaciones(String habitaciones) {
        this.habitaciones = habitaciones;
    }

    public String getManzanas() {
        return this.manzanas;
    }

    public Emprendimiento manzanas(String manzanas) {
        this.manzanas = manzanas;
        return this;
    }

    public void setManzanas(String manzanas) {
        this.manzanas = manzanas;
    }

    public String getDemanda() {
        return this.demanda;
    }

    public Emprendimiento demanda(String demanda) {
        this.demanda = demanda;
        return this;
    }

    public void setDemanda(String demanda) {
        this.demanda = demanda;
    }

    public LocalDate getFechaDeRelevamiento() {
        return this.fechaDeRelevamiento;
    }

    public Emprendimiento fechaDeRelevamiento(LocalDate fechaDeRelevamiento) {
        this.fechaDeRelevamiento = fechaDeRelevamiento;
        return this;
    }

    public void setFechaDeRelevamiento(LocalDate fechaDeRelevamiento) {
        this.fechaDeRelevamiento = fechaDeRelevamiento;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Emprendimiento telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getAnoPriorizacion() {
        return this.anoPriorizacion;
    }

    public Emprendimiento anoPriorizacion(LocalDate anoPriorizacion) {
        this.anoPriorizacion = anoPriorizacion;
        return this;
    }

    public void setAnoPriorizacion(LocalDate anoPriorizacion) {
        this.anoPriorizacion = anoPriorizacion;
    }

    public String getContratoOpen() {
        return this.contratoOpen;
    }

    public Emprendimiento contratoOpen(String contratoOpen) {
        this.contratoOpen = contratoOpen;
        return this;
    }

    public void setContratoOpen(String contratoOpen) {
        this.contratoOpen = contratoOpen;
    }

    public Boolean getNegociacion() {
        return this.negociacion;
    }

    public Emprendimiento negociacion(Boolean negociacion) {
        this.negociacion = negociacion;
        return this;
    }

    public void setNegociacion(Boolean negociacion) {
        this.negociacion = negociacion;
    }

    public String getEstadoBC() {
        return this.estadoBC;
    }

    public Emprendimiento estadoBC(String estadoBC) {
        this.estadoBC = estadoBC;
        return this;
    }

    public void setEstadoBC(String estadoBC) {
        this.estadoBC = estadoBC;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Emprendimiento fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCodigoDeFirma() {
        return this.codigoDeFirma;
    }

    public Emprendimiento codigoDeFirma(String codigoDeFirma) {
        this.codigoDeFirma = codigoDeFirma;
        return this;
    }

    public void setCodigoDeFirma(String codigoDeFirma) {
        this.codigoDeFirma = codigoDeFirma;
    }

    public LocalDate getFechaFirma() {
        return this.fechaFirma;
    }

    public Emprendimiento fechaFirma(LocalDate fechaFirma) {
        this.fechaFirma = fechaFirma;
        return this;
    }

    public void setFechaFirma(LocalDate fechaFirma) {
        this.fechaFirma = fechaFirma;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public Emprendimiento observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getComentario() {
        return this.comentario;
    }

    public Emprendimiento comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getEstadoFirma() {
        return this.estadoFirma;
    }

    public Emprendimiento estadoFirma(String estadoFirma) {
        this.estadoFirma = estadoFirma;
        return this;
    }

    public void setEstadoFirma(String estadoFirma) {
        this.estadoFirma = estadoFirma;
    }

    public GrupoEmprendimiento getGrupoEmprendimiento() {
        return this.grupoEmprendimiento;
    }

    public Emprendimiento grupoEmprendimiento(GrupoEmprendimiento grupoEmprendimiento) {
        this.setGrupoEmprendimiento(grupoEmprendimiento);
        return this;
    }

    public void setGrupoEmprendimiento(GrupoEmprendimiento grupoEmprendimiento) {
        this.grupoEmprendimiento = grupoEmprendimiento;
    }

    public Obra getObra() {
        return this.obra;
    }

    public Emprendimiento obra(Obra obra) {
        this.setObra(obra);
        return this;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public TipoObra getTipoObra() {
        return this.tipoObra;
    }

    public Emprendimiento tipoObra(TipoObra tipoObra) {
        this.setTipoObra(tipoObra);
        return this;
    }

    public void setTipoObra(TipoObra tipoObra) {
        this.tipoObra = tipoObra;
    }

    public TipoEmp getTipoEmp() {
        return this.tipoEmp;
    }

    public Emprendimiento tipoEmp(TipoEmp tipoEmp) {
        this.setTipoEmp(tipoEmp);
        return this;
    }

    public void setTipoEmp(TipoEmp tipoEmp) {
        this.tipoEmp = tipoEmp;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public Emprendimiento estado(Estado estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Competencia getCompetencia() {
        return this.competencia;
    }

    public Emprendimiento competencia(Competencia competencia) {
        this.setCompetencia(competencia);
        return this;
    }

    public void setCompetencia(Competencia competencia) {
        this.competencia = competencia;
    }

    public Despliegue getDespliegue() {
        return this.despliegue;
    }

    public Emprendimiento despliegue(Despliegue despliegue) {
        this.setDespliegue(despliegue);
        return this;
    }

    public void setDespliegue(Despliegue despliegue) {
        this.despliegue = despliegue;
    }

    public NSE getNSE() {
        return this.nSE;
    }

    public Emprendimiento nSE(NSE nSE) {
        this.setNSE(nSE);
        return this;
    }

    public void setNSE(NSE nSE) {
        this.nSE = nSE;
    }

    public Segmento getSegmento() {
        return this.segmento;
    }

    public Emprendimiento segmento(Segmento segmento) {
        this.setSegmento(segmento);
        return this;
    }

    public void setSegmento(Segmento segmento) {
        this.segmento = segmento;
    }

    public Tecnologia getTecnologia() {
        return this.tecnologia;
    }

    public Emprendimiento tecnologia(Tecnologia tecnologia) {
        this.setTecnologia(tecnologia);
        return this;
    }

    public void setTecnologia(Tecnologia tecnologia) {
        this.tecnologia = tecnologia;
    }

    public EjecCuentas getEjecCuentas() {
        return this.ejecCuentas;
    }

    public Emprendimiento ejecCuentas(EjecCuentas ejecCuentas) {
        this.setEjecCuentas(ejecCuentas);
        return this;
    }

    public void setEjecCuentas(EjecCuentas ejecCuentas) {
        this.ejecCuentas = ejecCuentas;
    }

    public Direccion getDireccion() {
        return this.direccion;
    }

    public Emprendimiento direccion(Direccion direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Emprendimiento)) {
            return false;
        }
        return id != null && id.equals(((Emprendimiento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Emprendimiento{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", contacto='" + getContacto() + "'" +
            ", fechaFinObra='" + getFechaFinObra() + "'" +
            ", elementosDeRed='" + getElementosDeRed() + "'" +
            ", clientesCatv='" + getClientesCatv() + "'" +
            ", clientesFibertel='" + getClientesFibertel() + "'" +
            ", clientesFibertelLite='" + getClientesFibertelLite() + "'" +
            ", clientesFlow='" + getClientesFlow() + "'" +
            ", clientesCombo='" + getClientesCombo() + "'" +
            ", lineasVoz='" + getLineasVoz() + "'" +
            ", mesesDeFinalizado='" + getMesesDeFinalizado() + "'" +
            ", altasBC='" + getAltasBC() + "'" +
            ", penetracionVivLot='" + getPenetracionVivLot() + "'" +
            ", penetracionBC='" + getPenetracionBC() + "'" +
            ", demanda1='" + getDemanda1() + "'" +
            ", demanda2='" + getDemanda2() + "'" +
            ", demanda3='" + getDemanda3() + "'" +
            ", demanda4='" + getDemanda4() + "'" +
            ", lotes='" + getLotes() + "'" +
            ", viviendas='" + getViviendas() + "'" +
            ", comProf='" + getComProf() + "'" +
            ", habitaciones='" + getHabitaciones() + "'" +
            ", manzanas='" + getManzanas() + "'" +
            ", demanda='" + getDemanda() + "'" +
            ", fechaDeRelevamiento='" + getFechaDeRelevamiento() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", anoPriorizacion='" + getAnoPriorizacion() + "'" +
            ", contratoOpen='" + getContratoOpen() + "'" +
            ", negociacion='" + getNegociacion() + "'" +
            ", estadoBC='" + getEstadoBC() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", codigoDeFirma='" + getCodigoDeFirma() + "'" +
            ", fechaFirma='" + getFechaFirma() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", comentario='" + getComentario() + "'" +
            ", estadoFirma='" + getEstadoFirma() + "'" +
            "}";
    }
}
