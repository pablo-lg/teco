import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISegmento } from 'app/shared/model/segmento.model';
import { getEntities as getSegmentos } from 'app/entities/segmento/segmento.reducer';
import { getEntity, updateEntity, createEntity, reset } from './tipo-obra.reducer';
import { ITipoObra } from 'app/shared/model/tipo-obra.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITipoObraUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TipoObraUpdate = (props: ITipoObraUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { tipoObraEntity, segmentos, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/tipo-obra');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getSegmentos();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...tipoObraEntity,
        ...values,
        segmento: segmentos.find(it => it.id.toString() === values.segmentoId.toString()),
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
          <h2 id="gempBoostrapApp.tipoObra.home.createOrEditLabel" data-cy="TipoObraCreateUpdateHeading">
            Create or edit a TipoObra
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : tipoObraEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="tipo-obra-id">Id</Label>
                  <AvInput id="tipo-obra-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descripcionLabel" for="tipo-obra-descripcion">
                  Descripcion
                </Label>
                <AvField id="tipo-obra-descripcion" data-cy="descripcion" type="text" name="descripcion" />
              </AvGroup>
              <AvGroup>
                <Label for="tipo-obra-segmento">Segmento</Label>
                <AvInput id="tipo-obra-segmento" data-cy="segmento" type="select" className="form-control" name="segmentoId">
                  <option value="" key="0" />
                  {segmentos
                    ? segmentos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descripcion}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/tipo-obra" replace color="info">
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
  segmentos: storeState.segmento.entities,
  tipoObraEntity: storeState.tipoObra.entity,
  loading: storeState.tipoObra.loading,
  updating: storeState.tipoObra.updating,
  updateSuccess: storeState.tipoObra.updateSuccess,
});

const mapDispatchToProps = {
  getSegmentos,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TipoObraUpdate);
