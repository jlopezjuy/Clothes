import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Dominio } from './dominio.model';
import { DominioPopupService } from './dominio-popup.service';
import { DominioService } from './dominio.service';

@Component({
    selector: 'jhi-dominio-delete-dialog',
    templateUrl: './dominio-delete-dialog.component.html'
})
export class DominioDeleteDialogComponent {

    dominio: Dominio;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private dominioService: DominioService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['dominio']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.dominioService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dominioListModification',
                content: 'Deleted an dominio'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dominio-delete-popup',
    template: ''
})
export class DominioDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private dominioPopupService: DominioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.dominioPopupService
                .open(DominioDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
