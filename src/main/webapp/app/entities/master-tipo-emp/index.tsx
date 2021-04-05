import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MasterTipoEmp from './master-tipo-emp';
import MasterTipoEmpDetail from './master-tipo-emp-detail';
import MasterTipoEmpUpdate from './master-tipo-emp-update';
import MasterTipoEmpDeleteDialog from './master-tipo-emp-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MasterTipoEmpUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MasterTipoEmpUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MasterTipoEmpDetail} />
      <ErrorBoundaryRoute path={match.url} component={MasterTipoEmp} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MasterTipoEmpDeleteDialog} />
  </>
);

export default Routes;
