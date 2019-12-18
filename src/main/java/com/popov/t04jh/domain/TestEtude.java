package com.popov.t04jh.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A TestEtude.
 */
@Entity
@Table(name = "test_etude")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TestEtude implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "test_title", nullable = false)
    private String testTitle;

    @Column(name = "niveau_etude")
    private String niveauEtude;

    @Column(name = "date_examen")
    private Instant dateExamen;

    @Column(name = "average")
    private Long average;

    @OneToOne(mappedBy = "testEtude")
    @JsonIgnore
    private TestPerformance testperformance;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestTitle() {
        return testTitle;
    }

    public TestEtude testTitle(String testTitle) {
        this.testTitle = testTitle;
        return this;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }

    public String getNiveauEtude() {
        return niveauEtude;
    }

    public TestEtude niveauEtude(String niveauEtude) {
        this.niveauEtude = niveauEtude;
        return this;
    }

    public void setNiveauEtude(String niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public Instant getDateExamen() {
        return dateExamen;
    }

    public TestEtude dateExamen(Instant dateExamen) {
        this.dateExamen = dateExamen;
        return this;
    }

    public void setDateExamen(Instant dateExamen) {
        this.dateExamen = dateExamen;
    }

    public Long getAverage() {
        return average;
    }

    public TestEtude average(Long average) {
        this.average = average;
        return this;
    }

    public void setAverage(Long average) {
        this.average = average;
    }

    public TestPerformance getTestperformance() {
        return testperformance;
    }

    public TestEtude testperformance(TestPerformance testPerformance) {
        this.testperformance = testPerformance;
        return this;
    }

    public void setTestperformance(TestPerformance testPerformance) {
        this.testperformance = testPerformance;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TestEtude)) {
            return false;
        }
        return id != null && id.equals(((TestEtude) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TestEtude{" +
            "id=" + getId() +
            ", testTitle='" + getTestTitle() + "'" +
            ", niveauEtude='" + getNiveauEtude() + "'" +
            ", dateExamen='" + getDateExamen() + "'" +
            ", average=" + getAverage() +
            "}";
    }
}
