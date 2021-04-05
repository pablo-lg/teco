import dayjs from 'dayjs';
import { ITipoObra } from 'app/shared/model/tipo-obra.model';

export interface IObra {
  id?: number;
  descripcion?: string | null;
  habilitada?: boolean | null;
  fechaFinObra?: string | null;
  tipoObra?: ITipoObra | null;
}

export const defaultValue: Readonly<IObra> = {
  habilitada: false,
};
