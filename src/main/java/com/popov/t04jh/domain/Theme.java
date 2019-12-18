package com.popov.t04jh.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Theme.
 */
@Entity
@Table(name = "theme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Theme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "theme_name", nullable = false)
    private String themeName;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "theme_objectif",
               joinColumns = @JoinColumn(name = "theme_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "objectif_id", referencedColumnName = "id"))
    private Set<Objectif> objectifs = new HashSet<>();

    @ManyToMany(mappedBy = "locations")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<FicheSeance> ficheSeances = new HashSet<>();

    @ManyToMany(mappedBy = "documents")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Programmation> programmations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThemeName() {
        return themeName;
    }

    public Theme themeName(String themeName) {
        this.themeName = themeName;
        return this;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getDescription() {
        return description;
    }

    public Theme description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Objectif> getObjectifs() {
        return objectifs;
    }

    public Theme objectifs(Set<Objectif> objectifs) {
        this.objectifs = objectifs;
        return this;
    }

    public Theme addObjectif(Objectif objectif) {
        this.objectifs.add(objectif);
        objectif.getThemes().add(this);
        return this;
    }

    public Theme removeObjectif(Objectif objectif) {
        this.objectifs.remove(objectif);
        objectif.getThemes().remove(this);
        return this;
    }

    public void setObjectifs(Set<Objectif> objectifs) {
        this.objectifs = objectifs;
    }

    public Set<FicheSeance> getFicheSeances() {
        return ficheSeances;
    }

    public Theme ficheSeances(Set<FicheSeance> ficheSeances) {
        this.ficheSeances = ficheSeances;
        return this;
    }

    public Theme addFicheSeance(FicheSeance ficheSeance) {
        this.ficheSeances.add(ficheSeance);
        ficheSeance.getLocations().add(this);
        return this;
    }

    public Theme removeFicheSeance(FicheSeance ficheSeance) {
        this.ficheSeances.remove(ficheSeance);
        ficheSeance.getLocations().remove(this);
        return this;
    }

    public void setFicheSeances(Set<FicheSeance> ficheSeances) {
        this.ficheSeances = ficheSeances;
    }

    public Set<Programmation> getProgrammations() {
        return programmations;
    }

    public Theme programmations(Set<Programmation> programmations) {
        this.programmations = programmations;
        return this;
    }

    public Theme addProgrammation(Programmation programmation) {
        this.programmations.add(programmation);
        programmation.getDocuments().add(this);
        return this;
    }

    public Theme removeProgrammation(Programmation programmation) {
        this.programmations.remove(programmation);
        programmation.getDocuments().remove(this);
        return this;
    }

    public void setProgrammations(Set<Programmation> programmations) {
        this.programmations = programmations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Theme)) {
            return false;
        }
        return id != null && id.equals(((Theme) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Theme{" +
            "id=" + getId() +
            ", themeName='" + getThemeName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
