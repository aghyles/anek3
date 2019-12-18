package com.popov.t04jh.web.rest;

import com.popov.t04jh.T04JhApp;
import com.popov.t04jh.domain.FicheSeance;
import com.popov.t04jh.repository.FicheSeanceRepository;
import com.popov.t04jh.service.FicheSeanceService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.popov.t04jh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FicheSeanceResource} REST controller.
 */
@SpringBootTest(classes = T04JhApp.class)
public class FicheSeanceResourceIT {

    private static final Long DEFAULT_FICHE_SEANCE = 1L;
    private static final Long UPDATED_FICHE_SEANCE = 2L;

    private static final String DEFAULT_GROUPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GROUPE_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_THEME_PRIMARY = "AAAAAAAAAA";
    private static final String UPDATED_THEME_PRIMARY = "BBBBBBBBBB";

    private static final String DEFAULT_THEME_SECONDARY = "AAAAAAAAAA";
    private static final String UPDATED_THEME_SECONDARY = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECTIF_PRIMARY = "AAAAAAAAAA";
    private static final String UPDATED_OBJECTIF_PRIMARY = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECTIF_SECONDARY = "AAAAAAAAAA";
    private static final String UPDATED_OBJECTIF_SECONDARY = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    private static final String DEFAULT_EXERCICE_ECHAUFFEMENT_1 = "AAAAAAAAAA";
    private static final String UPDATED_EXERCICE_ECHAUFFEMENT_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EXERCICE_ECHAUFFEMENT_2 = "AAAAAAAAAA";
    private static final String UPDATED_EXERCICE_ECHAUFFEMENT_2 = "BBBBBBBBBB";

    private static final String DEFAULT_EXERCICE_ECHAUFFEMENT_3 = "AAAAAAAAAA";
    private static final String UPDATED_EXERCICE_ECHAUFFEMENT_3 = "BBBBBBBBBB";

    private static final String DEFAULT_EXERCICE_PRIMARY_1 = "AAAAAAAAAA";
    private static final String UPDATED_EXERCICE_PRIMARY_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EXERCICE_PRIMARY_2 = "AAAAAAAAAA";
    private static final String UPDATED_EXERCICE_PRIMARY_2 = "BBBBBBBBBB";

    private static final String DEFAULT_EXERCICE_PRIMARY_3 = "AAAAAAAAAA";
    private static final String UPDATED_EXERCICE_PRIMARY_3 = "BBBBBBBBBB";

    private static final String DEFAULT_EXERCICE_PRIMARY_4 = "AAAAAAAAAA";
    private static final String UPDATED_EXERCICE_PRIMARY_4 = "BBBBBBBBBB";

    private static final String DEFAULT_EXERCICE_PRIMARY_5 = "AAAAAAAAAA";
    private static final String UPDATED_EXERCICE_PRIMARY_5 = "BBBBBBBBBB";

    private static final String DEFAULT_EXERCICE_PRIMARY_6 = "AAAAAAAAAA";
    private static final String UPDATED_EXERCICE_PRIMARY_6 = "BBBBBBBBBB";

    private static final String DEFAULT_EXERCICE_PRIMARY_7 = "AAAAAAAAAA";
    private static final String UPDATED_EXERCICE_PRIMARY_7 = "BBBBBBBBBB";

    private static final String DEFAULT_EXERCICE_PRIMARY_8 = "AAAAAAAAAA";
    private static final String UPDATED_EXERCICE_PRIMARY_8 = "BBBBBBBBBB";

    private static final Long DEFAULT_EXERCICE_FINALE_1 = 1L;
    private static final Long UPDATED_EXERCICE_FINALE_1 = 2L;

    private static final Long DEFAULT_EXERCICE_FINALE_2 = 1L;
    private static final Long UPDATED_EXERCICE_FINALE_2 = 2L;

    private static final String DEFAULT_BILAN = "AAAAAAAAAA";
    private static final String UPDATED_BILAN = "BBBBBBBBBB";

    @Autowired
    private FicheSeanceRepository ficheSeanceRepository;

    @Mock
    private FicheSeanceRepository ficheSeanceRepositoryMock;

    @Mock
    private FicheSeanceService ficheSeanceServiceMock;

    @Autowired
    private FicheSeanceService ficheSeanceService;

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

    private MockMvc restFicheSeanceMockMvc;

    private FicheSeance ficheSeance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FicheSeanceResource ficheSeanceResource = new FicheSeanceResource(ficheSeanceService);
        this.restFicheSeanceMockMvc = MockMvcBuilders.standaloneSetup(ficheSeanceResource)
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
    public static FicheSeance createEntity(EntityManager em) {
        FicheSeance ficheSeance = new FicheSeance()
            .ficheSeance(DEFAULT_FICHE_SEANCE)
            .groupeName(DEFAULT_GROUPE_NAME)
            .date(DEFAULT_DATE)
            .themePrimary(DEFAULT_THEME_PRIMARY)
            .themeSecondary(DEFAULT_THEME_SECONDARY)
            .objectifPrimary(DEFAULT_OBJECTIF_PRIMARY)
            .objectifSecondary(DEFAULT_OBJECTIF_SECONDARY)
            .observation(DEFAULT_OBSERVATION)
            .exerciceEchauffement1(DEFAULT_EXERCICE_ECHAUFFEMENT_1)
            .exerciceEchauffement2(DEFAULT_EXERCICE_ECHAUFFEMENT_2)
            .exerciceEchauffement3(DEFAULT_EXERCICE_ECHAUFFEMENT_3)
            .exercicePrimary1(DEFAULT_EXERCICE_PRIMARY_1)
            .exercicePrimary2(DEFAULT_EXERCICE_PRIMARY_2)
            .exercicePrimary3(DEFAULT_EXERCICE_PRIMARY_3)
            .exercicePrimary4(DEFAULT_EXERCICE_PRIMARY_4)
            .exercicePrimary5(DEFAULT_EXERCICE_PRIMARY_5)
            .exercicePrimary6(DEFAULT_EXERCICE_PRIMARY_6)
            .exercicePrimary7(DEFAULT_EXERCICE_PRIMARY_7)
            .exercicePrimary8(DEFAULT_EXERCICE_PRIMARY_8)
            .exerciceFinale1(DEFAULT_EXERCICE_FINALE_1)
            .exerciceFinale2(DEFAULT_EXERCICE_FINALE_2)
            .bilan(DEFAULT_BILAN);
        return ficheSeance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FicheSeance createUpdatedEntity(EntityManager em) {
        FicheSeance ficheSeance = new FicheSeance()
            .ficheSeance(UPDATED_FICHE_SEANCE)
            .groupeName(UPDATED_GROUPE_NAME)
            .date(UPDATED_DATE)
            .themePrimary(UPDATED_THEME_PRIMARY)
            .themeSecondary(UPDATED_THEME_SECONDARY)
            .objectifPrimary(UPDATED_OBJECTIF_PRIMARY)
            .objectifSecondary(UPDATED_OBJECTIF_SECONDARY)
            .observation(UPDATED_OBSERVATION)
            .exerciceEchauffement1(UPDATED_EXERCICE_ECHAUFFEMENT_1)
            .exerciceEchauffement2(UPDATED_EXERCICE_ECHAUFFEMENT_2)
            .exerciceEchauffement3(UPDATED_EXERCICE_ECHAUFFEMENT_3)
            .exercicePrimary1(UPDATED_EXERCICE_PRIMARY_1)
            .exercicePrimary2(UPDATED_EXERCICE_PRIMARY_2)
            .exercicePrimary3(UPDATED_EXERCICE_PRIMARY_3)
            .exercicePrimary4(UPDATED_EXERCICE_PRIMARY_4)
            .exercicePrimary5(UPDATED_EXERCICE_PRIMARY_5)
            .exercicePrimary6(UPDATED_EXERCICE_PRIMARY_6)
            .exercicePrimary7(UPDATED_EXERCICE_PRIMARY_7)
            .exercicePrimary8(UPDATED_EXERCICE_PRIMARY_8)
            .exerciceFinale1(UPDATED_EXERCICE_FINALE_1)
            .exerciceFinale2(UPDATED_EXERCICE_FINALE_2)
            .bilan(UPDATED_BILAN);
        return ficheSeance;
    }

    @BeforeEach
    public void initTest() {
        ficheSeance = createEntity(em);
    }

    @Test
    @Transactional
    public void createFicheSeance() throws Exception {
        int databaseSizeBeforeCreate = ficheSeanceRepository.findAll().size();

        // Create the FicheSeance
        restFicheSeanceMockMvc.perform(post("/api/fiche-seances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheSeance)))
            .andExpect(status().isCreated());

        // Validate the FicheSeance in the database
        List<FicheSeance> ficheSeanceList = ficheSeanceRepository.findAll();
        assertThat(ficheSeanceList).hasSize(databaseSizeBeforeCreate + 1);
        FicheSeance testFicheSeance = ficheSeanceList.get(ficheSeanceList.size() - 1);
        assertThat(testFicheSeance.getFicheSeance()).isEqualTo(DEFAULT_FICHE_SEANCE);
        assertThat(testFicheSeance.getGroupeName()).isEqualTo(DEFAULT_GROUPE_NAME);
        assertThat(testFicheSeance.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testFicheSeance.getThemePrimary()).isEqualTo(DEFAULT_THEME_PRIMARY);
        assertThat(testFicheSeance.getThemeSecondary()).isEqualTo(DEFAULT_THEME_SECONDARY);
        assertThat(testFicheSeance.getObjectifPrimary()).isEqualTo(DEFAULT_OBJECTIF_PRIMARY);
        assertThat(testFicheSeance.getObjectifSecondary()).isEqualTo(DEFAULT_OBJECTIF_SECONDARY);
        assertThat(testFicheSeance.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testFicheSeance.getExerciceEchauffement1()).isEqualTo(DEFAULT_EXERCICE_ECHAUFFEMENT_1);
        assertThat(testFicheSeance.getExerciceEchauffement2()).isEqualTo(DEFAULT_EXERCICE_ECHAUFFEMENT_2);
        assertThat(testFicheSeance.getExerciceEchauffement3()).isEqualTo(DEFAULT_EXERCICE_ECHAUFFEMENT_3);
        assertThat(testFicheSeance.getExercicePrimary1()).isEqualTo(DEFAULT_EXERCICE_PRIMARY_1);
        assertThat(testFicheSeance.getExercicePrimary2()).isEqualTo(DEFAULT_EXERCICE_PRIMARY_2);
        assertThat(testFicheSeance.getExercicePrimary3()).isEqualTo(DEFAULT_EXERCICE_PRIMARY_3);
        assertThat(testFicheSeance.getExercicePrimary4()).isEqualTo(DEFAULT_EXERCICE_PRIMARY_4);
        assertThat(testFicheSeance.getExercicePrimary5()).isEqualTo(DEFAULT_EXERCICE_PRIMARY_5);
        assertThat(testFicheSeance.getExercicePrimary6()).isEqualTo(DEFAULT_EXERCICE_PRIMARY_6);
        assertThat(testFicheSeance.getExercicePrimary7()).isEqualTo(DEFAULT_EXERCICE_PRIMARY_7);
        assertThat(testFicheSeance.getExercicePrimary8()).isEqualTo(DEFAULT_EXERCICE_PRIMARY_8);
        assertThat(testFicheSeance.getExerciceFinale1()).isEqualTo(DEFAULT_EXERCICE_FINALE_1);
        assertThat(testFicheSeance.getExerciceFinale2()).isEqualTo(DEFAULT_EXERCICE_FINALE_2);
        assertThat(testFicheSeance.getBilan()).isEqualTo(DEFAULT_BILAN);
    }

    @Test
    @Transactional
    public void createFicheSeanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ficheSeanceRepository.findAll().size();

        // Create the FicheSeance with an existing ID
        ficheSeance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFicheSeanceMockMvc.perform(post("/api/fiche-seances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheSeance)))
            .andExpect(status().isBadRequest());

        // Validate the FicheSeance in the database
        List<FicheSeance> ficheSeanceList = ficheSeanceRepository.findAll();
        assertThat(ficheSeanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFicheSeanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = ficheSeanceRepository.findAll().size();
        // set the field null
        ficheSeance.setFicheSeance(null);

        // Create the FicheSeance, which fails.

        restFicheSeanceMockMvc.perform(post("/api/fiche-seances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheSeance)))
            .andExpect(status().isBadRequest());

        List<FicheSeance> ficheSeanceList = ficheSeanceRepository.findAll();
        assertThat(ficheSeanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGroupeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ficheSeanceRepository.findAll().size();
        // set the field null
        ficheSeance.setGroupeName(null);

        // Create the FicheSeance, which fails.

        restFicheSeanceMockMvc.perform(post("/api/fiche-seances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheSeance)))
            .andExpect(status().isBadRequest());

        List<FicheSeance> ficheSeanceList = ficheSeanceRepository.findAll();
        assertThat(ficheSeanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = ficheSeanceRepository.findAll().size();
        // set the field null
        ficheSeance.setDate(null);

        // Create the FicheSeance, which fails.

        restFicheSeanceMockMvc.perform(post("/api/fiche-seances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheSeance)))
            .andExpect(status().isBadRequest());

        List<FicheSeance> ficheSeanceList = ficheSeanceRepository.findAll();
        assertThat(ficheSeanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFicheSeances() throws Exception {
        // Initialize the database
        ficheSeanceRepository.saveAndFlush(ficheSeance);

        // Get all the ficheSeanceList
        restFicheSeanceMockMvc.perform(get("/api/fiche-seances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ficheSeance.getId().intValue())))
            .andExpect(jsonPath("$.[*].ficheSeance").value(hasItem(DEFAULT_FICHE_SEANCE.intValue())))
            .andExpect(jsonPath("$.[*].groupeName").value(hasItem(DEFAULT_GROUPE_NAME)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].themePrimary").value(hasItem(DEFAULT_THEME_PRIMARY)))
            .andExpect(jsonPath("$.[*].themeSecondary").value(hasItem(DEFAULT_THEME_SECONDARY)))
            .andExpect(jsonPath("$.[*].objectifPrimary").value(hasItem(DEFAULT_OBJECTIF_PRIMARY)))
            .andExpect(jsonPath("$.[*].objectifSecondary").value(hasItem(DEFAULT_OBJECTIF_SECONDARY)))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)))
            .andExpect(jsonPath("$.[*].exerciceEchauffement1").value(hasItem(DEFAULT_EXERCICE_ECHAUFFEMENT_1)))
            .andExpect(jsonPath("$.[*].exerciceEchauffement2").value(hasItem(DEFAULT_EXERCICE_ECHAUFFEMENT_2)))
            .andExpect(jsonPath("$.[*].exerciceEchauffement3").value(hasItem(DEFAULT_EXERCICE_ECHAUFFEMENT_3)))
            .andExpect(jsonPath("$.[*].exercicePrimary1").value(hasItem(DEFAULT_EXERCICE_PRIMARY_1)))
            .andExpect(jsonPath("$.[*].exercicePrimary2").value(hasItem(DEFAULT_EXERCICE_PRIMARY_2)))
            .andExpect(jsonPath("$.[*].exercicePrimary3").value(hasItem(DEFAULT_EXERCICE_PRIMARY_3)))
            .andExpect(jsonPath("$.[*].exercicePrimary4").value(hasItem(DEFAULT_EXERCICE_PRIMARY_4)))
            .andExpect(jsonPath("$.[*].exercicePrimary5").value(hasItem(DEFAULT_EXERCICE_PRIMARY_5)))
            .andExpect(jsonPath("$.[*].exercicePrimary6").value(hasItem(DEFAULT_EXERCICE_PRIMARY_6)))
            .andExpect(jsonPath("$.[*].exercicePrimary7").value(hasItem(DEFAULT_EXERCICE_PRIMARY_7)))
            .andExpect(jsonPath("$.[*].exercicePrimary8").value(hasItem(DEFAULT_EXERCICE_PRIMARY_8)))
            .andExpect(jsonPath("$.[*].exerciceFinale1").value(hasItem(DEFAULT_EXERCICE_FINALE_1.intValue())))
            .andExpect(jsonPath("$.[*].exerciceFinale2").value(hasItem(DEFAULT_EXERCICE_FINALE_2.intValue())))
            .andExpect(jsonPath("$.[*].bilan").value(hasItem(DEFAULT_BILAN)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllFicheSeancesWithEagerRelationshipsIsEnabled() throws Exception {
        FicheSeanceResource ficheSeanceResource = new FicheSeanceResource(ficheSeanceServiceMock);
        when(ficheSeanceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restFicheSeanceMockMvc = MockMvcBuilders.standaloneSetup(ficheSeanceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFicheSeanceMockMvc.perform(get("/api/fiche-seances?eagerload=true"))
        .andExpect(status().isOk());

        verify(ficheSeanceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllFicheSeancesWithEagerRelationshipsIsNotEnabled() throws Exception {
        FicheSeanceResource ficheSeanceResource = new FicheSeanceResource(ficheSeanceServiceMock);
            when(ficheSeanceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restFicheSeanceMockMvc = MockMvcBuilders.standaloneSetup(ficheSeanceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFicheSeanceMockMvc.perform(get("/api/fiche-seances?eagerload=true"))
        .andExpect(status().isOk());

            verify(ficheSeanceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getFicheSeance() throws Exception {
        // Initialize the database
        ficheSeanceRepository.saveAndFlush(ficheSeance);

        // Get the ficheSeance
        restFicheSeanceMockMvc.perform(get("/api/fiche-seances/{id}", ficheSeance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ficheSeance.getId().intValue()))
            .andExpect(jsonPath("$.ficheSeance").value(DEFAULT_FICHE_SEANCE.intValue()))
            .andExpect(jsonPath("$.groupeName").value(DEFAULT_GROUPE_NAME))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.themePrimary").value(DEFAULT_THEME_PRIMARY))
            .andExpect(jsonPath("$.themeSecondary").value(DEFAULT_THEME_SECONDARY))
            .andExpect(jsonPath("$.objectifPrimary").value(DEFAULT_OBJECTIF_PRIMARY))
            .andExpect(jsonPath("$.objectifSecondary").value(DEFAULT_OBJECTIF_SECONDARY))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION))
            .andExpect(jsonPath("$.exerciceEchauffement1").value(DEFAULT_EXERCICE_ECHAUFFEMENT_1))
            .andExpect(jsonPath("$.exerciceEchauffement2").value(DEFAULT_EXERCICE_ECHAUFFEMENT_2))
            .andExpect(jsonPath("$.exerciceEchauffement3").value(DEFAULT_EXERCICE_ECHAUFFEMENT_3))
            .andExpect(jsonPath("$.exercicePrimary1").value(DEFAULT_EXERCICE_PRIMARY_1))
            .andExpect(jsonPath("$.exercicePrimary2").value(DEFAULT_EXERCICE_PRIMARY_2))
            .andExpect(jsonPath("$.exercicePrimary3").value(DEFAULT_EXERCICE_PRIMARY_3))
            .andExpect(jsonPath("$.exercicePrimary4").value(DEFAULT_EXERCICE_PRIMARY_4))
            .andExpect(jsonPath("$.exercicePrimary5").value(DEFAULT_EXERCICE_PRIMARY_5))
            .andExpect(jsonPath("$.exercicePrimary6").value(DEFAULT_EXERCICE_PRIMARY_6))
            .andExpect(jsonPath("$.exercicePrimary7").value(DEFAULT_EXERCICE_PRIMARY_7))
            .andExpect(jsonPath("$.exercicePrimary8").value(DEFAULT_EXERCICE_PRIMARY_8))
            .andExpect(jsonPath("$.exerciceFinale1").value(DEFAULT_EXERCICE_FINALE_1.intValue()))
            .andExpect(jsonPath("$.exerciceFinale2").value(DEFAULT_EXERCICE_FINALE_2.intValue()))
            .andExpect(jsonPath("$.bilan").value(DEFAULT_BILAN));
    }

    @Test
    @Transactional
    public void getNonExistingFicheSeance() throws Exception {
        // Get the ficheSeance
        restFicheSeanceMockMvc.perform(get("/api/fiche-seances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFicheSeance() throws Exception {
        // Initialize the database
        ficheSeanceService.save(ficheSeance);

        int databaseSizeBeforeUpdate = ficheSeanceRepository.findAll().size();

        // Update the ficheSeance
        FicheSeance updatedFicheSeance = ficheSeanceRepository.findById(ficheSeance.getId()).get();
        // Disconnect from session so that the updates on updatedFicheSeance are not directly saved in db
        em.detach(updatedFicheSeance);
        updatedFicheSeance
            .ficheSeance(UPDATED_FICHE_SEANCE)
            .groupeName(UPDATED_GROUPE_NAME)
            .date(UPDATED_DATE)
            .themePrimary(UPDATED_THEME_PRIMARY)
            .themeSecondary(UPDATED_THEME_SECONDARY)
            .objectifPrimary(UPDATED_OBJECTIF_PRIMARY)
            .objectifSecondary(UPDATED_OBJECTIF_SECONDARY)
            .observation(UPDATED_OBSERVATION)
            .exerciceEchauffement1(UPDATED_EXERCICE_ECHAUFFEMENT_1)
            .exerciceEchauffement2(UPDATED_EXERCICE_ECHAUFFEMENT_2)
            .exerciceEchauffement3(UPDATED_EXERCICE_ECHAUFFEMENT_3)
            .exercicePrimary1(UPDATED_EXERCICE_PRIMARY_1)
            .exercicePrimary2(UPDATED_EXERCICE_PRIMARY_2)
            .exercicePrimary3(UPDATED_EXERCICE_PRIMARY_3)
            .exercicePrimary4(UPDATED_EXERCICE_PRIMARY_4)
            .exercicePrimary5(UPDATED_EXERCICE_PRIMARY_5)
            .exercicePrimary6(UPDATED_EXERCICE_PRIMARY_6)
            .exercicePrimary7(UPDATED_EXERCICE_PRIMARY_7)
            .exercicePrimary8(UPDATED_EXERCICE_PRIMARY_8)
            .exerciceFinale1(UPDATED_EXERCICE_FINALE_1)
            .exerciceFinale2(UPDATED_EXERCICE_FINALE_2)
            .bilan(UPDATED_BILAN);

        restFicheSeanceMockMvc.perform(put("/api/fiche-seances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFicheSeance)))
            .andExpect(status().isOk());

        // Validate the FicheSeance in the database
        List<FicheSeance> ficheSeanceList = ficheSeanceRepository.findAll();
        assertThat(ficheSeanceList).hasSize(databaseSizeBeforeUpdate);
        FicheSeance testFicheSeance = ficheSeanceList.get(ficheSeanceList.size() - 1);
        assertThat(testFicheSeance.getFicheSeance()).isEqualTo(UPDATED_FICHE_SEANCE);
        assertThat(testFicheSeance.getGroupeName()).isEqualTo(UPDATED_GROUPE_NAME);
        assertThat(testFicheSeance.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testFicheSeance.getThemePrimary()).isEqualTo(UPDATED_THEME_PRIMARY);
        assertThat(testFicheSeance.getThemeSecondary()).isEqualTo(UPDATED_THEME_SECONDARY);
        assertThat(testFicheSeance.getObjectifPrimary()).isEqualTo(UPDATED_OBJECTIF_PRIMARY);
        assertThat(testFicheSeance.getObjectifSecondary()).isEqualTo(UPDATED_OBJECTIF_SECONDARY);
        assertThat(testFicheSeance.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testFicheSeance.getExerciceEchauffement1()).isEqualTo(UPDATED_EXERCICE_ECHAUFFEMENT_1);
        assertThat(testFicheSeance.getExerciceEchauffement2()).isEqualTo(UPDATED_EXERCICE_ECHAUFFEMENT_2);
        assertThat(testFicheSeance.getExerciceEchauffement3()).isEqualTo(UPDATED_EXERCICE_ECHAUFFEMENT_3);
        assertThat(testFicheSeance.getExercicePrimary1()).isEqualTo(UPDATED_EXERCICE_PRIMARY_1);
        assertThat(testFicheSeance.getExercicePrimary2()).isEqualTo(UPDATED_EXERCICE_PRIMARY_2);
        assertThat(testFicheSeance.getExercicePrimary3()).isEqualTo(UPDATED_EXERCICE_PRIMARY_3);
        assertThat(testFicheSeance.getExercicePrimary4()).isEqualTo(UPDATED_EXERCICE_PRIMARY_4);
        assertThat(testFicheSeance.getExercicePrimary5()).isEqualTo(UPDATED_EXERCICE_PRIMARY_5);
        assertThat(testFicheSeance.getExercicePrimary6()).isEqualTo(UPDATED_EXERCICE_PRIMARY_6);
        assertThat(testFicheSeance.getExercicePrimary7()).isEqualTo(UPDATED_EXERCICE_PRIMARY_7);
        assertThat(testFicheSeance.getExercicePrimary8()).isEqualTo(UPDATED_EXERCICE_PRIMARY_8);
        assertThat(testFicheSeance.getExerciceFinale1()).isEqualTo(UPDATED_EXERCICE_FINALE_1);
        assertThat(testFicheSeance.getExerciceFinale2()).isEqualTo(UPDATED_EXERCICE_FINALE_2);
        assertThat(testFicheSeance.getBilan()).isEqualTo(UPDATED_BILAN);
    }

    @Test
    @Transactional
    public void updateNonExistingFicheSeance() throws Exception {
        int databaseSizeBeforeUpdate = ficheSeanceRepository.findAll().size();

        // Create the FicheSeance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFicheSeanceMockMvc.perform(put("/api/fiche-seances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheSeance)))
            .andExpect(status().isBadRequest());

        // Validate the FicheSeance in the database
        List<FicheSeance> ficheSeanceList = ficheSeanceRepository.findAll();
        assertThat(ficheSeanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFicheSeance() throws Exception {
        // Initialize the database
        ficheSeanceService.save(ficheSeance);

        int databaseSizeBeforeDelete = ficheSeanceRepository.findAll().size();

        // Delete the ficheSeance
        restFicheSeanceMockMvc.perform(delete("/api/fiche-seances/{id}", ficheSeance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FicheSeance> ficheSeanceList = ficheSeanceRepository.findAll();
        assertThat(ficheSeanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
