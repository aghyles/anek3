import { Moment } from 'moment';
import { ITestAutre } from 'app/shared/model/test-autre.model';
import { ITestEtude } from 'app/shared/model/test-etude.model';
import { ITestNage } from 'app/shared/model/test-nage.model';
import { IDocument } from 'app/shared/model/document.model';
import { ITestPhysique } from 'app/shared/model/test-physique.model';
import { INageurs } from 'app/shared/model/nageurs.model';

export interface ITestPerformance {
  id?: number;
  typeTest?: string;
  date?: Moment;
  idphotos?: number;
  idtestDoc?: number;
  testAutre?: ITestAutre;
  testEtude?: ITestEtude;
  testNage?: ITestNage;
  document?: IDocument;
  testPhysique?: ITestPhysique;
  nageurs?: INageurs;
}

export class TestPerformance implements ITestPerformance {
  constructor(
    public id?: number,
    public typeTest?: string,
    public date?: Moment,
    public idphotos?: number,
    public idtestDoc?: number,
    public testAutre?: ITestAutre,
    public testEtude?: ITestEtude,
    public testNage?: ITestNage,
    public document?: IDocument,
    public testPhysique?: ITestPhysique,
    public nageurs?: INageurs
  ) {}
}
