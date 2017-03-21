import { ValorDominio } from '../valor-dominio';
export class Dominio {
    constructor(
        public id?: number,
        public descripcion?: string,
        public valorDominio?: ValorDominio,
    ) {
    }
}
