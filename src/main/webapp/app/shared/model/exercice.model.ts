import { IDocument } from 'app/shared/model/document.model';
import { IObjectif } from 'app/shared/model/objectif.model';

export interface IExercice {
  id?: number;
  exerciceName?: string;
  description?: string;
  idDocSchema?: number;
  dosage?: string;
  longDosage?: number;
  recomendation?: string;
  documents?: IDocument[];
  objectifs?: IObjectif[];
}

export class Exercice implements IExercice {
  constructor(
    public id?: number,
    public exerciceName?: string,
    public description?: string,
    public idDocSchema?: number,
    public dosage?: string,
    public longDosage?: number,
    public recomendation?: string,
    public documents?: IDocument[],
    public objectifs?: IObjectif[]
  ) {}
}
