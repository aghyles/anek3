package com.popov.t04jh.web.rest;

import com.popov.t04jh.T04JhApp;
import com.popov.t04jh.domain.Programmation;
import com.popov.t04jh.repository.ProgrammationRepository;
import com.popov.t04jh.service.ProgrammationService;
import com.popov.t04jh.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.popov.t04jh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProgrammationResource} REST controller.
 */
@SpringBootTest(classes = T04JhApp.class)
public class ProgrammationResourceIT {

    private static final String DEFAULT_PROGRAMME = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAMME = "BBBBBBBBBB";

    private static final Long DEFAULT_GROUPE = 1L;
    private static final Long UPDATED_GROUPE = 2L;

    @Autowired
    private ProgrammationRepository programmationRepository;

    @Mock
    private ProgrammationRepository programmationRepositoryMock;

    @Mock
    private ProgrammationService programmationServiceMock;

    @Autowired
    private ProgrammationService programmationService;

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

    private MockMvc restProgrammationMockMvc;

    private Programmation programmation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProgrammationResource programmationResource = new ProgrammationResource(programmationService);
        this.restProgrammationMockMvc = MockMvcBuilders.standaloneSetup(programmationResource)
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
    public static Programmation createEntity(EntityManager em) {
        Programmation programmation = new Programmation()
            .programme(DEFAULT_PROGRAMME)
            .groupe(DEFAULT_GROUPE);
        return programmation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Programmation createUpdatedEntity(EntityManager em) {
        Programmation programmation = new Programmation()
            .programme(UPDATED_PROGRAMME)
            .groupe(UPDATED_GROUPE);
        return programmation;
    }

    @BeforeEach
    public void initTest() {
        programmation = createEntity(em);
    }

    @Test
    @Transactional
    public void createProgrammation() throws Exception {
        int databaseSizeBeforeCreate = programmationRepository.findAll().size();

        // Create the Programmation
        restProgrammationMockMvc.perform(post("/api/programmations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmation)))
            .andExpect(status().isCreated());

        // Validate the Programmation in the database
        List<Programmation> programmationList = programmationRepository.findAll();
        assertThat(programmationList).hasSize(databaseSizeBeforeCreate + 1);
        Programmation testProgrammation = programmationList.get(programmationList.size() - 1);
        assertThat(testProgrammation.getProgramme()).isEqualTo(DEFAULT_PROGRAMME);
        assertThat(testProgrammation.getGroupe()).isEqualTo(DEFAULT_GROUPE);
    }

    @Test
    @Transactional
    public void createProgrammationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = programmationRepository.findAll().size();

        // Create the Programmation with an existing ID
        programmation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgrammationMockMvc.perform(post("/api/programmations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmation)))
            .andExpect(status().isBadRequest());

        // Validate the Programmation in the database
        List<Programmation> programmationList = programmationRepository.findAll();
        assertThat(programmationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGroupeIsRequired() throws Exception {
        int databaseSizeBeforeTest = programmationRepository.findAll().size();
        // set the field null
        programmation.setGroupe(null);

        // Create the Programmation, which fails.

        restProgrammationMockMvc.perform(post("/api/programmations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmation)))
            .andExpect(status().isBadRequest());

        List<Programmation> programmationList = programmationRepository.findAll();
        assertThat(programmationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProgrammations() throws Exception {
        // Initialize the database
        programmationRepository.saveAndFlush(programmation);

        // Get all the programmationList
        restProgrammationMockMvc.perform(get("/api/programmations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programmation.getId().intValue())))
            .andExpect(jsonPath("$.[*].programme").value(hasItem(DEFAULT_PROGRAMME)))
            .andExpect(jsonPath("$.[*].groupe").value(hasItem(DEFAULT_GROUPE.intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllProgrammationsWithEagerRelationshipsIsEnabled() throws Exception {
        ProgrammationResource programmationResource = new ProgrammationResource(programmationServiceMock);
        when(programmationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restProgrammationMockMvc = MockMvcBuilders.standaloneSetup(programmationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProgrammationMockMvc.perform(get("/api/programmations?eagerload=true"))
        .andExpect(status().isOk());

        verify(programmationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllProgrammationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ProgrammationResource programmationResource = new ProgrammationResource(programmationServiceMock);
            when(programmationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restProgrammationMockMvc = MockMvcBuilders.standaloneSetup(programmationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProgrammationMockMvc.perform(get("/api/programmations?eagerload=true"))
        .andExpect(status().isOk());

            verify(programmationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProgrammation() throws Exception {
        // Initialize the database
        programmationRepository.saveAndFlush(programmation);

        // Get the programmation
        restProgrammationMockMvc.perform(get("/api/programmations/{id}", programmation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(programmation.getId().intValue()))
            .andExpect(jsonPath("$.programme").value(DEFAULT_PROGRAMME))
            .andExpect(jsonPath("$.groupe").value(DEFAULT_GROUPE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProgrammation() throws Exception {
        // Get the programmation
        restProgrammationMockMvc.perform(get("/api/programmations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProgrammation() throws Exception {
        // Initialize the database
        programmationService.save(programmation);

        int databaseSizeBeforeUpdate = programmationRepository.findAll().size();

        // Update the programmation
        Programmation updatedProgrammation = programmationRepository.findById(programmation.getId()).get();
        // Disconnect from session so that the updates on updatedProgrammation are not directly saved in db
        em.detach(updatedProgrammation);
        updatedProgrammation
            .programme(UPDATED_PROGRAMME)
            .groupe(UPDATED_GROUPE);

        restProgrammationMockMvc.perform(put("/api/programmations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProgrammation)))
            .andExpect(status().isOk());

        // Validate the Programmation in the database
        List<Programmation> programmationList = programmationRepository.findAll();
        assertThat(programmationList).hasSize(databaseSizeBeforeUpdate);
        Programmation testProgrammation = programmationList.get(programmationList.size() - 1);
        assertThat(testProgrammation.getProgramme()).isEqualTo(UPDATED_PROGRAMME);
        assertThat(testProgrammation.getGroupe()).isEqualTo(UPDATED_GROUPE);
    }

    @Test
    @Transactional
    public void updateNonExistingProgrammation() throws Exception {
        int databaseSizeBeforeUpdate = programmationRepository.findAll().size();

        // Create the Programmation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgrammationMockMvc.perform(put("/api/programmations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmation)))
            .andExpect(status().isBadRequest());

        // Validate the Programmation in the database
        List<Programmation> programmationList = programmationRepository.findAll();
        assertThat(programmationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProgrammation() throws Exception {
        // Initialize the database
        programmationService.save(programmation);

        int databaseSizeBeforeDelete = programmationRepository.findAll().size();

        // Delete the programmation
        restProgrammationMockMvc.perform(delete("/api/programmations/{id}", programmation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Programmation> programmationList = programmationRepository.findAll();
        assertThat(programmationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
