import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'groupes',
        loadChildren: () => import('./groupes/groupes.module').then(m => m.T04JhGroupesModule)
      },
      {
        path: 'nageur',
        loadChildren: () => import('./nageur/nageur.module').then(m => m.T04JhNageurModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class T04JhEntityModule {}
