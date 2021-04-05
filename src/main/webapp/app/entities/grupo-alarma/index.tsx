import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GrupoAlarma from './grupo-alarma';
import GrupoAlarmaDetail from './grupo-alarma-detail';
import GrupoAlarmaUpdate from './grupo-alarma-update';
import GrupoAlarmaDeleteDialog from './grupo-alarma-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GrupoAlarmaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GrupoAlarmaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GrupoAlarmaDetail} />
      <ErrorBoundaryRoute path={match.url} component={GrupoAlarma} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GrupoAlarmaDeleteDialog} />
  </>
);

export default Routes;
