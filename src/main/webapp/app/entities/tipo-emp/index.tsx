import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TipoEmp from './tipo-emp';
import TipoEmpDetail from './tipo-emp-detail';
import TipoEmpUpdate from './tipo-emp-update';
import TipoEmpDeleteDialog from './tipo-emp-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TipoEmpUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TipoEmpUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TipoEmpDetail} />
      <ErrorBoundaryRoute path={match.url} component={TipoEmp} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TipoEmpDeleteDialog} />
  </>
);

export default Routes;
