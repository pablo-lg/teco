import { IMasterTipoEmp } from 'app/shared/model/master-tipo-emp.model';

export interface ITipoEmp {
  id?: number;
  descripcion?: string | null;
  valor?: string | null;
  masterTipoEmp?: IMasterTipoEmp | null;
}

export const defaultValue: Readonly<ITipoEmp> = {};
