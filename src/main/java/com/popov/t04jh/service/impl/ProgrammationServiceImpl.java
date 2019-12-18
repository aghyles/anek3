package com.popov.t04jh.service.impl;

import com.popov.t04jh.service.ProgrammationService;
import com.popov.t04jh.domain.Programmation;
import com.popov.t04jh.repository.ProgrammationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Programmation}.
 */
@Service
@Transactional
public class ProgrammationServiceImpl implements ProgrammationService {

    private final Logger log = LoggerFactory.getLogger(ProgrammationServiceImpl.class);

    private final ProgrammationRepository programmationRepository;

    public ProgrammationServiceImpl(ProgrammationRepository programmationRepository) {
        this.programmationRepository = programmationRepository;
    }

    /**
     * Save a programmation.
     *
     * @param programmation the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Programmation save(Programmation programmation) {
        log.debug("Request to save Programmation : {}", programmation);
        return programmationRepository.save(programmation);
    }

    /**
     * Get all the programmations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Programmation> findAll(Pageable pageable) {
        log.debug("Request to get all Programmations");
        return programmationRepository.findAll(pageable);
    }

    /**
     * Get all the programmations with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Programmation> findAllWithEagerRelationships(Pageable pageable) {
        return programmationRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one programmation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Programmation> findOne(Long id) {
        log.debug("Request to get Programmation : {}", id);
        return programmationRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the programmation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Programmation : {}", id);
        programmationRepository.deleteById(id);
    }
}
