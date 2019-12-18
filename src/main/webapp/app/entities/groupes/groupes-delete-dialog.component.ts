import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGroupes } from 'app/shared/model/groupes.model';
import { GroupesService } from './groupes.service';

@Component({
  selector: 'jhi-groupes-delete-dialog',
  templateUrl: './groupes-delete-dialog.component.html'
})
export class GroupesDeleteDialogComponent {
  groupes: IGroupes;

  constructor(protected groupesService: GroupesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.groupesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'groupesListModification',
        content: 'Deleted an groupes'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-groupes-delete-popup',
  template: ''
})
export class GroupesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ groupes }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(GroupesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.groupes = groupes;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/groupes', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/groupes', { outlets: { popup: null } }]);
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
