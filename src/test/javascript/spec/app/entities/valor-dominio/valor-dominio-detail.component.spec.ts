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
import { ValorDominioDetailComponent } from '../../../../../../main/webapp/app/entities/valor-dominio/valor-dominio-detail.component';
import { ValorDominioService } from '../../../../../../main/webapp/app/entities/valor-dominio/valor-dominio.service';
import { ValorDominio } from '../../../../../../main/webapp/app/entities/valor-dominio/valor-dominio.model';

describe('Component Tests', () => {

    describe('ValorDominio Management Detail Component', () => {
        let comp: ValorDominioDetailComponent;
        let fixture: ComponentFixture<ValorDominioDetailComponent>;
        let service: ValorDominioService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ValorDominioDetailComponent],
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
                    ValorDominioService
                ]
            }).overrideComponent(ValorDominioDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ValorDominioDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ValorDominioService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ValorDominio(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.valorDominio).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
