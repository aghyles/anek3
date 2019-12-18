package com.popov.t04jh.web.rest;

import com.popov.t04jh.T04JhApp;
import com.popov.t04jh.domain.Nageur;
import com.popov.t04jh.repository.NageurRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.popov.t04jh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link NageurResource} REST controller.
 */
@SpringBootTest(classes = T04JhApp.class)
public class NageurResourceIT {

    private static final String DEFAULT_LICENCE = "AAAAAAAAAA";
    private static final String UPDATED_LICENCE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_TEL = 1;
    private static final Integer UPDATED_TEL = 2;

    private static final String DEFAULT_HAURAIRE_ETUDE = "AAAAAAAAAA";
    private static final String UPDATED_HAURAIRE_ETUDE = "BBBBBBBBBB";

    @Autowired
    private NageurRepository nageurRepository;

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

    private MockMvc restNageurMockMvc;

    private Nageur nageur;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NageurResource nageurResource = new NageurResource(nageurRepository);
        this.restNageurMockMvc = MockMvcBuilders.standaloneSetup(nageurResource)
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
    public static Nageur createEntity(EntityManager em) {
        Nageur nageur = new Nageur()
            .licence(DEFAULT_LICENCE)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .tel(DEFAULT_TEL)
            .hauraireEtude(DEFAULT_HAURAIRE_ETUDE);
        return nageur;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nageur createUpdatedEntity(EntityManager em) {
        Nageur nageur = new Nageur()
            .licence(UPDATED_LICENCE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .tel(UPDATED_TEL)
            .hauraireEtude(UPDATED_HAURAIRE_ETUDE);
        return nageur;
    }

    @BeforeEach
    public void initTest() {
        nageur = createEntity(em);
    }

    @Test
    @Transactional
    public void createNageur() throws Exception {
        int databaseSizeBeforeCreate = nageurRepository.findAll().size();

        // Create the Nageur
        restNageurMockMvc.perform(post("/api/nageurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nageur)))
            .andExpect(status().isCreated());

        // Validate the Nageur in the database
        List<Nageur> nageurList = nageurRepository.findAll();
        assertThat(nageurList).hasSize(databaseSizeBeforeCreate + 1);
        Nageur testNageur = nageurList.get(nageurList.size() - 1);
        assertThat(testNageur.getLicence()).isEqualTo(DEFAULT_LICENCE);
        assertThat(testNageur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testNageur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testNageur.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testNageur.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testNageur.getHauraireEtude()).isEqualTo(DEFAULT_HAURAIRE_ETUDE);
    }

    @Test
    @Transactional
    public void createNageurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nageurRepository.findAll().size();

        // Create the Nageur with an existing ID
        nageur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNageurMockMvc.perform(post("/api/nageurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nageur)))
            .andExpect(status().isBadRequest());

        // Validate the Nageur in the database
        List<Nageur> nageurList = nageurRepository.findAll();
        assertThat(nageurList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNageurs() throws Exception {
        // Initialize the database
        nageurRepository.saveAndFlush(nageur);

        // Get all the nageurList
        restNageurMockMvc.perform(get("/api/nageurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nageur.getId().intValue())))
            .andExpect(jsonPath("$.[*].licence").value(hasItem(DEFAULT_LICENCE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].date_naissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)))
            .andExpect(jsonPath("$.[*].hauraire_etude").value(hasItem(DEFAULT_HAURAIRE_ETUDE)));
    }

    @Test
    @Transactional
    public void getNageur() throws Exception {
        // Initialize the database
        nageurRepository.saveAndFlush(nageur);

        // Get the nageur
        restNageurMockMvc.perform(get("/api/nageurs/{id}", nageur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nageur.getId().intValue()))
            .andExpect(jsonPath("$.licence").value(DEFAULT_LICENCE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.date_naissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL))
            .andExpect(jsonPath("$.hauraire_etude").value(DEFAULT_HAURAIRE_ETUDE));
    }

    @Test
    @Transactional
    public void getNonExistingNageur() throws Exception {
        // Get the nageur
        restNageurMockMvc.perform(get("/api/nageurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNageur() throws Exception {
        // Initialize the database
        nageurRepository.saveAndFlush(nageur);

        int databaseSizeBeforeUpdate = nageurRepository.findAll().size();

        // Update the nageur
        Nageur updatedNageur = nageurRepository.findById(nageur.getId()).get();
        // Disconnect from session so that the updates on updatedNageur are not directly saved in db
        em.detach(updatedNageur);
        updatedNageur
            .licence(UPDATED_LICENCE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .tel(UPDATED_TEL)
            .hauraireEtude(UPDATED_HAURAIRE_ETUDE);

        restNageurMockMvc.perform(put("/api/nageurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNageur)))
            .andExpect(status().isOk());

        // Validate the Nageur in the database
        List<Nageur> nageurList = nageurRepository.findAll();
        assertThat(nageurList).hasSize(databaseSizeBeforeUpdate);
        Nageur testNageur = nageurList.get(nageurList.size() - 1);
        assertThat(testNageur.getLicence()).isEqualTo(UPDATED_LICENCE);
        assertThat(testNageur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testNageur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testNageur.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testNageur.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testNageur.getHauraireEtude()).isEqualTo(UPDATED_HAURAIRE_ETUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingNageur() throws Exception {
        int databaseSizeBeforeUpdate = nageurRepository.findAll().size();

        // Create the Nageur

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNageurMockMvc.perform(put("/api/nageurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nageur)))
            .andExpect(status().isBadRequest());

        // Validate the Nageur in the database
        List<Nageur> nageurList = nageurRepository.findAll();
        assertThat(nageurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNageur() throws Exception {
        // Initialize the database
        nageurRepository.saveAndFlush(nageur);

        int databaseSizeBeforeDelete = nageurRepository.findAll().size();

        // Delete the nageur
        restNageurMockMvc.perform(delete("/api/nageurs/{id}", nageur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nageur> nageurList = nageurRepository.findAll();
        assertThat(nageurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nageur.class);
        Nageur nageur1 = new Nageur();
        nageur1.setId(1L);
        Nageur nageur2 = new Nageur();
        nageur2.setId(nageur1.getId());
        assertThat(nageur1).isEqualTo(nageur2);
        nageur2.setId(2L);
        assertThat(nageur1).isNotEqualTo(nageur2);
        nageur1.setId(null);
        assertThat(nageur1).isNotEqualTo(nageur2);
    }
}
