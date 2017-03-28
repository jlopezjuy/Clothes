import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Dominio } from './dominio.model';
import { DominioPopupService } from './dominio-popup.service';
import { DominioService } from './dominio.service';
import { ValorDominio, ValorDominioService } from '../valor-dominio';

@Component({
    selector: 'jhi-dominio-dialog',
    templateUrl: './dominio-dialog.component.html'
})
export class DominioDialogComponent implements OnInit {

    dominio: Dominio;
    authorities: any[];
    isSaving: boolean;

    valordominios: ValorDominio[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private dominioService: DominioService,
        private valorDominioService: ValorDominioService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['dominio']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.valorDominioService.query().subscribe(
            (res: Response) => { this.valordominios = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.dominio.id !== undefined) {
            this.dominioService.update(this.dominio)
                .subscribe((res: Dominio) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.dominioService.create(this.dominio)
                .subscribe((res: Dominio) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Dominio) {
        this.eventManager.broadcast({ name: 'dominioListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackValorDominioById(index: number, item: ValorDominio) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-dominio-popup',
    template: ''
})
export class DominioPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private dominioPopupService: DominioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.dominioPopupService
                    .open(DominioDialogComponent, params['id']);
            } else {
                this.modalRef = this.dominioPopupService
                    .open(DominioDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
