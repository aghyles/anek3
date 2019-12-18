import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Nageurs } from 'app/shared/model/nageurs.model';
import { NageursService } from './nageurs.service';
import { NageursComponent } from './nageurs.component';
import { NageursDetailComponent } from './nageurs-detail.component';
import { NageursUpdateComponent } from './nageurs-update.component';
import { INageurs } from 'app/shared/model/nageurs.model';

@Injectable({ providedIn: 'root' })
export class NageursResolve implements Resolve<INageurs> {
  constructor(private service: NageursService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INageurs> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((nageurs: HttpResponse<Nageurs>) => nageurs.body));
    }
    return of(new Nageurs());
  }
}

export const nageursRoute: Routes = [
  {
    path: '',
    component: NageursComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.nageurs.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NageursDetailComponent,
    resolve: {
      nageurs: NageursResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.nageurs.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NageursUpdateComponent,
    resolve: {
      nageurs: NageursResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.nageurs.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NageursUpdateComponent,
    resolve: {
      nageurs: NageursResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.nageurs.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
