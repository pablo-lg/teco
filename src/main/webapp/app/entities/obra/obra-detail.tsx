import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './obra.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IObraDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ObraDetail = (props: IObraDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { obraEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="obraDetailsHeading">Obra</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{obraEntity.id}</dd>
          <dt>
            <span id="descripcion">Descripcion</span>
          </dt>
          <dd>{obraEntity.descripcion}</dd>
          <dt>
            <span id="habilitada">Habilitada</span>
          </dt>
          <dd>{obraEntity.habilitada ? 'true' : 'false'}</dd>
          <dt>
            <span id="fechaFinObra">Fecha Fin Obra</span>
          </dt>
          <dd>
            {obraEntity.fechaFinObra ? <TextFormat value={obraEntity.fechaFinObra} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>Tipo Obra</dt>
          <dd>{obraEntity.tipoObra ? obraEntity.tipoObra.descripcion : ''}</dd>
        </dl>
        <Button tag={Link} to="/obra" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/obra/${obraEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ obra }: IRootState) => ({
  obraEntity: obra.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ObraDetail);
