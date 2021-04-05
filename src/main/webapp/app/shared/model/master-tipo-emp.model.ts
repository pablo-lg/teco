export interface IMasterTipoEmp {
  id?: number;
  descripcion?: string | null;
  sobreLote?: string | null;
  sobreVivienda?: string | null;
}

export const defaultValue: Readonly<IMasterTipoEmp> = {};
