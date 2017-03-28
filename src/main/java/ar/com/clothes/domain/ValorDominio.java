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
 * A ValorDominio.
 */
@Entity
@Table(name = "valor_dominio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ValorDominio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    private Dominio dominio;

    @OneToMany(mappedBy = "tipoEcargo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Encargo> tipoEventos = new HashSet<>();

    @OneToMany(mappedBy = "estado")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Encargo> estados = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ValorDominio descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Dominio getDominio() {
        return dominio;
    }

    public ValorDominio dominio(Dominio dominio) {
        this.dominio = dominio;
        return this;
    }

    public void setDominio(Dominio dominio) {
        this.dominio = dominio;
    }

    public Set<Encargo> getTipoEventos() {
        return tipoEventos;
    }

    public ValorDominio tipoEventos(Set<Encargo> encargos) {
        this.tipoEventos = encargos;
        return this;
    }

    public ValorDominio addTipoEvento(Encargo encargo) {
        this.tipoEventos.add(encargo);
        encargo.setTipoEcargo(this);
        return this;
    }

    public ValorDominio removeTipoEvento(Encargo encargo) {
        this.tipoEventos.remove(encargo);
        encargo.setTipoEcargo(null);
        return this;
    }

    public void setTipoEventos(Set<Encargo> encargos) {
        this.tipoEventos = encargos;
    }

    public Set<Encargo> getEstados() {
        return estados;
    }

    public ValorDominio estados(Set<Encargo> encargos) {
        this.estados = encargos;
        return this;
    }

    public ValorDominio addEstado(Encargo encargo) {
        this.estados.add(encargo);
        encargo.setEstado(this);
        return this;
    }

    public ValorDominio removeEstado(Encargo encargo) {
        this.estados.remove(encargo);
        encargo.setEstado(null);
        return this;
    }

    public void setEstados(Set<Encargo> encargos) {
        this.estados = encargos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ValorDominio valorDominio = (ValorDominio) o;
        if (valorDominio.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, valorDominio.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ValorDominio{" +
            "id=" + id +
            ", descripcion='" + descripcion + "'" +
            '}';
    }
}
