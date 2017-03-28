import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Pago } from './pago.model';
import { PagoService } from './pago.service';

@Component({
    selector: 'jhi-pago-detail',
    templateUrl: './pago-detail.component.html'
})
export class PagoDetailComponent implements OnInit, OnDestroy {

    pago: Pago;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private pagoService: PagoService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['pago']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.pagoService.find(id).subscribe(pago => {
            this.pago = pago;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
