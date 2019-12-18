import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Programmation } from 'app/shared/model/programmation.model';
import { ProgrammationService } from './programmation.service';
import { ProgrammationComponent } from './programmation.component';
import { ProgrammationDetailComponent } from './programmation-detail.component';
import { ProgrammationUpdateComponent } from './programmation-update.component';
import { IProgrammation } from 'app/shared/model/programmation.model';

@Injectable({ providedIn: 'root' })
export class ProgrammationResolve implements Resolve<IProgrammation> {
  constructor(private service: ProgrammationService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProgrammation> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((programmation: HttpResponse<Programmation>) => programmation.body));
    }
    return of(new Programmation());
  }
}

export const programmationRoute: Routes = [
  {
    path: '',
    component: ProgrammationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.programmation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProgrammationDetailComponent,
    resolve: {
      programmation: ProgrammationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.programmation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProgrammationUpdateComponent,
    resolve: {
      programmation: ProgrammationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.programmation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProgrammationUpdateComponent,
    resolve: {
      programmation: ProgrammationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.programmation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
