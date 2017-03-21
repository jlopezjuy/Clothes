import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ClothesClienteModule } from './cliente/cliente.module';
import { ClothesDominioModule } from './dominio/dominio.module';
import { ClothesEncargoModule } from './encargo/encargo.module';
import { ClothesMedidaModule } from './medida/medida.module';
import { ClothesModeloModule } from './modelo/modelo.module';
import { ClothesPagoModule } from './pago/pago.module';
import { ClothesValorDominioModule } from './valor-dominio/valor-dominio.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ClothesClienteModule,
        ClothesDominioModule,
        ClothesEncargoModule,
        ClothesMedidaModule,
        ClothesModeloModule,
        ClothesPagoModule,
        ClothesValorDominioModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClothesEntityModule {}
