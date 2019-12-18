import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ITestAutre, TestAutre } from 'app/shared/model/test-autre.model';
import { TestAutreService } from './test-autre.service';
import { ITestPerformance } from 'app/shared/model/test-performance.model';
import { TestPerformanceService } from 'app/entities/test-performance/test-performance.service';

@Component({
  selector: 'jhi-test-autre-update',
  templateUrl: './test-autre-update.component.html'
})
export class TestAutreUpdateComponent implements OnInit {
  isSaving: boolean;

  testperformances: ITestPerformance[];

  editForm = this.fb.group({
    id: [],
    testTitle: [null, [Validators.required]],
    typeTest: [],
    resultat: [],
    observation: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected testAutreService: TestAutreService,
    protected testPerformanceService: TestPerformanceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ testAutre }) => {
      this.updateForm(testAutre);
    });
    this.testPerformanceService
      .query()
      .subscribe(
        (res: HttpResponse<ITestPerformance[]>) => (this.testperformances = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(testAutre: ITestAutre) {
    this.editForm.patchValue({
      id: testAutre.id,
      testTitle: testAutre.testTitle,
      typeTest: testAutre.typeTest,
      resultat: testAutre.resultat,
      observation: testAutre.observation
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const testAutre = this.createFromForm();
    if (testAutre.id !== undefined) {
      this.subscribeToSaveResponse(this.testAutreService.update(testAutre));
    } else {
      this.subscribeToSaveResponse(this.testAutreService.create(testAutre));
    }
  }

  private createFromForm(): ITestAutre {
    return {
      ...new TestAutre(),
      id: this.editForm.get(['id']).value,
      testTitle: this.editForm.get(['testTitle']).value,
      typeTest: this.editForm.get(['typeTest']).value,
      resultat: this.editForm.get(['resultat']).value,
      observation: this.editForm.get(['observation']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITestAutre>>) {
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
