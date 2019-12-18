import { Moment } from 'moment';
import { IGroupes } from 'app/shared/model/groupes.model';

export interface INageur {
  id?: number;
  licence?: string;
  nom?: string;
  prenom?: string;
  dateNaissance?: Moment;
  tel?: number;
  hauraireEtude?: string;
  groupes?: IGroupes;
}

export class Nageur implements INageur {
  constructor(
    public id?: number,
    public licence?: string,
    public nom?: string,
    public prenom?: string,
    public dateNaissance?: Moment,
    public tel?: number,
    public hauraireEtude?: string,
    public groupes?: IGroupes
  ) {}
}
