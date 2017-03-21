import { Dominio } from '../dominio';
import { Encargo } from '../encargo';
export class ValorDominio {
    constructor(
        public id?: number,
        public descripcion?: string,
        public dominio?: Dominio,
        public tipoEvento?: Encargo,
        public estado?: Encargo,
    ) {
    }
}
