import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClothesSharedModule } from '../../shared';

import {
    DominioService,
    DominioPopupService,
    DominioComponent,
    DominioDetailComponent,
    DominioDialogComponent,
    DominioPopupComponent,
    DominioDeletePopupComponent,
    DominioDeleteDialogComponent,
    dominioRoute,
    dominioPopupRoute,
} from './';

let ENTITY_STATES = [
    ...dominioRoute,
    ...dominioPopupRoute,
];

@NgModule({
    imports: [
        ClothesSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DominioComponent,
        DominioDetailComponent,
        DominioDialogComponent,
        DominioDeleteDialogComponent,
        DominioPopupComponent,
        DominioDeletePopupComponent,
    ],
    entryComponents: [
        DominioComponent,
        DominioDialogComponent,
        DominioPopupComponent,
        DominioDeleteDialogComponent,
        DominioDeletePopupComponent,
    ],
    providers: [
        DominioService,
        DominioPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClothesDominioModule {}
