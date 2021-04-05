package ar.com.teco.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TipoEmp.
 */
@Entity
@Table(name = "tipo_emp")
public class TipoEmp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "valor")
    private String valor;

    @ManyToOne
    private MasterTipoEmp masterTipoEmp;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoEmp id(Long id) {
        this.id = id;
        return this;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public TipoEmp descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValor() {
        return this.valor;
    }

    public TipoEmp valor(String valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public MasterTipoEmp getMasterTipoEmp() {
        return this.masterTipoEmp;
    }

    public TipoEmp masterTipoEmp(MasterTipoEmp masterTipoEmp) {
        this.setMasterTipoEmp(masterTipoEmp);
        return this;
    }

    public void setMasterTipoEmp(MasterTipoEmp masterTipoEmp) {
        this.masterTipoEmp = masterTipoEmp;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoEmp)) {
            return false;
        }
        return id != null && id.equals(((TipoEmp) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoEmp{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", valor='" + getValor() + "'" +
            "}";
    }
}
