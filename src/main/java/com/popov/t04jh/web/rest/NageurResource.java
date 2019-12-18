package com.popov.t04jh.web.rest;

import com.popov.t04jh.domain.Nageur;
import com.popov.t04jh.repository.NageurRepository;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.popov.t04jh.domain.Nageur}.
 */
@RestController
@RequestMapping("/api")
public class NageurResource {

    private final Logger log = LoggerFactory.getLogger(NageurResource.class);

    private static final String ENTITY_NAME = "nageur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NageurRepository nageurRepository;

    public NageurResource(NageurRepository nageurRepository) {
        this.nageurRepository = nageurRepository;
    }

    /**
     * {@code POST  /nageurs} : Create a new nageur.
     *
     * @param nageur the nageur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nageur, or with status {@code 400 (Bad Request)} if the nageur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nageurs")
    public ResponseEntity<Nageur> createNageur(@RequestBody Nageur nageur) throws URISyntaxException {
        log.debug("REST request to save Nageur : {}", nageur);
        if (nageur.getId() != null) {
            throw new BadRequestAlertException("A new nageur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Nageur result = nageurRepository.save(nageur);
        return ResponseEntity.created(new URI("/api/nageurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nageurs} : Updates an existing nageur.
     *
     * @param nageur the nageur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nageur,
     * or with status {@code 400 (Bad Request)} if the nageur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nageur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nageurs")
    public ResponseEntity<Nageur> updateNageur(@RequestBody Nageur nageur) throws URISyntaxException {
        log.debug("REST request to update Nageur : {}", nageur);
        if (nageur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Nageur result = nageurRepository.save(nageur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nageur.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nageurs} : get all the nageurs.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nageurs in body.
     */
    @GetMapping("/nageurs")
    public ResponseEntity<List<Nageur>> getAllNageurs(Pageable pageable) {
        log.debug("REST request to get a page of Nageurs");
        Page<Nageur> page = nageurRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nageurs/:id} : get the "id" nageur.
     *
     * @param id the id of the nageur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nageur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nageurs/{id}")
    public ResponseEntity<Nageur> getNageur(@PathVariable Long id) {
        log.debug("REST request to get Nageur : {}", id);
        Optional<Nageur> nageur = nageurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nageur);
    }

    /**
     * {@code DELETE  /nageurs/:id} : delete the "id" nageur.
     *
     * @param id the id of the nageur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nageurs/{id}")
    public ResponseEntity<Void> deleteNageur(@PathVariable Long id) {
        log.debug("REST request to delete Nageur : {}", id);
        nageurRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
