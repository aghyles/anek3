import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Nageur } from 'app/shared/model/nageur.model';
import { NageurService } from './nageur.service';
import { NageurComponent } from './nageur.component';
import { NageurDetailComponent } from './nageur-detail.component';
import { NageurUpdateComponent } from './nageur-update.component';
import { NageurDeletePopupComponent } from './nageur-delete-dialog.component';
import { INageur } from 'app/shared/model/nageur.model';

@Injectable({ providedIn: 'root' })
export class NageurResolve implements Resolve<INageur> {
  constructor(private service: NageurService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INageur> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Nageur>) => response.ok),
        map((nageur: HttpResponse<Nageur>) => nageur.body)
      );
    }
    return of(new Nageur());
  }
}

export const nageurRoute: Routes = [
  {
    path: '',
    component: NageurComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 't04JhApp.nageur.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NageurDetailComponent,
    resolve: {
      nageur: NageurResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.nageur.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NageurUpdateComponent,
    resolve: {
      nageur: NageurResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.nageur.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NageurUpdateComponent,
    resolve: {
      nageur: NageurResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.nageur.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const nageurPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: NageurDeletePopupComponent,
    resolve: {
      nageur: NageurResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.nageur.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
