import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INageur } from 'app/shared/model/nageur.model';

@Component({
  selector: 'jhi-nageur-detail',
  templateUrl: './nageur-detail.component.html'
})
export class NageurDetailComponent implements OnInit {
  nageur: INageur;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ nageur }) => {
      this.nageur = nageur;
    });
  }

  previousState() {
    window.history.back();
  }
}
