import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISwimer } from 'app/shared/model/swimer.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SwimerService } from './swimer.service';
import { SwimerDeleteDialogComponent } from './swimer-delete-dialog.component';

@Component({
  selector: 'jhi-swimer',
  templateUrl: './swimer.component.html'
})
export class SwimerComponent implements OnInit, OnDestroy {
  swimers: ISwimer[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected swimerService: SwimerService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.swimers = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.swimerService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ISwimer[]>) => this.paginateSwimers(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.swimers = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInSwimers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISwimer) {
    return item.id;
  }

  registerChangeInSwimers() {
    this.eventSubscriber = this.eventManager.subscribe('swimerListModification', () => this.reset());
  }

  delete(swimer: ISwimer) {
    const modalRef = this.modalService.open(SwimerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.swimer = swimer;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSwimers(data: ISwimer[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.swimers.push(data[i]);
    }
  }
}
