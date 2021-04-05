import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IGrupoEmprendimiento } from 'app/shared/model/grupo-emprendimiento.model';
import { getEntities as getGrupoEmprendimientos } from 'app/entities/grupo-emprendimiento/grupo-emprendimiento.reducer';
import { IGrupoUsuario } from 'app/shared/model/grupo-usuario.model';
import { getEntities as getGrupoUsuarios } from 'app/entities/grupo-usuario/grupo-usuario.reducer';
import { getEntity, updateEntity, createEntity, reset } from './grupo-alarma.reducer';
import { IGrupoAlarma } from 'app/shared/model/grupo-alarma.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGrupoAlarmaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GrupoAlarmaUpdate = (props: IGrupoAlarmaUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { grupoAlarmaEntity, grupoEmprendimientos, grupoUsuarios, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/grupo-alarma');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getGrupoEmprendimientos();
    props.getGrupoUsuarios();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...grupoAlarmaEntity,
        ...values,
        grupoEmprendimiento: grupoEmprendimientos.find(it => it.id.toString() === values.grupoEmprendimientoId.toString()),
        grupoUsuario: grupoUsuarios.find(it => it.id.toString() === values.grupoUsuarioId.toString()),
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
          <h2 id="gempBoostrapApp.grupoAlarma.home.createOrEditLabel" data-cy="GrupoAlarmaCreateUpdateHeading">
            Create or edit a GrupoAlarma
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : grupoAlarmaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="grupo-alarma-id">Id</Label>
                  <AvInput id="grupo-alarma-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nombreGrupoLabel" for="grupo-alarma-nombreGrupo">
                  Nombre Grupo
                </Label>
                <AvField
                  id="grupo-alarma-nombreGrupo"
                  data-cy="nombreGrupo"
                  type="text"
                  name="nombreGrupo"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="alarmaTiempoLabel" for="grupo-alarma-alarmaTiempo">
                  Alarma Tiempo
                </Label>
                <AvField id="grupo-alarma-alarmaTiempo" data-cy="alarmaTiempo" type="string" className="form-control" name="alarmaTiempo" />
              </AvGroup>
              <AvGroup>
                <Label id="alarmaSvaLabel" for="grupo-alarma-alarmaSva">
                  Alarma Sva
                </Label>
                <AvField id="grupo-alarma-alarmaSva" data-cy="alarmaSva" type="string" className="form-control" name="alarmaSva" />
              </AvGroup>
              <AvGroup>
                <Label id="alarmaBusinesscaseLabel" for="grupo-alarma-alarmaBusinesscase">
                  Alarma Businesscase
                </Label>
                <AvField
                  id="grupo-alarma-alarmaBusinesscase"
                  data-cy="alarmaBusinesscase"
                  type="string"
                  className="form-control"
                  name="alarmaBusinesscase"
                />
              </AvGroup>
              <AvGroup>
                <Label for="grupo-alarma-grupoEmprendimiento">Grupo Emprendimiento</Label>
                <AvInput
                  id="grupo-alarma-grupoEmprendimiento"
                  data-cy="grupoEmprendimiento"
                  type="select"
                  className="form-control"
                  name="grupoEmprendimientoId"
                >
                  <option value="" key="0" />
                  {grupoEmprendimientos
                    ? grupoEmprendimientos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descripcion}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="grupo-alarma-grupoUsuario">Grupo Usuario</Label>
                <AvInput id="grupo-alarma-grupoUsuario" data-cy="grupoUsuario" type="select" className="form-control" name="grupoUsuarioId">
                  <option value="" key="0" />
                  {grupoUsuarios
                    ? grupoUsuarios.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.usuario}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/grupo-alarma" replace color="info">
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
  grupoEmprendimientos: storeState.grupoEmprendimiento.entities,
  grupoUsuarios: storeState.grupoUsuario.entities,
  grupoAlarmaEntity: storeState.grupoAlarma.entity,
  loading: storeState.grupoAlarma.loading,
  updating: storeState.grupoAlarma.updating,
  updateSuccess: storeState.grupoAlarma.updateSuccess,
});

const mapDispatchToProps = {
  getGrupoEmprendimientos,
  getGrupoUsuarios,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GrupoAlarmaUpdate);
