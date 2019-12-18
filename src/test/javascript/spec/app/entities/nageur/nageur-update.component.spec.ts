import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { T04JhTestModule } from '../../../test.module';
import { NageurUpdateComponent } from 'app/entities/nageur/nageur-update.component';
import { NageurService } from 'app/entities/nageur/nageur.service';
import { Nageur } from 'app/shared/model/nageur.model';

describe('Component Tests', () => {
  describe('Nageur Management Update Component', () => {
    let comp: NageurUpdateComponent;
    let fixture: ComponentFixture<NageurUpdateComponent>;
    let service: NageurService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [T04JhTestModule],
        declarations: [NageurUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NageurUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NageurUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NageurService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Nageur(123);
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
        const entity = new Nageur();
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
