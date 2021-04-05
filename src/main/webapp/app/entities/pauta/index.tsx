import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Pauta from './pauta';
import PautaDetail from './pauta-detail';
import PautaUpdate from './pauta-update';
import PautaDeleteDialog from './pauta-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PautaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PautaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PautaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Pauta} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PautaDeleteDialog} />
  </>
);

export default Routes;
