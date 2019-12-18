import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { T04JhTestModule } from '../../../test.module';
import { GroupesUpdateComponent } from 'app/entities/groupes/groupes-update.component';
import { GroupesService } from 'app/entities/groupes/groupes.service';
import { Groupes } from 'app/shared/model/groupes.model';

describe('Component Tests', () => {
  describe('Groupes Management Update Component', () => {
    let comp: GroupesUpdateComponent;
    let fixture: ComponentFixture<GroupesUpdateComponent>;
    let service: GroupesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [T04JhTestModule],
        declarations: [GroupesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GroupesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GroupesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Groupes(123);
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
        const entity = new Groupes();
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
