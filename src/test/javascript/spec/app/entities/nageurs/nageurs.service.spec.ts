import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { NageursService } from 'app/entities/nageurs/nageurs.service';
import { INageurs, Nageurs } from 'app/shared/model/nageurs.model';
import { Sexe } from 'app/shared/model/enumerations/sexe.model';

describe('Service Tests', () => {
  describe('Nageurs Service', () => {
    let injector: TestBed;
    let service: NageursService;
    let httpMock: HttpTestingController;
    let elemDefault: INageurs;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(NageursService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Nageurs(
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        Sexe.HOMME,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            bearthday: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Nageurs', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            bearthday: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            bearthday: currentDate
          },
          returnedFromService
        );
        service
          .create(new Nageurs(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Nageurs', () => {
        const returnedFromService = Object.assign(
          {
            licenceNum: 'BBBBBB',
            groupeName: 'BBBBBB',
            document: 1,
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            sexe: 'BBBBBB',
            bearthday: currentDate.format(DATE_TIME_FORMAT),
            phoneNumber: 'BBBBBB',
            eMail: 'BBBBBB',
            address: 'BBBBBB',
            studyTime: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            bearthday: currentDate
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

      it('should return a list of Nageurs', () => {
        const returnedFromService = Object.assign(
          {
            licenceNum: 'BBBBBB',
            groupeName: 'BBBBBB',
            document: 1,
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            sexe: 'BBBBBB',
            bearthday: currentDate.format(DATE_TIME_FORMAT),
            phoneNumber: 'BBBBBB',
            eMail: 'BBBBBB',
            address: 'BBBBBB',
            studyTime: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            bearthday: currentDate
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

      it('should delete a Nageurs', () => {
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
