import { ITestPerformance } from 'app/shared/model/test-performance.model';

export interface ITestPhysique {
  id?: number;
  testPhy1?: string;
  testPhy2?: string;
  testPhy3?: string;
  testPhy4?: string;
  testPhy5?: string;
  testperformance?: ITestPerformance;
}

export class TestPhysique implements ITestPhysique {
  constructor(
    public id?: number,
    public testPhy1?: string,
    public testPhy2?: string,
    public testPhy3?: string,
    public testPhy4?: string,
    public testPhy5?: string,
    public testperformance?: ITestPerformance
  ) {}
}
