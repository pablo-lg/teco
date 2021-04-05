import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Tecnologia from './tecnologia';
import TecnologiaDetail from './tecnologia-detail';
import TecnologiaUpdate from './tecnologia-update';
import TecnologiaDeleteDialog from './tecnologia-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TecnologiaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TecnologiaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TecnologiaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Tecnologia} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TecnologiaDeleteDialog} />
  </>
);

export default Routes;
