import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Cliente } from './cliente.model';
import { ClienteService } from './cliente.service';

@Component({
    selector: 'jhi-cliente-detail',
    templateUrl: './cliente-detail.component.html'
})
export class ClienteDetailComponent implements OnInit, OnDestroy {

    cliente: Cliente;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private clienteService: ClienteService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['cliente']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.clienteService.find(id).subscribe(cliente => {
            this.cliente = cliente;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
