import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { T04JhSharedModule } from 'app/shared/shared.module';
import { SwimerComponent } from './swimer.component';
import { SwimerDetailComponent } from './swimer-detail.component';
import { SwimerUpdateComponent } from './swimer-update.component';
import { SwimerDeleteDialogComponent } from './swimer-delete-dialog.component';
import { swimerRoute } from './swimer.route';

@NgModule({
  imports: [T04JhSharedModule, RouterModule.forChild(swimerRoute)],
  declarations: [SwimerComponent, SwimerDetailComponent, SwimerUpdateComponent, SwimerDeleteDialogComponent],
  entryComponents: [SwimerDeleteDialogComponent]
})
export class T04JhSwimerModule {}
