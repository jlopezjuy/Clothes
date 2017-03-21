import { Cliente } from '../cliente';
export class Medida {
    constructor(
        public id?: number,
        public contornoBusto?: number,
        public anchoPecho?: number,
        public altoBusto?: number,
        public bajoBusto?: number,
        public alturaPinza?: number,
        public separacionBusto?: number,
        public talleDeltantero?: number,
        public talleEspalda?: number,
        public largoCorset?: number,
        public costado?: number,
        public hombro?: number,
        public anchoHombro?: number,
        public largoManga?: number,
        public sisa?: number,
        public cintura?: number,
        public anteCadera?: number,
        public cadera?: number,
        public largoPollera?: number,
        public fechaMedida?: any,
        public cliente?: Cliente,
    ) {
    }
}
