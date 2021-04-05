package ar.com.teco.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A MasterTipoEmp.
 */
@Entity
@Table(name = "master_tipo_emp")
public class MasterTipoEmp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "sobre_lote")
    private String sobreLote;

    @Column(name = "sobre_vivienda")
    private String sobreVivienda;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MasterTipoEmp id(Long id) {
        this.id = id;
        return this;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public MasterTipoEmp descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSobreLote() {
        return this.sobreLote;
    }

    public MasterTipoEmp sobreLote(String sobreLote) {
        this.sobreLote = sobreLote;
        return this;
    }

    public void setSobreLote(String sobreLote) {
        this.sobreLote = sobreLote;
    }

    public String getSobreVivienda() {
        return this.sobreVivienda;
    }

    public MasterTipoEmp sobreVivienda(String sobreVivienda) {
        this.sobreVivienda = sobreVivienda;
        return this;
    }

    public void setSobreVivienda(String sobreVivienda) {
        this.sobreVivienda = sobreVivienda;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MasterTipoEmp)) {
            return false;
        }
        return id != null && id.equals(((MasterTipoEmp) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MasterTipoEmp{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", sobreLote='" + getSobreLote() + "'" +
            ", sobreVivienda='" + getSobreVivienda() + "'" +
            "}";
    }
}
