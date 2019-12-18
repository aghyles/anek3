import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { T04JhTestModule } from '../../../test.module';
import { TestPerformanceDeleteDialogComponent } from 'app/entities/test-performance/test-performance-delete-dialog.component';
import { TestPerformanceService } from 'app/entities/test-performance/test-performance.service';

describe('Component Tests', () => {
  describe('TestPerformance Management Delete Component', () => {
    let comp: TestPerformanceDeleteDialogComponent;
    let fixture: ComponentFixture<TestPerformanceDeleteDialogComponent>;
    let service: TestPerformanceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [T04JhTestModule],
        declarations: [TestPerformanceDeleteDialogComponent]
      })
        .overrideTemplate(TestPerformanceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TestPerformanceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TestPerformanceService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
