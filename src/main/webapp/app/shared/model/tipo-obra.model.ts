import { ISegmento } from 'app/shared/model/segmento.model';

export interface ITipoObra {
  id?: number;
  descripcion?: string | null;
  segmento?: ISegmento | null;
}

export const defaultValue: Readonly<ITipoObra> = {};
