import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './grupo-usuario.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGrupoUsuarioDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GrupoUsuarioDetail = (props: IGrupoUsuarioDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { grupoUsuarioEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="grupoUsuarioDetailsHeading">GrupoUsuario</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{grupoUsuarioEntity.id}</dd>
          <dt>
            <span id="usuario">Usuario</span>
          </dt>
          <dd>{grupoUsuarioEntity.usuario}</dd>
        </dl>
        <Button tag={Link} to="/grupo-usuario" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/grupo-usuario/${grupoUsuarioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ grupoUsuario }: IRootState) => ({
  grupoUsuarioEntity: grupoUsuario.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GrupoUsuarioDetail);
