import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMasterTipoEmp } from 'app/shared/model/master-tipo-emp.model';
import { getEntities as getMasterTipoEmps } from 'app/entities/master-tipo-emp/master-tipo-emp.reducer';
import { getEntity, updateEntity, createEntity, reset } from './pauta.reducer';
import { IPauta } from 'app/shared/model/pauta.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPautaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PautaUpdate = (props: IPautaUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { pautaEntity, masterTipoEmps, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/pauta');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getMasterTipoEmps();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...pautaEntity,
        ...values,
        masterTipoEmp: masterTipoEmps.find(it => it.id.toString() === values.masterTipoEmpId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gempBoostrapApp.pauta.home.createOrEditLabel" data-cy="PautaCreateUpdateHeading">
            Create or edit a Pauta
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : pautaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="pauta-id">Id</Label>
                  <AvInput id="pauta-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="aniosLabel" for="pauta-anios">
                  Anios
                </Label>
                <AvField id="pauta-anios" data-cy="anios" type="string" className="form-control" name="anios" />
              </AvGroup>
              <AvGroup>
                <Label id="tipoPautaLabel" for="pauta-tipoPauta">
                  Tipo Pauta
                </Label>
                <AvField id="pauta-tipoPauta" data-cy="tipoPauta" type="text" name="tipoPauta" />
              </AvGroup>
              <AvGroup>
                <Label for="pauta-masterTipoEmp">Master Tipo Emp</Label>
                <AvInput id="pauta-masterTipoEmp" data-cy="masterTipoEmp" type="select" className="form-control" name="masterTipoEmpId">
                  <option value="" key="0" />
                  {masterTipoEmps
                    ? masterTipoEmps.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/pauta" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  masterTipoEmps: storeState.masterTipoEmp.entities,
  pautaEntity: storeState.pauta.entity,
  loading: storeState.pauta.loading,
  updating: storeState.pauta.updating,
  updateSuccess: storeState.pauta.updateSuccess,
});

const mapDispatchToProps = {
  getMasterTipoEmps,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PautaUpdate);
