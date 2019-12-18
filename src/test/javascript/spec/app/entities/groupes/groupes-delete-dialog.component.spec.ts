import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { T04JhTestModule } from '../../../test.module';
import { GroupesDeleteDialogComponent } from 'app/entities/groupes/groupes-delete-dialog.component';
import { GroupesService } from 'app/entities/groupes/groupes.service';

describe('Component Tests', () => {
  describe('Groupes Management Delete Component', () => {
    let comp: GroupesDeleteDialogComponent;
    let fixture: ComponentFixture<GroupesDeleteDialogComponent>;
    let service: GroupesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [T04JhTestModule],
        declarations: [GroupesDeleteDialogComponent]
      })
        .overrideTemplate(GroupesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GroupesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupesService);
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
