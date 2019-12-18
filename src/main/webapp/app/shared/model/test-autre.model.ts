import { ITestPerformance } from 'app/shared/model/test-performance.model';

export interface ITestAutre {
  id?: number;
  testTitle?: string;
  typeTest?: string;
  resultat?: string;
  observation?: string;
  testperformance?: ITestPerformance;
}

export class TestAutre implements ITestAutre {
  constructor(
    public id?: number,
    public testTitle?: string,
    public typeTest?: string,
    public resultat?: string,
    public observation?: string,
    public testperformance?: ITestPerformance
  ) {}
}
