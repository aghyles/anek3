import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProgrammation } from 'app/shared/model/programmation.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ProgrammationService } from './programmation.service';
import { ProgrammationDeleteDialogComponent } from './programmation-delete-dialog.component';

@Component({
  selector: 'jhi-programmation',
  templateUrl: './programmation.component.html'
})
export class ProgrammationComponent implements OnInit, OnDestroy {
  programmations: IProgrammation[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected programmationService: ProgrammationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.programmations = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.programmationService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IProgrammation[]>) => this.paginateProgrammations(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.programmations = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInProgrammations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProgrammation) {
    return item.id;
  }

  registerChangeInProgrammations() {
    this.eventSubscriber = this.eventManager.subscribe('programmationListModification', () => this.reset());
  }

  delete(programmation: IProgrammation) {
    const modalRef = this.modalService.open(ProgrammationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.programmation = programmation;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateProgrammations(data: IProgrammation[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.programmations.push(data[i]);
    }
  }
}
