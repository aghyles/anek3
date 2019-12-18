package com.popov.t04jh.repository;
import com.popov.t04jh.domain.Nageur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Nageur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NageurRepository extends JpaRepository<Nageur, Long> {

}
