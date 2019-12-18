package com.popov.t04jh.web.rest;

import com.popov.t04jh.T04JhApp;
import com.popov.t04jh.domain.TestPerformance;
import com.popov.t04jh.repository.TestPerformanceRepository;
import com.popov.t04jh.service.TestPerformanceService;
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

/**
 * Integration tests for the {@link TestPerformanceResource} REST controller.
 */
@SpringBootTest(classes = T04JhApp.class)
public class TestPerformanceResourceIT {

    private static final String DEFAULT_TYPE_TEST = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_TEST = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_IDPHOTOS = 1L;
    private static final Long UPDATED_IDPHOTOS = 2L;

    private static final Long DEFAULT_IDTEST_DOC = 1L;
    private static final Long UPDATED_IDTEST_DOC = 2L;

    @Autowired
    private TestPerformanceRepository testPerformanceRepository;

    @Autowired
    private TestPerformanceService testPerformanceService;

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

    private MockMvc restTestPerformanceMockMvc;

    private TestPerformance testPerformance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TestPerformanceResource testPerformanceResource = new TestPerformanceResource(testPerformanceService);
        this.restTestPerformanceMockMvc = MockMvcBuilders.standaloneSetup(testPerformanceResource)
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
    public static TestPerformance createEntity(EntityManager em) {
        TestPerformance testPerformance = new TestPerformance()
            .typeTest(DEFAULT_TYPE_TEST)
            .date(DEFAULT_DATE)
            .idphotos(DEFAULT_IDPHOTOS)
            .idtestDoc(DEFAULT_IDTEST_DOC);
        return testPerformance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TestPerformance createUpdatedEntity(EntityManager em) {
        TestPerformance testPerformance = new TestPerformance()
            .typeTest(UPDATED_TYPE_TEST)
            .date(UPDATED_DATE)
            .idphotos(UPDATED_IDPHOTOS)
            .idtestDoc(UPDATED_IDTEST_DOC);
        return testPerformance;
    }

    @BeforeEach
    public void initTest() {
        testPerformance = createEntity(em);
    }

    @Test
    @Transactional
    public void createTestPerformance() throws Exception {
        int databaseSizeBeforeCreate = testPerformanceRepository.findAll().size();

        // Create the TestPerformance
        restTestPerformanceMockMvc.perform(post("/api/test-performances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testPerformance)))
            .andExpect(status().isCreated());

        // Validate the TestPerformance in the database
        List<TestPerformance> testPerformanceList = testPerformanceRepository.findAll();
        assertThat(testPerformanceList).hasSize(databaseSizeBeforeCreate + 1);
        TestPerformance testTestPerformance = testPerformanceList.get(testPerformanceList.size() - 1);
        assertThat(testTestPerformance.getTypeTest()).isEqualTo(DEFAULT_TYPE_TEST);
        assertThat(testTestPerformance.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTestPerformance.getIdphotos()).isEqualTo(DEFAULT_IDPHOTOS);
        assertThat(testTestPerformance.getIdtestDoc()).isEqualTo(DEFAULT_IDTEST_DOC);
    }

    @Test
    @Transactional
    public void createTestPerformanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = testPerformanceRepository.findAll().size();

        // Create the TestPerformance with an existing ID
        testPerformance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTestPerformanceMockMvc.perform(post("/api/test-performances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testPerformance)))
            .andExpect(status().isBadRequest());

        // Validate the TestPerformance in the database
        List<TestPerformance> testPerformanceList = testPerformanceRepository.findAll();
        assertThat(testPerformanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeTestIsRequired() throws Exception {
        int databaseSizeBeforeTest = testPerformanceRepository.findAll().size();
        // set the field null
        testPerformance.setTypeTest(null);

        // Create the TestPerformance, which fails.

        restTestPerformanceMockMvc.perform(post("/api/test-performances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testPerformance)))
            .andExpect(status().isBadRequest());

        List<TestPerformance> testPerformanceList = testPerformanceRepository.findAll();
        assertThat(testPerformanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTestPerformances() throws Exception {
        // Initialize the database
        testPerformanceRepository.saveAndFlush(testPerformance);

        // Get all the testPerformanceList
        restTestPerformanceMockMvc.perform(get("/api/test-performances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testPerformance.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeTest").value(hasItem(DEFAULT_TYPE_TEST)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].idphotos").value(hasItem(DEFAULT_IDPHOTOS.intValue())))
            .andExpect(jsonPath("$.[*].idtestDoc").value(hasItem(DEFAULT_IDTEST_DOC.intValue())));
    }
    
    @Test
    @Transactional
    public void getTestPerformance() throws Exception {
        // Initialize the database
        testPerformanceRepository.saveAndFlush(testPerformance);

        // Get the testPerformance
        restTestPerformanceMockMvc.perform(get("/api/test-performances/{id}", testPerformance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(testPerformance.getId().intValue()))
            .andExpect(jsonPath("$.typeTest").value(DEFAULT_TYPE_TEST))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.idphotos").value(DEFAULT_IDPHOTOS.intValue()))
            .andExpect(jsonPath("$.idtestDoc").value(DEFAULT_IDTEST_DOC.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTestPerformance() throws Exception {
        // Get the testPerformance
        restTestPerformanceMockMvc.perform(get("/api/test-performances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTestPerformance() throws Exception {
        // Initialize the database
        testPerformanceService.save(testPerformance);

        int databaseSizeBeforeUpdate = testPerformanceRepository.findAll().size();

        // Update the testPerformance
        TestPerformance updatedTestPerformance = testPerformanceRepository.findById(testPerformance.getId()).get();
        // Disconnect from session so that the updates on updatedTestPerformance are not directly saved in db
        em.detach(updatedTestPerformance);
        updatedTestPerformance
            .typeTest(UPDATED_TYPE_TEST)
            .date(UPDATED_DATE)
            .idphotos(UPDATED_IDPHOTOS)
            .idtestDoc(UPDATED_IDTEST_DOC);

        restTestPerformanceMockMvc.perform(put("/api/test-performances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTestPerformance)))
            .andExpect(status().isOk());

        // Validate the TestPerformance in the database
        List<TestPerformance> testPerformanceList = testPerformanceRepository.findAll();
        assertThat(testPerformanceList).hasSize(databaseSizeBeforeUpdate);
        TestPerformance testTestPerformance = testPerformanceList.get(testPerformanceList.size() - 1);
        assertThat(testTestPerformance.getTypeTest()).isEqualTo(UPDATED_TYPE_TEST);
        assertThat(testTestPerformance.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTestPerformance.getIdphotos()).isEqualTo(UPDATED_IDPHOTOS);
        assertThat(testTestPerformance.getIdtestDoc()).isEqualTo(UPDATED_IDTEST_DOC);
    }

    @Test
    @Transactional
    public void updateNonExistingTestPerformance() throws Exception {
        int databaseSizeBeforeUpdate = testPerformanceRepository.findAll().size();

        // Create the TestPerformance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTestPerformanceMockMvc.perform(put("/api/test-performances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testPerformance)))
            .andExpect(status().isBadRequest());

        // Validate the TestPerformance in the database
        List<TestPerformance> testPerformanceList = testPerformanceRepository.findAll();
        assertThat(testPerformanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTestPerformance() throws Exception {
        // Initialize the database
        testPerformanceService.save(testPerformance);

        int databaseSizeBeforeDelete = testPerformanceRepository.findAll().size();

        // Delete the testPerformance
        restTestPerformanceMockMvc.perform(delete("/api/test-performances/{id}", testPerformance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TestPerformance> testPerformanceList = testPerformanceRepository.findAll();
        assertThat(testPerformanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
