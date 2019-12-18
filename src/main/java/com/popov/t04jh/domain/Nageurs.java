package com.popov.t04jh.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.popov.t04jh.domain.enumeration.Sexe;

/**
 * A Nageurs.
 */
@Entity
@Table(name = "nageurs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Nageurs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "licence_num")
    private String licenceNum;

    @Column(name = "groupe_name")
    private String groupeName;

    @Column(name = "document")
    private Long document;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexe")
    private Sexe sexe;

    @Column(name = "bearthday")
    private Instant bearthday;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")
    @Column(name = "e_mail")
    private String eMail;

    @Column(name = "address")
    private String address;

    @Column(name = "study_time")
    private String studyTime;

    @OneToMany(mappedBy = "nageurs")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MesureAnthropo> poidsTailles = new HashSet<>();

    @OneToMany(mappedBy = "nageurs")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TestPerformance> testPerformances = new HashSet<>();

    @OneToMany(mappedBy = "nageurs")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Document> documents = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("nageurs")
    private Groupe groupe;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicenceNum() {
        return licenceNum;
    }

    public Nageurs licenceNum(String licenceNum) {
        this.licenceNum = licenceNum;
        return this;
    }

    public void setLicenceNum(String licenceNum) {
        this.licenceNum = licenceNum;
    }

    public String getGroupeName() {
        return groupeName;
    }

    public Nageurs groupeName(String groupeName) {
        this.groupeName = groupeName;
        return this;
    }

    public void setGroupeName(String groupeName) {
        this.groupeName = groupeName;
    }

    public Long getDocument() {
        return document;
    }

    public Nageurs document(Long document) {
        this.document = document;
        return this;
    }

    public void setDocument(Long document) {
        this.document = document;
    }

    public String getFirstName() {
        return firstName;
    }

    public Nageurs firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Nageurs lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public Nageurs sexe(Sexe sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Instant getBearthday() {
        return bearthday;
    }

    public Nageurs bearthday(Instant bearthday) {
        this.bearthday = bearthday;
        return this;
    }

    public void setBearthday(Instant bearthday) {
        this.bearthday = bearthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Nageurs phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String geteMail() {
        return eMail;
    }

    public Nageurs eMail(String eMail) {
        this.eMail = eMail;
        return this;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getAddress() {
        return address;
    }

    public Nageurs address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStudyTime() {
        return studyTime;
    }

    public Nageurs studyTime(String studyTime) {
        this.studyTime = studyTime;
        return this;
    }

    public void setStudyTime(String studyTime) {
        this.studyTime = studyTime;
    }

    public Set<MesureAnthropo> getPoidsTailles() {
        return poidsTailles;
    }

    public Nageurs poidsTailles(Set<MesureAnthropo> mesureAnthropos) {
        this.poidsTailles = mesureAnthropos;
        return this;
    }

    public Nageurs addPoidsTaille(MesureAnthropo mesureAnthropo) {
        this.poidsTailles.add(mesureAnthropo);
        mesureAnthropo.setNageurs(this);
        return this;
    }

    public Nageurs removePoidsTaille(MesureAnthropo mesureAnthropo) {
        this.poidsTailles.remove(mesureAnthropo);
        mesureAnthropo.setNageurs(null);
        return this;
    }

    public void setPoidsTailles(Set<MesureAnthropo> mesureAnthropos) {
        this.poidsTailles = mesureAnthropos;
    }

    public Set<TestPerformance> getTestPerformances() {
        return testPerformances;
    }

    public Nageurs testPerformances(Set<TestPerformance> testPerformances) {
        this.testPerformances = testPerformances;
        return this;
    }

    public Nageurs addTestPerformance(TestPerformance testPerformance) {
        this.testPerformances.add(testPerformance);
        testPerformance.setNageurs(this);
        return this;
    }

    public Nageurs removeTestPerformance(TestPerformance testPerformance) {
        this.testPerformances.remove(testPerformance);
        testPerformance.setNageurs(null);
        return this;
    }

    public void setTestPerformances(Set<TestPerformance> testPerformances) {
        this.testPerformances = testPerformances;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public Nageurs documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public Nageurs addDocument(Document document) {
        this.documents.add(document);
        document.setNageurs(this);
        return this;
    }

    public Nageurs removeDocument(Document document) {
        this.documents.remove(document);
        document.setNageurs(null);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public Nageurs groupe(Groupe groupe) {
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
        if (!(o instanceof Nageurs)) {
            return false;
        }
        return id != null && id.equals(((Nageurs) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Nageurs{" +
            "id=" + getId() +
            ", licenceNum='" + getLicenceNum() + "'" +
            ", groupeName='" + getGroupeName() + "'" +
            ", document=" + getDocument() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", bearthday='" + getBearthday() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", eMail='" + geteMail() + "'" +
            ", address='" + getAddress() + "'" +
            ", studyTime='" + getStudyTime() + "'" +
            "}";
    }
}
