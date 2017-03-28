import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Encargo } from './encargo.model';
import { EncargoPopupService } from './encargo-popup.service';
import { EncargoService } from './encargo.service';
import { Cliente, ClienteService } from '../cliente';
import { Pago, PagoService } from '../pago';
import { ValorDominio, ValorDominioService } from '../valor-dominio';
@Component({
    selector: 'jhi-encargo-dialog',
    templateUrl: './encargo-dialog.component.html'
})
export class EncargoDialogComponent implements OnInit {

    encargo: Encargo;
    authorities: any[];
    isSaving: boolean;

    clientes: Cliente[];

    pagos: Pago[];

    valordominios: ValorDominio[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private encargoService: EncargoService,
        private clienteService: ClienteService,
        private pagoService: PagoService,
        private valorDominioService: ValorDominioService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['encargo']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.clienteService.query().subscribe(
            (res: Response) => { this.clientes = res.json(); }, (res: Response) => this.onError(res.json()));
        this.pagoService.query().subscribe(
            (res: Response) => { this.pagos = res.json(); }, (res: Response) => this.onError(res.json()));
        this.valorDominioService.query().subscribe(
            (res: Response) => { this.valordominios = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.encargo.id !== undefined) {
            this.encargoService.update(this.encargo)
                .subscribe((res: Encargo) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.encargoService.create(this.encargo)
                .subscribe((res: Encargo) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Encargo) {
        this.eventManager.broadcast({ name: 'encargoListModification', content: 'OK'});
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

    trackClienteById(index: number, item: Cliente) {
        return item.id;
    }

    trackPagoById(index: number, item: Pago) {
        return item.id;
    }

    trackValorDominioById(index: number, item: ValorDominio) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-encargo-popup',
    template: ''
})
export class EncargoPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private encargoPopupService: EncargoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.encargoPopupService
                    .open(EncargoDialogComponent, params['id']);
            } else {
                this.modalRef = this.encargoPopupService
                    .open(EncargoDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
