import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './grupo-alarma.reducer';
import { IGrupoAlarma } from 'app/shared/model/grupo-alarma.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGrupoAlarmaProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const GrupoAlarma = (props: IGrupoAlarmaProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { grupoAlarmaList, match, loading } = props;
  return (
    <div>
      <h2 id="grupo-alarma-heading" data-cy="GrupoAlarmaHeading">
        Grupo Alarmas
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Grupo Alarma
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {grupoAlarmaList && grupoAlarmaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>Id</th>
                <th>Nombre Grupo</th>
                <th>Alarma Tiempo</th>
                <th>Alarma Sva</th>
                <th>Alarma Businesscase</th>
                <th>Grupo Emprendimiento</th>
                <th>Grupo Usuario</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {grupoAlarmaList.map((grupoAlarma, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${grupoAlarma.id}`} color="link" size="sm">
                      {grupoAlarma.id}
                    </Button>
                  </td>
                  <td>{grupoAlarma.nombreGrupo}</td>
                  <td>{grupoAlarma.alarmaTiempo}</td>
                  <td>{grupoAlarma.alarmaSva}</td>
                  <td>{grupoAlarma.alarmaBusinesscase}</td>
                  <td>
                    {grupoAlarma.grupoEmprendimiento ? (
                      <Link to={`grupo-emprendimiento/${grupoAlarma.grupoEmprendimiento.id}`}>
                        {grupoAlarma.grupoEmprendimiento.descripcion}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {grupoAlarma.grupoUsuario ? (
                      <Link to={`grupo-usuario/${grupoAlarma.grupoUsuario.id}`}>{grupoAlarma.grupoUsuario.usuario}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${grupoAlarma.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${grupoAlarma.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${grupoAlarma.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Grupo Alarmas found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ grupoAlarma }: IRootState) => ({
  grupoAlarmaList: grupoAlarma.entities,
  loading: grupoAlarma.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GrupoAlarma);
