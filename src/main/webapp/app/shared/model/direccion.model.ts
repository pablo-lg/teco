export interface IDireccion {
  id?: string;
  identification?: string | null;
  pais?: string;
  provincia?: string;
  partido?: string;
  localidad?: string;
  calle?: string;
  altura?: number;
  region?: string | null;
  subregion?: string | null;
  hub?: string | null;
  barriosEspeciales?: string | null;
  codigoPostal?: string | null;
  tipoCalle?: string | null;
  zonaCompetencia?: string | null;
  intersectionLeft?: string | null;
  intersectionRight?: string | null;
  streetType?: string | null;
  latitud?: string | null;
  longitud?: string | null;
  elementosDeRed?: string | null;
}

export const defaultValue: Readonly<IDireccion> = {};
