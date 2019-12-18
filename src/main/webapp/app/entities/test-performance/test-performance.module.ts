import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { T04JhSharedModule } from 'app/shared/shared.module';
import { TestPerformanceComponent } from './test-performance.component';
import { TestPerformanceDetailComponent } from './test-performance-detail.component';
import { TestPerformanceUpdateComponent } from './test-performance-update.component';
import { TestPerformanceDeleteDialogComponent } from './test-performance-delete-dialog.component';
import { testPerformanceRoute } from './test-performance.route';

@NgModule({
  imports: [T04JhSharedModule, RouterModule.forChild(testPerformanceRoute)],
  declarations: [
    TestPerformanceComponent,
    TestPerformanceDetailComponent,
    TestPerformanceUpdateComponent,
    TestPerformanceDeleteDialogComponent
  ],
  entryComponents: [TestPerformanceDeleteDialogComponent]
})
export class T04JhTestPerformanceModule {}
