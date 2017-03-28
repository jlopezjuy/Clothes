import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { ValorDominio } from './valor-dominio.model';
import { ValorDominioService } from './valor-dominio.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-valor-dominio',
    templateUrl: './valor-dominio.component.html'
})
export class ValorDominioComponent implements OnInit, OnDestroy {
valorDominios: ValorDominio[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private valorDominioService: ValorDominioService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['valorDominio']);
    }

    loadAll() {
        this.valorDominioService.query().subscribe(
            (res: Response) => {
                this.valorDominios = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInValorDominios();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: ValorDominio) {
        return item.id;
    }



    registerChangeInValorDominios() {
        this.eventSubscriber = this.eventManager.subscribe('valorDominioListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
