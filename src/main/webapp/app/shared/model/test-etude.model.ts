import { Moment } from 'moment';
import { ITestPerformance } from 'app/shared/model/test-performance.model';

export interface ITestEtude {
  id?: number;
  testTitle?: string;
  niveauEtude?: string;
  dateExamen?: Moment;
  average?: number;
  testperformance?: ITestPerformance;
}

export class TestEtude implements ITestEtude {
  constructor(
    public id?: number,
    public testTitle?: string,
    public niveauEtude?: string,
    public dateExamen?: Moment,
    public average?: number,
    public testperformance?: ITestPerformance
  ) {}
}
