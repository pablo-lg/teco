export interface INSE {
  id?: number;
  descripcion?: string | null;
  activo?: boolean | null;
}

export const defaultValue: Readonly<INSE> = {
  activo: false,
};
