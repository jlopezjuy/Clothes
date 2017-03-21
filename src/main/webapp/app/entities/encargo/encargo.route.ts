import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { EncargoComponent } from './encargo.component';
import { EncargoDetailComponent } from './encargo-detail.component';
import { EncargoPopupComponent } from './encargo-dialog.component';
import { EncargoDeletePopupComponent } from './encargo-delete-dialog.component';

import { Principal } from '../../shared';


export const encargoRoute: Routes = [
  {
    path: 'encargo',
    component: EncargoComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.encargo.home.title'
    }
  }, {
    path: 'encargo/:id',
    component: EncargoDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.encargo.home.title'
    }
  }
];

export const encargoPopupRoute: Routes = [
  {
    path: 'encargo-new',
    component: EncargoPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.encargo.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'encargo/:id/edit',
    component: EncargoPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.encargo.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'encargo/:id/delete',
    component: EncargoDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'clothesApp.encargo.home.title'
    },
    outlet: 'popup'
  }
];
