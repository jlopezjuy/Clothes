import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ClienteComponent } from './cliente.component';
import { ClienteDetailComponent } from './cliente-detail.component';
import { ClientePopupComponent } from './cliente-dialog.component';
import { ClienteDeletePopupComponent } from './cliente-delete-dialog.component';

import { Principal } from '../../shared';


export const clienteRoute: Routes = [
  {
    path: 'cliente',
    component: ClienteComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.cliente.home.title'
    }
  }, {
    path: 'cliente/:id',
    component: ClienteDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.cliente.home.title'
    }
  }
];

export const clientePopupRoute: Routes = [
  {
    path: 'cliente-new',
    component: ClientePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.cliente.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'cliente/:id/edit',
    component: ClientePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.cliente.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'cliente/:id/delete',
    component: ClienteDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.cliente.home.title'
    },
    outlet: 'popup'
  }
];
