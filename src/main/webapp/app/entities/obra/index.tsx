import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Obra from './obra';
import ObraDetail from './obra-detail';
import ObraUpdate from './obra-update';
import ObraDeleteDialog from './obra-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ObraUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ObraUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ObraDetail} />
      <ErrorBoundaryRoute path={match.url} component={Obra} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ObraDeleteDialog} />
  </>
);

export default Routes;
