package com.popov.t04jh.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Programmation.
 */
@Entity
@Table(name = "programmation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Programmation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "programme")
    private String programme;

    @NotNull
    @Column(name = "groupe", nullable = false)
    private Long groupe;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "programmation_document",
               joinColumns = @JoinColumn(name = "programmation_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "document_id", referencedColumnName = "id"))
    private Set<Theme> documents = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("programmations")
    private Groupe groupe;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProgramme() {
        return programme;
    }

    public Programmation programme(String programme) {
        this.programme = programme;
        return this;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public Long getGroupe() {
        return groupe;
    }

    public Programmation groupe(Long groupe) {
        this.groupe = groupe;
        return this;
    }

    public void setGroupe(Long groupe) {
        this.groupe = groupe;
    }

    public Set<Theme> getDocuments() {
        return documents;
    }

    public Programmation documents(Set<Theme> themes) {
        this.documents = themes;
        return this;
    }

    public Programmation addDocument(Theme theme) {
        this.documents.add(theme);
        theme.getProgrammations().add(this);
        return this;
    }

    public Programmation removeDocument(Theme theme) {
        this.documents.remove(theme);
        theme.getProgrammations().remove(this);
        return this;
    }

    public void setDocuments(Set<Theme> themes) {
        this.documents = themes;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public Programmation groupe(Groupe groupe) {
        this.groupe = groupe;
        return this;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Programmation)) {
            return false;
        }
        return id != null && id.equals(((Programmation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Programmation{" +
            "id=" + getId() +
            ", programme='" + getProgramme() + "'" +
            ", groupe=" + getGroupe() +
            "}";
    }
}
