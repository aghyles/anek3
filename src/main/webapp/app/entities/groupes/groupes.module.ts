import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { T04JhSharedModule } from 'app/shared/shared.module';
import { GroupesComponent } from './groupes.component';
import { GroupesDetailComponent } from './groupes-detail.component';
import { GroupesUpdateComponent } from './groupes-update.component';
import { GroupesDeletePopupComponent, GroupesDeleteDialogComponent } from './groupes-delete-dialog.component';
import { groupesRoute, groupesPopupRoute } from './groupes.route';

const ENTITY_STATES = [...groupesRoute, ...groupesPopupRoute];

@NgModule({
  imports: [T04JhSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    GroupesComponent,
    GroupesDetailComponent,
    GroupesUpdateComponent,
    GroupesDeleteDialogComponent,
    GroupesDeletePopupComponent
  ],
  entryComponents: [GroupesDeleteDialogComponent]
})
export class T04JhGroupesModule {}
