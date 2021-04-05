import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './obra.reducer';
import { IObra } from 'app/shared/model/obra.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IObraProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Obra = (props: IObraProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { obraList, match, loading } = props;
  return (
    <div>
      <h2 id="obra-heading" data-cy="ObraHeading">
        Obras
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Obra
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {obraList && obraList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>Id</th>
                <th>Descripcion</th>
                <th>Habilitada</th>
                <th>Fecha Fin Obra</th>
                <th>Tipo Obra</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {obraList.map((obra, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${obra.id}`} color="link" size="sm">
                      {obra.id}
                    </Button>
                  </td>
                  <td>{obra.descripcion}</td>
                  <td>{obra.habilitada ? 'true' : 'false'}</td>
                  <td>{obra.fechaFinObra ? <TextFormat type="date" value={obra.fechaFinObra} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{obra.tipoObra ? <Link to={`tipo-obra/${obra.tipoObra.id}`}>{obra.tipoObra.descripcion}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${obra.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${obra.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${obra.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Obras found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ obra }: IRootState) => ({
  obraList: obra.entities,
  loading: obra.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Obra);
