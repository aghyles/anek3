package com.popov.t04jh.service.impl;

import com.popov.t04jh.service.NageursService;
import com.popov.t04jh.domain.Nageurs;
import com.popov.t04jh.repository.NageursRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Nageurs}.
 */
@Service
@Transactional
public class NageursServiceImpl implements NageursService {

    private final Logger log = LoggerFactory.getLogger(NageursServiceImpl.class);

    private final NageursRepository nageursRepository;

    public NageursServiceImpl(NageursRepository nageursRepository) {
        this.nageursRepository = nageursRepository;
    }

    /**
     * Save a nageurs.
     *
     * @param nageurs the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Nageurs save(Nageurs nageurs) {
        log.debug("Request to save Nageurs : {}", nageurs);
        return nageursRepository.save(nageurs);
    }

    /**
     * Get all the nageurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Nageurs> findAll(Pageable pageable) {
        log.debug("Request to get all Nageurs");
        return nageursRepository.findAll(pageable);
    }


    /**
     * Get one nageurs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Nageurs> findOne(Long id) {
        log.debug("Request to get Nageurs : {}", id);
        return nageursRepository.findById(id);
    }

    /**
     * Delete the nageurs by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Nageurs : {}", id);
        nageursRepository.deleteById(id);
    }
}
