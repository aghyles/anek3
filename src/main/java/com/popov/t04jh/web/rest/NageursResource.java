package com.popov.t04jh.web.rest;

import com.popov.t04jh.domain.Nageurs;
import com.popov.t04jh.service.NageursService;
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
 * REST controller for managing {@link com.popov.t04jh.domain.Nageurs}.
 */
@RestController
@RequestMapping("/api")
public class NageursResource {

    private final Logger log = LoggerFactory.getLogger(NageursResource.class);

    private static final String ENTITY_NAME = "nageurs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NageursService nageursService;

    public NageursResource(NageursService nageursService) {
        this.nageursService = nageursService;
    }

    /**
     * {@code POST  /nageurs} : Create a new nageurs.
     *
     * @param nageurs the nageurs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nageurs, or with status {@code 400 (Bad Request)} if the nageurs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nageurs")
    public ResponseEntity<Nageurs> createNageurs(@Valid @RequestBody Nageurs nageurs) throws URISyntaxException {
        log.debug("REST request to save Nageurs : {}", nageurs);
        if (nageurs.getId() != null) {
            throw new BadRequestAlertException("A new nageurs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Nageurs result = nageursService.save(nageurs);
        return ResponseEntity.created(new URI("/api/nageurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nageurs} : Updates an existing nageurs.
     *
     * @param nageurs the nageurs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nageurs,
     * or with status {@code 400 (Bad Request)} if the nageurs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nageurs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nageurs")
    public ResponseEntity<Nageurs> updateNageurs(@Valid @RequestBody Nageurs nageurs) throws URISyntaxException {
        log.debug("REST request to update Nageurs : {}", nageurs);
        if (nageurs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Nageurs result = nageursService.save(nageurs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nageurs.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nageurs} : get all the nageurs.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nageurs in body.
     */
    @GetMapping("/nageurs")
    public ResponseEntity<List<Nageurs>> getAllNageurs(Pageable pageable) {
        log.debug("REST request to get a page of Nageurs");
        Page<Nageurs> page = nageursService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nageurs/:id} : get the "id" nageurs.
     *
     * @param id the id of the nageurs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nageurs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nageurs/{id}")
    public ResponseEntity<Nageurs> getNageurs(@PathVariable Long id) {
        log.debug("REST request to get Nageurs : {}", id);
        Optional<Nageurs> nageurs = nageursService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nageurs);
    }

    /**
     * {@code DELETE  /nageurs/:id} : delete the "id" nageurs.
     *
     * @param id the id of the nageurs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nageurs/{id}")
    public ResponseEntity<Void> deleteNageurs(@PathVariable Long id) {
        log.debug("REST request to delete Nageurs : {}", id);
        nageursService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
