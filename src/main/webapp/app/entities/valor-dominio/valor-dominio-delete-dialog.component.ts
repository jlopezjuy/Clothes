import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ValorDominio } from './valor-dominio.model';
import { ValorDominioPopupService } from './valor-dominio-popup.service';
import { ValorDominioService } from './valor-dominio.service';

@Component({
    selector: 'jhi-valor-dominio-delete-dialog',
    templateUrl: './valor-dominio-delete-dialog.component.html'
})
export class ValorDominioDeleteDialogComponent {

    valorDominio: ValorDominio;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private valorDominioService: ValorDominioService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['valorDominio']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.valorDominioService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'valorDominioListModification',
                content: 'Deleted an valorDominio'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-valor-dominio-delete-popup',
    template: ''
})
export class ValorDominioDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private valorDominioPopupService: ValorDominioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.valorDominioPopupService
                .open(ValorDominioDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
