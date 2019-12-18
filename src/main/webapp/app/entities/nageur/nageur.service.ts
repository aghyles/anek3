import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INageur } from 'app/shared/model/nageur.model';

type EntityResponseType = HttpResponse<INageur>;
type EntityArrayResponseType = HttpResponse<INageur[]>;

@Injectable({ providedIn: 'root' })
export class NageurService {
  public resourceUrl = SERVER_API_URL + 'api/nageurs';

  constructor(protected http: HttpClient) {}

  create(nageur: INageur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nageur);
    return this.http
      .post<INageur>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(nageur: INageur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nageur);
    return this.http
      .put<INageur>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INageur>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INageur[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(nageur: INageur): INageur {
    const copy: INageur = Object.assign({}, nageur, {
      dateNaissance: nageur.dateNaissance != null && nageur.dateNaissance.isValid() ? nageur.dateNaissance.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateNaissance = res.body.dateNaissance != null ? moment(res.body.dateNaissance) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((nageur: INageur) => {
        nageur.dateNaissance = nageur.dateNaissance != null ? moment(nageur.dateNaissance) : null;
      });
    }
    return res;
  }
}
