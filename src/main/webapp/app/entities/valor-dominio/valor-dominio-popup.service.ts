import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ValorDominio } from './valor-dominio.model';
import { ValorDominioService } from './valor-dominio.service';
@Injectable()
export class ValorDominioPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private valorDominioService: ValorDominioService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.valorDominioService.find(id).subscribe(valorDominio => {
                this.valorDominioModalRef(component, valorDominio);
            });
        } else {
            return this.valorDominioModalRef(component, new ValorDominio());
        }
    }

    valorDominioModalRef(component: Component, valorDominio: ValorDominio): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.valorDominio = valorDominio;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
