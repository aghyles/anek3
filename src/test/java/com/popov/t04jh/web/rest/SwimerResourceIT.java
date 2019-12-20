package com.popov.t04jh.web.rest;

import com.popov.t04jh.T04JhApp;
import com.popov.t04jh.domain.Swimer;
import com.popov.t04jh.repository.SwimerRepository;
import com.popov.t04jh.service.SwimerService;
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
 * Integration tests for the {@link SwimerResource} REST controller.
 */
@SpringBootTest(classes = T04JhApp.class)
public class SwimerResourceIT {

    private static final String DEFAULT_LICENCE_NUM = "AAAAAAAAAA";
    private static final String UPDATED_LICENCE_NUM = "BBBBBBBBBB";

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

    private static final String DEFAULT_E_MAIL = "Xr@aKn.kM";
    private static final String UPDATED_E_MAIL = "Ve@1R-M.F4l.SH";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_STUDY_TIME = "AAAAAAAAAA";
    private static final String UPDATED_STUDY_TIME = "BBBBBBBBBB";

    private static final Instant DEFAULT_FIRST_SWIM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIRST_SWIM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_GROUPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GROUPE_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_DOCUMENT = 1L;
    private static final Long UPDATED_DOCUMENT = 2L;

    @Autowired
    private SwimerRepository swimerRepository;

    @Autowired
    private SwimerService swimerService;

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

    private MockMvc restSwimerMockMvc;

    private Swimer swimer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SwimerResource swimerResource = new SwimerResource(swimerService);
        this.restSwimerMockMvc = MockMvcBuilders.standaloneSetup(swimerResource)
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
    public static Swimer createEntity(EntityManager em) {
        Swimer swimer = new Swimer()
            .licenceNum(DEFAULT_LICENCE_NUM)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .sexe(DEFAULT_SEXE)
            .bearthday(DEFAULT_BEARTHDAY)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .eMail(DEFAULT_E_MAIL)
            .address(DEFAULT_ADDRESS)
            .studyTime(DEFAULT_STUDY_TIME)
            .firstSwim(DEFAULT_FIRST_SWIM)
            .groupeName(DEFAULT_GROUPE_NAME)
            .document(DEFAULT_DOCUMENT);
        return swimer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Swimer createUpdatedEntity(EntityManager em) {
        Swimer swimer = new Swimer()
            .licenceNum(UPDATED_LICENCE_NUM)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .sexe(UPDATED_SEXE)
            .bearthday(UPDATED_BEARTHDAY)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .eMail(UPDATED_E_MAIL)
            .address(UPDATED_ADDRESS)
            .studyTime(UPDATED_STUDY_TIME)
            .firstSwim(UPDATED_FIRST_SWIM)
            .groupeName(UPDATED_GROUPE_NAME)
            .document(UPDATED_DOCUMENT);
        return swimer;
    }

    @BeforeEach
    public void initTest() {
        swimer = createEntity(em);
    }

    @Test
    @Transactional
    public void createSwimer() throws Exception {
        int databaseSizeBeforeCreate = swimerRepository.findAll().size();

        // Create the Swimer
        restSwimerMockMvc.perform(post("/api/swimers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(swimer)))
            .andExpect(status().isCreated());

        // Validate the Swimer in the database
        List<Swimer> swimerList = swimerRepository.findAll();
        assertThat(swimerList).hasSize(databaseSizeBeforeCreate + 1);
        Swimer testSwimer = swimerList.get(swimerList.size() - 1);
        assertThat(testSwimer.getLicenceNum()).isEqualTo(DEFAULT_LICENCE_NUM);
        assertThat(testSwimer.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testSwimer.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testSwimer.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testSwimer.getBearthday()).isEqualTo(DEFAULT_BEARTHDAY);
        assertThat(testSwimer.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testSwimer.geteMail()).isEqualTo(DEFAULT_E_MAIL);
        assertThat(testSwimer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testSwimer.getStudyTime()).isEqualTo(DEFAULT_STUDY_TIME);
        assertThat(testSwimer.getFirstSwim()).isEqualTo(DEFAULT_FIRST_SWIM);
        assertThat(testSwimer.getGroupeName()).isEqualTo(DEFAULT_GROUPE_NAME);
        assertThat(testSwimer.getDocument()).isEqualTo(DEFAULT_DOCUMENT);
    }

    @Test
    @Transactional
    public void createSwimerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = swimerRepository.findAll().size();

        // Create the Swimer with an existing ID
        swimer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSwimerMockMvc.perform(post("/api/swimers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(swimer)))
            .andExpect(status().isBadRequest());

        // Validate the Swimer in the database
        List<Swimer> swimerList = swimerRepository.findAll();
        assertThat(swimerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = swimerRepository.findAll().size();
        // set the field null
        swimer.setFirstName(null);

        // Create the Swimer, which fails.

        restSwimerMockMvc.perform(post("/api/swimers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(swimer)))
            .andExpect(status().isBadRequest());

        List<Swimer> swimerList = swimerRepository.findAll();
        assertThat(swimerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = swimerRepository.findAll().size();
        // set the field null
        swimer.setLastName(null);

        // Create the Swimer, which fails.

        restSwimerMockMvc.perform(post("/api/swimers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(swimer)))
            .andExpect(status().isBadRequest());

        List<Swimer> swimerList = swimerRepository.findAll();
        assertThat(swimerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSwimers() throws Exception {
        // Initialize the database
        swimerRepository.saveAndFlush(swimer);

        // Get all the swimerList
        restSwimerMockMvc.perform(get("/api/swimers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(swimer.getId().intValue())))
            .andExpect(jsonPath("$.[*].licenceNum").value(hasItem(DEFAULT_LICENCE_NUM)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].bearthday").value(hasItem(DEFAULT_BEARTHDAY.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].eMail").value(hasItem(DEFAULT_E_MAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].studyTime").value(hasItem(DEFAULT_STUDY_TIME)))
            .andExpect(jsonPath("$.[*].firstSwim").value(hasItem(DEFAULT_FIRST_SWIM.toString())))
            .andExpect(jsonPath("$.[*].groupeName").value(hasItem(DEFAULT_GROUPE_NAME)))
            .andExpect(jsonPath("$.[*].document").value(hasItem(DEFAULT_DOCUMENT.intValue())));
    }
    
    @Test
    @Transactional
    public void getSwimer() throws Exception {
        // Initialize the database
        swimerRepository.saveAndFlush(swimer);

        // Get the swimer
        restSwimerMockMvc.perform(get("/api/swimers/{id}", swimer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(swimer.getId().intValue()))
            .andExpect(jsonPath("$.licenceNum").value(DEFAULT_LICENCE_NUM))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.bearthday").value(DEFAULT_BEARTHDAY.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.eMail").value(DEFAULT_E_MAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.studyTime").value(DEFAULT_STUDY_TIME))
            .andExpect(jsonPath("$.firstSwim").value(DEFAULT_FIRST_SWIM.toString()))
            .andExpect(jsonPath("$.groupeName").value(DEFAULT_GROUPE_NAME))
            .andExpect(jsonPath("$.document").value(DEFAULT_DOCUMENT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSwimer() throws Exception {
        // Get the swimer
        restSwimerMockMvc.perform(get("/api/swimers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSwimer() throws Exception {
        // Initialize the database
        swimerService.save(swimer);

        int databaseSizeBeforeUpdate = swimerRepository.findAll().size();

        // Update the swimer
        Swimer updatedSwimer = swimerRepository.findById(swimer.getId()).get();
        // Disconnect from session so that the updates on updatedSwimer are not directly saved in db
        em.detach(updatedSwimer);
        updatedSwimer
            .licenceNum(UPDATED_LICENCE_NUM)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .sexe(UPDATED_SEXE)
            .bearthday(UPDATED_BEARTHDAY)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .eMail(UPDATED_E_MAIL)
            .address(UPDATED_ADDRESS)
            .studyTime(UPDATED_STUDY_TIME)
            .firstSwim(UPDATED_FIRST_SWIM)
            .groupeName(UPDATED_GROUPE_NAME)
            .document(UPDATED_DOCUMENT);

        restSwimerMockMvc.perform(put("/api/swimers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSwimer)))
            .andExpect(status().isOk());

        // Validate the Swimer in the database
        List<Swimer> swimerList = swimerRepository.findAll();
        assertThat(swimerList).hasSize(databaseSizeBeforeUpdate);
        Swimer testSwimer = swimerList.get(swimerList.size() - 1);
        assertThat(testSwimer.getLicenceNum()).isEqualTo(UPDATED_LICENCE_NUM);
        assertThat(testSwimer.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSwimer.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testSwimer.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testSwimer.getBearthday()).isEqualTo(UPDATED_BEARTHDAY);
        assertThat(testSwimer.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testSwimer.geteMail()).isEqualTo(UPDATED_E_MAIL);
        assertThat(testSwimer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSwimer.getStudyTime()).isEqualTo(UPDATED_STUDY_TIME);
        assertThat(testSwimer.getFirstSwim()).isEqualTo(UPDATED_FIRST_SWIM);
        assertThat(testSwimer.getGroupeName()).isEqualTo(UPDATED_GROUPE_NAME);
        assertThat(testSwimer.getDocument()).isEqualTo(UPDATED_DOCUMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingSwimer() throws Exception {
        int databaseSizeBeforeUpdate = swimerRepository.findAll().size();

        // Create the Swimer

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSwimerMockMvc.perform(put("/api/swimers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(swimer)))
            .andExpect(status().isBadRequest());

        // Validate the Swimer in the database
        List<Swimer> swimerList = swimerRepository.findAll();
        assertThat(swimerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSwimer() throws Exception {
        // Initialize the database
        swimerService.save(swimer);

        int databaseSizeBeforeDelete = swimerRepository.findAll().size();

        // Delete the swimer
        restSwimerMockMvc.perform(delete("/api/swimers/{id}", swimer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Swimer> swimerList = swimerRepository.findAll();
        assertThat(swimerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
