import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Encargo } from './encargo.model';
import { EncargoService } from './encargo.service';

@Component({
    selector: 'jhi-encargo-detail',
    templateUrl: './encargo-detail.component.html'
})
export class EncargoDetailComponent implements OnInit, OnDestroy {

    encargo: Encargo;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private encargoService: EncargoService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['encargo']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.encargoService.find(id).subscribe(encargo => {
            this.encargo = encargo;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
