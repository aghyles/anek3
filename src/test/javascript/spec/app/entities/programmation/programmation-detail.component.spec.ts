import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { T04JhTestModule } from '../../../test.module';
import { ProgrammationDetailComponent } from 'app/entities/programmation/programmation-detail.component';
import { Programmation } from 'app/shared/model/programmation.model';

describe('Component Tests', () => {
  describe('Programmation Management Detail Component', () => {
    let comp: ProgrammationDetailComponent;
    let fixture: ComponentFixture<ProgrammationDetailComponent>;
    const route = ({ data: of({ programmation: new Programmation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [T04JhTestModule],
        declarations: [ProgrammationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProgrammationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProgrammationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.programmation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
