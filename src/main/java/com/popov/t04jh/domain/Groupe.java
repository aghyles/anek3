package com.popov.t04jh.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.popov.t04jh.domain.enumeration.Category;

/**
 * si ya changement d'entraineur il faut cree un nouveau groupe
 */
@ApiModel(description = "si ya changement d'entraineur il faut cree un nouveau groupe")
@Entity
@Table(name = "groupe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Groupe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "groupe_name", nullable = false)
    private String groupeName;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "saison")
    private String saison;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @OneToMany(mappedBy = "groupe")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Nageurs> nageurs = new HashSet<>();

    @OneToMany(mappedBy = "groupe")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FicheSeance> ficheSeances = new HashSet<>();

    @OneToMany(mappedBy = "groupe")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Programmation> programmations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupeName() {
        return groupeName;
    }

    public Groupe groupeName(String groupeName) {
        this.groupeName = groupeName;
        return this;
    }

    public void setGroupeName(String groupeName) {
        this.groupeName = groupeName;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Groupe startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public Groupe endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getSaison() {
        return saison;
    }

    public Groupe saison(String saison) {
        this.saison = saison;
        return this;
    }

    public void setSaison(String saison) {
        this.saison = saison;
    }

    public Category getCategory() {
        return category;
    }

    public Groupe category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Nageurs> getNageurs() {
        return nageurs;
    }

    public Groupe nageurs(Set<Nageurs> nageurs) {
        this.nageurs = nageurs;
        return this;
    }

    public Groupe addNageurs(Nageurs nageurs) {
        this.nageurs.add(nageurs);
        nageurs.setGroupe(this);
        return this;
    }

    public Groupe removeNageurs(Nageurs nageurs) {
        this.nageurs.remove(nageurs);
        nageurs.setGroupe(null);
        return this;
    }

    public void setNageurs(Set<Nageurs> nageurs) {
        this.nageurs = nageurs;
    }

    public Set<FicheSeance> getFicheSeances() {
        return ficheSeances;
    }

    public Groupe ficheSeances(Set<FicheSeance> ficheSeances) {
        this.ficheSeances = ficheSeances;
        return this;
    }

    public Groupe addFicheSeance(FicheSeance ficheSeance) {
        this.ficheSeances.add(ficheSeance);
        ficheSeance.setGroupe(this);
        return this;
    }

    public Groupe removeFicheSeance(FicheSeance ficheSeance) {
        this.ficheSeances.remove(ficheSeance);
        ficheSeance.setGroupe(null);
        return this;
    }

    public void setFicheSeances(Set<FicheSeance> ficheSeances) {
        this.ficheSeances = ficheSeances;
    }

    public Set<Programmation> getProgrammations() {
        return programmations;
    }

    public Groupe programmations(Set<Programmation> programmations) {
        this.programmations = programmations;
        return this;
    }

    public Groupe addProgrammation(Programmation programmation) {
        this.programmations.add(programmation);
        programmation.setGroupe(this);
        return this;
    }

    public Groupe removeProgrammation(Programmation programmation) {
        this.programmations.remove(programmation);
        programmation.setGroupe(null);
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
        if (!(o instanceof Groupe)) {
            return false;
        }
        return id != null && id.equals(((Groupe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Groupe{" +
            "id=" + getId() +
            ", groupeName='" + getGroupeName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", saison='" + getSaison() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
