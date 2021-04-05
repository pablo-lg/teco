import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './grupo-usuario.reducer';
import { IGrupoUsuario } from 'app/shared/model/grupo-usuario.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGrupoUsuarioProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const GrupoUsuario = (props: IGrupoUsuarioProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { grupoUsuarioList, match, loading } = props;
  return (
    <div>
      <h2 id="grupo-usuario-heading" data-cy="GrupoUsuarioHeading">
        Grupo Usuarios
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Grupo Usuario
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {grupoUsuarioList && grupoUsuarioList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>Id</th>
                <th>Usuario</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {grupoUsuarioList.map((grupoUsuario, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${grupoUsuario.id}`} color="link" size="sm">
                      {grupoUsuario.id}
                    </Button>
                  </td>
                  <td>{grupoUsuario.usuario}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${grupoUsuario.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${grupoUsuario.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${grupoUsuario.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Grupo Usuarios found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ grupoUsuario }: IRootState) => ({
  grupoUsuarioList: grupoUsuario.entities,
  loading: grupoUsuario.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GrupoUsuario);
