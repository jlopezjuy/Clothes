import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { ValorDominio } from './valor-dominio.model';
import { ValorDominioService } from './valor-dominio.service';

@Component({
    selector: 'jhi-valor-dominio-detail',
    templateUrl: './valor-dominio-detail.component.html'
})
export class ValorDominioDetailComponent implements OnInit, OnDestroy {

    valorDominio: ValorDominio;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private valorDominioService: ValorDominioService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['valorDominio']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.valorDominioService.find(id).subscribe(valorDominio => {
            this.valorDominio = valorDominio;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
