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
import { ModeloDetailComponent } from '../../../../../../main/webapp/app/entities/modelo/modelo-detail.component';
import { ModeloService } from '../../../../../../main/webapp/app/entities/modelo/modelo.service';
import { Modelo } from '../../../../../../main/webapp/app/entities/modelo/modelo.model';

describe('Component Tests', () => {

    describe('Modelo Management Detail Component', () => {
        let comp: ModeloDetailComponent;
        let fixture: ComponentFixture<ModeloDetailComponent>;
        let service: ModeloService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ModeloDetailComponent],
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
                    ModeloService
                ]
            }).overrideComponent(ModeloDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ModeloDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModeloService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Modelo(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.modelo).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
