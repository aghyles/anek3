import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ITestPhysique, TestPhysique } from 'app/shared/model/test-physique.model';
import { TestPhysiqueService } from './test-physique.service';
import { ITestPerformance } from 'app/shared/model/test-performance.model';
import { TestPerformanceService } from 'app/entities/test-performance/test-performance.service';

@Component({
  selector: 'jhi-test-physique-update',
  templateUrl: './test-physique-update.component.html'
})
export class TestPhysiqueUpdateComponent implements OnInit {
  isSaving: boolean;

  testperformances: ITestPerformance[];

  editForm = this.fb.group({
    id: [],
    testPhy1: [],
    testPhy2: [],
    testPhy3: [],
    testPhy4: [],
    testPhy5: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected testPhysiqueService: TestPhysiqueService,
    protected testPerformanceService: TestPerformanceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ testPhysique }) => {
      this.updateForm(testPhysique);
    });
    this.testPerformanceService
      .query()
      .subscribe(
        (res: HttpResponse<ITestPerformance[]>) => (this.testperformances = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(testPhysique: ITestPhysique) {
    this.editForm.patchValue({
      id: testPhysique.id,
      testPhy1: testPhysique.testPhy1,
      testPhy2: testPhysique.testPhy2,
      testPhy3: testPhysique.testPhy3,
      testPhy4: testPhysique.testPhy4,
      testPhy5: testPhysique.testPhy5
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const testPhysique = this.createFromForm();
    if (testPhysique.id !== undefined) {
      this.subscribeToSaveResponse(this.testPhysiqueService.update(testPhysique));
    } else {
      this.subscribeToSaveResponse(this.testPhysiqueService.create(testPhysique));
    }
  }

  private createFromForm(): ITestPhysique {
    return {
      ...new TestPhysique(),
      id: this.editForm.get(['id']).value,
      testPhy1: this.editForm.get(['testPhy1']).value,
      testPhy2: this.editForm.get(['testPhy2']).value,
      testPhy3: this.editForm.get(['testPhy3']).value,
      testPhy4: this.editForm.get(['testPhy4']).value,
      testPhy5: this.editForm.get(['testPhy5']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITestPhysique>>) {
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
