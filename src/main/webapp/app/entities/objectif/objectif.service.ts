import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IObjectif } from 'app/shared/model/objectif.model';

type EntityResponseType = HttpResponse<IObjectif>;
type EntityArrayResponseType = HttpResponse<IObjectif[]>;

@Injectable({ providedIn: 'root' })
export class ObjectifService {
  public resourceUrl = SERVER_API_URL + 'api/objectifs';

  constructor(protected http: HttpClient) {}

  create(objectif: IObjectif): Observable<EntityResponseType> {
    return this.http.post<IObjectif>(this.resourceUrl, objectif, { observe: 'response' });
  }

  update(objectif: IObjectif): Observable<EntityResponseType> {
    return this.http.put<IObjectif>(this.resourceUrl, objectif, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IObjectif>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IObjectif[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
