package ar.com.teco.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A GrupoAlarma.
 */
@Entity
@Table(name = "grupo_alarma")
public class GrupoAlarma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nombre_grupo", nullable = false)
    private String nombreGrupo;

    @Column(name = "alarma_tiempo")
    private Long alarmaTiempo;

    @Column(name = "alarma_sva")
    private Long alarmaSva;

    @Column(name = "alarma_businesscase")
    private Long alarmaBusinesscase;

    @ManyToOne
    private GrupoEmprendimiento grupoEmprendimiento;

    @ManyToOne
    private GrupoUsuario grupoUsuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GrupoAlarma id(Long id) {
        this.id = id;
        return this;
    }

    public String getNombreGrupo() {
        return this.nombreGrupo;
    }

    public GrupoAlarma nombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
        return this;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public Long getAlarmaTiempo() {
        return this.alarmaTiempo;
    }

    public GrupoAlarma alarmaTiempo(Long alarmaTiempo) {
        this.alarmaTiempo = alarmaTiempo;
        return this;
    }

    public void setAlarmaTiempo(Long alarmaTiempo) {
        this.alarmaTiempo = alarmaTiempo;
    }

    public Long getAlarmaSva() {
        return this.alarmaSva;
    }

    public GrupoAlarma alarmaSva(Long alarmaSva) {
        this.alarmaSva = alarmaSva;
        return this;
    }

    public void setAlarmaSva(Long alarmaSva) {
        this.alarmaSva = alarmaSva;
    }

    public Long getAlarmaBusinesscase() {
        return this.alarmaBusinesscase;
    }

    public GrupoAlarma alarmaBusinesscase(Long alarmaBusinesscase) {
        this.alarmaBusinesscase = alarmaBusinesscase;
        return this;
    }

    public void setAlarmaBusinesscase(Long alarmaBusinesscase) {
        this.alarmaBusinesscase = alarmaBusinesscase;
    }

    public GrupoEmprendimiento getGrupoEmprendimiento() {
        return this.grupoEmprendimiento;
    }

    public GrupoAlarma grupoEmprendimiento(GrupoEmprendimiento grupoEmprendimiento) {
        this.setGrupoEmprendimiento(grupoEmprendimiento);
        return this;
    }

    public void setGrupoEmprendimiento(GrupoEmprendimiento grupoEmprendimiento) {
        this.grupoEmprendimiento = grupoEmprendimiento;
    }

    public GrupoUsuario getGrupoUsuario() {
        return this.grupoUsuario;
    }

    public GrupoAlarma grupoUsuario(GrupoUsuario grupoUsuario) {
        this.setGrupoUsuario(grupoUsuario);
        return this;
    }

    public void setGrupoUsuario(GrupoUsuario grupoUsuario) {
        this.grupoUsuario = grupoUsuario;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupoAlarma)) {
            return false;
        }
        return id != null && id.equals(((GrupoAlarma) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GrupoAlarma{" +
            "id=" + getId() +
            ", nombreGrupo='" + getNombreGrupo() + "'" +
            ", alarmaTiempo=" + getAlarmaTiempo() +
            ", alarmaSva=" + getAlarmaSva() +
            ", alarmaBusinesscase=" + getAlarmaBusinesscase() +
            "}";
    }
}
