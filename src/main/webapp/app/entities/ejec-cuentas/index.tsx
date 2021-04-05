import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EjecCuentas from './ejec-cuentas';
import EjecCuentasDetail from './ejec-cuentas-detail';
import EjecCuentasUpdate from './ejec-cuentas-update';
import EjecCuentasDeleteDialog from './ejec-cuentas-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EjecCuentasUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EjecCuentasUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EjecCuentasDetail} />
      <ErrorBoundaryRoute path={match.url} component={EjecCuentas} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EjecCuentasDeleteDialog} />
  </>
);

export default Routes;
