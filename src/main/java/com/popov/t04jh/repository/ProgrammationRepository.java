package com.popov.t04jh.repository;
import com.popov.t04jh.domain.Programmation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Programmation entity.
 */
@Repository
public interface ProgrammationRepository extends JpaRepository<Programmation, Long> {

    @Query(value = "select distinct programmation from Programmation programmation left join fetch programmation.documents",
        countQuery = "select count(distinct programmation) from Programmation programmation")
    Page<Programmation> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct programmation from Programmation programmation left join fetch programmation.documents")
    List<Programmation> findAllWithEagerRelationships();

    @Query("select programmation from Programmation programmation left join fetch programmation.documents where programmation.id =:id")
    Optional<Programmation> findOneWithEagerRelationships(@Param("id") Long id);

}
