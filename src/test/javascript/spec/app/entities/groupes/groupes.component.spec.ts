import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { T04JhTestModule } from '../../../test.module';
import { GroupesComponent } from 'app/entities/groupes/groupes.component';
import { GroupesService } from 'app/entities/groupes/groupes.service';
import { Groupes } from 'app/shared/model/groupes.model';

describe('Component Tests', () => {
  describe('Groupes Management Component', () => {
    let comp: GroupesComponent;
    let fixture: ComponentFixture<GroupesComponent>;
    let service: GroupesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [T04JhTestModule],
        declarations: [GroupesComponent],
        providers: []
      })
        .overrideTemplate(GroupesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GroupesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Groupes(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.groupes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
