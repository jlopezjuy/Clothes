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
import { PagoDetailComponent } from '../../../../../../main/webapp/app/entities/pago/pago-detail.component';
import { PagoService } from '../../../../../../main/webapp/app/entities/pago/pago.service';
import { Pago } from '../../../../../../main/webapp/app/entities/pago/pago.model';

describe('Component Tests', () => {

    describe('Pago Management Detail Component', () => {
        let comp: PagoDetailComponent;
        let fixture: ComponentFixture<PagoDetailComponent>;
        let service: PagoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [PagoDetailComponent],
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
                    PagoService
                ]
            }).overrideComponent(PagoDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PagoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PagoService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Pago(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pago).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
