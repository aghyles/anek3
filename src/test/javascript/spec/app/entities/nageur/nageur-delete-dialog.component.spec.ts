import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { T04JhTestModule } from '../../../test.module';
import { NageurDeleteDialogComponent } from 'app/entities/nageur/nageur-delete-dialog.component';
import { NageurService } from 'app/entities/nageur/nageur.service';

describe('Component Tests', () => {
  describe('Nageur Management Delete Component', () => {
    let comp: NageurDeleteDialogComponent;
    let fixture: ComponentFixture<NageurDeleteDialogComponent>;
    let service: NageurService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [T04JhTestModule],
        declarations: [NageurDeleteDialogComponent]
      })
        .overrideTemplate(NageurDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NageurDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NageurService);
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
