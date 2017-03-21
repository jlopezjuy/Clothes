import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { DominioComponent } from './dominio.component';
import { DominioDetailComponent } from './dominio-detail.component';
import { DominioPopupComponent } from './dominio-dialog.component';
import { DominioDeletePopupComponent } from './dominio-delete-dialog.component';

import { Principal } from '../../shared';


export const dominioRoute: Routes = [
  {
    path: 'dominio',
    component: DominioComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.dominio.home.title'
    }
  }, {
    path: 'dominio/:id',
    component: DominioDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.dominio.home.title'
    }
  }
];

export const dominioPopupRoute: Routes = [
  {
    path: 'dominio-new',
    component: DominioPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.dominio.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'dominio/:id/edit',
    component: DominioPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.dominio.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'dominio/:id/delete',
    component: DominioDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.dominio.home.title'
    },
    outlet: 'popup'
  }
];
