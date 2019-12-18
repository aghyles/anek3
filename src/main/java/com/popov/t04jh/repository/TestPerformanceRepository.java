package com.popov.t04jh.repository;
import com.popov.t04jh.domain.TestPerformance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TestPerformance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TestPerformanceRepository extends JpaRepository<TestPerformance, Long> {

}
