import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GrupoUsuario from './grupo-usuario';
import GrupoUsuarioDetail from './grupo-usuario-detail';
import GrupoUsuarioUpdate from './grupo-usuario-update';
import GrupoUsuarioDeleteDialog from './grupo-usuario-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GrupoUsuarioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GrupoUsuarioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GrupoUsuarioDetail} />
      <ErrorBoundaryRoute path={match.url} component={GrupoUsuario} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GrupoUsuarioDeleteDialog} />
  </>
);

export default Routes;
