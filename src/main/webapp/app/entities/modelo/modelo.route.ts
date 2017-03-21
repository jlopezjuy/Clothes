import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ModeloComponent } from './modelo.component';
import { ModeloDetailComponent } from './modelo-detail.component';
import { ModeloPopupComponent } from './modelo-dialog.component';
import { ModeloDeletePopupComponent } from './modelo-delete-dialog.component';

import { Principal } from '../../shared';


export const modeloRoute: Routes = [
  {
    path: 'modelo',
    component: ModeloComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.modelo.home.title'
    }
  }, {
    path: 'modelo/:id',
    component: ModeloDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.modelo.home.title'
    }
  }
];

export const modeloPopupRoute: Routes = [
  {
    path: 'modelo-new',
    component: ModeloPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.modelo.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'modelo/:id/edit',
    component: ModeloPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.modelo.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'modelo/:id/delete',
    component: ModeloDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.modelo.home.title'
    },
    outlet: 'popup'
  }
];
