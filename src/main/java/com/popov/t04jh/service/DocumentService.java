package com.popov.t04jh.service;

import com.popov.t04jh.domain.Document;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Document}.
 */
public interface DocumentService {

    /**
     * Save a document.
     *
     * @param document the entity to save.
     * @return the persisted entity.
     */
    Document save(Document document);

    /**
     * Get all the documents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Document> findAll(Pageable pageable);
    /**
     * Get all the DocumentDTO where Testperformance is {@code null}.
     *
     * @return the list of entities.
     */
    List<Document> findAllWhereTestperformanceIsNull();


    /**
     * Get the "id" document.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Document> findOne(Long id);

    /**
     * Delete the "id" document.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
