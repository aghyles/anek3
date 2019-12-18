import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { T04JhTestModule } from '../../../test.module';
import { NageursDetailComponent } from 'app/entities/nageurs/nageurs-detail.component';
import { Nageurs } from 'app/shared/model/nageurs.model';

describe('Component Tests', () => {
  describe('Nageurs Management Detail Component', () => {
    let comp: NageursDetailComponent;
    let fixture: ComponentFixture<NageursDetailComponent>;
    const route = ({ data: of({ nageurs: new Nageurs(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [T04JhTestModule],
        declarations: [NageursDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NageursDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NageursDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nageurs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
