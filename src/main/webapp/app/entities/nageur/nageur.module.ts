import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { T04JhSharedModule } from 'app/shared/shared.module';
import { NageurComponent } from './nageur.component';
import { NageurDetailComponent } from './nageur-detail.component';
import { NageurUpdateComponent } from './nageur-update.component';
import { NageurDeletePopupComponent, NageurDeleteDialogComponent } from './nageur-delete-dialog.component';
import { nageurRoute, nageurPopupRoute } from './nageur.route';

const ENTITY_STATES = [...nageurRoute, ...nageurPopupRoute];

@NgModule({
  imports: [T04JhSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [NageurComponent, NageurDetailComponent, NageurUpdateComponent, NageurDeleteDialogComponent, NageurDeletePopupComponent],
  entryComponents: [NageurDeleteDialogComponent]
})
export class T04JhNageurModule {}
