import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ejec-cuentas.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEjecCuentasDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EjecCuentasDetail = (props: IEjecCuentasDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { ejecCuentasEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ejecCuentasDetailsHeading">EjecCuentas</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{ejecCuentasEntity.id}</dd>
          <dt>
            <span id="telefono">Telefono</span>
          </dt>
          <dd>{ejecCuentasEntity.telefono}</dd>
          <dt>
            <span id="apellido">Apellido</span>
          </dt>
          <dd>{ejecCuentasEntity.apellido}</dd>
          <dt>
            <span id="celular">Celular</span>
          </dt>
          <dd>{ejecCuentasEntity.celular}</dd>
          <dt>
            <span id="mail">Mail</span>
          </dt>
          <dd>{ejecCuentasEntity.mail}</dd>
          <dt>
            <span id="nombre">Nombre</span>
          </dt>
          <dd>{ejecCuentasEntity.nombre}</dd>
          <dt>
            <span id="repcom1">Repcom 1</span>
          </dt>
          <dd>{ejecCuentasEntity.repcom1}</dd>
          <dt>
            <span id="repcom2">Repcom 2</span>
          </dt>
          <dd>{ejecCuentasEntity.repcom2}</dd>
          <dt>Segmento</dt>
          <dd>{ejecCuentasEntity.segmento ? ejecCuentasEntity.segmento.descripcion : ''}</dd>
        </dl>
        <Button tag={Link} to="/ejec-cuentas" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ejec-cuentas/${ejecCuentasEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ ejecCuentas }: IRootState) => ({
  ejecCuentasEntity: ejecCuentas.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EjecCuentasDetail);
