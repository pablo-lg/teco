import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './direccion.reducer';
import { IDireccion } from 'app/shared/model/direccion.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDireccionProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Direccion = (props: IDireccionProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { direccionList, match, loading } = props;
  return (
    <div>
      <h2 id="direccion-heading" data-cy="DireccionHeading">
        Direccions
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Direccion
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {direccionList && direccionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>Id</th>
                <th>Identification</th>
                <th>Pais</th>
                <th>Provincia</th>
                <th>Partido</th>
                <th>Localidad</th>
                <th>Calle</th>
                <th>Altura</th>
                <th>Region</th>
                <th>Subregion</th>
                <th>Hub</th>
                <th>Barrios Especiales</th>
                <th>Codigo Postal</th>
                <th>Tipo Calle</th>
                <th>Zona Competencia</th>
                <th>Intersection Left</th>
                <th>Intersection Right</th>
                <th>Street Type</th>
                <th>Latitud</th>
                <th>Longitud</th>
                <th>Elementos De Red</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {direccionList.map((direccion, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${direccion.id}`} color="link" size="sm">
                      {direccion.id}
                    </Button>
                  </td>
                  <td>{direccion.identification}</td>
                  <td>{direccion.pais}</td>
                  <td>{direccion.provincia}</td>
                  <td>{direccion.partido}</td>
                  <td>{direccion.localidad}</td>
                  <td>{direccion.calle}</td>
                  <td>{direccion.altura}</td>
                  <td>{direccion.region}</td>
                  <td>{direccion.subregion}</td>
                  <td>{direccion.hub}</td>
                  <td>{direccion.barriosEspeciales}</td>
                  <td>{direccion.codigoPostal}</td>
                  <td>{direccion.tipoCalle}</td>
                  <td>{direccion.zonaCompetencia}</td>
                  <td>{direccion.intersectionLeft}</td>
                  <td>{direccion.intersectionRight}</td>
                  <td>{direccion.streetType}</td>
                  <td>{direccion.latitud}</td>
                  <td>{direccion.longitud}</td>
                  <td>{direccion.elementosDeRed}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${direccion.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${direccion.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${direccion.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Direccions found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ direccion }: IRootState) => ({
  direccionList: direccion.entities,
  loading: direccion.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Direccion);
