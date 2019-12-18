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
 * A Exercice.
 */
@Entity
@Table(name = "exercice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Exercice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "exercice_name", nullable = false)
    private String exerciceName;

    @Column(name = "description")
    private String description;

    @Column(name = "id_doc_schema")
    private Long idDocSchema;

    @Column(name = "dosage")
    private String dosage;

    @Column(name = "long_dosage")
    private Long longDosage;

    @Column(name = "recomendation")
    private String recomendation;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "exercice_document",
               joinColumns = @JoinColumn(name = "exercice_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "document_id", referencedColumnName = "id"))
    private Set<Document> documents = new HashSet<>();

    @ManyToMany(mappedBy = "exercices")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Objectif> objectifs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExerciceName() {
        return exerciceName;
    }

    public Exercice exerciceName(String exerciceName) {
        this.exerciceName = exerciceName;
        return this;
    }

    public void setExerciceName(String exerciceName) {
        this.exerciceName = exerciceName;
    }

    public String getDescription() {
        return description;
    }

    public Exercice description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIdDocSchema() {
        return idDocSchema;
    }

    public Exercice idDocSchema(Long idDocSchema) {
        this.idDocSchema = idDocSchema;
        return this;
    }

    public void setIdDocSchema(Long idDocSchema) {
        this.idDocSchema = idDocSchema;
    }

    public String getDosage() {
        return dosage;
    }

    public Exercice dosage(String dosage) {
        this.dosage = dosage;
        return this;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public Long getLongDosage() {
        return longDosage;
    }

    public Exercice longDosage(Long longDosage) {
        this.longDosage = longDosage;
        return this;
    }

    public void setLongDosage(Long longDosage) {
        this.longDosage = longDosage;
    }

    public String getRecomendation() {
        return recomendation;
    }

    public Exercice recomendation(String recomendation) {
        this.recomendation = recomendation;
        return this;
    }

    public void setRecomendation(String recomendation) {
        this.recomendation = recomendation;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public Exercice documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public Exercice addDocument(Document document) {
        this.documents.add(document);
        document.getExercices().add(this);
        return this;
    }

    public Exercice removeDocument(Document document) {
        this.documents.remove(document);
        document.getExercices().remove(this);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public Set<Objectif> getObjectifs() {
        return objectifs;
    }

    public Exercice objectifs(Set<Objectif> objectifs) {
        this.objectifs = objectifs;
        return this;
    }

    public Exercice addObjectif(Objectif objectif) {
        this.objectifs.add(objectif);
        objectif.getExercices().add(this);
        return this;
    }

    public Exercice removeObjectif(Objectif objectif) {
        this.objectifs.remove(objectif);
        objectif.getExercices().remove(this);
        return this;
    }

    public void setObjectifs(Set<Objectif> objectifs) {
        this.objectifs = objectifs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exercice)) {
            return false;
        }
        return id != null && id.equals(((Exercice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Exercice{" +
            "id=" + getId() +
            ", exerciceName='" + getExerciceName() + "'" +
            ", description='" + getDescription() + "'" +
            ", idDocSchema=" + getIdDocSchema() +
            ", dosage='" + getDosage() + "'" +
            ", longDosage=" + getLongDosage() +
            ", recomendation='" + getRecomendation() + "'" +
            "}";
    }
}
