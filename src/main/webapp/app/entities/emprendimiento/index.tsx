import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Emprendimiento from './emprendimiento';
import EmprendimientoDetail from './emprendimiento-detail';
import EmprendimientoUpdate from './emprendimiento-update';
import EmprendimientoDeleteDialog from './emprendimiento-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmprendimientoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmprendimientoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmprendimientoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Emprendimiento} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmprendimientoDeleteDialog} />
  </>
);

export default Routes;
