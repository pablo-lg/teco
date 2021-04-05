import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NSE from './nse';
import NSEDetail from './nse-detail';
import NSEUpdate from './nse-update';
import NSEDeleteDialog from './nse-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NSEUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NSEUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NSEDetail} />
      <ErrorBoundaryRoute path={match.url} component={NSE} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={NSEDeleteDialog} />
  </>
);

export default Routes;
