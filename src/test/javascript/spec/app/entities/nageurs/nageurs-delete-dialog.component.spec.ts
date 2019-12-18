import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { T04JhTestModule } from '../../../test.module';
import { NageursDeleteDialogComponent } from 'app/entities/nageurs/nageurs-delete-dialog.component';
import { NageursService } from 'app/entities/nageurs/nageurs.service';

describe('Component Tests', () => {
  describe('Nageurs Management Delete Component', () => {
    let comp: NageursDeleteDialogComponent;
    let fixture: ComponentFixture<NageursDeleteDialogComponent>;
    let service: NageursService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [T04JhTestModule],
        declarations: [NageursDeleteDialogComponent]
      })
        .overrideTemplate(NageursDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NageursDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NageursService);
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
