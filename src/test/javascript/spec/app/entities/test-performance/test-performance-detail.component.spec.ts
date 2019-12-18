import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { T04JhTestModule } from '../../../test.module';
import { TestPerformanceDetailComponent } from 'app/entities/test-performance/test-performance-detail.component';
import { TestPerformance } from 'app/shared/model/test-performance.model';

describe('Component Tests', () => {
  describe('TestPerformance Management Detail Component', () => {
    let comp: TestPerformanceDetailComponent;
    let fixture: ComponentFixture<TestPerformanceDetailComponent>;
    const route = ({ data: of({ testPerformance: new TestPerformance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [T04JhTestModule],
        declarations: [TestPerformanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TestPerformanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TestPerformanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.testPerformance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
