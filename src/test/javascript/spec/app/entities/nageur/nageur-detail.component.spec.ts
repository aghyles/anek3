import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { T04JhTestModule } from '../../../test.module';
import { NageurDetailComponent } from 'app/entities/nageur/nageur-detail.component';
import { Nageur } from 'app/shared/model/nageur.model';

describe('Component Tests', () => {
  describe('Nageur Management Detail Component', () => {
    let comp: NageurDetailComponent;
    let fixture: ComponentFixture<NageurDetailComponent>;
    const route = ({ data: of({ nageur: new Nageur(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [T04JhTestModule],
        declarations: [NageurDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NageurDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NageurDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nageur).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
