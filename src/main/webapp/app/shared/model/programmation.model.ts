import { ITheme } from 'app/shared/model/theme.model';
import { IGroupe } from 'app/shared/model/groupe.model';

export interface IProgrammation {
  id?: number;
  programme?: string;
  idGroupe?: number;
  documents?: ITheme[];
  groupe?: IGroupe;
}

export class Programmation implements IProgrammation {
  constructor(
    public id?: number,
    public programme?: string,
    public idGroupe?: number,
    public documents?: ITheme[],
    public groupe?: IGroupe
  ) {}
}
