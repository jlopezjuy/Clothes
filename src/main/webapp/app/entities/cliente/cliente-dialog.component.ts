import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Cliente } from './cliente.model';
import { ClientePopupService } from './cliente-popup.service';
import { ClienteService } from './cliente.service';
import { Modelo, ModeloService } from '../modelo';
import { Medida, MedidaService } from '../medida';
import { Encargo, EncargoService } from '../encargo';

@Component({
    selector: 'jhi-cliente-dialog',
    templateUrl: './cliente-dialog.component.html'
})
export class ClienteDialogComponent implements OnInit {

    cliente: Cliente;
    authorities: any[];
    isSaving: boolean;

    modelos: Modelo[];

    medidas: Medida[];

    encargos: Encargo[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private clienteService: ClienteService,
        private modeloService: ModeloService,
        private medidaService: MedidaService,
        private encargoService: EncargoService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['cliente']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.modeloService.query().subscribe(
            (res: Response) => { this.modelos = res.json(); }, (res: Response) => this.onError(res.json()));
        this.medidaService.query().subscribe(
            (res: Response) => { this.medidas = res.json(); }, (res: Response) => this.onError(res.json()));
        this.encargoService.query().subscribe(
            (res: Response) => { this.encargos = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.cliente.id !== undefined) {
            this.clienteService.update(this.cliente)
                .subscribe((res: Cliente) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.clienteService.create(this.cliente)
                .subscribe((res: Cliente) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Cliente) {
        this.eventManager.broadcast({ name: 'clienteListModification', content: 'OK'});
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

    trackModeloById(index: number, item: Modelo) {
        return item.id;
    }

    trackMedidaById(index: number, item: Medida) {
        return item.id;
    }

    trackEncargoById(index: number, item: Encargo) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-cliente-popup',
    template: ''
})
export class ClientePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private clientePopupService: ClientePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.clientePopupService
                    .open(ClienteDialogComponent, params['id']);
            } else {
                this.modalRef = this.clientePopupService
                    .open(ClienteDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
