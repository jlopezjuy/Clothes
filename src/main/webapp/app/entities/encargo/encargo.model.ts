import { Cliente } from '../cliente';
import { Pago } from '../pago';
import { ValorDominio } from '../valor-dominio';
export class Encargo {
    constructor(
        public id?: number,
        public importeTotal?: number,
        public fechaEncargo?: any,
        public fechaEntrega?: any,
        public detalleVestido?: string,
        public cliente?: Cliente,
        public pago?: Pago,
        public tipoEcargo?: ValorDominio,
        public estado?: ValorDominio,
    ) {
    }
}
