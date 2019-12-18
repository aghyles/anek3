import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { T04JhTestModule } from '../../../test.module';
import { NageursUpdateComponent } from 'app/entities/nageurs/nageurs-update.component';
import { NageursService } from 'app/entities/nageurs/nageurs.service';
import { Nageurs } from 'app/shared/model/nageurs.model';

describe('Component Tests', () => {
  describe('Nageurs Management Update Component', () => {
    let comp: NageursUpdateComponent;
    let fixture: ComponentFixture<NageursUpdateComponent>;
    let service: NageursService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [T04JhTestModule],
        declarations: [NageursUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NageursUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NageursUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NageursService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Nageurs(123);
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
        const entity = new Nageurs();
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
