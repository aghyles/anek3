import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IProgrammation, Programmation } from 'app/shared/model/programmation.model';
import { ProgrammationService } from './programmation.service';
import { ITheme } from 'app/shared/model/theme.model';
import { ThemeService } from 'app/entities/theme/theme.service';
import { IGroupe } from 'app/shared/model/groupe.model';
import { GroupeService } from 'app/entities/groupe/groupe.service';

@Component({
  selector: 'jhi-programmation-update',
  templateUrl: './programmation-update.component.html'
})
export class ProgrammationUpdateComponent implements OnInit {
  isSaving: boolean;

  themes: ITheme[];

  groupes: IGroupe[];

  editForm = this.fb.group({
    id: [],
    programme: [],
    groupe: [null, [Validators.required]],
    documents: [],
    groupe: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected programmationService: ProgrammationService,
    protected themeService: ThemeService,
    protected groupeService: GroupeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ programmation }) => {
      this.updateForm(programmation);
    });
    this.themeService
      .query()
      .subscribe((res: HttpResponse<ITheme[]>) => (this.themes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.groupeService
      .query()
      .subscribe((res: HttpResponse<IGroupe[]>) => (this.groupes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(programmation: IProgrammation) {
    this.editForm.patchValue({
      id: programmation.id,
      programme: programmation.programme,
      groupe: programmation.groupe,
      documents: programmation.documents,
      groupe: programmation.groupe
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const programmation = this.createFromForm();
    if (programmation.id !== undefined) {
      this.subscribeToSaveResponse(this.programmationService.update(programmation));
    } else {
      this.subscribeToSaveResponse(this.programmationService.create(programmation));
    }
  }

  private createFromForm(): IProgrammation {
    return {
      ...new Programmation(),
      id: this.editForm.get(['id']).value,
      programme: this.editForm.get(['programme']).value,
      groupe: this.editForm.get(['groupe']).value,
      documents: this.editForm.get(['documents']).value,
      groupe: this.editForm.get(['groupe']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgrammation>>) {
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

  trackThemeById(index: number, item: ITheme) {
    return item.id;
  }

  trackGroupeById(index: number, item: IGroupe) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
