import { Moment } from 'moment';
import { INageurs } from 'app/shared/model/nageurs.model';
import { IFicheSeance } from 'app/shared/model/fiche-seance.model';
import { IProgrammation } from 'app/shared/model/programmation.model';
import { Category } from 'app/shared/model/enumerations/category.model';

export interface IGroupe {
  id?: number;
  groupeName?: string;
  startDate?: Moment;
  endDate?: Moment;
  saison?: string;
  category?: Category;
  nageurs?: INageurs[];
  ficheSeances?: IFicheSeance[];
  programmations?: IProgrammation[];
}

export class Groupe implements IGroupe {
  constructor(
    public id?: number,
    public groupeName?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public saison?: string,
    public category?: Category,
    public nageurs?: INageurs[],
    public ficheSeances?: IFicheSeance[],
    public programmations?: IProgrammation[]
  ) {}
}
