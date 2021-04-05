import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './direccion.reducer';
import { IDireccion } from 'app/shared/model/direccion.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDireccionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DireccionUpdate = (props: IDireccionUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { direccionEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/direccion');
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
        ...direccionEntity,
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
          <h2 id="gempBoostrapApp.direccion.home.createOrEditLabel" data-cy="DireccionCreateUpdateHeading">
            Create or edit a Direccion
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : direccionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="direccion-id">Id</Label>
                  <AvInput id="direccion-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="identificationLabel" for="direccion-identification">
                  Identification
                </Label>
                <AvField id="direccion-identification" data-cy="identification" type="text" name="identification" validate={{}} />
              </AvGroup>
              <AvGroup>
                <Label id="paisLabel" for="direccion-pais">
                  Pais
                </Label>
                <AvField
                  id="direccion-pais"
                  data-cy="pais"
                  type="text"
                  name="pais"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="provinciaLabel" for="direccion-provincia">
                  Provincia
                </Label>
                <AvField
                  id="direccion-provincia"
                  data-cy="provincia"
                  type="text"
                  name="provincia"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="partidoLabel" for="direccion-partido">
                  Partido
                </Label>
                <AvField
                  id="direccion-partido"
                  data-cy="partido"
                  type="text"
                  name="partido"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="localidadLabel" for="direccion-localidad">
                  Localidad
                </Label>
                <AvField
                  id="direccion-localidad"
                  data-cy="localidad"
                  type="text"
                  name="localidad"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="calleLabel" for="direccion-calle">
                  Calle
                </Label>
                <AvField
                  id="direccion-calle"
                  data-cy="calle"
                  type="text"
                  name="calle"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="alturaLabel" for="direccion-altura">
                  Altura
                </Label>
                <AvField
                  id="direccion-altura"
                  data-cy="altura"
                  type="string"
                  className="form-control"
                  name="altura"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="regionLabel" for="direccion-region">
                  Region
                </Label>
                <AvField id="direccion-region" data-cy="region" type="text" name="region" />
              </AvGroup>
              <AvGroup>
                <Label id="subregionLabel" for="direccion-subregion">
                  Subregion
                </Label>
                <AvField id="direccion-subregion" data-cy="subregion" type="text" name="subregion" />
              </AvGroup>
              <AvGroup>
                <Label id="hubLabel" for="direccion-hub">
                  Hub
                </Label>
                <AvField id="direccion-hub" data-cy="hub" type="text" name="hub" />
              </AvGroup>
              <AvGroup>
                <Label id="barriosEspecialesLabel" for="direccion-barriosEspeciales">
                  Barrios Especiales
                </Label>
                <AvField id="direccion-barriosEspeciales" data-cy="barriosEspeciales" type="text" name="barriosEspeciales" />
              </AvGroup>
              <AvGroup>
                <Label id="codigoPostalLabel" for="direccion-codigoPostal">
                  Codigo Postal
                </Label>
                <AvField id="direccion-codigoPostal" data-cy="codigoPostal" type="text" name="codigoPostal" />
              </AvGroup>
              <AvGroup>
                <Label id="tipoCalleLabel" for="direccion-tipoCalle">
                  Tipo Calle
                </Label>
                <AvField id="direccion-tipoCalle" data-cy="tipoCalle" type="text" name="tipoCalle" />
              </AvGroup>
              <AvGroup>
                <Label id="zonaCompetenciaLabel" for="direccion-zonaCompetencia">
                  Zona Competencia
                </Label>
                <AvField id="direccion-zonaCompetencia" data-cy="zonaCompetencia" type="text" name="zonaCompetencia" />
              </AvGroup>
              <AvGroup>
                <Label id="intersectionLeftLabel" for="direccion-intersectionLeft">
                  Intersection Left
                </Label>
                <AvField id="direccion-intersectionLeft" data-cy="intersectionLeft" type="text" name="intersectionLeft" />
              </AvGroup>
              <AvGroup>
                <Label id="intersectionRightLabel" for="direccion-intersectionRight">
                  Intersection Right
                </Label>
                <AvField id="direccion-intersectionRight" data-cy="intersectionRight" type="text" name="intersectionRight" />
              </AvGroup>
              <AvGroup>
                <Label id="streetTypeLabel" for="direccion-streetType">
                  Street Type
                </Label>
                <AvField id="direccion-streetType" data-cy="streetType" type="text" name="streetType" />
              </AvGroup>
              <AvGroup>
                <Label id="latitudLabel" for="direccion-latitud">
                  Latitud
                </Label>
                <AvField id="direccion-latitud" data-cy="latitud" type="text" name="latitud" />
              </AvGroup>
              <AvGroup>
                <Label id="longitudLabel" for="direccion-longitud">
                  Longitud
                </Label>
                <AvField id="direccion-longitud" data-cy="longitud" type="text" name="longitud" />
              </AvGroup>
              <AvGroup>
                <Label id="elementosDeRedLabel" for="direccion-elementosDeRed">
                  Elementos De Red
                </Label>
                <AvField id="direccion-elementosDeRed" data-cy="elementosDeRed" type="text" name="elementosDeRed" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/direccion" replace color="info">
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
  direccionEntity: storeState.direccion.entity,
  loading: storeState.direccion.loading,
  updating: storeState.direccion.updating,
  updateSuccess: storeState.direccion.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DireccionUpdate);
