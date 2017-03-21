import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ValorDominioComponent } from './valor-dominio.component';
import { ValorDominioDetailComponent } from './valor-dominio-detail.component';
import { ValorDominioPopupComponent } from './valor-dominio-dialog.component';
import { ValorDominioDeletePopupComponent } from './valor-dominio-delete-dialog.component';

import { Principal } from '../../shared';


export const valorDominioRoute: Routes = [
  {
    path: 'valor-dominio',
    component: ValorDominioComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.valorDominio.home.title'
    }
  }, {
    path: 'valor-dominio/:id',
    component: ValorDominioDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.valorDominio.home.title'
    }
  }
];

export const valorDominioPopupRoute: Routes = [
  {
    path: 'valor-dominio-new',
    component: ValorDominioPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.valorDominio.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'valor-dominio/:id/edit',
    component: ValorDominioPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.valorDominio.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'valor-dominio/:id/delete',
    component: ValorDominioDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.valorDominio.home.title'
    },
    outlet: 'popup'
  }
];
