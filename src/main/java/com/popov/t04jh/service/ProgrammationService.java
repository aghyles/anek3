package com.popov.t04jh.service;

import com.popov.t04jh.domain.Programmation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Programmation}.
 */
public interface ProgrammationService {

    /**
     * Save a programmation.
     *
     * @param programmation the entity to save.
     * @return the persisted entity.
     */
    Programmation save(Programmation programmation);

    /**
     * Get all the programmations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Programmation> findAll(Pageable pageable);

    /**
     * Get all the programmations with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Programmation> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" programmation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Programmation> findOne(Long id);

    /**
     * Delete the "id" programmation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
