import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GrupoEmprendimiento from './grupo-emprendimiento';
import GrupoEmprendimientoDetail from './grupo-emprendimiento-detail';
import GrupoEmprendimientoUpdate from './grupo-emprendimiento-update';
import GrupoEmprendimientoDeleteDialog from './grupo-emprendimiento-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GrupoEmprendimientoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GrupoEmprendimientoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GrupoEmprendimientoDetail} />
      <ErrorBoundaryRoute path={match.url} component={GrupoEmprendimiento} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GrupoEmprendimientoDeleteDialog} />
  </>
);

export default Routes;
