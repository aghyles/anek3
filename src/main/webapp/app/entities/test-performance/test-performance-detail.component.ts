import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITestPerformance } from 'app/shared/model/test-performance.model';

@Component({
  selector: 'jhi-test-performance-detail',
  templateUrl: './test-performance-detail.component.html'
})
export class TestPerformanceDetailComponent implements OnInit {
  testPerformance: ITestPerformance;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ testPerformance }) => {
      this.testPerformance = testPerformance;
    });
  }

  previousState() {
    window.history.back();
  }
}
