import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INageurs } from 'app/shared/model/nageurs.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { NageursService } from './nageurs.service';
import { NageursDeleteDialogComponent } from './nageurs-delete-dialog.component';

@Component({
  selector: 'jhi-nageurs',
  templateUrl: './nageurs.component.html'
})
export class NageursComponent implements OnInit, OnDestroy {
  nageurs: INageurs[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected nageursService: NageursService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.nageurs = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.nageursService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<INageurs[]>) => this.paginateNageurs(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.nageurs = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInNageurs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: INageurs) {
    return item.id;
  }

  registerChangeInNageurs() {
    this.eventSubscriber = this.eventManager.subscribe('nageursListModification', () => this.reset());
  }

  delete(nageurs: INageurs) {
    const modalRef = this.modalService.open(NageursDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nageurs = nageurs;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateNageurs(data: INageurs[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.nageurs.push(data[i]);
    }
  }
}
