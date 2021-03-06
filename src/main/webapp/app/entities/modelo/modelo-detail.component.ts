import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService, DataUtils } from 'ng-jhipster';
import { Modelo } from './modelo.model';
import { ModeloService } from './modelo.service';

@Component({
    selector: 'jhi-modelo-detail',
    templateUrl: './modelo-detail.component.html'
})
export class ModeloDetailComponent implements OnInit, OnDestroy {

    modelo: Modelo;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private modeloService: ModeloService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['modelo']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.modeloService.find(id).subscribe(modelo => {
            this.modelo = modelo;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
