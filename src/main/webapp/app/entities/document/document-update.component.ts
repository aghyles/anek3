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
import { IDocument, Document } from 'app/shared/model/document.model';
import { DocumentService } from './document.service';
import { ITestPerformance } from 'app/shared/model/test-performance.model';
import { TestPerformanceService } from 'app/entities/test-performance/test-performance.service';
import { INageurs } from 'app/shared/model/nageurs.model';
import { NageursService } from 'app/entities/nageurs/nageurs.service';
import { IExercice } from 'app/shared/model/exercice.model';
import { ExerciceService } from 'app/entities/exercice/exercice.service';

@Component({
  selector: 'jhi-document-update',
  templateUrl: './document-update.component.html'
})
export class DocumentUpdateComponent implements OnInit {
  isSaving: boolean;

  testperformances: ITestPerformance[];

  nageurs: INageurs[];

  exercices: IExercice[];

  editForm = this.fb.group({
    id: [],
    docTitle: [null, [Validators.required]],
    dateDocument: [],
    linkDocument: [],
    typeDocument: [],
    nageurs: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected documentService: DocumentService,
    protected testPerformanceService: TestPerformanceService,
    protected nageursService: NageursService,
    protected exerciceService: ExerciceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ document }) => {
      this.updateForm(document);
    });
    this.testPerformanceService
      .query()
      .subscribe(
        (res: HttpResponse<ITestPerformance[]>) => (this.testperformances = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.nageursService
      .query()
      .subscribe((res: HttpResponse<INageurs[]>) => (this.nageurs = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.exerciceService
      .query()
      .subscribe((res: HttpResponse<IExercice[]>) => (this.exercices = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(document: IDocument) {
    this.editForm.patchValue({
      id: document.id,
      docTitle: document.docTitle,
      dateDocument: document.dateDocument != null ? document.dateDocument.format(DATE_TIME_FORMAT) : null,
      linkDocument: document.linkDocument,
      typeDocument: document.typeDocument,
      nageurs: document.nageurs
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const document = this.createFromForm();
    if (document.id !== undefined) {
      this.subscribeToSaveResponse(this.documentService.update(document));
    } else {
      this.subscribeToSaveResponse(this.documentService.create(document));
    }
  }

  private createFromForm(): IDocument {
    return {
      ...new Document(),
      id: this.editForm.get(['id']).value,
      docTitle: this.editForm.get(['docTitle']).value,
      dateDocument:
        this.editForm.get(['dateDocument']).value != null ? moment(this.editForm.get(['dateDocument']).value, DATE_TIME_FORMAT) : undefined,
      linkDocument: this.editForm.get(['linkDocument']).value,
      typeDocument: this.editForm.get(['typeDocument']).value,
      nageurs: this.editForm.get(['nageurs']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocument>>) {
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

  trackTestPerformanceById(index: number, item: ITestPerformance) {
    return item.id;
  }

  trackNageursById(index: number, item: INageurs) {
    return item.id;
  }

  trackExerciceById(index: number, item: IExercice) {
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
