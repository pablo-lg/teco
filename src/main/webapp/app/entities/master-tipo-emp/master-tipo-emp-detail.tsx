import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './master-tipo-emp.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMasterTipoEmpDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MasterTipoEmpDetail = (props: IMasterTipoEmpDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { masterTipoEmpEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="masterTipoEmpDetailsHeading">MasterTipoEmp</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{masterTipoEmpEntity.id}</dd>
          <dt>
            <span id="descripcion">Descripcion</span>
          </dt>
          <dd>{masterTipoEmpEntity.descripcion}</dd>
          <dt>
            <span id="sobreLote">Sobre Lote</span>
          </dt>
          <dd>{masterTipoEmpEntity.sobreLote}</dd>
          <dt>
            <span id="sobreVivienda">Sobre Vivienda</span>
          </dt>
          <dd>{masterTipoEmpEntity.sobreVivienda}</dd>
        </dl>
        <Button tag={Link} to="/master-tipo-emp" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/master-tipo-emp/${masterTipoEmpEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ masterTipoEmp }: IRootState) => ({
  masterTipoEmpEntity: masterTipoEmp.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MasterTipoEmpDetail);
