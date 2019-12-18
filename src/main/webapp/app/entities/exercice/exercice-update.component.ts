import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IExercice, Exercice } from 'app/shared/model/exercice.model';
import { ExerciceService } from './exercice.service';
import { IDocument } from 'app/shared/model/document.model';
import { DocumentService } from 'app/entities/document/document.service';
import { IObjectif } from 'app/shared/model/objectif.model';
import { ObjectifService } from 'app/entities/objectif/objectif.service';

@Component({
  selector: 'jhi-exercice-update',
  templateUrl: './exercice-update.component.html'
})
export class ExerciceUpdateComponent implements OnInit {
  isSaving: boolean;

  documents: IDocument[];

  objectifs: IObjectif[];

  editForm = this.fb.group({
    id: [],
    exerciceName: [null, [Validators.required]],
    description: [],
    idDocSchema: [],
    dosage: [],
    longDosage: [],
    recomendation: [],
    documents: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected exerciceService: ExerciceService,
    protected documentService: DocumentService,
    protected objectifService: ObjectifService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ exercice }) => {
      this.updateForm(exercice);
    });
    this.documentService
      .query()
      .subscribe((res: HttpResponse<IDocument[]>) => (this.documents = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.objectifService
      .query()
      .subscribe((res: HttpResponse<IObjectif[]>) => (this.objectifs = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(exercice: IExercice) {
    this.editForm.patchValue({
      id: exercice.id,
      exerciceName: exercice.exerciceName,
      description: exercice.description,
      idDocSchema: exercice.idDocSchema,
      dosage: exercice.dosage,
      longDosage: exercice.longDosage,
      recomendation: exercice.recomendation,
      documents: exercice.documents
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const exercice = this.createFromForm();
    if (exercice.id !== undefined) {
      this.subscribeToSaveResponse(this.exerciceService.update(exercice));
    } else {
      this.subscribeToSaveResponse(this.exerciceService.create(exercice));
    }
  }

  private createFromForm(): IExercice {
    return {
      ...new Exercice(),
      id: this.editForm.get(['id']).value,
      exerciceName: this.editForm.get(['exerciceName']).value,
      description: this.editForm.get(['description']).value,
      idDocSchema: this.editForm.get(['idDocSchema']).value,
      dosage: this.editForm.get(['dosage']).value,
      longDosage: this.editForm.get(['longDosage']).value,
      recomendation: this.editForm.get(['recomendation']).value,
      documents: this.editForm.get(['documents']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExercice>>) {
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

  trackDocumentById(index: number, item: IDocument) {
    return item.id;
  }

  trackObjectifById(index: number, item: IObjectif) {
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
