import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './pauta.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPautaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PautaDetail = (props: IPautaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { pautaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pautaDetailsHeading">Pauta</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{pautaEntity.id}</dd>
          <dt>
            <span id="anios">Anios</span>
          </dt>
          <dd>{pautaEntity.anios}</dd>
          <dt>
            <span id="tipoPauta">Tipo Pauta</span>
          </dt>
          <dd>{pautaEntity.tipoPauta}</dd>
          <dt>Master Tipo Emp</dt>
          <dd>{pautaEntity.masterTipoEmp ? pautaEntity.masterTipoEmp.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/pauta" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pauta/${pautaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ pauta }: IRootState) => ({
  pautaEntity: pauta.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PautaDetail);
