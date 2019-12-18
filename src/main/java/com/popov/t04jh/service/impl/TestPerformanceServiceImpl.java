package com.popov.t04jh.service.impl;

import com.popov.t04jh.service.TestPerformanceService;
import com.popov.t04jh.domain.TestPerformance;
import com.popov.t04jh.repository.TestPerformanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TestPerformance}.
 */
@Service
@Transactional
public class TestPerformanceServiceImpl implements TestPerformanceService {

    private final Logger log = LoggerFactory.getLogger(TestPerformanceServiceImpl.class);

    private final TestPerformanceRepository testPerformanceRepository;

    public TestPerformanceServiceImpl(TestPerformanceRepository testPerformanceRepository) {
        this.testPerformanceRepository = testPerformanceRepository;
    }

    /**
     * Save a testPerformance.
     *
     * @param testPerformance the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TestPerformance save(TestPerformance testPerformance) {
        log.debug("Request to save TestPerformance : {}", testPerformance);
        return testPerformanceRepository.save(testPerformance);
    }

    /**
     * Get all the testPerformances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TestPerformance> findAll(Pageable pageable) {
        log.debug("Request to get all TestPerformances");
        return testPerformanceRepository.findAll(pageable);
    }


    /**
     * Get one testPerformance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TestPerformance> findOne(Long id) {
        log.debug("Request to get TestPerformance : {}", id);
        return testPerformanceRepository.findById(id);
    }

    /**
     * Delete the testPerformance by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TestPerformance : {}", id);
        testPerformanceRepository.deleteById(id);
    }
}
