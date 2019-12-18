import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { INageurs, Nageurs } from 'app/shared/model/nageurs.model';
import { NageursService } from './nageurs.service';
import { IGroupe } from 'app/shared/model/groupe.model';
import { GroupeService } from 'app/entities/groupe/groupe.service';

@Component({
  selector: 'jhi-nageurs-update',
  templateUrl: './nageurs-update.component.html'
})
export class NageursUpdateComponent implements OnInit {
  isSaving: boolean;

  groupes: IGroupe[];

  editForm = this.fb.group({
    id: [],
    licenceNum: [],
    groupeName: [],
    document: [],
    firstName: [null, [Validators.required]],
    lastName: [null, [Validators.required]],
    sexe: [],
    bearthday: [],
    phoneNumber: [],
    eMail: [null, [Validators.pattern('^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$')]],
    address: [],
    studyTime: [],
    groupe: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected nageursService: NageursService,
    protected groupeService: GroupeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ nageurs }) => {
      this.updateForm(nageurs);
    });
    this.groupeService
      .query()
      .subscribe((res: HttpResponse<IGroupe[]>) => (this.groupes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(nageurs: INageurs) {
    this.editForm.patchValue({
      id: nageurs.id,
      licenceNum: nageurs.licenceNum,
      groupeName: nageurs.groupeName,
      document: nageurs.document,
      firstName: nageurs.firstName,
      lastName: nageurs.lastName,
      sexe: nageurs.sexe,
      bearthday: nageurs.bearthday != null ? nageurs.bearthday.format(DATE_TIME_FORMAT) : null,
      phoneNumber: nageurs.phoneNumber,
      eMail: nageurs.eMail,
      address: nageurs.address,
      studyTime: nageurs.studyTime,
      groupe: nageurs.groupe
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const nageurs = this.createFromForm();
    if (nageurs.id !== undefined) {
      this.subscribeToSaveResponse(this.nageursService.update(nageurs));
    } else {
      this.subscribeToSaveResponse(this.nageursService.create(nageurs));
    }
  }

  private createFromForm(): INageurs {
    return {
      ...new Nageurs(),
      id: this.editForm.get(['id']).value,
      licenceNum: this.editForm.get(['licenceNum']).value,
      groupeName: this.editForm.get(['groupeName']).value,
      document: this.editForm.get(['document']).value,
      firstName: this.editForm.get(['firstName']).value,
      lastName: this.editForm.get(['lastName']).value,
      sexe: this.editForm.get(['sexe']).value,
      bearthday:
        this.editForm.get(['bearthday']).value != null ? moment(this.editForm.get(['bearthday']).value, DATE_TIME_FORMAT) : undefined,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      eMail: this.editForm.get(['eMail']).value,
      address: this.editForm.get(['address']).value,
      studyTime: this.editForm.get(['studyTime']).value,
      groupe: this.editForm.get(['groupe']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INageurs>>) {
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

  trackGroupeById(index: number, item: IGroupe) {
    return item.id;
  }
}
