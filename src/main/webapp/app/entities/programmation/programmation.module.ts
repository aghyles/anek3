import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { T04JhSharedModule } from 'app/shared/shared.module';
import { ProgrammationComponent } from './programmation.component';
import { ProgrammationDetailComponent } from './programmation-detail.component';
import { ProgrammationUpdateComponent } from './programmation-update.component';
import { ProgrammationDeleteDialogComponent } from './programmation-delete-dialog.component';
import { programmationRoute } from './programmation.route';

@NgModule({
  imports: [T04JhSharedModule, RouterModule.forChild(programmationRoute)],
  declarations: [ProgrammationComponent, ProgrammationDetailComponent, ProgrammationUpdateComponent, ProgrammationDeleteDialogComponent],
  entryComponents: [ProgrammationDeleteDialogComponent]
})
export class T04JhProgrammationModule {}
