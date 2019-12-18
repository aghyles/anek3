import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { T04JhSharedModule } from 'app/shared/shared.module';
import { NageursComponent } from './nageurs.component';
import { NageursDetailComponent } from './nageurs-detail.component';
import { NageursUpdateComponent } from './nageurs-update.component';
import { NageursDeleteDialogComponent } from './nageurs-delete-dialog.component';
import { nageursRoute } from './nageurs.route';

@NgModule({
  imports: [T04JhSharedModule, RouterModule.forChild(nageursRoute)],
  declarations: [NageursComponent, NageursDetailComponent, NageursUpdateComponent, NageursDeleteDialogComponent],
  entryComponents: [NageursDeleteDialogComponent]
})
export class T04JhNageursModule {}
