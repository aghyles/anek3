package com.popov.t04jh.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Presence.
 */
@Entity
@Table(name = "presence")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Presence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_nageur_abs")
    private String idNageurAbs;

    @ManyToMany(mappedBy = "presences")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<FicheSeance> nageurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdNageurAbs() {
        return idNageurAbs;
    }

    public Presence idNageurAbs(String idNageurAbs) {
        this.idNageurAbs = idNageurAbs;
        return this;
    }

    public void setIdNageurAbs(String idNageurAbs) {
        this.idNageurAbs = idNageurAbs;
    }

    public Set<FicheSeance> getNageurs() {
        return nageurs;
    }

    public Presence nageurs(Set<FicheSeance> ficheSeances) {
        this.nageurs = ficheSeances;
        return this;
    }

    public Presence addNageur(FicheSeance ficheSeance) {
        this.nageurs.add(ficheSeance);
        ficheSeance.getPresences().add(this);
        return this;
    }

    public Presence removeNageur(FicheSeance ficheSeance) {
        this.nageurs.remove(ficheSeance);
        ficheSeance.getPresences().remove(this);
        return this;
    }

    public void setNageurs(Set<FicheSeance> ficheSeances) {
        this.nageurs = ficheSeances;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Presence)) {
            return false;
        }
        return id != null && id.equals(((Presence) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Presence{" +
            "id=" + getId() +
            ", idNageurAbs='" + getIdNageurAbs() + "'" +
            "}";
    }
}
