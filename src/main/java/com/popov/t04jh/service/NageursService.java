package com.popov.t04jh.service;

import com.popov.t04jh.domain.Nageurs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Nageurs}.
 */
public interface NageursService {

    /**
     * Save a nageurs.
     *
     * @param nageurs the entity to save.
     * @return the persisted entity.
     */
    Nageurs save(Nageurs nageurs);

    /**
     * Get all the nageurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Nageurs> findAll(Pageable pageable);


    /**
     * Get the "id" nageurs.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Nageurs> findOne(Long id);

    /**
     * Delete the "id" nageurs.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
