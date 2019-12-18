package com.popov.t04jh.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Nageur.
 */
@Entity
@Table(name = "nageur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Nageur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "licence")
    private String licence;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "dateNaissance")
    private LocalDate dateNaissance;

    @Column(name = "tel")
    private Integer tel;

    @Column(name = "hauraireEtude")
    private String hauraireEtude;

    @ManyToOne
    private Groupes groupes;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicence() {
        return licence;
    }

    public Nageur licence(String licence) {
        this.licence = licence;
        return this;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getNom() {
        return nom;
    }

    public Nageur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Nageur prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Nageur dateNaissance(LocalDate date_naissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate date_naissance) {
        this.dateNaissance = dateNaissance;
    }

    public Integer getTel() {
        return tel;
    }

    public Nageur tel(Integer tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public String getHauraireEtude() {
        return hauraireEtude;
    }

    public Nageur hauraireEtude(String hauraire_etude) {
        this.hauraireEtude = hauraireEtude;
        return this;
    }

    public void setHauraireEtude(String hauraire_etude) {
        this.hauraireEtude = hauraireEtude;
    }

    public Groupes getGroupes() {
        return groupes;
    }

    public Nageur groupes(Groupes groupes) {
        this.groupes = groupes;
        return this;
    }

    public void setGroupes(Groupes groupes) {
        this.groupes = groupes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nageur)) {
            return false;
        }
        return id != null && id.equals(((Nageur) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Nageur{" +
            "id=" + getId() +
            ", licence='" + getLicence() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", tel=" + getTel() +
            ", hauraireEtude='" + getHauraireEtude() + "'" +
            "}";
    }
}
