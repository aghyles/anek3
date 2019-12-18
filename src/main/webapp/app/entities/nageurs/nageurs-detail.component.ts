import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INageurs } from 'app/shared/model/nageurs.model';

@Component({
  selector: 'jhi-nageurs-detail',
  templateUrl: './nageurs-detail.component.html'
})
export class NageursDetailComponent implements OnInit {
  nageurs: INageurs;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ nageurs }) => {
      this.nageurs = nageurs;
    });
  }

  previousState() {
    window.history.back();
  }
}
