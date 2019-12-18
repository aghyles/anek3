import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INageur } from 'app/shared/model/nageur.model';
import { NageurService } from './nageur.service';

@Component({
  selector: 'jhi-nageur-delete-dialog',
  templateUrl: './nageur-delete-dialog.component.html'
})
export class NageurDeleteDialogComponent {
  nageur: INageur;

  constructor(protected nageurService: NageurService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.nageurService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'nageurListModification',
        content: 'Deleted an nageur'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-nageur-delete-popup',
  template: ''
})
export class NageurDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ nageur }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(NageurDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.nageur = nageur;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/nageur', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/nageur', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
