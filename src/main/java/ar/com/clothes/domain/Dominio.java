package ar.com.clothes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Dominio.
 */
@Entity
@Table(name = "dominio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dominio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "dominio")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ValorDominio> valorDominios = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Dominio descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<ValorDominio> getValorDominios() {
        return valorDominios;
    }

    public Dominio valorDominios(Set<ValorDominio> valorDominios) {
        this.valorDominios = valorDominios;
        return this;
    }

    public Dominio addValorDominio(ValorDominio valorDominio) {
        this.valorDominios.add(valorDominio);
        valorDominio.setDominio(this);
        return this;
    }

    public Dominio removeValorDominio(ValorDominio valorDominio) {
        this.valorDominios.remove(valorDominio);
        valorDominio.setDominio(null);
        return this;
    }

    public void setValorDominios(Set<ValorDominio> valorDominios) {
        this.valorDominios = valorDominios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dominio dominio = (Dominio) o;
        if (dominio.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, dominio.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Dominio{" +
            "id=" + id +
            ", descripcion='" + descripcion + "'" +
            '}';
    }
}
