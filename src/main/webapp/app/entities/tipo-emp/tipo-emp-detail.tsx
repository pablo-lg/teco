import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './tipo-emp.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITipoEmpDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TipoEmpDetail = (props: ITipoEmpDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { tipoEmpEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tipoEmpDetailsHeading">TipoEmp</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{tipoEmpEntity.id}</dd>
          <dt>
            <span id="descripcion">Descripcion</span>
          </dt>
          <dd>{tipoEmpEntity.descripcion}</dd>
          <dt>
            <span id="valor">Valor</span>
          </dt>
          <dd>{tipoEmpEntity.valor}</dd>
          <dt>Master Tipo Emp</dt>
          <dd>{tipoEmpEntity.masterTipoEmp ? tipoEmpEntity.masterTipoEmp.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tipo-emp" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tipo-emp/${tipoEmpEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ tipoEmp }: IRootState) => ({
  tipoEmpEntity: tipoEmp.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TipoEmpDetail);
