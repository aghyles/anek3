import { Moment } from 'moment';
import { ITestPerformance } from 'app/shared/model/test-performance.model';
import { INageurs } from 'app/shared/model/nageurs.model';
import { IExercice } from 'app/shared/model/exercice.model';
import { TypeDocument } from 'app/shared/model/enumerations/type-document.model';

export interface IDocument {
  id?: number;
  docTitle?: string;
  dateDocument?: Moment;
  linkDocument?: string;
  typeDocument?: TypeDocument;
  testperformance?: ITestPerformance;
  nageurs?: INageurs;
  exercices?: IExercice[];
}

export class Document implements IDocument {
  constructor(
    public id?: number,
    public docTitle?: string,
    public dateDocument?: Moment,
    public linkDocument?: string,
    public typeDocument?: TypeDocument,
    public testperformance?: ITestPerformance,
    public nageurs?: INageurs,
    public exercices?: IExercice[]
  ) {}
}
