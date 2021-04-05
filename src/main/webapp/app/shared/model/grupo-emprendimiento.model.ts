export interface IGrupoEmprendimiento {
  id?: number;
  descripcion?: string | null;
  esProtegido?: boolean | null;
}

export const defaultValue: Readonly<IGrupoEmprendimiento> = {
  esProtegido: false,
};
