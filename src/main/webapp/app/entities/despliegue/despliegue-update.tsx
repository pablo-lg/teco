import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './despliegue.reducer';
import { IDespliegue } from 'app/shared/model/despliegue.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDespliegueUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DespliegueUpdate = (props: IDespliegueUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { despliegueEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/despliegue');
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
        ...despliegueEntity,
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
          <h2 id="gempBoostrapApp.despliegue.home.createOrEditLabel" data-cy="DespliegueCreateUpdateHeading">
            Create or edit a Despliegue
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : despliegueEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="despliegue-id">Id</Label>
                  <AvInput id="despliegue-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descripcionLabel" for="despliegue-descripcion">
                  Descripcion
                </Label>
                <AvField id="despliegue-descripcion" data-cy="descripcion" type="text" name="descripcion" />
              </AvGroup>
              <AvGroup>
                <Label id="valorLabel" for="despliegue-valor">
                  Valor
                </Label>
                <AvField id="despliegue-valor" data-cy="valor" type="text" name="valor" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/despliegue" replace color="info">
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
  despliegueEntity: storeState.despliegue.entity,
  loading: storeState.despliegue.loading,
  updating: storeState.despliegue.updating,
  updateSuccess: storeState.despliegue.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DespliegueUpdate);
