package com.popov.t04jh.service;

import com.popov.t04jh.domain.TestPerformance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TestPerformance}.
 */
public interface TestPerformanceService {

    /**
     * Save a testPerformance.
     *
     * @param testPerformance the entity to save.
     * @return the persisted entity.
     */
    TestPerformance save(TestPerformance testPerformance);

    /**
     * Get all the testPerformances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TestPerformance> findAll(Pageable pageable);


    /**
     * Get the "id" testPerformance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TestPerformance> findOne(Long id);

    /**
     * Delete the "id" testPerformance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
