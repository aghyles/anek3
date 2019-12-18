import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITestEtude } from 'app/shared/model/test-etude.model';

type EntityResponseType = HttpResponse<ITestEtude>;
type EntityArrayResponseType = HttpResponse<ITestEtude[]>;

@Injectable({ providedIn: 'root' })
export class TestEtudeService {
  public resourceUrl = SERVER_API_URL + 'api/test-etudes';

  constructor(protected http: HttpClient) {}

  create(testEtude: ITestEtude): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(testEtude);
    return this.http
      .post<ITestEtude>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(testEtude: ITestEtude): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(testEtude);
    return this.http
      .put<ITestEtude>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITestEtude>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITestEtude[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(testEtude: ITestEtude): ITestEtude {
    const copy: ITestEtude = Object.assign({}, testEtude, {
      dateExamen: testEtude.dateExamen != null && testEtude.dateExamen.isValid() ? testEtude.dateExamen.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateExamen = res.body.dateExamen != null ? moment(res.body.dateExamen) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((testEtude: ITestEtude) => {
        testEtude.dateExamen = testEtude.dateExamen != null ? moment(testEtude.dateExamen) : null;
      });
    }
    return res;
  }
}
