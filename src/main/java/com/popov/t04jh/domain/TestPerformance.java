package com.popov.t04jh.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A TestPerformance.
 */
@Entity
@Table(name = "test_performance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TestPerformance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "type_test", nullable = false)
    private String typeTest;

    @Column(name = "date")
    private Instant date;

    @Column(name = "idphotos")
    private Long idphotos;

    @Column(name = "idtest_doc")
    private Long idtestDoc;

    @OneToOne
    @JoinColumn(unique = true)
    private TestAutre testAutre;

    @OneToOne
    @JoinColumn(unique = true)
    private TestEtude testEtude;

    @OneToOne
    @JoinColumn(unique = true)
    private TestNage testNage;

    @OneToOne
    @JoinColumn(unique = true)
    private Document document;

    @OneToOne
    @JoinColumn(unique = true)
    private TestPhysique testPhysique;

    @ManyToOne
    @JsonIgnoreProperties("testPerformances")
    private Nageurs nageurs;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeTest() {
        return typeTest;
    }

    public TestPerformance typeTest(String typeTest) {
        this.typeTest = typeTest;
        return this;
    }

    public void setTypeTest(String typeTest) {
        this.typeTest = typeTest;
    }

    public Instant getDate() {
        return date;
    }

    public TestPerformance date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Long getIdphotos() {
        return idphotos;
    }

    public TestPerformance idphotos(Long idphotos) {
        this.idphotos = idphotos;
        return this;
    }

    public void setIdphotos(Long idphotos) {
        this.idphotos = idphotos;
    }

    public Long getIdtestDoc() {
        return idtestDoc;
    }

    public TestPerformance idtestDoc(Long idtestDoc) {
        this.idtestDoc = idtestDoc;
        return this;
    }

    public void setIdtestDoc(Long idtestDoc) {
        this.idtestDoc = idtestDoc;
    }

    public TestAutre getTestAutre() {
        return testAutre;
    }

    public TestPerformance testAutre(TestAutre testAutre) {
        this.testAutre = testAutre;
        return this;
    }

    public void setTestAutre(TestAutre testAutre) {
        this.testAutre = testAutre;
    }

    public TestEtude getTestEtude() {
        return testEtude;
    }

    public TestPerformance testEtude(TestEtude testEtude) {
        this.testEtude = testEtude;
        return this;
    }

    public void setTestEtude(TestEtude testEtude) {
        this.testEtude = testEtude;
    }

    public TestNage getTestNage() {
        return testNage;
    }

    public TestPerformance testNage(TestNage testNage) {
        this.testNage = testNage;
        return this;
    }

    public void setTestNage(TestNage testNage) {
        this.testNage = testNage;
    }

    public Document getDocument() {
        return document;
    }

    public TestPerformance document(Document document) {
        this.document = document;
        return this;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public TestPhysique getTestPhysique() {
        return testPhysique;
    }

    public TestPerformance testPhysique(TestPhysique testPhysique) {
        this.testPhysique = testPhysique;
        return this;
    }

    public void setTestPhysique(TestPhysique testPhysique) {
        this.testPhysique = testPhysique;
    }

    public Nageurs getNageurs() {
        return nageurs;
    }

    public TestPerformance nageurs(Nageurs nageurs) {
        this.nageurs = nageurs;
        return this;
    }

    public void setNageurs(Nageurs nageurs) {
        this.nageurs = nageurs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TestPerformance)) {
            return false;
        }
        return id != null && id.equals(((TestPerformance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TestPerformance{" +
            "id=" + getId() +
            ", typeTest='" + getTypeTest() + "'" +
            ", date='" + getDate() + "'" +
            ", idphotos=" + getIdphotos() +
            ", idtestDoc=" + getIdtestDoc() +
            "}";
    }
}
