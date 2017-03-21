import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClothesSharedModule } from '../../shared';

import {
    ValorDominioService,
    ValorDominioPopupService,
    ValorDominioComponent,
    ValorDominioDetailComponent,
    ValorDominioDialogComponent,
    ValorDominioPopupComponent,
    ValorDominioDeletePopupComponent,
    ValorDominioDeleteDialogComponent,
    valorDominioRoute,
    valorDominioPopupRoute,
} from './';

let ENTITY_STATES = [
    ...valorDominioRoute,
    ...valorDominioPopupRoute,
];

@NgModule({
    imports: [
        ClothesSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ValorDominioComponent,
        ValorDominioDetailComponent,
        ValorDominioDialogComponent,
        ValorDominioDeleteDialogComponent,
        ValorDominioPopupComponent,
        ValorDominioDeletePopupComponent,
    ],
    entryComponents: [
        ValorDominioComponent,
        ValorDominioDialogComponent,
        ValorDominioPopupComponent,
        ValorDominioDeleteDialogComponent,
        ValorDominioDeletePopupComponent,
    ],
    providers: [
        ValorDominioService,
        ValorDominioPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClothesValorDominioModule {}
