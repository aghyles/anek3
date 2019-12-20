import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Swimer } from 'app/shared/model/swimer.model';
import { SwimerService } from './swimer.service';
import { SwimerComponent } from './swimer.component';
import { SwimerDetailComponent } from './swimer-detail.component';
import { SwimerUpdateComponent } from './swimer-update.component';
import { ISwimer } from 'app/shared/model/swimer.model';

@Injectable({ providedIn: 'root' })
export class SwimerResolve implements Resolve<ISwimer> {
  constructor(private service: SwimerService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISwimer> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((swimer: HttpResponse<Swimer>) => swimer.body));
    }
    return of(new Swimer());
  }
}

export const swimerRoute: Routes = [
  {
    path: '',
    component: SwimerComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.swimer.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SwimerDetailComponent,
    resolve: {
      swimer: SwimerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.swimer.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SwimerUpdateComponent,
    resolve: {
      swimer: SwimerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.swimer.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SwimerUpdateComponent,
    resolve: {
      swimer: SwimerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.swimer.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
