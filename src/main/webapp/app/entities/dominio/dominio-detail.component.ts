import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Dominio } from './dominio.model';
import { DominioService } from './dominio.service';

@Component({
    selector: 'jhi-dominio-detail',
    templateUrl: './dominio-detail.component.html'
})
export class DominioDetailComponent implements OnInit, OnDestroy {

    dominio: Dominio;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private dominioService: DominioService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['dominio']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.dominioService.find(id).subscribe(dominio => {
            this.dominio = dominio;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
