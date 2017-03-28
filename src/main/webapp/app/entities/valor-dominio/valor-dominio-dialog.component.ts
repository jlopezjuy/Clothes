import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ValorDominio } from './valor-dominio.model';
import { ValorDominioPopupService } from './valor-dominio-popup.service';
import { ValorDominioService } from './valor-dominio.service';
import { Dominio, DominioService } from '../dominio';
import { Encargo, EncargoService } from '../encargo';
@Component({
    selector: 'jhi-valor-dominio-dialog',
    templateUrl: './valor-dominio-dialog.component.html'
})
export class ValorDominioDialogComponent implements OnInit {

    valorDominio: ValorDominio;
    authorities: any[];
    isSaving: boolean;

    dominios: Dominio[];

    encargos: Encargo[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private valorDominioService: ValorDominioService,
        private dominioService: DominioService,
        private encargoService: EncargoService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['valorDominio']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.dominioService.query().subscribe(
            (res: Response) => { this.dominios = res.json(); }, (res: Response) => this.onError(res.json()));
        this.encargoService.query().subscribe(
            (res: Response) => { this.encargos = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.valorDominio.id !== undefined) {
            this.valorDominioService.update(this.valorDominio)
                .subscribe((res: ValorDominio) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.valorDominioService.create(this.valorDominio)
                .subscribe((res: ValorDominio) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: ValorDominio) {
        this.eventManager.broadcast({ name: 'valorDominioListModification', content: 'OK'});
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

    trackDominioById(index: number, item: Dominio) {
        return item.id;
    }

    trackEncargoById(index: number, item: Encargo) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-valor-dominio-popup',
    template: ''
})
export class ValorDominioPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private valorDominioPopupService: ValorDominioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.valorDominioPopupService
                    .open(ValorDominioDialogComponent, params['id']);
            } else {
                this.modalRef = this.valorDominioPopupService
                    .open(ValorDominioDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
