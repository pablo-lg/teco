import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './grupo-emprendimiento.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGrupoEmprendimientoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GrupoEmprendimientoDetail = (props: IGrupoEmprendimientoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { grupoEmprendimientoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="grupoEmprendimientoDetailsHeading">GrupoEmprendimiento</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{grupoEmprendimientoEntity.id}</dd>
          <dt>
            <span id="descripcion">Descripcion</span>
          </dt>
          <dd>{grupoEmprendimientoEntity.descripcion}</dd>
          <dt>
            <span id="esProtegido">Es Protegido</span>
          </dt>
          <dd>{grupoEmprendimientoEntity.esProtegido ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/grupo-emprendimiento" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/grupo-emprendimiento/${grupoEmprendimientoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ grupoEmprendimiento }: IRootState) => ({
  grupoEmprendimientoEntity: grupoEmprendimiento.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GrupoEmprendimientoDetail);
