import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'groupe',
        loadChildren: () => import('./groupe/groupe.module').then(m => m.T04JhGroupeModule)
      },
      {
        path: 'nageurs',
        loadChildren: () => import('./nageurs/nageurs.module').then(m => m.T04JhNageursModule)
      },
      {
        path: 'mesure-anthropo',
        loadChildren: () => import('./mesure-anthropo/mesure-anthropo.module').then(m => m.T04JhMesureAnthropoModule)
      },
      {
        path: 'document',
        loadChildren: () => import('./document/document.module').then(m => m.T04JhDocumentModule)
      },
      {
        path: 'test-performance',
        loadChildren: () => import('./test-performance/test-performance.module').then(m => m.T04JhTestPerformanceModule)
      },
      {
        path: 'test-nage',
        loadChildren: () => import('./test-nage/test-nage.module').then(m => m.T04JhTestNageModule)
      },
      {
        path: 'test-autre',
        loadChildren: () => import('./test-autre/test-autre.module').then(m => m.T04JhTestAutreModule)
      },
      {
        path: 'test-etude',
        loadChildren: () => import('./test-etude/test-etude.module').then(m => m.T04JhTestEtudeModule)
      },
      {
        path: 'test-physique',
        loadChildren: () => import('./test-physique/test-physique.module').then(m => m.T04JhTestPhysiqueModule)
      },
      {
        path: 'fiche-seance',
        loadChildren: () => import('./fiche-seance/fiche-seance.module').then(m => m.T04JhFicheSeanceModule)
      },
      {
        path: 'presence',
        loadChildren: () => import('./presence/presence.module').then(m => m.T04JhPresenceModule)
      },
      {
        path: 'programmation',
        loadChildren: () => import('./programmation/programmation.module').then(m => m.T04JhProgrammationModule)
      },
      {
        path: 'theme',
        loadChildren: () => import('./theme/theme.module').then(m => m.T04JhThemeModule)
      },
      {
        path: 'objectif',
        loadChildren: () => import('./objectif/objectif.module').then(m => m.T04JhObjectifModule)
      },
      {
        path: 'exercice',
        loadChildren: () => import('./exercice/exercice.module').then(m => m.T04JhExerciceModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class T04JhEntityModule {}
