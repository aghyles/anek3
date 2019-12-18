package com.popov.t04jh.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Groupes.
 */
@Entity
@Table(name = "groupes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Groupes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "groupes")
    private String groupes;

    @Column(name = "saison")
    private String saison;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupes() {
        return groupes;
    }

    public Groupes groupes(String groupes) {
        this.groupes = groupes;
        return this;
    }

    public void setGroupes(String groupes) {
        this.groupes = groupes;
    }

    public String getSaison() {
        return saison;
    }

    public Groupes saison(String saison) {
        this.saison = saison;
        return this;
    }

    public void setSaison(String saison) {
        this.saison = saison;
    }

    public User getUser() {
        return user;
    }

    public Groupes user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Groupes)) {
            return false;
        }
        return id != null && id.equals(((Groupes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Groupes{" +
            "id=" + getId() +
            ", groupes='" + getGroupes() + "'" +
            ", saison='" + getSaison() + "'" +
            "}";
    }
}
