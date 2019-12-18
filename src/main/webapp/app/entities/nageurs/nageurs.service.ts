import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INageurs } from 'app/shared/model/nageurs.model';

type EntityResponseType = HttpResponse<INageurs>;
type EntityArrayResponseType = HttpResponse<INageurs[]>;

@Injectable({ providedIn: 'root' })
export class NageursService {
  public resourceUrl = SERVER_API_URL + 'api/nageurs';

  constructor(protected http: HttpClient) {}

  create(nageurs: INageurs): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nageurs);
    return this.http
      .post<INageurs>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(nageurs: INageurs): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nageurs);
    return this.http
      .put<INageurs>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INageurs>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INageurs[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(nageurs: INageurs): INageurs {
    const copy: INageurs = Object.assign({}, nageurs, {
      bearthday: nageurs.bearthday != null && nageurs.bearthday.isValid() ? nageurs.bearthday.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.bearthday = res.body.bearthday != null ? moment(res.body.bearthday) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((nageurs: INageurs) => {
        nageurs.bearthday = nageurs.bearthday != null ? moment(nageurs.bearthday) : null;
      });
    }
    return res;
  }
}
