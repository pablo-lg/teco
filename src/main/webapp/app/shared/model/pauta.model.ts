import { IMasterTipoEmp } from 'app/shared/model/master-tipo-emp.model';

export interface IPauta {
  id?: number;
  anios?: number | null;
  tipoPauta?: string | null;
  masterTipoEmp?: IMasterTipoEmp | null;
}

export const defaultValue: Readonly<IPauta> = {};
