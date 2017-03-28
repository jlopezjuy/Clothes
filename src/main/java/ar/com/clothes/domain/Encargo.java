package ar.com.clothes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Encargo.
 */
@Entity
@Table(name = "encargo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Encargo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "importe_total")
    private Double importeTotal;

    @Column(name = "fecha_encargo")
    private LocalDate fechaEncargo;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    @Column(name = "detalle_vestido")
    private String detalleVestido;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "encargo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pago> pagos = new HashSet<>();

    @ManyToOne
    private ValorDominio tipoEcargo;

    @ManyToOne
    private ValorDominio estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getImporteTotal() {
        return importeTotal;
    }

    public Encargo importeTotal(Double importeTotal) {
        this.importeTotal = importeTotal;
        return this;
    }

    public void setImporteTotal(Double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public LocalDate getFechaEncargo() {
        return fechaEncargo;
    }

    public Encargo fechaEncargo(LocalDate fechaEncargo) {
        this.fechaEncargo = fechaEncargo;
        return this;
    }

    public void setFechaEncargo(LocalDate fechaEncargo) {
        this.fechaEncargo = fechaEncargo;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public Encargo fechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
        return this;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getDetalleVestido() {
        return detalleVestido;
    }

    public Encargo detalleVestido(String detalleVestido) {
        this.detalleVestido = detalleVestido;
        return this;
    }

    public void setDetalleVestido(String detalleVestido) {
        this.detalleVestido = detalleVestido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Encargo cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<Pago> getPagos() {
        return pagos;
    }

    public Encargo pagos(Set<Pago> pagos) {
        this.pagos = pagos;
        return this;
    }

    public Encargo addPago(Pago pago) {
        this.pagos.add(pago);
        pago.setEncargo(this);
        return this;
    }

    public Encargo removePago(Pago pago) {
        this.pagos.remove(pago);
        pago.setEncargo(null);
        return this;
    }

    public void setPagos(Set<Pago> pagos) {
        this.pagos = pagos;
    }

    public ValorDominio getTipoEcargo() {
        return tipoEcargo;
    }

    public Encargo tipoEcargo(ValorDominio valorDominio) {
        this.tipoEcargo = valorDominio;
        return this;
    }

    public void setTipoEcargo(ValorDominio valorDominio) {
        this.tipoEcargo = valorDominio;
    }

    public ValorDominio getEstado() {
        return estado;
    }

    public Encargo estado(ValorDominio valorDominio) {
        this.estado = valorDominio;
        return this;
    }

    public void setEstado(ValorDominio valorDominio) {
        this.estado = valorDominio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Encargo encargo = (Encargo) o;
        if (encargo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, encargo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Encargo{" +
            "id=" + id +
            ", importeTotal='" + importeTotal + "'" +
            ", fechaEncargo='" + fechaEncargo + "'" +
            ", fechaEntrega='" + fechaEntrega + "'" +
            ", detalleVestido='" + detalleVestido + "'" +
            '}';
    }
}
