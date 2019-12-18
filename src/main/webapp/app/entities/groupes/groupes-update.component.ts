import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IGroupes, Groupes } from 'app/shared/model/groupes.model';
import { GroupesService } from './groupes.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-groupes-update',
  templateUrl: './groupes-update.component.html'
})
export class GroupesUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    groupes: [],
    saison: [],
    user: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected groupesService: GroupesService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ groupes }) => {
      this.updateForm(groupes);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(groupes: IGroupes) {
    this.editForm.patchValue({
      id: groupes.id,
      groupes: groupes.groupes,
      saison: groupes.saison,
      user: groupes.user
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const groupes = this.createFromForm();
    if (groupes.id !== undefined) {
      this.subscribeToSaveResponse(this.groupesService.update(groupes));
    } else {
      this.subscribeToSaveResponse(this.groupesService.create(groupes));
    }
  }

  private createFromForm(): IGroupes {
    return {
      ...new Groupes(),
      id: this.editForm.get(['id']).value,
      groupes: this.editForm.get(['groupes']).value,
      saison: this.editForm.get(['saison']).value,
      user: this.editForm.get(['user']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGroupes>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
