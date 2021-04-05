import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TipoObra from './tipo-obra';
import TipoObraDetail from './tipo-obra-detail';
import TipoObraUpdate from './tipo-obra-update';
import TipoObraDeleteDialog from './tipo-obra-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TipoObraUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TipoObraUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TipoObraDetail} />
      <ErrorBoundaryRoute path={match.url} component={TipoObra} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TipoObraDeleteDialog} />
  </>
);

export default Routes;
