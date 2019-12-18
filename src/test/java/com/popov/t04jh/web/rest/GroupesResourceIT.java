package com.popov.t04jh.web.rest;

import com.popov.t04jh.T04JhApp;
import com.popov.t04jh.domain.Groupes;
import com.popov.t04jh.repository.GroupesRepository;
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
import java.util.List;

import static com.popov.t04jh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GroupesResource} REST controller.
 */
@SpringBootTest(classes = T04JhApp.class)
public class GroupesResourceIT {

    private static final String DEFAULT_GROUPES = "AAAAAAAAAA";
    private static final String UPDATED_GROUPES = "BBBBBBBBBB";

    private static final String DEFAULT_SAISON = "AAAAAAAAAA";
    private static final String UPDATED_SAISON = "BBBBBBBBBB";

    @Autowired
    private GroupesRepository groupesRepository;

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

    private MockMvc restGroupesMockMvc;

    private Groupes groupes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GroupesResource groupesResource = new GroupesResource(groupesRepository);
        this.restGroupesMockMvc = MockMvcBuilders.standaloneSetup(groupesResource)
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
    public static Groupes createEntity(EntityManager em) {
        Groupes groupes = new Groupes()
            .groupes(DEFAULT_GROUPES)
            .saison(DEFAULT_SAISON);
        return groupes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Groupes createUpdatedEntity(EntityManager em) {
        Groupes groupes = new Groupes()
            .groupes(UPDATED_GROUPES)
            .saison(UPDATED_SAISON);
        return groupes;
    }

    @BeforeEach
    public void initTest() {
        groupes = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupes() throws Exception {
        int databaseSizeBeforeCreate = groupesRepository.findAll().size();

        // Create the Groupes
        restGroupesMockMvc.perform(post("/api/groupes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupes)))
            .andExpect(status().isCreated());

        // Validate the Groupes in the database
        List<Groupes> groupesList = groupesRepository.findAll();
        assertThat(groupesList).hasSize(databaseSizeBeforeCreate + 1);
        Groupes testGroupes = groupesList.get(groupesList.size() - 1);
        assertThat(testGroupes.getGroupes()).isEqualTo(DEFAULT_GROUPES);
        assertThat(testGroupes.getSaison()).isEqualTo(DEFAULT_SAISON);
    }

    @Test
    @Transactional
    public void createGroupesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupesRepository.findAll().size();

        // Create the Groupes with an existing ID
        groupes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupesMockMvc.perform(post("/api/groupes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupes)))
            .andExpect(status().isBadRequest());

        // Validate the Groupes in the database
        List<Groupes> groupesList = groupesRepository.findAll();
        assertThat(groupesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGroupes() throws Exception {
        // Initialize the database
        groupesRepository.saveAndFlush(groupes);

        // Get all the groupesList
        restGroupesMockMvc.perform(get("/api/groupes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupes.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupes").value(hasItem(DEFAULT_GROUPES)))
            .andExpect(jsonPath("$.[*].saison").value(hasItem(DEFAULT_SAISON)));
    }
    
    @Test
    @Transactional
    public void getGroupes() throws Exception {
        // Initialize the database
        groupesRepository.saveAndFlush(groupes);

        // Get the groupes
        restGroupesMockMvc.perform(get("/api/groupes/{id}", groupes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(groupes.getId().intValue()))
            .andExpect(jsonPath("$.groupes").value(DEFAULT_GROUPES))
            .andExpect(jsonPath("$.saison").value(DEFAULT_SAISON));
    }

    @Test
    @Transactional
    public void getNonExistingGroupes() throws Exception {
        // Get the groupes
        restGroupesMockMvc.perform(get("/api/groupes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupes() throws Exception {
        // Initialize the database
        groupesRepository.saveAndFlush(groupes);

        int databaseSizeBeforeUpdate = groupesRepository.findAll().size();

        // Update the groupes
        Groupes updatedGroupes = groupesRepository.findById(groupes.getId()).get();
        // Disconnect from session so that the updates on updatedGroupes are not directly saved in db
        em.detach(updatedGroupes);
        updatedGroupes
            .groupes(UPDATED_GROUPES)
            .saison(UPDATED_SAISON);

        restGroupesMockMvc.perform(put("/api/groupes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGroupes)))
            .andExpect(status().isOk());

        // Validate the Groupes in the database
        List<Groupes> groupesList = groupesRepository.findAll();
        assertThat(groupesList).hasSize(databaseSizeBeforeUpdate);
        Groupes testGroupes = groupesList.get(groupesList.size() - 1);
        assertThat(testGroupes.getGroupes()).isEqualTo(UPDATED_GROUPES);
        assertThat(testGroupes.getSaison()).isEqualTo(UPDATED_SAISON);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupes() throws Exception {
        int databaseSizeBeforeUpdate = groupesRepository.findAll().size();

        // Create the Groupes

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupesMockMvc.perform(put("/api/groupes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupes)))
            .andExpect(status().isBadRequest());

        // Validate the Groupes in the database
        List<Groupes> groupesList = groupesRepository.findAll();
        assertThat(groupesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupes() throws Exception {
        // Initialize the database
        groupesRepository.saveAndFlush(groupes);

        int databaseSizeBeforeDelete = groupesRepository.findAll().size();

        // Delete the groupes
        restGroupesMockMvc.perform(delete("/api/groupes/{id}", groupes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Groupes> groupesList = groupesRepository.findAll();
        assertThat(groupesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Groupes.class);
        Groupes groupes1 = new Groupes();
        groupes1.setId(1L);
        Groupes groupes2 = new Groupes();
        groupes2.setId(groupes1.getId());
        assertThat(groupes1).isEqualTo(groupes2);
        groupes2.setId(2L);
        assertThat(groupes1).isNotEqualTo(groupes2);
        groupes1.setId(null);
        assertThat(groupes1).isNotEqualTo(groupes2);
    }
}
