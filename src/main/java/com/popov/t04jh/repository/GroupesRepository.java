package com.popov.t04jh.repository;
import com.popov.t04jh.domain.Groupes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Groupes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupesRepository extends JpaRepository<Groupes, Long> {

}
