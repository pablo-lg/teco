import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './emprendimiento.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmprendimientoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmprendimientoDetail = (props: IEmprendimientoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { emprendimientoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="emprendimientoDetailsHeading">Emprendimiento</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{emprendimientoEntity.id}</dd>
          <dt>
            <span id="nombre">Nombre</span>
          </dt>
          <dd>{emprendimientoEntity.nombre}</dd>
          <dt>
            <span id="contacto">Contacto</span>
          </dt>
          <dd>{emprendimientoEntity.contacto}</dd>
          <dt>
            <span id="fechaFinObra">Fecha Fin Obra</span>
          </dt>
          <dd>
            {emprendimientoEntity.fechaFinObra ? (
              <TextFormat value={emprendimientoEntity.fechaFinObra} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="elementosDeRed">Elementos De Red</span>
          </dt>
          <dd>{emprendimientoEntity.elementosDeRed}</dd>
          <dt>
            <span id="clientesCatv">Clientes Catv</span>
          </dt>
          <dd>{emprendimientoEntity.clientesCatv}</dd>
          <dt>
            <span id="clientesFibertel">Clientes Fibertel</span>
          </dt>
          <dd>{emprendimientoEntity.clientesFibertel}</dd>
          <dt>
            <span id="clientesFibertelLite">Clientes Fibertel Lite</span>
          </dt>
          <dd>{emprendimientoEntity.clientesFibertelLite}</dd>
          <dt>
            <span id="clientesFlow">Clientes Flow</span>
          </dt>
          <dd>{emprendimientoEntity.clientesFlow}</dd>
          <dt>
            <span id="clientesCombo">Clientes Combo</span>
          </dt>
          <dd>{emprendimientoEntity.clientesCombo}</dd>
          <dt>
            <span id="lineasVoz">Lineas Voz</span>
          </dt>
          <dd>{emprendimientoEntity.lineasVoz}</dd>
          <dt>
            <span id="mesesDeFinalizado">Meses De Finalizado</span>
          </dt>
          <dd>{emprendimientoEntity.mesesDeFinalizado}</dd>
          <dt>
            <span id="altasBC">Altas BC</span>
          </dt>
          <dd>{emprendimientoEntity.altasBC}</dd>
          <dt>
            <span id="penetracionVivLot">Penetracion Viv Lot</span>
          </dt>
          <dd>{emprendimientoEntity.penetracionVivLot}</dd>
          <dt>
            <span id="penetracionBC">Penetracion BC</span>
          </dt>
          <dd>{emprendimientoEntity.penetracionBC}</dd>
          <dt>
            <span id="demanda1">Demanda 1</span>
          </dt>
          <dd>{emprendimientoEntity.demanda1}</dd>
          <dt>
            <span id="demanda2">Demanda 2</span>
          </dt>
          <dd>{emprendimientoEntity.demanda2}</dd>
          <dt>
            <span id="demanda3">Demanda 3</span>
          </dt>
          <dd>{emprendimientoEntity.demanda3}</dd>
          <dt>
            <span id="demanda4">Demanda 4</span>
          </dt>
          <dd>{emprendimientoEntity.demanda4}</dd>
          <dt>
            <span id="lotes">Lotes</span>
          </dt>
          <dd>{emprendimientoEntity.lotes}</dd>
          <dt>
            <span id="viviendas">Viviendas</span>
          </dt>
          <dd>{emprendimientoEntity.viviendas}</dd>
          <dt>
            <span id="comProf">Com Prof</span>
          </dt>
          <dd>{emprendimientoEntity.comProf}</dd>
          <dt>
            <span id="habitaciones">Habitaciones</span>
          </dt>
          <dd>{emprendimientoEntity.habitaciones}</dd>
          <dt>
            <span id="manzanas">Manzanas</span>
          </dt>
          <dd>{emprendimientoEntity.manzanas}</dd>
          <dt>
            <span id="demanda">Demanda</span>
          </dt>
          <dd>{emprendimientoEntity.demanda}</dd>
          <dt>
            <span id="fechaDeRelevamiento">Fecha De Relevamiento</span>
          </dt>
          <dd>
            {emprendimientoEntity.fechaDeRelevamiento ? (
              <TextFormat value={emprendimientoEntity.fechaDeRelevamiento} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="telefono">Telefono</span>
          </dt>
          <dd>{emprendimientoEntity.telefono}</dd>
          <dt>
            <span id="anoPriorizacion">Ano Priorizacion</span>
          </dt>
          <dd>
            {emprendimientoEntity.anoPriorizacion ? (
              <TextFormat value={emprendimientoEntity.anoPriorizacion} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="contratoOpen">Contrato Open</span>
          </dt>
          <dd>{emprendimientoEntity.contratoOpen}</dd>
          <dt>
            <span id="negociacion">Negociacion</span>
          </dt>
          <dd>{emprendimientoEntity.negociacion ? 'true' : 'false'}</dd>
          <dt>
            <span id="estadoBC">Estado BC</span>
          </dt>
          <dd>{emprendimientoEntity.estadoBC}</dd>
          <dt>
            <span id="fecha">Fecha</span>
          </dt>
          <dd>
            {emprendimientoEntity.fecha ? (
              <TextFormat value={emprendimientoEntity.fecha} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="codigoDeFirma">Codigo De Firma</span>
          </dt>
          <dd>{emprendimientoEntity.codigoDeFirma}</dd>
          <dt>
            <span id="fechaFirma">Fecha Firma</span>
          </dt>
          <dd>
            {emprendimientoEntity.fechaFirma ? (
              <TextFormat value={emprendimientoEntity.fechaFirma} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="observaciones">Observaciones</span>
          </dt>
          <dd>{emprendimientoEntity.observaciones}</dd>
          <dt>
            <span id="comentario">Comentario</span>
          </dt>
          <dd>{emprendimientoEntity.comentario}</dd>
          <dt>
            <span id="estadoFirma">Estado Firma</span>
          </dt>
          <dd>{emprendimientoEntity.estadoFirma}</dd>
          <dt>Grupo Emprendimiento</dt>
          <dd>{emprendimientoEntity.grupoEmprendimiento ? emprendimientoEntity.grupoEmprendimiento.descripcion : ''}</dd>
          <dt>Obra</dt>
          <dd>{emprendimientoEntity.obra ? emprendimientoEntity.obra.descripcion : ''}</dd>
          <dt>Tipo Obra</dt>
          <dd>{emprendimientoEntity.tipoObra ? emprendimientoEntity.tipoObra.descripcion : ''}</dd>
          <dt>Tipo Emp</dt>
          <dd>{emprendimientoEntity.tipoEmp ? emprendimientoEntity.tipoEmp.descripcion : ''}</dd>
          <dt>Estado</dt>
          <dd>{emprendimientoEntity.estado ? emprendimientoEntity.estado.descripcion : ''}</dd>
          <dt>Competencia</dt>
          <dd>{emprendimientoEntity.competencia ? emprendimientoEntity.competencia.descripcion : ''}</dd>
          <dt>Despliegue</dt>
          <dd>{emprendimientoEntity.despliegue ? emprendimientoEntity.despliegue.descripcion : ''}</dd>
          <dt>N SE</dt>
          <dd>{emprendimientoEntity.nSE ? emprendimientoEntity.nSE.descripcion : ''}</dd>
          <dt>Segmento</dt>
          <dd>{emprendimientoEntity.segmento ? emprendimientoEntity.segmento.descripcion : ''}</dd>
          <dt>Tecnologia</dt>
          <dd>{emprendimientoEntity.tecnologia ? emprendimientoEntity.tecnologia.descripcion : ''}</dd>
          <dt>Ejec Cuentas</dt>
          <dd>{emprendimientoEntity.ejecCuentas ? emprendimientoEntity.ejecCuentas.nombre : ''}</dd>
          <dt>Direccion</dt>
          <dd>{emprendimientoEntity.direccion ? emprendimientoEntity.direccion.calle : ''}</dd>
        </dl>
        <Button tag={Link} to="/emprendimiento" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/emprendimiento/${emprendimientoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ emprendimiento }: IRootState) => ({
  emprendimientoEntity: emprendimiento.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmprendimientoDetail);
