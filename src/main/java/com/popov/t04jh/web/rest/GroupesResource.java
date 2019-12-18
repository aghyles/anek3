package com.popov.t04jh.web.rest;

import com.popov.t04jh.domain.Groupes;
import com.popov.t04jh.repository.GroupesRepository;
import com.popov.t04jh.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.popov.t04jh.domain.Groupes}.
 */
@RestController
@RequestMapping("/api")
public class GroupesResource {

    private final Logger log = LoggerFactory.getLogger(GroupesResource.class);

    private static final String ENTITY_NAME = "groupes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupesRepository groupesRepository;

    public GroupesResource(GroupesRepository groupesRepository) {
        this.groupesRepository = groupesRepository;
    }

    /**
     * {@code POST  /groupes} : Create a new groupes.
     *
     * @param groupes the groupes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupes, or with status {@code 400 (Bad Request)} if the groupes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/groupes")
    public ResponseEntity<Groupes> createGroupes(@RequestBody Groupes groupes) throws URISyntaxException {
        log.debug("REST request to save Groupes : {}", groupes);
        if (groupes.getId() != null) {
            throw new BadRequestAlertException("A new groupes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Groupes result = groupesRepository.save(groupes);
        return ResponseEntity.created(new URI("/api/groupes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /groupes} : Updates an existing groupes.
     *
     * @param groupes the groupes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupes,
     * or with status {@code 400 (Bad Request)} if the groupes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/groupes")
    public ResponseEntity<Groupes> updateGroupes(@RequestBody Groupes groupes) throws URISyntaxException {
        log.debug("REST request to update Groupes : {}", groupes);
        if (groupes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Groupes result = groupesRepository.save(groupes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /groupes} : get all the groupes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupes in body.
     */
    @GetMapping("/groupes")
    public List<Groupes> getAllGroupes() {
        log.debug("REST request to get all Groupes");
        return groupesRepository.findAll();
    }

    /**
     * {@code GET  /groupes/:id} : get the "id" groupes.
     *
     * @param id the id of the groupes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/groupes/{id}")
    public ResponseEntity<Groupes> getGroupes(@PathVariable Long id) {
        log.debug("REST request to get Groupes : {}", id);
        Optional<Groupes> groupes = groupesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(groupes);
    }

    /**
     * {@code DELETE  /groupes/:id} : delete the "id" groupes.
     *
     * @param id the id of the groupes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/groupes/{id}")
    public ResponseEntity<Void> deleteGroupes(@PathVariable Long id) {
        log.debug("REST request to delete Groupes : {}", id);
        groupesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
