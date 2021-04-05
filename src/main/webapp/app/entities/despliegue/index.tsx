import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Despliegue from './despliegue';
import DespliegueDetail from './despliegue-detail';
import DespliegueUpdate from './despliegue-update';
import DespliegueDeleteDialog from './despliegue-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DespliegueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DespliegueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DespliegueDetail} />
      <ErrorBoundaryRoute path={match.url} component={Despliegue} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DespliegueDeleteDialog} />
  </>
);

export default Routes;
