import { IObjectif } from 'app/shared/model/objectif.model';
import { IFicheSeance } from 'app/shared/model/fiche-seance.model';
import { IProgrammation } from 'app/shared/model/programmation.model';

export interface ITheme {
  id?: number;
  themeName?: string;
  description?: string;
  objectifs?: IObjectif[];
  ficheSeances?: IFicheSeance[];
  programmations?: IProgrammation[];
}

export class Theme implements ITheme {
  constructor(
    public id?: number,
    public themeName?: string,
    public description?: string,
    public objectifs?: IObjectif[],
    public ficheSeances?: IFicheSeance[],
    public programmations?: IProgrammation[]
  ) {}
}
