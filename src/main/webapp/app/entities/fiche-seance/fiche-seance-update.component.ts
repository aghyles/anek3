import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IFicheSeance, FicheSeance } from 'app/shared/model/fiche-seance.model';
import { FicheSeanceService } from './fiche-seance.service';
import { IPresence } from 'app/shared/model/presence.model';
import { PresenceService } from 'app/entities/presence/presence.service';
import { ITheme } from 'app/shared/model/theme.model';
import { ThemeService } from 'app/entities/theme/theme.service';
import { IGroupe } from 'app/shared/model/groupe.model';
import { GroupeService } from 'app/entities/groupe/groupe.service';

@Component({
  selector: 'jhi-fiche-seance-update',
  templateUrl: './fiche-seance-update.component.html'
})
export class FicheSeanceUpdateComponent implements OnInit {
  isSaving: boolean;

  presences: IPresence[];

  themes: ITheme[];

  groupes: IGroupe[];

  editForm = this.fb.group({
    id: [],
    ficheSeance: [null, [Validators.required]],
    groupeName: [null, [Validators.required]],
    date: [null, [Validators.required]],
    themePrimary: [],
    themeSecondary: [],
    objectifPrimary: [],
    objectifSecondary: [],
    observation: [],
    exerciceEchauffement1: [],
    exerciceEchauffement2: [],
    exerciceEchauffement3: [],
    exercicePrimary1: [],
    exercicePrimary2: [],
    exercicePrimary3: [],
    exercicePrimary4: [],
    exercicePrimary5: [],
    exercicePrimary6: [],
    exercicePrimary7: [],
    exercicePrimary8: [],
    exerciceFinale1: [],
    exerciceFinale2: [],
    bilan: [],
    presences: [],
    locations: [],
    groupe: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ficheSeanceService: FicheSeanceService,
    protected presenceService: PresenceService,
    protected themeService: ThemeService,
    protected groupeService: GroupeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ ficheSeance }) => {
      this.updateForm(ficheSeance);
    });
    this.presenceService
      .query()
      .subscribe((res: HttpResponse<IPresence[]>) => (this.presences = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.themeService
      .query()
      .subscribe((res: HttpResponse<ITheme[]>) => (this.themes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.groupeService
      .query()
      .subscribe((res: HttpResponse<IGroupe[]>) => (this.groupes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(ficheSeance: IFicheSeance) {
    this.editForm.patchValue({
      id: ficheSeance.id,
      ficheSeance: ficheSeance.ficheSeance,
      groupeName: ficheSeance.groupeName,
      date: ficheSeance.date != null ? ficheSeance.date.format(DATE_TIME_FORMAT) : null,
      themePrimary: ficheSeance.themePrimary,
      themeSecondary: ficheSeance.themeSecondary,
      objectifPrimary: ficheSeance.objectifPrimary,
      objectifSecondary: ficheSeance.objectifSecondary,
      observation: ficheSeance.observation,
      exerciceEchauffement1: ficheSeance.exerciceEchauffement1,
      exerciceEchauffement2: ficheSeance.exerciceEchauffement2,
      exerciceEchauffement3: ficheSeance.exerciceEchauffement3,
      exercicePrimary1: ficheSeance.exercicePrimary1,
      exercicePrimary2: ficheSeance.exercicePrimary2,
      exercicePrimary3: ficheSeance.exercicePrimary3,
      exercicePrimary4: ficheSeance.exercicePrimary4,
      exercicePrimary5: ficheSeance.exercicePrimary5,
      exercicePrimary6: ficheSeance.exercicePrimary6,
      exercicePrimary7: ficheSeance.exercicePrimary7,
      exercicePrimary8: ficheSeance.exercicePrimary8,
      exerciceFinale1: ficheSeance.exerciceFinale1,
      exerciceFinale2: ficheSeance.exerciceFinale2,
      bilan: ficheSeance.bilan,
      presences: ficheSeance.presences,
      locations: ficheSeance.locations,
      groupe: ficheSeance.groupe
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const ficheSeance = this.createFromForm();
    if (ficheSeance.id !== undefined) {
      this.subscribeToSaveResponse(this.ficheSeanceService.update(ficheSeance));
    } else {
      this.subscribeToSaveResponse(this.ficheSeanceService.create(ficheSeance));
    }
  }

  private createFromForm(): IFicheSeance {
    return {
      ...new FicheSeance(),
      id: this.editForm.get(['id']).value,
      ficheSeance: this.editForm.get(['ficheSeance']).value,
      groupeName: this.editForm.get(['groupeName']).value,
      date: this.editForm.get(['date']).value != null ? moment(this.editForm.get(['date']).value, DATE_TIME_FORMAT) : undefined,
      themePrimary: this.editForm.get(['themePrimary']).value,
      themeSecondary: this.editForm.get(['themeSecondary']).value,
      objectifPrimary: this.editForm.get(['objectifPrimary']).value,
      objectifSecondary: this.editForm.get(['objectifSecondary']).value,
      observation: this.editForm.get(['observation']).value,
      exerciceEchauffement1: this.editForm.get(['exerciceEchauffement1']).value,
      exerciceEchauffement2: this.editForm.get(['exerciceEchauffement2']).value,
      exerciceEchauffement3: this.editForm.get(['exerciceEchauffement3']).value,
      exercicePrimary1: this.editForm.get(['exercicePrimary1']).value,
      exercicePrimary2: this.editForm.get(['exercicePrimary2']).value,
      exercicePrimary3: this.editForm.get(['exercicePrimary3']).value,
      exercicePrimary4: this.editForm.get(['exercicePrimary4']).value,
      exercicePrimary5: this.editForm.get(['exercicePrimary5']).value,
      exercicePrimary6: this.editForm.get(['exercicePrimary6']).value,
      exercicePrimary7: this.editForm.get(['exercicePrimary7']).value,
      exercicePrimary8: this.editForm.get(['exercicePrimary8']).value,
      exerciceFinale1: this.editForm.get(['exerciceFinale1']).value,
      exerciceFinale2: this.editForm.get(['exerciceFinale2']).value,
      bilan: this.editForm.get(['bilan']).value,
      presences: this.editForm.get(['presences']).value,
      locations: this.editForm.get(['locations']).value,
      groupe: this.editForm.get(['groupe']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFicheSeance>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackPresenceById(index: number, item: IPresence) {
    return item.id;
  }

  trackThemeById(index: number, item: ITheme) {
    return item.id;
  }

  trackGroupeById(index: number, item: IGroupe) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
