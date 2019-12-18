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
import { ITestEtude, TestEtude } from 'app/shared/model/test-etude.model';
import { TestEtudeService } from './test-etude.service';
import { ITestPerformance } from 'app/shared/model/test-performance.model';
import { TestPerformanceService } from 'app/entities/test-performance/test-performance.service';

@Component({
  selector: 'jhi-test-etude-update',
  templateUrl: './test-etude-update.component.html'
})
export class TestEtudeUpdateComponent implements OnInit {
  isSaving: boolean;

  testperformances: ITestPerformance[];

  editForm = this.fb.group({
    id: [],
    testTitle: [null, [Validators.required]],
    niveauEtude: [],
    dateExamen: [],
    average: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected testEtudeService: TestEtudeService,
    protected testPerformanceService: TestPerformanceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ testEtude }) => {
      this.updateForm(testEtude);
    });
    this.testPerformanceService
      .query()
      .subscribe(
        (res: HttpResponse<ITestPerformance[]>) => (this.testperformances = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(testEtude: ITestEtude) {
    this.editForm.patchValue({
      id: testEtude.id,
      testTitle: testEtude.testTitle,
      niveauEtude: testEtude.niveauEtude,
      dateExamen: testEtude.dateExamen != null ? testEtude.dateExamen.format(DATE_TIME_FORMAT) : null,
      average: testEtude.average
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const testEtude = this.createFromForm();
    if (testEtude.id !== undefined) {
      this.subscribeToSaveResponse(this.testEtudeService.update(testEtude));
    } else {
      this.subscribeToSaveResponse(this.testEtudeService.create(testEtude));
    }
  }

  private createFromForm(): ITestEtude {
    return {
      ...new TestEtude(),
      id: this.editForm.get(['id']).value,
      testTitle: this.editForm.get(['testTitle']).value,
      niveauEtude: this.editForm.get(['niveauEtude']).value,
      dateExamen:
        this.editForm.get(['dateExamen']).value != null ? moment(this.editForm.get(['dateExamen']).value, DATE_TIME_FORMAT) : undefined,
      average: this.editForm.get(['average']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITestEtude>>) {
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
}
