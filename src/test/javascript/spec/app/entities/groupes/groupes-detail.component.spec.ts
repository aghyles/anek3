import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { T04JhTestModule } from '../../../test.module';
import { GroupesDetailComponent } from 'app/entities/groupes/groupes-detail.component';
import { Groupes } from 'app/shared/model/groupes.model';

describe('Component Tests', () => {
  describe('Groupes Management Detail Component', () => {
    let comp: GroupesDetailComponent;
    let fixture: ComponentFixture<GroupesDetailComponent>;
    const route = ({ data: of({ groupes: new Groupes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [T04JhTestModule],
        declarations: [GroupesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GroupesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GroupesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.groupes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
