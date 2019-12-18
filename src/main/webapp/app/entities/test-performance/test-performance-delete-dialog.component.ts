import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITestPerformance } from 'app/shared/model/test-performance.model';
import { TestPerformanceService } from './test-performance.service';

@Component({
  templateUrl: './test-performance-delete-dialog.component.html'
})
export class TestPerformanceDeleteDialogComponent {
  testPerformance: ITestPerformance;

  constructor(
    protected testPerformanceService: TestPerformanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.testPerformanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'testPerformanceListModification',
        content: 'Deleted an testPerformance'
      });
      this.activeModal.dismiss(true);
    });
  }
}
