import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITestPerformance } from 'app/shared/model/test-performance.model';

type EntityResponseType = HttpResponse<ITestPerformance>;
type EntityArrayResponseType = HttpResponse<ITestPerformance[]>;

@Injectable({ providedIn: 'root' })
export class TestPerformanceService {
  public resourceUrl = SERVER_API_URL + 'api/test-performances';

  constructor(protected http: HttpClient) {}

  create(testPerformance: ITestPerformance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(testPerformance);
    return this.http
      .post<ITestPerformance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(testPerformance: ITestPerformance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(testPerformance);
    return this.http
      .put<ITestPerformance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITestPerformance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITestPerformance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(testPerformance: ITestPerformance): ITestPerformance {
    const copy: ITestPerformance = Object.assign({}, testPerformance, {
      date: testPerformance.date != null && testPerformance.date.isValid() ? testPerformance.date.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date != null ? moment(res.body.date) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((testPerformance: ITestPerformance) => {
        testPerformance.date = testPerformance.date != null ? moment(testPerformance.date) : null;
      });
    }
    return res;
  }
}
