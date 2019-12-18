import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TestPerformance } from 'app/shared/model/test-performance.model';
import { TestPerformanceService } from './test-performance.service';
import { TestPerformanceComponent } from './test-performance.component';
import { TestPerformanceDetailComponent } from './test-performance-detail.component';
import { TestPerformanceUpdateComponent } from './test-performance-update.component';
import { ITestPerformance } from 'app/shared/model/test-performance.model';

@Injectable({ providedIn: 'root' })
export class TestPerformanceResolve implements Resolve<ITestPerformance> {
  constructor(private service: TestPerformanceService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITestPerformance> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((testPerformance: HttpResponse<TestPerformance>) => testPerformance.body));
    }
    return of(new TestPerformance());
  }
}

export const testPerformanceRoute: Routes = [
  {
    path: '',
    component: TestPerformanceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 't04JhApp.testPerformance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TestPerformanceDetailComponent,
    resolve: {
      testPerformance: TestPerformanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.testPerformance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TestPerformanceUpdateComponent,
    resolve: {
      testPerformance: TestPerformanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.testPerformance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TestPerformanceUpdateComponent,
    resolve: {
      testPerformance: TestPerformanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 't04JhApp.testPerformance.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
