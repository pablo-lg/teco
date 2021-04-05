import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './master-tipo-emp.reducer';
import { IMasterTipoEmp } from 'app/shared/model/master-tipo-emp.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMasterTipoEmpUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MasterTipoEmpUpdate = (props: IMasterTipoEmpUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { masterTipoEmpEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/master-tipo-emp');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...masterTipoEmpEntity,
        ...values,
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
          <h2 id="gempBoostrapApp.masterTipoEmp.home.createOrEditLabel" data-cy="MasterTipoEmpCreateUpdateHeading">
            Create or edit a MasterTipoEmp
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : masterTipoEmpEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="master-tipo-emp-id">Id</Label>
                  <AvInput id="master-tipo-emp-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descripcionLabel" for="master-tipo-emp-descripcion">
                  Descripcion
                </Label>
                <AvField id="master-tipo-emp-descripcion" data-cy="descripcion" type="text" name="descripcion" />
              </AvGroup>
              <AvGroup>
                <Label id="sobreLoteLabel" for="master-tipo-emp-sobreLote">
                  Sobre Lote
                </Label>
                <AvField id="master-tipo-emp-sobreLote" data-cy="sobreLote" type="text" name="sobreLote" />
              </AvGroup>
              <AvGroup>
                <Label id="sobreViviendaLabel" for="master-tipo-emp-sobreVivienda">
                  Sobre Vivienda
                </Label>
                <AvField id="master-tipo-emp-sobreVivienda" data-cy="sobreVivienda" type="text" name="sobreVivienda" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/master-tipo-emp" replace color="info">
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
  masterTipoEmpEntity: storeState.masterTipoEmp.entity,
  loading: storeState.masterTipoEmp.loading,
  updating: storeState.masterTipoEmp.updating,
  updateSuccess: storeState.masterTipoEmp.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MasterTipoEmpUpdate);
