package com.popov.t04jh.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.popov.t04jh.domain.enumeration.TypeDocument;

/**
 * A Document.
 */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "doc_title", nullable = false)
    private String docTitle;

    @Column(name = "date_document")
    private Instant dateDocument;

    @Column(name = "link_document")
    private String linkDocument;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_document")
    private TypeDocument typeDocument;

    @OneToOne(mappedBy = "document")
    @JsonIgnore
    private TestPerformance testperformance;

    @ManyToOne
    @JsonIgnoreProperties("documents")
    private Nageurs nageurs;

    @ManyToMany(mappedBy = "documents")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Exercice> exercices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public Document docTitle(String docTitle) {
        this.docTitle = docTitle;
        return this;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public Instant getDateDocument() {
        return dateDocument;
    }

    public Document dateDocument(Instant dateDocument) {
        this.dateDocument = dateDocument;
        return this;
    }

    public void setDateDocument(Instant dateDocument) {
        this.dateDocument = dateDocument;
    }

    public String getLinkDocument() {
        return linkDocument;
    }

    public Document linkDocument(String linkDocument) {
        this.linkDocument = linkDocument;
        return this;
    }

    public void setLinkDocument(String linkDocument) {
        this.linkDocument = linkDocument;
    }

    public TypeDocument getTypeDocument() {
        return typeDocument;
    }

    public Document typeDocument(TypeDocument typeDocument) {
        this.typeDocument = typeDocument;
        return this;
    }

    public void setTypeDocument(TypeDocument typeDocument) {
        this.typeDocument = typeDocument;
    }

    public TestPerformance getTestperformance() {
        return testperformance;
    }

    public Document testperformance(TestPerformance testPerformance) {
        this.testperformance = testPerformance;
        return this;
    }

    public void setTestperformance(TestPerformance testPerformance) {
        this.testperformance = testPerformance;
    }

    public Nageurs getNageurs() {
        return nageurs;
    }

    public Document nageurs(Nageurs nageurs) {
        this.nageurs = nageurs;
        return this;
    }

    public void setNageurs(Nageurs nageurs) {
        this.nageurs = nageurs;
    }

    public Set<Exercice> getExercices() {
        return exercices;
    }

    public Document exercices(Set<Exercice> exercices) {
        this.exercices = exercices;
        return this;
    }

    public Document addExercice(Exercice exercice) {
        this.exercices.add(exercice);
        exercice.getDocuments().add(this);
        return this;
    }

    public Document removeExercice(Exercice exercice) {
        this.exercices.remove(exercice);
        exercice.getDocuments().remove(this);
        return this;
    }

    public void setExercices(Set<Exercice> exercices) {
        this.exercices = exercices;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Document)) {
            return false;
        }
        return id != null && id.equals(((Document) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", docTitle='" + getDocTitle() + "'" +
            ", dateDocument='" + getDateDocument() + "'" +
            ", linkDocument='" + getLinkDocument() + "'" +
            ", typeDocument='" + getTypeDocument() + "'" +
            "}";
    }
}
