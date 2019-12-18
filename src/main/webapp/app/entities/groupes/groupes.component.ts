import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IGroupes } from 'app/shared/model/groupes.model';
import { AccountService } from 'app/core/auth/account.service';
import { GroupesService } from './groupes.service';

@Component({
  selector: 'jhi-groupes',
  templateUrl: './groupes.component.html'
})
export class GroupesComponent implements OnInit, OnDestroy {
  groupes: IGroupes[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected groupesService: GroupesService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.groupesService
      .query()
      .pipe(
        filter((res: HttpResponse<IGroupes[]>) => res.ok),
        map((res: HttpResponse<IGroupes[]>) => res.body)
      )
      .subscribe((res: IGroupes[]) => {
        this.groupes = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInGroupes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IGroupes) {
    return item.id;
  }

  registerChangeInGroupes() {
    this.eventSubscriber = this.eventManager.subscribe('groupesListModification', response => this.loadAll());
  }
}
