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
import { IObra } from 'app/shared/model/obra.model';
import { getEntities as getObras } from 'app/entities/obra/obra.reducer';
import { ITipoObra } from 'app/shared/model/tipo-obra.model';
import { getEntities as getTipoObras } from 'app/entities/tipo-obra/tipo-obra.reducer';
import { ITipoEmp } from 'app/shared/model/tipo-emp.model';
import { getEntities as getTipoEmps } from 'app/entities/tipo-emp/tipo-emp.reducer';
import { IEstado } from 'app/shared/model/estado.model';
import { getEntities as getEstados } from 'app/entities/estado/estado.reducer';
import { ICompetencia } from 'app/shared/model/competencia.model';
import { getEntities as getCompetencias } from 'app/entities/competencia/competencia.reducer';
import { IDespliegue } from 'app/shared/model/despliegue.model';
import { getEntities as getDespliegues } from 'app/entities/despliegue/despliegue.reducer';
import { INSE } from 'app/shared/model/nse.model';
import { getEntities as getNSes } from 'app/entities/nse/nse.reducer';
import { ISegmento } from 'app/shared/model/segmento.model';
import { getEntities as getSegmentos } from 'app/entities/segmento/segmento.reducer';
import { ITecnologia } from 'app/shared/model/tecnologia.model';
import { getEntities as getTecnologias } from 'app/entities/tecnologia/tecnologia.reducer';
import { IEjecCuentas } from 'app/shared/model/ejec-cuentas.model';
import { getEntities as getEjecCuentas } from 'app/entities/ejec-cuentas/ejec-cuentas.reducer';
import { IDireccion } from 'app/shared/model/direccion.model';
import { getEntities as getDireccions } from 'app/entities/direccion/direccion.reducer';
import { getEntity, updateEntity, createEntity, reset } from './emprendimiento.reducer';
import { IEmprendimiento } from 'app/shared/model/emprendimiento.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmprendimientoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmprendimientoUpdate = (props: IEmprendimientoUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const {
    emprendimientoEntity,
    grupoEmprendimientos,
    obras,
    tipoObras,
    tipoEmps,
    estados,
    competencias,
    despliegues,
    nSES,
    segmentos,
    tecnologias,
    ejecCuentas,
    direccions,
    loading,
    updating,
  } = props;

  const handleClose = () => {
    props.history.push('/emprendimiento');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getGrupoEmprendimientos();
    props.getObras();
    props.getTipoObras();
    props.getTipoEmps();
    props.getEstados();
    props.getCompetencias();
    props.getDespliegues();
    props.getNSes();
    props.getSegmentos();
    props.getTecnologias();
    props.getEjecCuentas();
    props.getDireccions();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...emprendimientoEntity,
        ...values,
        grupoEmprendimiento: grupoEmprendimientos.find(it => it.id.toString() === values.grupoEmprendimientoId.toString()),
        obra: obras.find(it => it.id.toString() === values.obraId.toString()),
        tipoObra: tipoObras.find(it => it.id.toString() === values.tipoObraId.toString()),
        tipoEmp: tipoEmps.find(it => it.id.toString() === values.tipoEmpId.toString()),
        estado: estados.find(it => it.id.toString() === values.estadoId.toString()),
        competencia: competencias.find(it => it.id.toString() === values.competenciaId.toString()),
        despliegue: despliegues.find(it => it.id.toString() === values.despliegueId.toString()),
        nSE: nSES.find(it => it.id.toString() === values.nSEId.toString()),
        segmento: segmentos.find(it => it.id.toString() === values.segmentoId.toString()),
        tecnologia: tecnologias.find(it => it.id.toString() === values.tecnologiaId.toString()),
        ejecCuentas: ejecCuentas.find(it => it.id.toString() === values.ejecCuentasId.toString()),
        direccion: direccions.find(it => it.id.toString() === values.direccionId.toString()),
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
          <h2 id="gempBoostrapApp.emprendimiento.home.createOrEditLabel" data-cy="EmprendimientoCreateUpdateHeading">
            Create or edit a Emprendimiento
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : emprendimientoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="emprendimiento-id">Id</Label>
                  <AvInput id="emprendimiento-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nombreLabel" for="emprendimiento-nombre">
                  Nombre
                </Label>
                <AvField id="emprendimiento-nombre" data-cy="nombre" type="text" name="nombre" />
              </AvGroup>
              <AvGroup>
                <Label id="contactoLabel" for="emprendimiento-contacto">
                  Contacto
                </Label>
                <AvField id="emprendimiento-contacto" data-cy="contacto" type="text" name="contacto" />
              </AvGroup>
              <AvGroup>
                <Label id="fechaFinObraLabel" for="emprendimiento-fechaFinObra">
                  Fecha Fin Obra
                </Label>
                <AvField id="emprendimiento-fechaFinObra" data-cy="fechaFinObra" type="date" className="form-control" name="fechaFinObra" />
              </AvGroup>
              <AvGroup>
                <Label id="elementosDeRedLabel" for="emprendimiento-elementosDeRed">
                  Elementos De Red
                </Label>
                <AvField id="emprendimiento-elementosDeRed" data-cy="elementosDeRed" type="text" name="elementosDeRed" />
              </AvGroup>
              <AvGroup>
                <Label id="clientesCatvLabel" for="emprendimiento-clientesCatv">
                  Clientes Catv
                </Label>
                <AvField id="emprendimiento-clientesCatv" data-cy="clientesCatv" type="text" name="clientesCatv" />
              </AvGroup>
              <AvGroup>
                <Label id="clientesFibertelLabel" for="emprendimiento-clientesFibertel">
                  Clientes Fibertel
                </Label>
                <AvField id="emprendimiento-clientesFibertel" data-cy="clientesFibertel" type="text" name="clientesFibertel" />
              </AvGroup>
              <AvGroup>
                <Label id="clientesFibertelLiteLabel" for="emprendimiento-clientesFibertelLite">
                  Clientes Fibertel Lite
                </Label>
                <AvField id="emprendimiento-clientesFibertelLite" data-cy="clientesFibertelLite" type="text" name="clientesFibertelLite" />
              </AvGroup>
              <AvGroup>
                <Label id="clientesFlowLabel" for="emprendimiento-clientesFlow">
                  Clientes Flow
                </Label>
                <AvField id="emprendimiento-clientesFlow" data-cy="clientesFlow" type="text" name="clientesFlow" />
              </AvGroup>
              <AvGroup>
                <Label id="clientesComboLabel" for="emprendimiento-clientesCombo">
                  Clientes Combo
                </Label>
                <AvField id="emprendimiento-clientesCombo" data-cy="clientesCombo" type="text" name="clientesCombo" />
              </AvGroup>
              <AvGroup>
                <Label id="lineasVozLabel" for="emprendimiento-lineasVoz">
                  Lineas Voz
                </Label>
                <AvField id="emprendimiento-lineasVoz" data-cy="lineasVoz" type="text" name="lineasVoz" />
              </AvGroup>
              <AvGroup>
                <Label id="mesesDeFinalizadoLabel" for="emprendimiento-mesesDeFinalizado">
                  Meses De Finalizado
                </Label>
                <AvField id="emprendimiento-mesesDeFinalizado" data-cy="mesesDeFinalizado" type="text" name="mesesDeFinalizado" />
              </AvGroup>
              <AvGroup>
                <Label id="altasBCLabel" for="emprendimiento-altasBC">
                  Altas BC
                </Label>
                <AvField id="emprendimiento-altasBC" data-cy="altasBC" type="text" name="altasBC" />
              </AvGroup>
              <AvGroup>
                <Label id="penetracionVivLotLabel" for="emprendimiento-penetracionVivLot">
                  Penetracion Viv Lot
                </Label>
                <AvField id="emprendimiento-penetracionVivLot" data-cy="penetracionVivLot" type="text" name="penetracionVivLot" />
              </AvGroup>
              <AvGroup>
                <Label id="penetracionBCLabel" for="emprendimiento-penetracionBC">
                  Penetracion BC
                </Label>
                <AvField id="emprendimiento-penetracionBC" data-cy="penetracionBC" type="text" name="penetracionBC" />
              </AvGroup>
              <AvGroup>
                <Label id="demanda1Label" for="emprendimiento-demanda1">
                  Demanda 1
                </Label>
                <AvField id="emprendimiento-demanda1" data-cy="demanda1" type="text" name="demanda1" />
              </AvGroup>
              <AvGroup>
                <Label id="demanda2Label" for="emprendimiento-demanda2">
                  Demanda 2
                </Label>
                <AvField id="emprendimiento-demanda2" data-cy="demanda2" type="text" name="demanda2" />
              </AvGroup>
              <AvGroup>
                <Label id="demanda3Label" for="emprendimiento-demanda3">
                  Demanda 3
                </Label>
                <AvField id="emprendimiento-demanda3" data-cy="demanda3" type="text" name="demanda3" />
              </AvGroup>
              <AvGroup>
                <Label id="demanda4Label" for="emprendimiento-demanda4">
                  Demanda 4
                </Label>
                <AvField id="emprendimiento-demanda4" data-cy="demanda4" type="text" name="demanda4" />
              </AvGroup>
              <AvGroup>
                <Label id="lotesLabel" for="emprendimiento-lotes">
                  Lotes
                </Label>
                <AvField id="emprendimiento-lotes" data-cy="lotes" type="text" name="lotes" />
              </AvGroup>
              <AvGroup>
                <Label id="viviendasLabel" for="emprendimiento-viviendas">
                  Viviendas
                </Label>
                <AvField id="emprendimiento-viviendas" data-cy="viviendas" type="text" name="viviendas" />
              </AvGroup>
              <AvGroup>
                <Label id="comProfLabel" for="emprendimiento-comProf">
                  Com Prof
                </Label>
                <AvField id="emprendimiento-comProf" data-cy="comProf" type="text" name="comProf" />
              </AvGroup>
              <AvGroup>
                <Label id="habitacionesLabel" for="emprendimiento-habitaciones">
                  Habitaciones
                </Label>
                <AvField id="emprendimiento-habitaciones" data-cy="habitaciones" type="text" name="habitaciones" />
              </AvGroup>
              <AvGroup>
                <Label id="manzanasLabel" for="emprendimiento-manzanas">
                  Manzanas
                </Label>
                <AvField id="emprendimiento-manzanas" data-cy="manzanas" type="text" name="manzanas" />
              </AvGroup>
              <AvGroup>
                <Label id="demandaLabel" for="emprendimiento-demanda">
                  Demanda
                </Label>
                <AvField id="emprendimiento-demanda" data-cy="demanda" type="text" name="demanda" />
              </AvGroup>
              <AvGroup>
                <Label id="fechaDeRelevamientoLabel" for="emprendimiento-fechaDeRelevamiento">
                  Fecha De Relevamiento
                </Label>
                <AvField
                  id="emprendimiento-fechaDeRelevamiento"
                  data-cy="fechaDeRelevamiento"
                  type="date"
                  className="form-control"
                  name="fechaDeRelevamiento"
                />
              </AvGroup>
              <AvGroup>
                <Label id="telefonoLabel" for="emprendimiento-telefono">
                  Telefono
                </Label>
                <AvField id="emprendimiento-telefono" data-cy="telefono" type="text" name="telefono" />
              </AvGroup>
              <AvGroup>
                <Label id="anoPriorizacionLabel" for="emprendimiento-anoPriorizacion">
                  Ano Priorizacion
                </Label>
                <AvField
                  id="emprendimiento-anoPriorizacion"
                  data-cy="anoPriorizacion"
                  type="date"
                  className="form-control"
                  name="anoPriorizacion"
                />
              </AvGroup>
              <AvGroup>
                <Label id="contratoOpenLabel" for="emprendimiento-contratoOpen">
                  Contrato Open
                </Label>
                <AvField id="emprendimiento-contratoOpen" data-cy="contratoOpen" type="text" name="contratoOpen" />
              </AvGroup>
              <AvGroup check>
                <Label id="negociacionLabel">
                  <AvInput
                    id="emprendimiento-negociacion"
                    data-cy="negociacion"
                    type="checkbox"
                    className="form-check-input"
                    name="negociacion"
                  />
                  Negociacion
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="estadoBCLabel" for="emprendimiento-estadoBC">
                  Estado BC
                </Label>
                <AvField id="emprendimiento-estadoBC" data-cy="estadoBC" type="text" name="estadoBC" />
              </AvGroup>
              <AvGroup>
                <Label id="fechaLabel" for="emprendimiento-fecha">
                  Fecha
                </Label>
                <AvField id="emprendimiento-fecha" data-cy="fecha" type="date" className="form-control" name="fecha" />
              </AvGroup>
              <AvGroup>
                <Label id="codigoDeFirmaLabel" for="emprendimiento-codigoDeFirma">
                  Codigo De Firma
                </Label>
                <AvField id="emprendimiento-codigoDeFirma" data-cy="codigoDeFirma" type="text" name="codigoDeFirma" />
              </AvGroup>
              <AvGroup>
                <Label id="fechaFirmaLabel" for="emprendimiento-fechaFirma">
                  Fecha Firma
                </Label>
                <AvField id="emprendimiento-fechaFirma" data-cy="fechaFirma" type="date" className="form-control" name="fechaFirma" />
              </AvGroup>
              <AvGroup>
                <Label id="observacionesLabel" for="emprendimiento-observaciones">
                  Observaciones
                </Label>
                <AvField id="emprendimiento-observaciones" data-cy="observaciones" type="text" name="observaciones" />
              </AvGroup>
              <AvGroup>
                <Label id="comentarioLabel" for="emprendimiento-comentario">
                  Comentario
                </Label>
                <AvField id="emprendimiento-comentario" data-cy="comentario" type="text" name="comentario" />
              </AvGroup>
              <AvGroup>
                <Label id="estadoFirmaLabel" for="emprendimiento-estadoFirma">
                  Estado Firma
                </Label>
                <AvField id="emprendimiento-estadoFirma" data-cy="estadoFirma" type="text" name="estadoFirma" />
              </AvGroup>
              <AvGroup>
                <Label for="emprendimiento-grupoEmprendimiento">Grupo Emprendimiento</Label>
                <AvInput
                  id="emprendimiento-grupoEmprendimiento"
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
                <Label for="emprendimiento-obra">Obra</Label>
                <AvInput id="emprendimiento-obra" data-cy="obra" type="select" className="form-control" name="obraId">
                  <option value="" key="0" />
                  {obras
                    ? obras.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descripcion}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="emprendimiento-tipoObra">Tipo Obra</Label>
                <AvInput id="emprendimiento-tipoObra" data-cy="tipoObra" type="select" className="form-control" name="tipoObraId">
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
              <AvGroup>
                <Label for="emprendimiento-tipoEmp">Tipo Emp</Label>
                <AvInput id="emprendimiento-tipoEmp" data-cy="tipoEmp" type="select" className="form-control" name="tipoEmpId">
                  <option value="" key="0" />
                  {tipoEmps
                    ? tipoEmps.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descripcion}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="emprendimiento-estado">Estado</Label>
                <AvInput id="emprendimiento-estado" data-cy="estado" type="select" className="form-control" name="estadoId">
                  <option value="" key="0" />
                  {estados
                    ? estados.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descripcion}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="emprendimiento-competencia">Competencia</Label>
                <AvInput id="emprendimiento-competencia" data-cy="competencia" type="select" className="form-control" name="competenciaId">
                  <option value="" key="0" />
                  {competencias
                    ? competencias.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descripcion}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="emprendimiento-despliegue">Despliegue</Label>
                <AvInput id="emprendimiento-despliegue" data-cy="despliegue" type="select" className="form-control" name="despliegueId">
                  <option value="" key="0" />
                  {despliegues
                    ? despliegues.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descripcion}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="emprendimiento-nSE">N SE</Label>
                <AvInput id="emprendimiento-nSE" data-cy="nSE" type="select" className="form-control" name="nSEId">
                  <option value="" key="0" />
                  {nSES
                    ? nSES.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descripcion}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="emprendimiento-segmento">Segmento</Label>
                <AvInput id="emprendimiento-segmento" data-cy="segmento" type="select" className="form-control" name="segmentoId">
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
              <AvGroup>
                <Label for="emprendimiento-tecnologia">Tecnologia</Label>
                <AvInput id="emprendimiento-tecnologia" data-cy="tecnologia" type="select" className="form-control" name="tecnologiaId">
                  <option value="" key="0" />
                  {tecnologias
                    ? tecnologias.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descripcion}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="emprendimiento-ejecCuentas">Ejec Cuentas</Label>
                <AvInput id="emprendimiento-ejecCuentas" data-cy="ejecCuentas" type="select" className="form-control" name="ejecCuentasId">
                  <option value="" key="0" />
                  {ejecCuentas
                    ? ejecCuentas.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nombre}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="emprendimiento-direccion">Direccion</Label>
                <AvInput id="emprendimiento-direccion" data-cy="direccion" type="select" className="form-control" name="direccionId">
                  <option value="" key="0" />
                  {direccions
                    ? direccions.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.calle}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/emprendimiento" replace color="info">
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
  obras: storeState.obra.entities,
  tipoObras: storeState.tipoObra.entities,
  tipoEmps: storeState.tipoEmp.entities,
  estados: storeState.estado.entities,
  competencias: storeState.competencia.entities,
  despliegues: storeState.despliegue.entities,
  nSES: storeState.nSE.entities,
  segmentos: storeState.segmento.entities,
  tecnologias: storeState.tecnologia.entities,
  ejecCuentas: storeState.ejecCuentas.entities,
  direccions: storeState.direccion.entities,
  emprendimientoEntity: storeState.emprendimiento.entity,
  loading: storeState.emprendimiento.loading,
  updating: storeState.emprendimiento.updating,
  updateSuccess: storeState.emprendimiento.updateSuccess,
});

const mapDispatchToProps = {
  getGrupoEmprendimientos,
  getObras,
  getTipoObras,
  getTipoEmps,
  getEstados,
  getCompetencias,
  getDespliegues,
  getNSes,
  getSegmentos,
  getTecnologias,
  getEjecCuentas,
  getDireccions,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmprendimientoUpdate);
