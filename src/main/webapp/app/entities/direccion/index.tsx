import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Direccion from './direccion';
import DireccionDetail from './direccion-detail';
import DireccionUpdate from './direccion-update';
import DireccionDeleteDialog from './direccion-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DireccionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DireccionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DireccionDetail} />
      <ErrorBoundaryRoute path={match.url} component={Direccion} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DireccionDeleteDialog} />
  </>
);

export default Routes;
