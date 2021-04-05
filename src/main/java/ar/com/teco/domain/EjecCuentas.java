package ar.com.teco.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A EjecCuentas.
 */
@Entity
@Table(name = "ejec_cuentas")
public class EjecCuentas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "celular")
    private String celular;

    @Column(name = "mail")
    private String mail;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "repcom_1")
    private String repcom1;

    @Column(name = "repcom_2")
    private String repcom2;

    @ManyToOne
    private Segmento segmento;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EjecCuentas id(Long id) {
        this.id = id;
        return this;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public EjecCuentas telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getApellido() {
        return this.apellido;
    }

    public EjecCuentas apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCelular() {
        return this.celular;
    }

    public EjecCuentas celular(String celular) {
        this.celular = celular;
        return this;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getMail() {
        return this.mail;
    }

    public EjecCuentas mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNombre() {
        return this.nombre;
    }

    public EjecCuentas nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRepcom1() {
        return this.repcom1;
    }

    public EjecCuentas repcom1(String repcom1) {
        this.repcom1 = repcom1;
        return this;
    }

    public void setRepcom1(String repcom1) {
        this.repcom1 = repcom1;
    }

    public String getRepcom2() {
        return this.repcom2;
    }

    public EjecCuentas repcom2(String repcom2) {
        this.repcom2 = repcom2;
        return this;
    }

    public void setRepcom2(String repcom2) {
        this.repcom2 = repcom2;
    }

    public Segmento getSegmento() {
        return this.segmento;
    }

    public EjecCuentas segmento(Segmento segmento) {
        this.setSegmento(segmento);
        return this;
    }

    public void setSegmento(Segmento segmento) {
        this.segmento = segmento;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EjecCuentas)) {
            return false;
        }
        return id != null && id.equals(((EjecCuentas) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EjecCuentas{" +
            "id=" + getId() +
            ", telefono='" + getTelefono() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", celular='" + getCelular() + "'" +
            ", mail='" + getMail() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", repcom1='" + getRepcom1() + "'" +
            ", repcom2='" + getRepcom2() + "'" +
            "}";
    }
}
