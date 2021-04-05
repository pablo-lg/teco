import dayjs from 'dayjs';
import { IGrupoEmprendimiento } from 'app/shared/model/grupo-emprendimiento.model';
import { IObra } from 'app/shared/model/obra.model';
import { ITipoObra } from 'app/shared/model/tipo-obra.model';
import { ITipoEmp } from 'app/shared/model/tipo-emp.model';
import { IEstado } from 'app/shared/model/estado.model';
import { ICompetencia } from 'app/shared/model/competencia.model';
import { IDespliegue } from 'app/shared/model/despliegue.model';
import { INSE } from 'app/shared/model/nse.model';
import { ISegmento } from 'app/shared/model/segmento.model';
import { ITecnologia } from 'app/shared/model/tecnologia.model';
import { IEjecCuentas } from 'app/shared/model/ejec-cuentas.model';
import { IDireccion } from 'app/shared/model/direccion.model';

export interface IEmprendimiento {
  id?: number;
  nombre?: string | null;
  contacto?: string | null;
  fechaFinObra?: string | null;
  elementosDeRed?: string | null;
  clientesCatv?: string | null;
  clientesFibertel?: string | null;
  clientesFibertelLite?: string | null;
  clientesFlow?: string | null;
  clientesCombo?: string | null;
  lineasVoz?: string | null;
  mesesDeFinalizado?: string | null;
  altasBC?: string | null;
  penetracionVivLot?: string | null;
  penetracionBC?: string | null;
  demanda1?: string | null;
  demanda2?: string | null;
  demanda3?: string | null;
  demanda4?: string | null;
  lotes?: string | null;
  viviendas?: string | null;
  comProf?: string | null;
  habitaciones?: string | null;
  manzanas?: string | null;
  demanda?: string | null;
  fechaDeRelevamiento?: string | null;
  telefono?: string | null;
  anoPriorizacion?: string | null;
  contratoOpen?: string | null;
  negociacion?: boolean | null;
  estadoBC?: string | null;
  fecha?: string | null;
  codigoDeFirma?: string | null;
  fechaFirma?: string | null;
  observaciones?: string | null;
  comentario?: string | null;
  estadoFirma?: string | null;
  grupoEmprendimiento?: IGrupoEmprendimiento | null;
  obra?: IObra | null;
  tipoObra?: ITipoObra | null;
  tipoEmp?: ITipoEmp | null;
  estado?: IEstado | null;
  competencia?: ICompetencia | null;
  despliegue?: IDespliegue | null;
  nSE?: INSE | null;
  segmento?: ISegmento | null;
  tecnologia?: ITecnologia | null;
  ejecCuentas?: IEjecCuentas | null;
  direccion?: IDireccion | null;
}

export const defaultValue: Readonly<IEmprendimiento> = {
  negociacion: false,
};
