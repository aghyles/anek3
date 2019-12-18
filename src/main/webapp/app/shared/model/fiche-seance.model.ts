import { Moment } from 'moment';
import { IPresence } from 'app/shared/model/presence.model';
import { ITheme } from 'app/shared/model/theme.model';
import { IGroupe } from 'app/shared/model/groupe.model';

export interface IFicheSeance {
  id?: number;
  ficheSeance?: number;
  groupeName?: string;
  date?: Moment;
  themePrimary?: string;
  themeSecondary?: string;
  objectifPrimary?: string;
  objectifSecondary?: string;
  observation?: string;
  exerciceEchauffement1?: string;
  exerciceEchauffement2?: string;
  exerciceEchauffement3?: string;
  exercicePrimary1?: string;
  exercicePrimary2?: string;
  exercicePrimary3?: string;
  exercicePrimary4?: string;
  exercicePrimary5?: string;
  exercicePrimary6?: string;
  exercicePrimary7?: string;
  exercicePrimary8?: string;
  exerciceFinale1?: number;
  exerciceFinale2?: number;
  bilan?: string;
  presences?: IPresence[];
  locations?: ITheme[];
  groupe?: IGroupe;
}

export class FicheSeance implements IFicheSeance {
  constructor(
    public id?: number,
    public ficheSeance?: number,
    public groupeName?: string,
    public date?: Moment,
    public themePrimary?: string,
    public themeSecondary?: string,
    public objectifPrimary?: string,
    public objectifSecondary?: string,
    public observation?: string,
    public exerciceEchauffement1?: string,
    public exerciceEchauffement2?: string,
    public exerciceEchauffement3?: string,
    public exercicePrimary1?: string,
    public exercicePrimary2?: string,
    public exercicePrimary3?: string,
    public exercicePrimary4?: string,
    public exercicePrimary5?: string,
    public exercicePrimary6?: string,
    public exercicePrimary7?: string,
    public exercicePrimary8?: string,
    public exerciceFinale1?: number,
    public exerciceFinale2?: number,
    public bilan?: string,
    public presences?: IPresence[],
    public locations?: ITheme[],
    public groupe?: IGroupe
  ) {}
}
