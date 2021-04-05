export interface IDespliegue {
  id?: number;
  descripcion?: string | null;
  valor?: string | null;
}

export const defaultValue: Readonly<IDespliegue> = {};
