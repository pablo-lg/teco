import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './grupo-alarma.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGrupoAlarmaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GrupoAlarmaDetail = (props: IGrupoAlarmaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { grupoAlarmaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="grupoAlarmaDetailsHeading">GrupoAlarma</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{grupoAlarmaEntity.id}</dd>
          <dt>
            <span id="nombreGrupo">Nombre Grupo</span>
          </dt>
          <dd>{grupoAlarmaEntity.nombreGrupo}</dd>
          <dt>
            <span id="alarmaTiempo">Alarma Tiempo</span>
          </dt>
          <dd>{grupoAlarmaEntity.alarmaTiempo}</dd>
          <dt>
            <span id="alarmaSva">Alarma Sva</span>
          </dt>
          <dd>{grupoAlarmaEntity.alarmaSva}</dd>
          <dt>
            <span id="alarmaBusinesscase">Alarma Businesscase</span>
          </dt>
          <dd>{grupoAlarmaEntity.alarmaBusinesscase}</dd>
          <dt>Grupo Emprendimiento</dt>
          <dd>{grupoAlarmaEntity.grupoEmprendimiento ? grupoAlarmaEntity.grupoEmprendimiento.descripcion : ''}</dd>
          <dt>Grupo Usuario</dt>
          <dd>{grupoAlarmaEntity.grupoUsuario ? grupoAlarmaEntity.grupoUsuario.usuario : ''}</dd>
        </dl>
        <Button tag={Link} to="/grupo-alarma" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/grupo-alarma/${grupoAlarmaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ grupoAlarma }: IRootState) => ({
  grupoAlarmaEntity: grupoAlarma.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GrupoAlarmaDetail);
