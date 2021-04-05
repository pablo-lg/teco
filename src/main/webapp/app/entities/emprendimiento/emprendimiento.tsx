import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './emprendimiento.reducer';
import { IEmprendimiento } from 'app/shared/model/emprendimiento.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmprendimientoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Emprendimiento = (props: IEmprendimientoProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { emprendimientoList, match, loading } = props;
  return (
    <div>
      <h2 id="emprendimiento-heading" data-cy="EmprendimientoHeading">
        Emprendimientos
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Emprendimiento
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {emprendimientoList && emprendimientoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>Id</th>
                <th>Nombre</th>
                <th>Contacto</th>
                <th>Fecha Fin Obra</th>
                <th>Elementos De Red</th>
                <th>Clientes Catv</th>
                <th>Clientes Fibertel</th>
                <th>Clientes Fibertel Lite</th>
                <th>Clientes Flow</th>
                <th>Clientes Combo</th>
                <th>Lineas Voz</th>
                <th>Meses De Finalizado</th>
                <th>Altas BC</th>
                <th>Penetracion Viv Lot</th>
                <th>Penetracion BC</th>
                <th>Demanda 1</th>
                <th>Demanda 2</th>
                <th>Demanda 3</th>
                <th>Demanda 4</th>
                <th>Lotes</th>
                <th>Viviendas</th>
                <th>Com Prof</th>
                <th>Habitaciones</th>
                <th>Manzanas</th>
                <th>Demanda</th>
                <th>Fecha De Relevamiento</th>
                <th>Telefono</th>
                <th>Ano Priorizacion</th>
                <th>Contrato Open</th>
                <th>Negociacion</th>
                <th>Estado BC</th>
                <th>Fecha</th>
                <th>Codigo De Firma</th>
                <th>Fecha Firma</th>
                <th>Observaciones</th>
                <th>Comentario</th>
                <th>Estado Firma</th>
                <th>Grupo Emprendimiento</th>
                <th>Obra</th>
                <th>Tipo Obra</th>
                <th>Tipo Emp</th>
                <th>Estado</th>
                <th>Competencia</th>
                <th>Despliegue</th>
                <th>N SE</th>
                <th>Segmento</th>
                <th>Tecnologia</th>
                <th>Ejec Cuentas</th>
                <th>Direccion</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {emprendimientoList.map((emprendimiento, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${emprendimiento.id}`} color="link" size="sm">
                      {emprendimiento.id}
                    </Button>
                  </td>
                  <td>{emprendimiento.nombre}</td>
                  <td>{emprendimiento.contacto}</td>
                  <td>
                    {emprendimiento.fechaFinObra ? (
                      <TextFormat type="date" value={emprendimiento.fechaFinObra} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{emprendimiento.elementosDeRed}</td>
                  <td>{emprendimiento.clientesCatv}</td>
                  <td>{emprendimiento.clientesFibertel}</td>
                  <td>{emprendimiento.clientesFibertelLite}</td>
                  <td>{emprendimiento.clientesFlow}</td>
                  <td>{emprendimiento.clientesCombo}</td>
                  <td>{emprendimiento.lineasVoz}</td>
                  <td>{emprendimiento.mesesDeFinalizado}</td>
                  <td>{emprendimiento.altasBC}</td>
                  <td>{emprendimiento.penetracionVivLot}</td>
                  <td>{emprendimiento.penetracionBC}</td>
                  <td>{emprendimiento.demanda1}</td>
                  <td>{emprendimiento.demanda2}</td>
                  <td>{emprendimiento.demanda3}</td>
                  <td>{emprendimiento.demanda4}</td>
                  <td>{emprendimiento.lotes}</td>
                  <td>{emprendimiento.viviendas}</td>
                  <td>{emprendimiento.comProf}</td>
                  <td>{emprendimiento.habitaciones}</td>
                  <td>{emprendimiento.manzanas}</td>
                  <td>{emprendimiento.demanda}</td>
                  <td>
                    {emprendimiento.fechaDeRelevamiento ? (
                      <TextFormat type="date" value={emprendimiento.fechaDeRelevamiento} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{emprendimiento.telefono}</td>
                  <td>
                    {emprendimiento.anoPriorizacion ? (
                      <TextFormat type="date" value={emprendimiento.anoPriorizacion} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{emprendimiento.contratoOpen}</td>
                  <td>{emprendimiento.negociacion ? 'true' : 'false'}</td>
                  <td>{emprendimiento.estadoBC}</td>
                  <td>
                    {emprendimiento.fecha ? <TextFormat type="date" value={emprendimiento.fecha} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{emprendimiento.codigoDeFirma}</td>
                  <td>
                    {emprendimiento.fechaFirma ? (
                      <TextFormat type="date" value={emprendimiento.fechaFirma} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{emprendimiento.observaciones}</td>
                  <td>{emprendimiento.comentario}</td>
                  <td>{emprendimiento.estadoFirma}</td>
                  <td>
                    {emprendimiento.grupoEmprendimiento ? (
                      <Link to={`grupo-emprendimiento/${emprendimiento.grupoEmprendimiento.id}`}>
                        {emprendimiento.grupoEmprendimiento.descripcion}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{emprendimiento.obra ? <Link to={`obra/${emprendimiento.obra.id}`}>{emprendimiento.obra.descripcion}</Link> : ''}</td>
                  <td>
                    {emprendimiento.tipoObra ? (
                      <Link to={`tipo-obra/${emprendimiento.tipoObra.id}`}>{emprendimiento.tipoObra.descripcion}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {emprendimiento.tipoEmp ? (
                      <Link to={`tipo-emp/${emprendimiento.tipoEmp.id}`}>{emprendimiento.tipoEmp.descripcion}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {emprendimiento.estado ? (
                      <Link to={`estado/${emprendimiento.estado.id}`}>{emprendimiento.estado.descripcion}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {emprendimiento.competencia ? (
                      <Link to={`competencia/${emprendimiento.competencia.id}`}>{emprendimiento.competencia.descripcion}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {emprendimiento.despliegue ? (
                      <Link to={`despliegue/${emprendimiento.despliegue.id}`}>{emprendimiento.despliegue.descripcion}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{emprendimiento.nSE ? <Link to={`nse/${emprendimiento.nSE.id}`}>{emprendimiento.nSE.descripcion}</Link> : ''}</td>
                  <td>
                    {emprendimiento.segmento ? (
                      <Link to={`segmento/${emprendimiento.segmento.id}`}>{emprendimiento.segmento.descripcion}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {emprendimiento.tecnologia ? (
                      <Link to={`tecnologia/${emprendimiento.tecnologia.id}`}>{emprendimiento.tecnologia.descripcion}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {emprendimiento.ejecCuentas ? (
                      <Link to={`ejec-cuentas/${emprendimiento.ejecCuentas.id}`}>{emprendimiento.ejecCuentas.nombre}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {emprendimiento.direccion ? (
                      <Link to={`direccion/${emprendimiento.direccion.id}`}>{emprendimiento.direccion.calle}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${emprendimiento.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${emprendimiento.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${emprendimiento.id}/delete`}
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
          !loading && <div className="alert alert-warning">No Emprendimientos found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ emprendimiento }: IRootState) => ({
  emprendimientoList: emprendimiento.entities,
  loading: emprendimiento.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Emprendimiento);
