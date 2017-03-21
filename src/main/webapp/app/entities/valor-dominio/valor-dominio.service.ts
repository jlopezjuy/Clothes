import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ValorDominio } from './valor-dominio.model';
@Injectable()
export class ValorDominioService {

    private resourceUrl = 'api/valor-dominios';
    private resourceUrlEstado = 'api/valor-dominios-estado';
    private resourceUrlTipoEvento = 'api/valor-dominios-tipo-evento';

    constructor(private http: Http) { }

    create(valorDominio: ValorDominio): Observable<ValorDominio> {
        let copy: ValorDominio = Object.assign({}, valorDominio);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(valorDominio: ValorDominio): Observable<ValorDominio> {
        let copy: ValorDominio = Object.assign({}, valorDominio);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ValorDominio> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
        ;
    }

    queryEstado(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrlEstado, options)
        ;
    }

    queryTipoEvento(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrlTipoEvento, options)
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }



    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
