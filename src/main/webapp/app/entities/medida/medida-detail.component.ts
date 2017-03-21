import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Medida } from './medida.model';
import { MedidaService } from './medida.service';

@Component({
    selector: 'jhi-medida-detail',
    templateUrl: './medida-detail.component.html'
})
export class MedidaDetailComponent implements OnInit, OnDestroy {

    medida: Medida;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private medidaService: MedidaService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['medida']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.medidaService.find(id).subscribe(medida => {
            this.medida = medida;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
