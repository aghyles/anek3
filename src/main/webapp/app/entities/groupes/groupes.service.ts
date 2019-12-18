import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGroupes } from 'app/shared/model/groupes.model';

type EntityResponseType = HttpResponse<IGroupes>;
type EntityArrayResponseType = HttpResponse<IGroupes[]>;

@Injectable({ providedIn: 'root' })
export class GroupesService {
  public resourceUrl = SERVER_API_URL + 'api/groupes';

  constructor(protected http: HttpClient) {}

  create(groupes: IGroupes): Observable<EntityResponseType> {
    return this.http.post<IGroupes>(this.resourceUrl, groupes, { observe: 'response' });
  }

  update(groupes: IGroupes): Observable<EntityResponseType> {
    return this.http.put<IGroupes>(this.resourceUrl, groupes, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGroupes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGroupes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
