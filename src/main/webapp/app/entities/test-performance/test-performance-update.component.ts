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
import { ITestPerformance, TestPerformance } from 'app/shared/model/test-performance.model';
import { TestPerformanceService } from './test-performance.service';
import { ITestAutre } from 'app/shared/model/test-autre.model';
import { TestAutreService } from 'app/entities/test-autre/test-autre.service';
import { ITestEtude } from 'app/shared/model/test-etude.model';
import { TestEtudeService } from 'app/entities/test-etude/test-etude.service';
import { ITestNage } from 'app/shared/model/test-nage.model';
import { TestNageService } from 'app/entities/test-nage/test-nage.service';
import { IDocument } from 'app/shared/model/document.model';
import { DocumentService } from 'app/entities/document/document.service';
import { ITestPhysique } from 'app/shared/model/test-physique.model';
import { TestPhysiqueService } from 'app/entities/test-physique/test-physique.service';
import { INageurs } from 'app/shared/model/nageurs.model';
import { NageursService } from 'app/entities/nageurs/nageurs.service';

@Component({
  selector: 'jhi-test-performance-update',
  templateUrl: './test-performance-update.component.html'
})
export class TestPerformanceUpdateComponent implements OnInit {
  isSaving: boolean;

  testautres: ITestAutre[];

  testetudes: ITestEtude[];

  testnages: ITestNage[];

  documents: IDocument[];

  testphysiques: ITestPhysique[];

  nageurs: INageurs[];

  editForm = this.fb.group({
    id: [],
    typeTest: [null, [Validators.required]],
    date: [],
    idphotos: [],
    idtestDoc: [],
    testAutre: [],
    testEtude: [],
    testNage: [],
    document: [],
    testPhysique: [],
    nageurs: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected testPerformanceService: TestPerformanceService,
    protected testAutreService: TestAutreService,
    protected testEtudeService: TestEtudeService,
    protected testNageService: TestNageService,
    protected documentService: DocumentService,
    protected testPhysiqueService: TestPhysiqueService,
    protected nageursService: NageursService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ testPerformance }) => {
      this.updateForm(testPerformance);
    });
    this.testAutreService.query({ filter: 'testperformance-is-null' }).subscribe(
      (res: HttpResponse<ITestAutre[]>) => {
        if (!this.editForm.get('testAutre').value || !this.editForm.get('testAutre').value.id) {
          this.testautres = res.body;
        } else {
          this.testAutreService
            .find(this.editForm.get('testAutre').value.id)
            .subscribe(
              (subRes: HttpResponse<ITestAutre>) => (this.testautres = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.testEtudeService.query({ filter: 'testperformance-is-null' }).subscribe(
      (res: HttpResponse<ITestEtude[]>) => {
        if (!this.editForm.get('testEtude').value || !this.editForm.get('testEtude').value.id) {
          this.testetudes = res.body;
        } else {
          this.testEtudeService
            .find(this.editForm.get('testEtude').value.id)
            .subscribe(
              (subRes: HttpResponse<ITestEtude>) => (this.testetudes = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.testNageService.query({ filter: 'testperformance-is-null' }).subscribe(
      (res: HttpResponse<ITestNage[]>) => {
        if (!this.editForm.get('testNage').value || !this.editForm.get('testNage').value.id) {
          this.testnages = res.body;
        } else {
          this.testNageService
            .find(this.editForm.get('testNage').value.id)
            .subscribe(
              (subRes: HttpResponse<ITestNage>) => (this.testnages = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.documentService.query({ filter: 'testperformance-is-null' }).subscribe(
      (res: HttpResponse<IDocument[]>) => {
        if (!this.editForm.get('document').value || !this.editForm.get('document').value.id) {
          this.documents = res.body;
        } else {
          this.documentService
            .find(this.editForm.get('document').value.id)
            .subscribe(
              (subRes: HttpResponse<IDocument>) => (this.documents = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.testPhysiqueService.query({ filter: 'testperformance-is-null' }).subscribe(
      (res: HttpResponse<ITestPhysique[]>) => {
        if (!this.editForm.get('testPhysique').value || !this.editForm.get('testPhysique').value.id) {
          this.testphysiques = res.body;
        } else {
          this.testPhysiqueService
            .find(this.editForm.get('testPhysique').value.id)
            .subscribe(
              (subRes: HttpResponse<ITestPhysique>) => (this.testphysiques = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.nageursService
      .query()
      .subscribe((res: HttpResponse<INageurs[]>) => (this.nageurs = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(testPerformance: ITestPerformance) {
    this.editForm.patchValue({
      id: testPerformance.id,
      typeTest: testPerformance.typeTest,
      date: testPerformance.date != null ? testPerformance.date.format(DATE_TIME_FORMAT) : null,
      idphotos: testPerformance.idphotos,
      idtestDoc: testPerformance.idtestDoc,
      testAutre: testPerformance.testAutre,
      testEtude: testPerformance.testEtude,
      testNage: testPerformance.testNage,
      document: testPerformance.document,
      testPhysique: testPerformance.testPhysique,
      nageurs: testPerformance.nageurs
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const testPerformance = this.createFromForm();
    if (testPerformance.id !== undefined) {
      this.subscribeToSaveResponse(this.testPerformanceService.update(testPerformance));
    } else {
      this.subscribeToSaveResponse(this.testPerformanceService.create(testPerformance));
    }
  }

  private createFromForm(): ITestPerformance {
    return {
      ...new TestPerformance(),
      id: this.editForm.get(['id']).value,
      typeTest: this.editForm.get(['typeTest']).value,
      date: this.editForm.get(['date']).value != null ? moment(this.editForm.get(['date']).value, DATE_TIME_FORMAT) : undefined,
      idphotos: this.editForm.get(['idphotos']).value,
      idtestDoc: this.editForm.get(['idtestDoc']).value,
      testAutre: this.editForm.get(['testAutre']).value,
      testEtude: this.editForm.get(['testEtude']).value,
      testNage: this.editForm.get(['testNage']).value,
      document: this.editForm.get(['document']).value,
      testPhysique: this.editForm.get(['testPhysique']).value,
      nageurs: this.editForm.get(['nageurs']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITestPerformance>>) {
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

  trackTestAutreById(index: number, item: ITestAutre) {
    return item.id;
  }

  trackTestEtudeById(index: number, item: ITestEtude) {
    return item.id;
  }

  trackTestNageById(index: number, item: ITestNage) {
    return item.id;
  }

  trackDocumentById(index: number, item: IDocument) {
    return item.id;
  }

  trackTestPhysiqueById(index: number, item: ITestPhysique) {
    return item.id;
  }

  trackNageursById(index: number, item: INageurs) {
    return item.id;
  }
}
