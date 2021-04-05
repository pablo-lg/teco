import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Despliegue from './despliegue';
import Segmento from './segmento';
import Tecnologia from './tecnologia';
import Competencia from './competencia';
import Estado from './estado';
import NSE from './nse';
import TipoObra from './tipo-obra';
import Obra from './obra';
import TipoEmp from './tipo-emp';
import EjecCuentas from './ejec-cuentas';
import Direccion from './direccion';
import Emprendimiento from './emprendimiento';
import GrupoAlarma from './grupo-alarma';
import GrupoEmprendimiento from './grupo-emprendimiento';
import GrupoUsuario from './grupo-usuario';
import Pauta from './pauta';
import MasterTipoEmp from './master-tipo-emp';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}despliegue`} component={Despliegue} />
      <ErrorBoundaryRoute path={`${match.url}segmento`} component={Segmento} />
      <ErrorBoundaryRoute path={`${match.url}tecnologia`} component={Tecnologia} />
      <ErrorBoundaryRoute path={`${match.url}competencia`} component={Competencia} />
      <ErrorBoundaryRoute path={`${match.url}estado`} component={Estado} />
      <ErrorBoundaryRoute path={`${match.url}nse`} component={NSE} />
      <ErrorBoundaryRoute path={`${match.url}tipo-obra`} component={TipoObra} />
      <ErrorBoundaryRoute path={`${match.url}obra`} component={Obra} />
      <ErrorBoundaryRoute path={`${match.url}tipo-emp`} component={TipoEmp} />
      <ErrorBoundaryRoute path={`${match.url}ejec-cuentas`} component={EjecCuentas} />
      <ErrorBoundaryRoute path={`${match.url}direccion`} component={Direccion} />
      <ErrorBoundaryRoute path={`${match.url}emprendimiento`} component={Emprendimiento} />
      <ErrorBoundaryRoute path={`${match.url}grupo-alarma`} component={GrupoAlarma} />
      <ErrorBoundaryRoute path={`${match.url}grupo-emprendimiento`} component={GrupoEmprendimiento} />
      <ErrorBoundaryRoute path={`${match.url}grupo-usuario`} component={GrupoUsuario} />
      <ErrorBoundaryRoute path={`${match.url}pauta`} component={Pauta} />
      <ErrorBoundaryRoute path={`${match.url}master-tipo-emp`} component={MasterTipoEmp} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
