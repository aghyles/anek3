import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TestEtudeService } from 'app/entities/test-etude/test-etude.service';
import { ITestEtude, TestEtude } from 'app/shared/model/test-etude.model';

describe('Service Tests', () => {
  describe('TestEtude Service', () => {
    let injector: TestBed;
    let service: TestEtudeService;
    let httpMock: HttpTestingController;
    let elemDefault: ITestEtude;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(TestEtudeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TestEtude(0, 'AAAAAAA', 'AAAAAAA', currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateExamen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a TestEtude', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateExamen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateExamen: currentDate
          },
          returnedFromService
        );
        service
          .create(new TestEtude(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a TestEtude', () => {
        const returnedFromService = Object.assign(
          {
            testTitle: 'BBBBBB',
            niveauEtude: 'BBBBBB',
            dateExamen: currentDate.format(DATE_TIME_FORMAT),
            average: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateExamen: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of TestEtude', () => {
        const returnedFromService = Object.assign(
          {
            testTitle: 'BBBBBB',
            niveauEtude: 'BBBBBB',
            dateExamen: currentDate.format(DATE_TIME_FORMAT),
            average: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateExamen: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TestEtude', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
