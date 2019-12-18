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

/**
 * A FicheSeance.
 */
@Entity
@Table(name = "fiche_seance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FicheSeance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "fiche_seance", nullable = false)
    private Long ficheSeance;

    @NotNull
    @Column(name = "groupe_name", nullable = false)
    private String groupeName;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @Column(name = "theme_primary")
    private String themePrimary;

    @Column(name = "theme_secondary")
    private String themeSecondary;

    @Column(name = "objectif_primary")
    private String objectifPrimary;

    @Column(name = "objectif_secondary")
    private String objectifSecondary;

    @Column(name = "observation")
    private String observation;

    @Column(name = "exercice_echauffement_1")
    private String exerciceEchauffement1;

    @Column(name = "exercice_echauffement_2")
    private String exerciceEchauffement2;

    @Column(name = "exercice_echauffement_3")
    private String exerciceEchauffement3;

    @Column(name = "exercice_primary_1")
    private String exercicePrimary1;

    @Column(name = "exercice_primary_2")
    private String exercicePrimary2;

    @Column(name = "exercice_primary_3")
    private String exercicePrimary3;

    @Column(name = "exercice_primary_4")
    private String exercicePrimary4;

    @Column(name = "exercice_primary_5")
    private String exercicePrimary5;

    @Column(name = "exercice_primary_6")
    private String exercicePrimary6;

    @Column(name = "exercice_primary_7")
    private String exercicePrimary7;

    @Column(name = "exercice_primary_8")
    private String exercicePrimary8;

    @Column(name = "exercice_finale_1")
    private Long exerciceFinale1;

    @Column(name = "exercice_finale_2")
    private Long exerciceFinale2;

    @Column(name = "bilan")
    private String bilan;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "fiche_seance_presence",
               joinColumns = @JoinColumn(name = "fiche_seance_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "presence_id", referencedColumnName = "id"))
    private Set<Presence> presences = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "fiche_seance_location",
               joinColumns = @JoinColumn(name = "fiche_seance_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "location_id", referencedColumnName = "id"))
    private Set<Theme> locations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("ficheSeances")
    private Groupe groupe;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFicheSeance() {
        return ficheSeance;
    }

    public FicheSeance ficheSeance(Long ficheSeance) {
        this.ficheSeance = ficheSeance;
        return this;
    }

    public void setFicheSeance(Long ficheSeance) {
        this.ficheSeance = ficheSeance;
    }

    public String getGroupeName() {
        return groupeName;
    }

    public FicheSeance groupeName(String groupeName) {
        this.groupeName = groupeName;
        return this;
    }

    public void setGroupeName(String groupeName) {
        this.groupeName = groupeName;
    }

    public Instant getDate() {
        return date;
    }

    public FicheSeance date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getThemePrimary() {
        return themePrimary;
    }

    public FicheSeance themePrimary(String themePrimary) {
        this.themePrimary = themePrimary;
        return this;
    }

    public void setThemePrimary(String themePrimary) {
        this.themePrimary = themePrimary;
    }

    public String getThemeSecondary() {
        return themeSecondary;
    }

    public FicheSeance themeSecondary(String themeSecondary) {
        this.themeSecondary = themeSecondary;
        return this;
    }

    public void setThemeSecondary(String themeSecondary) {
        this.themeSecondary = themeSecondary;
    }

    public String getObjectifPrimary() {
        return objectifPrimary;
    }

    public FicheSeance objectifPrimary(String objectifPrimary) {
        this.objectifPrimary = objectifPrimary;
        return this;
    }

    public void setObjectifPrimary(String objectifPrimary) {
        this.objectifPrimary = objectifPrimary;
    }

    public String getObjectifSecondary() {
        return objectifSecondary;
    }

    public FicheSeance objectifSecondary(String objectifSecondary) {
        this.objectifSecondary = objectifSecondary;
        return this;
    }

    public void setObjectifSecondary(String objectifSecondary) {
        this.objectifSecondary = objectifSecondary;
    }

    public String getObservation() {
        return observation;
    }

    public FicheSeance observation(String observation) {
        this.observation = observation;
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getExerciceEchauffement1() {
        return exerciceEchauffement1;
    }

    public FicheSeance exerciceEchauffement1(String exerciceEchauffement1) {
        this.exerciceEchauffement1 = exerciceEchauffement1;
        return this;
    }

    public void setExerciceEchauffement1(String exerciceEchauffement1) {
        this.exerciceEchauffement1 = exerciceEchauffement1;
    }

    public String getExerciceEchauffement2() {
        return exerciceEchauffement2;
    }

    public FicheSeance exerciceEchauffement2(String exerciceEchauffement2) {
        this.exerciceEchauffement2 = exerciceEchauffement2;
        return this;
    }

    public void setExerciceEchauffement2(String exerciceEchauffement2) {
        this.exerciceEchauffement2 = exerciceEchauffement2;
    }

    public String getExerciceEchauffement3() {
        return exerciceEchauffement3;
    }

    public FicheSeance exerciceEchauffement3(String exerciceEchauffement3) {
        this.exerciceEchauffement3 = exerciceEchauffement3;
        return this;
    }

    public void setExerciceEchauffement3(String exerciceEchauffement3) {
        this.exerciceEchauffement3 = exerciceEchauffement3;
    }

    public String getExercicePrimary1() {
        return exercicePrimary1;
    }

    public FicheSeance exercicePrimary1(String exercicePrimary1) {
        this.exercicePrimary1 = exercicePrimary1;
        return this;
    }

    public void setExercicePrimary1(String exercicePrimary1) {
        this.exercicePrimary1 = exercicePrimary1;
    }

    public String getExercicePrimary2() {
        return exercicePrimary2;
    }

    public FicheSeance exercicePrimary2(String exercicePrimary2) {
        this.exercicePrimary2 = exercicePrimary2;
        return this;
    }

    public void setExercicePrimary2(String exercicePrimary2) {
        this.exercicePrimary2 = exercicePrimary2;
    }

    public String getExercicePrimary3() {
        return exercicePrimary3;
    }

    public FicheSeance exercicePrimary3(String exercicePrimary3) {
        this.exercicePrimary3 = exercicePrimary3;
        return this;
    }

    public void setExercicePrimary3(String exercicePrimary3) {
        this.exercicePrimary3 = exercicePrimary3;
    }

    public String getExercicePrimary4() {
        return exercicePrimary4;
    }

    public FicheSeance exercicePrimary4(String exercicePrimary4) {
        this.exercicePrimary4 = exercicePrimary4;
        return this;
    }

    public void setExercicePrimary4(String exercicePrimary4) {
        this.exercicePrimary4 = exercicePrimary4;
    }

    public String getExercicePrimary5() {
        return exercicePrimary5;
    }

    public FicheSeance exercicePrimary5(String exercicePrimary5) {
        this.exercicePrimary5 = exercicePrimary5;
        return this;
    }

    public void setExercicePrimary5(String exercicePrimary5) {
        this.exercicePrimary5 = exercicePrimary5;
    }

    public String getExercicePrimary6() {
        return exercicePrimary6;
    }

    public FicheSeance exercicePrimary6(String exercicePrimary6) {
        this.exercicePrimary6 = exercicePrimary6;
        return this;
    }

    public void setExercicePrimary6(String exercicePrimary6) {
        this.exercicePrimary6 = exercicePrimary6;
    }

    public String getExercicePrimary7() {
        return exercicePrimary7;
    }

    public FicheSeance exercicePrimary7(String exercicePrimary7) {
        this.exercicePrimary7 = exercicePrimary7;
        return this;
    }

    public void setExercicePrimary7(String exercicePrimary7) {
        this.exercicePrimary7 = exercicePrimary7;
    }

    public String getExercicePrimary8() {
        return exercicePrimary8;
    }

    public FicheSeance exercicePrimary8(String exercicePrimary8) {
        this.exercicePrimary8 = exercicePrimary8;
        return this;
    }

    public void setExercicePrimary8(String exercicePrimary8) {
        this.exercicePrimary8 = exercicePrimary8;
    }

    public Long getExerciceFinale1() {
        return exerciceFinale1;
    }

    public FicheSeance exerciceFinale1(Long exerciceFinale1) {
        this.exerciceFinale1 = exerciceFinale1;
        return this;
    }

    public void setExerciceFinale1(Long exerciceFinale1) {
        this.exerciceFinale1 = exerciceFinale1;
    }

    public Long getExerciceFinale2() {
        return exerciceFinale2;
    }

    public FicheSeance exerciceFinale2(Long exerciceFinale2) {
        this.exerciceFinale2 = exerciceFinale2;
        return this;
    }

    public void setExerciceFinale2(Long exerciceFinale2) {
        this.exerciceFinale2 = exerciceFinale2;
    }

    public String getBilan() {
        return bilan;
    }

    public FicheSeance bilan(String bilan) {
        this.bilan = bilan;
        return this;
    }

    public void setBilan(String bilan) {
        this.bilan = bilan;
    }

    public Set<Presence> getPresences() {
        return presences;
    }

    public FicheSeance presences(Set<Presence> presences) {
        this.presences = presences;
        return this;
    }

    public FicheSeance addPresence(Presence presence) {
        this.presences.add(presence);
        presence.getNageurs().add(this);
        return this;
    }

    public FicheSeance removePresence(Presence presence) {
        this.presences.remove(presence);
        presence.getNageurs().remove(this);
        return this;
    }

    public void setPresences(Set<Presence> presences) {
        this.presences = presences;
    }

    public Set<Theme> getLocations() {
        return locations;
    }

    public FicheSeance locations(Set<Theme> themes) {
        this.locations = themes;
        return this;
    }

    public FicheSeance addLocation(Theme theme) {
        this.locations.add(theme);
        theme.getFicheSeances().add(this);
        return this;
    }

    public FicheSeance removeLocation(Theme theme) {
        this.locations.remove(theme);
        theme.getFicheSeances().remove(this);
        return this;
    }

    public void setLocations(Set<Theme> themes) {
        this.locations = themes;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public FicheSeance groupe(Groupe groupe) {
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
        if (!(o instanceof FicheSeance)) {
            return false;
        }
        return id != null && id.equals(((FicheSeance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FicheSeance{" +
            "id=" + getId() +
            ", ficheSeance=" + getFicheSeance() +
            ", groupeName='" + getGroupeName() + "'" +
            ", date='" + getDate() + "'" +
            ", themePrimary='" + getThemePrimary() + "'" +
            ", themeSecondary='" + getThemeSecondary() + "'" +
            ", objectifPrimary='" + getObjectifPrimary() + "'" +
            ", objectifSecondary='" + getObjectifSecondary() + "'" +
            ", observation='" + getObservation() + "'" +
            ", exerciceEchauffement1='" + getExerciceEchauffement1() + "'" +
            ", exerciceEchauffement2='" + getExerciceEchauffement2() + "'" +
            ", exerciceEchauffement3='" + getExerciceEchauffement3() + "'" +
            ", exercicePrimary1='" + getExercicePrimary1() + "'" +
            ", exercicePrimary2='" + getExercicePrimary2() + "'" +
            ", exercicePrimary3='" + getExercicePrimary3() + "'" +
            ", exercicePrimary4='" + getExercicePrimary4() + "'" +
            ", exercicePrimary5='" + getExercicePrimary5() + "'" +
            ", exercicePrimary6='" + getExercicePrimary6() + "'" +
            ", exercicePrimary7='" + getExercicePrimary7() + "'" +
            ", exercicePrimary8='" + getExercicePrimary8() + "'" +
            ", exerciceFinale1=" + getExerciceFinale1() +
            ", exerciceFinale2=" + getExerciceFinale2() +
            ", bilan='" + getBilan() + "'" +
            "}";
    }
}
