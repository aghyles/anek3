package com.popov.t04jh.web.rest;

import com.popov.t04jh.T04JhApp;
import com.popov.t04jh.domain.Nageurs;
import com.popov.t04jh.repository.NageursRepository;
import com.popov.t04jh.service.NageursService;
import com.popov.t04jh.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.popov.t04jh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.popov.t04jh.domain.enumeration.Sexe;
/**
 * Integration tests for the {@link NageursResource} REST controller.
 */
@SpringBootTest(classes = T04JhApp.class)
public class NageursResourceIT {

    private static final String DEFAULT_LICENCE_NUM = "AAAAAAAAAA";
    private static final String UPDATED_LICENCE_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_GROUPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GROUPE_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_DOCUMENT = 1L;
    private static final Long UPDATED_DOCUMENT = 2L;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Sexe DEFAULT_SEXE = Sexe.HOMME;
    private static final Sexe UPDATED_SEXE = Sexe.FEMME;

    private static final Instant DEFAULT_BEARTHDAY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BEARTHDAY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_E_MAIL = "UdEp@dU1.tqc";
    private static final String UPDATED_E_MAIL = "7@4.lld.zk";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_STUDY_TIME = "AAAAAAAAAA";
    private static final String UPDATED_STUDY_TIME = "BBBBBBBBBB";

    @Autowired
    private NageursRepository nageursRepository;

    @Autowired
    private NageursService nageursService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restNageursMockMvc;

    private Nageurs nageurs;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NageursResource nageursResource = new NageursResource(nageursService);
        this.restNageursMockMvc = MockMvcBuilders.standaloneSetup(nageursResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nageurs createEntity(EntityManager em) {
        Nageurs nageurs = new Nageurs()
            .licenceNum(DEFAULT_LICENCE_NUM)
            .groupeName(DEFAULT_GROUPE_NAME)
            .document(DEFAULT_DOCUMENT)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .sexe(DEFAULT_SEXE)
            .bearthday(DEFAULT_BEARTHDAY)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .eMail(DEFAULT_E_MAIL)
            .address(DEFAULT_ADDRESS)
            .studyTime(DEFAULT_STUDY_TIME);
        return nageurs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nageurs createUpdatedEntity(EntityManager em) {
        Nageurs nageurs = new Nageurs()
            .licenceNum(UPDATED_LICENCE_NUM)
            .groupeName(UPDATED_GROUPE_NAME)
            .document(UPDATED_DOCUMENT)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .sexe(UPDATED_SEXE)
            .bearthday(UPDATED_BEARTHDAY)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .eMail(UPDATED_E_MAIL)
            .address(UPDATED_ADDRESS)
            .studyTime(UPDATED_STUDY_TIME);
        return nageurs;
    }

    @BeforeEach
    public void initTest() {
        nageurs = createEntity(em);
    }

    @Test
    @Transactional
    public void createNageurs() throws Exception {
        int databaseSizeBeforeCreate = nageursRepository.findAll().size();

        // Create the Nageurs
        restNageursMockMvc.perform(post("/api/nageurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nageurs)))
            .andExpect(status().isCreated());

        // Validate the Nageurs in the database
        List<Nageurs> nageursList = nageursRepository.findAll();
        assertThat(nageursList).hasSize(databaseSizeBeforeCreate + 1);
        Nageurs testNageurs = nageursList.get(nageursList.size() - 1);
        assertThat(testNageurs.getLicenceNum()).isEqualTo(DEFAULT_LICENCE_NUM);
        assertThat(testNageurs.getGroupeName()).isEqualTo(DEFAULT_GROUPE_NAME);
        assertThat(testNageurs.getDocument()).isEqualTo(DEFAULT_DOCUMENT);
        assertThat(testNageurs.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testNageurs.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testNageurs.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testNageurs.getBearthday()).isEqualTo(DEFAULT_BEARTHDAY);
        assertThat(testNageurs.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testNageurs.geteMail()).isEqualTo(DEFAULT_E_MAIL);
        assertThat(testNageurs.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testNageurs.getStudyTime()).isEqualTo(DEFAULT_STUDY_TIME);
    }

    @Test
    @Transactional
    public void createNageursWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nageursRepository.findAll().size();

        // Create the Nageurs with an existing ID
        nageurs.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNageursMockMvc.perform(post("/api/nageurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nageurs)))
            .andExpect(status().isBadRequest());

        // Validate the Nageurs in the database
        List<Nageurs> nageursList = nageursRepository.findAll();
        assertThat(nageursList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nageursRepository.findAll().size();
        // set the field null
        nageurs.setFirstName(null);

        // Create the Nageurs, which fails.

        restNageursMockMvc.perform(post("/api/nageurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nageurs)))
            .andExpect(status().isBadRequest());

        List<Nageurs> nageursList = nageursRepository.findAll();
        assertThat(nageursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nageursRepository.findAll().size();
        // set the field null
        nageurs.setLastName(null);

        // Create the Nageurs, which fails.

        restNageursMockMvc.perform(post("/api/nageurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nageurs)))
            .andExpect(status().isBadRequest());

        List<Nageurs> nageursList = nageursRepository.findAll();
        assertThat(nageursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNageurs() throws Exception {
        // Initialize the database
        nageursRepository.saveAndFlush(nageurs);

        // Get all the nageursList
        restNageursMockMvc.perform(get("/api/nageurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nageurs.getId().intValue())))
            .andExpect(jsonPath("$.[*].licenceNum").value(hasItem(DEFAULT_LICENCE_NUM)))
            .andExpect(jsonPath("$.[*].groupeName").value(hasItem(DEFAULT_GROUPE_NAME)))
            .andExpect(jsonPath("$.[*].document").value(hasItem(DEFAULT_DOCUMENT.intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].bearthday").value(hasItem(DEFAULT_BEARTHDAY.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].eMail").value(hasItem(DEFAULT_E_MAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].studyTime").value(hasItem(DEFAULT_STUDY_TIME)));
    }
    
    @Test
    @Transactional
    public void getNageurs() throws Exception {
        // Initialize the database
        nageursRepository.saveAndFlush(nageurs);

        // Get the nageurs
        restNageursMockMvc.perform(get("/api/nageurs/{id}", nageurs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nageurs.getId().intValue()))
            .andExpect(jsonPath("$.licenceNum").value(DEFAULT_LICENCE_NUM))
            .andExpect(jsonPath("$.groupeName").value(DEFAULT_GROUPE_NAME))
            .andExpect(jsonPath("$.document").value(DEFAULT_DOCUMENT.intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.bearthday").value(DEFAULT_BEARTHDAY.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.eMail").value(DEFAULT_E_MAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.studyTime").value(DEFAULT_STUDY_TIME));
    }

    @Test
    @Transactional
    public void getNonExistingNageurs() throws Exception {
        // Get the nageurs
        restNageursMockMvc.perform(get("/api/nageurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNageurs() throws Exception {
        // Initialize the database
        nageursService.save(nageurs);

        int databaseSizeBeforeUpdate = nageursRepository.findAll().size();

        // Update the nageurs
        Nageurs updatedNageurs = nageursRepository.findById(nageurs.getId()).get();
        // Disconnect from session so that the updates on updatedNageurs are not directly saved in db
        em.detach(updatedNageurs);
        updatedNageurs
            .licenceNum(UPDATED_LICENCE_NUM)
            .groupeName(UPDATED_GROUPE_NAME)
            .document(UPDATED_DOCUMENT)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .sexe(UPDATED_SEXE)
            .bearthday(UPDATED_BEARTHDAY)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .eMail(UPDATED_E_MAIL)
            .address(UPDATED_ADDRESS)
            .studyTime(UPDATED_STUDY_TIME);

        restNageursMockMvc.perform(put("/api/nageurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNageurs)))
            .andExpect(status().isOk());

        // Validate the Nageurs in the database
        List<Nageurs> nageursList = nageursRepository.findAll();
        assertThat(nageursList).hasSize(databaseSizeBeforeUpdate);
        Nageurs testNageurs = nageursList.get(nageursList.size() - 1);
        assertThat(testNageurs.getLicenceNum()).isEqualTo(UPDATED_LICENCE_NUM);
        assertThat(testNageurs.getGroupeName()).isEqualTo(UPDATED_GROUPE_NAME);
        assertThat(testNageurs.getDocument()).isEqualTo(UPDATED_DOCUMENT);
        assertThat(testNageurs.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testNageurs.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testNageurs.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testNageurs.getBearthday()).isEqualTo(UPDATED_BEARTHDAY);
        assertThat(testNageurs.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testNageurs.geteMail()).isEqualTo(UPDATED_E_MAIL);
        assertThat(testNageurs.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testNageurs.getStudyTime()).isEqualTo(UPDATED_STUDY_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingNageurs() throws Exception {
        int databaseSizeBeforeUpdate = nageursRepository.findAll().size();

        // Create the Nageurs

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNageursMockMvc.perform(put("/api/nageurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nageurs)))
            .andExpect(status().isBadRequest());

        // Validate the Nageurs in the database
        List<Nageurs> nageursList = nageursRepository.findAll();
        assertThat(nageursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNageurs() throws Exception {
        // Initialize the database
        nageursService.save(nageurs);

        int databaseSizeBeforeDelete = nageursRepository.findAll().size();

        // Delete the nageurs
        restNageursMockMvc.perform(delete("/api/nageurs/{id}", nageurs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nageurs> nageursList = nageursRepository.findAll();
        assertThat(nageursList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
