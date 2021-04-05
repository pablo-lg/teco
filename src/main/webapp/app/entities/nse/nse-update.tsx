import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './nse.reducer';
import { INSE } from 'app/shared/model/nse.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INSEUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const NSEUpdate = (props: INSEUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { nSEEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/nse');
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
        ...nSEEntity,
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
          <h2 id="gempBoostrapApp.nSE.home.createOrEditLabel" data-cy="NSECreateUpdateHeading">
            Create or edit a NSE
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : nSEEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="nse-id">Id</Label>
                  <AvInput id="nse-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descripcionLabel" for="nse-descripcion">
                  Descripcion
                </Label>
                <AvField id="nse-descripcion" data-cy="descripcion" type="text" name="descripcion" />
              </AvGroup>
              <AvGroup check>
                <Label id="activoLabel">
                  <AvInput id="nse-activo" data-cy="activo" type="checkbox" className="form-check-input" name="activo" />
                  Activo
                </Label>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/nse" replace color="info">
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
  nSEEntity: storeState.nSE.entity,
  loading: storeState.nSE.loading,
  updating: storeState.nSE.updating,
  updateSuccess: storeState.nSE.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(NSEUpdate);
