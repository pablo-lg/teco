import { IGrupoEmprendimiento } from 'app/shared/model/grupo-emprendimiento.model';
import { IGrupoUsuario } from 'app/shared/model/grupo-usuario.model';

export interface IGrupoAlarma {
  id?: number;
  nombreGrupo?: string;
  alarmaTiempo?: number | null;
  alarmaSva?: number | null;
  alarmaBusinesscase?: number | null;
  grupoEmprendimiento?: IGrupoEmprendimiento | null;
  grupoUsuario?: IGrupoUsuario | null;
}

export const defaultValue: Readonly<IGrupoAlarma> = {};
