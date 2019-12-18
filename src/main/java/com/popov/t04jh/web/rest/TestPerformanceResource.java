package com.popov.t04jh.web.rest;

import com.popov.t04jh.domain.TestPerformance;
import com.popov.t04jh.service.TestPerformanceService;
import com.popov.t04jh.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.popov.t04jh.domain.TestPerformance}.
 */
@RestController
@RequestMapping("/api")
public class TestPerformanceResource {

    private final Logger log = LoggerFactory.getLogger(TestPerformanceResource.class);

    private static final String ENTITY_NAME = "testPerformance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TestPerformanceService testPerformanceService;

    public TestPerformanceResource(TestPerformanceService testPerformanceService) {
        this.testPerformanceService = testPerformanceService;
    }

    /**
     * {@code POST  /test-performances} : Create a new testPerformance.
     *
     * @param testPerformance the testPerformance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new testPerformance, or with status {@code 400 (Bad Request)} if the testPerformance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/test-performances")
    public ResponseEntity<TestPerformance> createTestPerformance(@Valid @RequestBody TestPerformance testPerformance) throws URISyntaxException {
        log.debug("REST request to save TestPerformance : {}", testPerformance);
        if (testPerformance.getId() != null) {
            throw new BadRequestAlertException("A new testPerformance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TestPerformance result = testPerformanceService.save(testPerformance);
        return ResponseEntity.created(new URI("/api/test-performances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /test-performances} : Updates an existing testPerformance.
     *
     * @param testPerformance the testPerformance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated testPerformance,
     * or with status {@code 400 (Bad Request)} if the testPerformance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the testPerformance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/test-performances")
    public ResponseEntity<TestPerformance> updateTestPerformance(@Valid @RequestBody TestPerformance testPerformance) throws URISyntaxException {
        log.debug("REST request to update TestPerformance : {}", testPerformance);
        if (testPerformance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TestPerformance result = testPerformanceService.save(testPerformance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, testPerformance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /test-performances} : get all the testPerformances.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of testPerformances in body.
     */
    @GetMapping("/test-performances")
    public ResponseEntity<List<TestPerformance>> getAllTestPerformances(Pageable pageable) {
        log.debug("REST request to get a page of TestPerformances");
        Page<TestPerformance> page = testPerformanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /test-performances/:id} : get the "id" testPerformance.
     *
     * @param id the id of the testPerformance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the testPerformance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/test-performances/{id}")
    public ResponseEntity<TestPerformance> getTestPerformance(@PathVariable Long id) {
        log.debug("REST request to get TestPerformance : {}", id);
        Optional<TestPerformance> testPerformance = testPerformanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(testPerformance);
    }

    /**
     * {@code DELETE  /test-performances/:id} : delete the "id" testPerformance.
     *
     * @param id the id of the testPerformance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/test-performances/{id}")
    public ResponseEntity<Void> deleteTestPerformance(@PathVariable Long id) {
        log.debug("REST request to delete TestPerformance : {}", id);
        testPerformanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
