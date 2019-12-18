import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGroupes } from 'app/shared/model/groupes.model';

@Component({
  selector: 'jhi-groupes-detail',
  templateUrl: './groupes-detail.component.html'
})
export class GroupesDetailComponent implements OnInit {
  groupes: IGroupes;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ groupes }) => {
      this.groupes = groupes;
    });
  }

  previousState() {
    window.history.back();
  }
}
