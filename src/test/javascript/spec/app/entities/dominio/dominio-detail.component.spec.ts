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
import { DominioDetailComponent } from '../../../../../../main/webapp/app/entities/dominio/dominio-detail.component';
import { DominioService } from '../../../../../../main/webapp/app/entities/dominio/dominio.service';
import { Dominio } from '../../../../../../main/webapp/app/entities/dominio/dominio.model';

describe('Component Tests', () => {

    describe('Dominio Management Detail Component', () => {
        let comp: DominioDetailComponent;
        let fixture: ComponentFixture<DominioDetailComponent>;
        let service: DominioService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [DominioDetailComponent],
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
                    DominioService
                ]
            }).overrideComponent(DominioDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DominioDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DominioService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Dominio(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.dominio).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
