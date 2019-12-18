import { Moment } from 'moment';
import { IMesureAnthropo } from 'app/shared/model/mesure-anthropo.model';
import { ITestPerformance } from 'app/shared/model/test-performance.model';
import { IDocument } from 'app/shared/model/document.model';
import { IGroupe } from 'app/shared/model/groupe.model';
import { Sexe } from 'app/shared/model/enumerations/sexe.model';

export interface INageurs {
  id?: number;
  licenceNum?: string;
  groupeName?: string;
  document?: number;
  firstName?: string;
  lastName?: string;
  sexe?: Sexe;
  bearthday?: Moment;
  phoneNumber?: string;
  eMail?: string;
  address?: string;
  studyTime?: string;
  poidsTailles?: IMesureAnthropo[];
  testPerformances?: ITestPerformance[];
  documents?: IDocument[];
  groupe?: IGroupe;
}

export class Nageurs implements INageurs {
  constructor(
    public id?: number,
    public licenceNum?: string,
    public groupeName?: string,
    public document?: number,
    public firstName?: string,
    public lastName?: string,
    public sexe?: Sexe,
    public bearthday?: Moment,
    public phoneNumber?: string,
    public eMail?: string,
    public address?: string,
    public studyTime?: string,
    public poidsTailles?: IMesureAnthropo[],
    public testPerformances?: ITestPerformance[],
    public documents?: IDocument[],
    public groupe?: IGroupe
  ) {}
}
