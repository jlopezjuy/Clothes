import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ClienteDetailComponent } from '../../../../../../main/webapp/app/entities/cliente/cliente-detail.component';
import { ClienteService } from '../../../../../../main/webapp/app/entities/cliente/cliente.service';
import { Cliente } from '../../../../../../main/webapp/app/entities/cliente/cliente.model';

describe('Component Tests', () => {

    describe('Cliente Management Detail Component', () => {
        let comp: ClienteDetailComponent;
        let fixture: ComponentFixture<ClienteDetailComponent>;
        let service: ClienteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ClienteDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    {
                        provide: JhiLanguageService,
                        useClass: MockLanguageService
                    },
                    ClienteService
                ]
            }).overrideComponent(ClienteDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClienteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClienteService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Cliente(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cliente).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
