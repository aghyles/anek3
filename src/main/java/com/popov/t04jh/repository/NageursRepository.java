package com.popov.t04jh.repository;
import com.popov.t04jh.domain.Nageurs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Nageurs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NageursRepository extends JpaRepository<Nageurs, Long> {

}
