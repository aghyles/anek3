import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Groupes } from 'app/shared/model/groupes.model';
import { GroupesService } from './groupes.service';
import { GroupesComponent } from './groupes.component';
import { GroupesDetailComponent } from './groupes-detail.component';
import { GroupesUpdateComponent } from './groupes-update.component';
import { GroupesDeletePopupComponent } from './groupes-delete-dialog.component';
import { IGroupes } from 'app/shared/model/groupes.model';

@Injectable({ providedIn: 'root' })
export class GroupesResolve implements Resolve<IGroupes> {
  constructor(private service: GroupesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IGroupes> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Groupes>) => response.ok),
        map((groupes: HttpResponse<Groupes>) => groupes.body)
      );
    }
    return of(new Groupes());
  }
}

export const groupesRoute: Routes = [
  {
    path: '',
    component: GroupesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.groupes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GroupesDetailComponent,
    resolve: {
      groupes: GroupesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.groupes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GroupesUpdateComponent,
    resolve: {
      groupes: GroupesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.groupes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GroupesUpdateComponent,
    resolve: {
      groupes: GroupesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.groupes.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const groupesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: GroupesDeletePopupComponent,
    resolve: {
      groupes: GroupesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.groupes.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
