import { IUser } from 'app/core/user/user.model';

export interface IGroupes {
  id?: number;
  groupes?: string;
  saison?: string;
  user?: IUser;
}

export class Groupes implements IGroupes {
  constructor(public id?: number, public groupes?: string, public saison?: string, public user?: IUser) {}
}
