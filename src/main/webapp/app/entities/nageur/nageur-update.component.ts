import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { INageur, Nageur } from 'app/shared/model/nageur.model';
import { NageurService } from './nageur.service';
import { IGroupes } from 'app/shared/model/groupes.model';
import { GroupesService } from 'app/entities/groupes/groupes.service';

@Component({
  selector: 'jhi-nageur-update',
  templateUrl: './nageur-update.component.html'
})
export class NageurUpdateComponent implements OnInit {
  isSaving: boolean;

  groupes: IGroupes[];
  dateNaissanceDp: any;

  editForm = this.fb.group({
    id: [],
    licence: [],
    nom: [],
    prenom: [],
    dateNaissance: [],
    tel: [],
    hauraireEtude: [],
    groupes: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected nageurService: NageurService,
    protected groupesService: GroupesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ nageur }) => {
      this.updateForm(nageur);
    });
    this.groupesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IGroupes[]>) => mayBeOk.ok),
        map((response: HttpResponse<IGroupes[]>) => response.body)
      )
      .subscribe((res: IGroupes[]) => (this.groupes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(nageur: INageur) {
    this.editForm.patchValue({
      id: nageur.id,
      licence: nageur.licence,
      nom: nageur.nom,
      prenom: nageur.prenom,
      dateNaissance: nageur.dateNaissance,
      tel: nageur.tel,
      hauraireEtude: nageur.hauraireEtude,
      groupes: nageur.groupes
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const nageur = this.createFromForm();
    if (nageur.id !== undefined) {
      this.subscribeToSaveResponse(this.nageurService.update(nageur));
    } else {
      this.subscribeToSaveResponse(this.nageurService.create(nageur));
    }
  }

  private createFromForm(): INageur {
    return {
      ...new Nageur(),
      id: this.editForm.get(['id']).value,
      licence: this.editForm.get(['licence']).value,
      nom: this.editForm.get(['nom']).value,
      prenom: this.editForm.get(['prenom']).value,
      dateNaissance: this.editForm.get(['dateNaissance']).value,
      tel: this.editForm.get(['tel']).value,
      hauraireEtude: this.editForm.get(['hauraireEtude']).value,
      groupes: this.editForm.get(['groupes']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INageur>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackGroupesById(index: number, item: IGroupes) {
    return item.id;
  }
}
