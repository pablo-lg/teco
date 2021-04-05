import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITipoObra } from 'app/shared/model/tipo-obra.model';
import { getEntities as getTipoObras } from 'app/entities/tipo-obra/tipo-obra.reducer';
import { getEntity, updateEntity, createEntity, reset } from './obra.reducer';
import { IObra } from 'app/shared/model/obra.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IObraUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ObraUpdate = (props: IObraUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { obraEntity, tipoObras, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/obra');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTipoObras();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...obraEntity,
        ...values,
        tipoObra: tipoObras.find(it => it.id.toString() === values.tipoObraId.toString()),
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
          <h2 id="gempBoostrapApp.obra.home.createOrEditLabel" data-cy="ObraCreateUpdateHeading">
            Create or edit a Obra
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : obraEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="obra-id">Id</Label>
                  <AvInput id="obra-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descripcionLabel" for="obra-descripcion">
                  Descripcion
                </Label>
                <AvField id="obra-descripcion" data-cy="descripcion" type="text" name="descripcion" />
              </AvGroup>
              <AvGroup check>
                <Label id="habilitadaLabel">
                  <AvInput id="obra-habilitada" data-cy="habilitada" type="checkbox" className="form-check-input" name="habilitada" />
                  Habilitada
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="fechaFinObraLabel" for="obra-fechaFinObra">
                  Fecha Fin Obra
                </Label>
                <AvField id="obra-fechaFinObra" data-cy="fechaFinObra" type="date" className="form-control" name="fechaFinObra" />
              </AvGroup>
              <AvGroup>
                <Label for="obra-tipoObra">Tipo Obra</Label>
                <AvInput id="obra-tipoObra" data-cy="tipoObra" type="select" className="form-control" name="tipoObraId">
                  <option value="" key="0" />
                  {tipoObras
                    ? tipoObras.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descripcion}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/obra" replace color="info">
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
  tipoObras: storeState.tipoObra.entities,
  obraEntity: storeState.obra.entity,
  loading: storeState.obra.loading,
  updating: storeState.obra.updating,
  updateSuccess: storeState.obra.updateSuccess,
});

const mapDispatchToProps = {
  getTipoObras,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ObraUpdate);
