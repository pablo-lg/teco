import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './tipo-emp.reducer';
import { ITipoEmp } from 'app/shared/model/tipo-emp.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITipoEmpProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const TipoEmp = (props: ITipoEmpProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { tipoEmpList, match, loading } = props;
  return (
    <div>
      <h2 id="tipo-emp-heading" data-cy="TipoEmpHeading">
        Tipo Emps
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Tipo Emp
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tipoEmpList && tipoEmpList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>Id</th>
                <th>Descripcion</th>
                <th>Valor</th>
                <th>Master Tipo Emp</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tipoEmpList.map((tipoEmp, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${tipoEmp.id}`} color="link" size="sm">
                      {tipoEmp.id}
                    </Button>
                  </td>
                  <td>{tipoEmp.descripcion}</td>
                  <td>{tipoEmp.valor}</td>
                  <td>
                    {tipoEmp.masterTipoEmp ? (
                      <Link to={`master-tipo-emp/${tipoEmp.masterTipoEmp.id}`}>{tipoEmp.masterTipoEmp.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${tipoEmp.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tipoEmp.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tipoEmp.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Tipo Emps found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ tipoEmp }: IRootState) => ({
  tipoEmpList: tipoEmp.entities,
  loading: tipoEmp.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TipoEmp);
