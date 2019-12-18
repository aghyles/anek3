import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INageurs } from 'app/shared/model/nageurs.model';
import { NageursService } from './nageurs.service';

@Component({
  templateUrl: './nageurs-delete-dialog.component.html'
})
export class NageursDeleteDialogComponent {
  nageurs: INageurs;

  constructor(protected nageursService: NageursService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.nageursService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'nageursListModification',
        content: 'Deleted an nageurs'
      });
      this.activeModal.dismiss(true);
    });
  }
}
