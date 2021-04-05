import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './grupo-usuario.reducer';
import { IGrupoUsuario } from 'app/shared/model/grupo-usuario.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGrupoUsuarioUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GrupoUsuarioUpdate = (props: IGrupoUsuarioUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { grupoUsuarioEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/grupo-usuario');
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
        ...grupoUsuarioEntity,
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
          <h2 id="gempBoostrapApp.grupoUsuario.home.createOrEditLabel" data-cy="GrupoUsuarioCreateUpdateHeading">
            Create or edit a GrupoUsuario
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : grupoUsuarioEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="grupo-usuario-id">Id</Label>
                  <AvInput id="grupo-usuario-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="usuarioLabel" for="grupo-usuario-usuario">
                  Usuario
                </Label>
                <AvField id="grupo-usuario-usuario" data-cy="usuario" type="text" name="usuario" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/grupo-usuario" replace color="info">
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
  grupoUsuarioEntity: storeState.grupoUsuario.entity,
  loading: storeState.grupoUsuario.loading,
  updating: storeState.grupoUsuario.updating,
  updateSuccess: storeState.grupoUsuario.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GrupoUsuarioUpdate);
