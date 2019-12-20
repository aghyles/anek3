import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISwimer } from 'app/shared/model/swimer.model';
import { SwimerService } from './swimer.service';

@Component({
  templateUrl: './swimer-delete-dialog.component.html'
})
export class SwimerDeleteDialogComponent {
  swimer: ISwimer;

  constructor(protected swimerService: SwimerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.swimerService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'swimerListModification',
        content: 'Deleted an swimer'
      });
      this.activeModal.dismiss(true);
    });
  }
}
