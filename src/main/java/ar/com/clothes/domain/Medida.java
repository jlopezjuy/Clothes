package ar.com.clothes.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Medida.
 */
@Entity
@Table(name = "medida")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Medida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contorno_busto")
    private Double contornoBusto;

    @Column(name = "ancho_pecho")
    private Double anchoPecho;

    @Column(name = "alto_busto")
    private Double altoBusto;

    @Column(name = "bajo_busto")
    private Double bajoBusto;

    @Column(name = "altura_pinza")
    private Double alturaPinza;

    @Column(name = "separacion_busto")
    private Double separacionBusto;

    @Column(name = "talle_deltantero")
    private Double talleDeltantero;

    @Column(name = "talle_espalda")
    private Double talleEspalda;

    @Column(name = "largo_corset")
    private Double largoCorset;

    @Column(name = "costado")
    private Double costado;

    @Column(name = "hombro")
    private Double hombro;

    @Column(name = "ancho_hombro")
    private Double anchoHombro;

    @Column(name = "largo_manga")
    private Double largoManga;

    @Column(name = "sisa")
    private Double sisa;

    @Column(name = "cintura")
    private Double cintura;

    @Column(name = "ante_cadera")
    private Double anteCadera;

    @Column(name = "cadera")
    private Double cadera;

    @Column(name = "largo_pollera")
    private Double largoPollera;

    @Column(name = "fecha_medida")
    private LocalDate fechaMedida;

    @ManyToOne
    private Cliente cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getContornoBusto() {
        return contornoBusto;
    }

    public Medida contornoBusto(Double contornoBusto) {
        this.contornoBusto = contornoBusto;
        return this;
    }

    public void setContornoBusto(Double contornoBusto) {
        this.contornoBusto = contornoBusto;
    }

    public Double getAnchoPecho() {
        return anchoPecho;
    }

    public Medida anchoPecho(Double anchoPecho) {
        this.anchoPecho = anchoPecho;
        return this;
    }

    public void setAnchoPecho(Double anchoPecho) {
        this.anchoPecho = anchoPecho;
    }

    public Double getAltoBusto() {
        return altoBusto;
    }

    public Medida altoBusto(Double altoBusto) {
        this.altoBusto = altoBusto;
        return this;
    }

    public void setAltoBusto(Double altoBusto) {
        this.altoBusto = altoBusto;
    }

    public Double getBajoBusto() {
        return bajoBusto;
    }

    public Medida bajoBusto(Double bajoBusto) {
        this.bajoBusto = bajoBusto;
        return this;
    }

    public void setBajoBusto(Double bajoBusto) {
        this.bajoBusto = bajoBusto;
    }

    public Double getAlturaPinza() {
        return alturaPinza;
    }

    public Medida alturaPinza(Double alturaPinza) {
        this.alturaPinza = alturaPinza;
        return this;
    }

    public void setAlturaPinza(Double alturaPinza) {
        this.alturaPinza = alturaPinza;
    }

    public Double getSeparacionBusto() {
        return separacionBusto;
    }

    public Medida separacionBusto(Double separacionBusto) {
        this.separacionBusto = separacionBusto;
        return this;
    }

    public void setSeparacionBusto(Double separacionBusto) {
        this.separacionBusto = separacionBusto;
    }

    public Double getTalleDeltantero() {
        return talleDeltantero;
    }

    public Medida talleDeltantero(Double talleDeltantero) {
        this.talleDeltantero = talleDeltantero;
        return this;
    }

    public void setTalleDeltantero(Double talleDeltantero) {
        this.talleDeltantero = talleDeltantero;
    }

    public Double getTalleEspalda() {
        return talleEspalda;
    }

    public Medida talleEspalda(Double talleEspalda) {
        this.talleEspalda = talleEspalda;
        return this;
    }

    public void setTalleEspalda(Double talleEspalda) {
        this.talleEspalda = talleEspalda;
    }

    public Double getLargoCorset() {
        return largoCorset;
    }

    public Medida largoCorset(Double largoCorset) {
        this.largoCorset = largoCorset;
        return this;
    }

    public void setLargoCorset(Double largoCorset) {
        this.largoCorset = largoCorset;
    }

    public Double getCostado() {
        return costado;
    }

    public Medida costado(Double costado) {
        this.costado = costado;
        return this;
    }

    public void setCostado(Double costado) {
        this.costado = costado;
    }

    public Double getHombro() {
        return hombro;
    }

    public Medida hombro(Double hombro) {
        this.hombro = hombro;
        return this;
    }

    public void setHombro(Double hombro) {
        this.hombro = hombro;
    }

    public Double getAnchoHombro() {
        return anchoHombro;
    }

    public Medida anchoHombro(Double anchoHombro) {
        this.anchoHombro = anchoHombro;
        return this;
    }

    public void setAnchoHombro(Double anchoHombro) {
        this.anchoHombro = anchoHombro;
    }

    public Double getLargoManga() {
        return largoManga;
    }

    public Medida largoManga(Double largoManga) {
        this.largoManga = largoManga;
        return this;
    }

    public void setLargoManga(Double largoManga) {
        this.largoManga = largoManga;
    }

    public Double getSisa() {
        return sisa;
    }

    public Medida sisa(Double sisa) {
        this.sisa = sisa;
        return this;
    }

    public void setSisa(Double sisa) {
        this.sisa = sisa;
    }

    public Double getCintura() {
        return cintura;
    }

    public Medida cintura(Double cintura) {
        this.cintura = cintura;
        return this;
    }

    public void setCintura(Double cintura) {
        this.cintura = cintura;
    }

    public Double getAnteCadera() {
        return anteCadera;
    }

    public Medida anteCadera(Double anteCadera) {
        this.anteCadera = anteCadera;
        return this;
    }

    public void setAnteCadera(Double anteCadera) {
        this.anteCadera = anteCadera;
    }

    public Double getCadera() {
        return cadera;
    }

    public Medida cadera(Double cadera) {
        this.cadera = cadera;
        return this;
    }

    public void setCadera(Double cadera) {
        this.cadera = cadera;
    }

    public Double getLargoPollera() {
        return largoPollera;
    }

    public Medida largoPollera(Double largoPollera) {
        this.largoPollera = largoPollera;
        return this;
    }

    public void setLargoPollera(Double largoPollera) {
        this.largoPollera = largoPollera;
    }

    public LocalDate getFechaMedida() {
        return fechaMedida;
    }

    public Medida fechaMedida(LocalDate fechaMedida) {
        this.fechaMedida = fechaMedida;
        return this;
    }

    public void setFechaMedida(LocalDate fechaMedida) {
        this.fechaMedida = fechaMedida;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Medida cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Medida medida = (Medida) o;
        if (medida.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, medida.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Medida{" +
            "id=" + id +
            ", contornoBusto='" + contornoBusto + "'" +
            ", anchoPecho='" + anchoPecho + "'" +
            ", altoBusto='" + altoBusto + "'" +
            ", bajoBusto='" + bajoBusto + "'" +
            ", alturaPinza='" + alturaPinza + "'" +
            ", separacionBusto='" + separacionBusto + "'" +
            ", talleDeltantero='" + talleDeltantero + "'" +
            ", talleEspalda='" + talleEspalda + "'" +
            ", largoCorset='" + largoCorset + "'" +
            ", costado='" + costado + "'" +
            ", hombro='" + hombro + "'" +
            ", anchoHombro='" + anchoHombro + "'" +
            ", largoManga='" + largoManga + "'" +
            ", sisa='" + sisa + "'" +
            ", cintura='" + cintura + "'" +
            ", anteCadera='" + anteCadera + "'" +
            ", cadera='" + cadera + "'" +
            ", largoPollera='" + largoPollera + "'" +
            ", fechaMedida='" + fechaMedida + "'" +
            '}';
    }
}
