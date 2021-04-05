import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './tipo-obra.reducer';
import { ITipoObra } from 'app/shared/model/tipo-obra.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITipoObraProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const TipoObra = (props: ITipoObraProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { tipoObraList, match, loading } = props;
  return (
    <div>
      <h2 id="tipo-obra-heading" data-cy="TipoObraHeading">
        Tipo Obras
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Tipo Obra
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tipoObraList && tipoObraList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>Id</th>
                <th>Descripcion</th>
                <th>Segmento</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tipoObraList.map((tipoObra, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${tipoObra.id}`} color="link" size="sm">
                      {tipoObra.id}
                    </Button>
                  </td>
                  <td>{tipoObra.descripcion}</td>
                  <td>{tipoObra.segmento ? <Link to={`segmento/${tipoObra.segmento.id}`}>{tipoObra.segmento.descripcion}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${tipoObra.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tipoObra.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tipoObra.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Tipo Obras found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ tipoObra }: IRootState) => ({
  tipoObraList: tipoObra.entities,
  loading: tipoObra.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TipoObra);
