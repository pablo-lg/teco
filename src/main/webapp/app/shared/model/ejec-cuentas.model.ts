import { ISegmento } from 'app/shared/model/segmento.model';

export interface IEjecCuentas {
  id?: number;
  telefono?: string | null;
  apellido?: string | null;
  celular?: string | null;
  mail?: string | null;
  nombre?: string | null;
  repcom1?: string | null;
  repcom2?: string | null;
  segmento?: ISegmento | null;
}

export const defaultValue: Readonly<IEjecCuentas> = {};
