import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { T04JhTestModule } from '../../../test.module';
import { TestPerformanceUpdateComponent } from 'app/entities/test-performance/test-performance-update.component';
import { TestPerformanceService } from 'app/entities/test-performance/test-performance.service';
import { TestPerformance } from 'app/shared/model/test-performance.model';

describe('Component Tests', () => {
  describe('TestPerformance Management Update Component', () => {
    let comp: TestPerformanceUpdateComponent;
    let fixture: ComponentFixture<TestPerformanceUpdateComponent>;
    let service: TestPerformanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [T04JhTestModule],
        declarations: [TestPerformanceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TestPerformanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TestPerformanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TestPerformanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TestPerformance(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TestPerformance();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
