package com.popov.t04jh.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A TestAutre.
 */
@Entity
@Table(name = "test_autre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TestAutre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "test_title", nullable = false)
    private String testTitle;

    @Column(name = "type_test")
    private String typeTest;

    @Column(name = "resultat")
    private String resultat;

    @Column(name = "observation")
    private String observation;

    @OneToOne(mappedBy = "testAutre")
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

    public TestAutre testTitle(String testTitle) {
        this.testTitle = testTitle;
        return this;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }

    public String getTypeTest() {
        return typeTest;
    }

    public TestAutre typeTest(String typeTest) {
        this.typeTest = typeTest;
        return this;
    }

    public void setTypeTest(String typeTest) {
        this.typeTest = typeTest;
    }

    public String getResultat() {
        return resultat;
    }

    public TestAutre resultat(String resultat) {
        this.resultat = resultat;
        return this;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public String getObservation() {
        return observation;
    }

    public TestAutre observation(String observation) {
        this.observation = observation;
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public TestPerformance getTestperformance() {
        return testperformance;
    }

    public TestAutre testperformance(TestPerformance testPerformance) {
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
        if (!(o instanceof TestAutre)) {
            return false;
        }
        return id != null && id.equals(((TestAutre) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TestAutre{" +
            "id=" + getId() +
            ", testTitle='" + getTestTitle() + "'" +
            ", typeTest='" + getTypeTest() + "'" +
            ", resultat='" + getResultat() + "'" +
            ", observation='" + getObservation() + "'" +
            "}";
    }
}
