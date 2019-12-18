package com.popov.t04jh.service.impl;

import com.popov.t04jh.service.GroupeService;
import com.popov.t04jh.domain.Groupe;
import com.popov.t04jh.repository.GroupeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Groupe}.
 */
@Service
@Transactional
public class GroupeServiceImpl implements GroupeService {

    private final Logger log = LoggerFactory.getLogger(GroupeServiceImpl.class);

    private final GroupeRepository groupeRepository;

    public GroupeServiceImpl(GroupeRepository groupeRepository) {
        this.groupeRepository = groupeRepository;
    }

    /**
     * Save a groupe.
     *
     * @param groupe the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Groupe save(Groupe groupe) {
        log.debug("Request to save Groupe : {}", groupe);
        return groupeRepository.save(groupe);
    }

    /**
     * Get all the groupes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Groupe> findAll(Pageable pageable) {
        log.debug("Request to get all Groupes");
        return groupeRepository.findAll(pageable);
    }


    /**
     * Get one groupe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Groupe> findOne(Long id) {
        log.debug("Request to get Groupe : {}", id);
        return groupeRepository.findById(id);
    }

    /**
     * Delete the groupe by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Groupe : {}", id);
        groupeRepository.deleteById(id);
    }
}
