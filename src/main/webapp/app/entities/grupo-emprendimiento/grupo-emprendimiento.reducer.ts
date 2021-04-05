import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGrupoEmprendimiento, defaultValue } from 'app/shared/model/grupo-emprendimiento.model';

export const ACTION_TYPES = {
  FETCH_GRUPOEMPRENDIMIENTO_LIST: 'grupoEmprendimiento/FETCH_GRUPOEMPRENDIMIENTO_LIST',
  FETCH_GRUPOEMPRENDIMIENTO: 'grupoEmprendimiento/FETCH_GRUPOEMPRENDIMIENTO',
  CREATE_GRUPOEMPRENDIMIENTO: 'grupoEmprendimiento/CREATE_GRUPOEMPRENDIMIENTO',
  UPDATE_GRUPOEMPRENDIMIENTO: 'grupoEmprendimiento/UPDATE_GRUPOEMPRENDIMIENTO',
  PARTIAL_UPDATE_GRUPOEMPRENDIMIENTO: 'grupoEmprendimiento/PARTIAL_UPDATE_GRUPOEMPRENDIMIENTO',
  DELETE_GRUPOEMPRENDIMIENTO: 'grupoEmprendimiento/DELETE_GRUPOEMPRENDIMIENTO',
  RESET: 'grupoEmprendimiento/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGrupoEmprendimiento>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type GrupoEmprendimientoState = Readonly<typeof initialState>;

// Reducer

export default (state: GrupoEmprendimientoState = initialState, action): GrupoEmprendimientoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GRUPOEMPRENDIMIENTO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GRUPOEMPRENDIMIENTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GRUPOEMPRENDIMIENTO):
    case REQUEST(ACTION_TYPES.UPDATE_GRUPOEMPRENDIMIENTO):
    case REQUEST(ACTION_TYPES.DELETE_GRUPOEMPRENDIMIENTO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_GRUPOEMPRENDIMIENTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GRUPOEMPRENDIMIENTO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GRUPOEMPRENDIMIENTO):
    case FAILURE(ACTION_TYPES.CREATE_GRUPOEMPRENDIMIENTO):
    case FAILURE(ACTION_TYPES.UPDATE_GRUPOEMPRENDIMIENTO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_GRUPOEMPRENDIMIENTO):
    case FAILURE(ACTION_TYPES.DELETE_GRUPOEMPRENDIMIENTO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GRUPOEMPRENDIMIENTO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GRUPOEMPRENDIMIENTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GRUPOEMPRENDIMIENTO):
    case SUCCESS(ACTION_TYPES.UPDATE_GRUPOEMPRENDIMIENTO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_GRUPOEMPRENDIMIENTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GRUPOEMPRENDIMIENTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/grupo-emprendimientos';

// Actions

export const getEntities: ICrudGetAllAction<IGrupoEmprendimiento> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_GRUPOEMPRENDIMIENTO_LIST,
  payload: axios.get<IGrupoEmprendimiento>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IGrupoEmprendimiento> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GRUPOEMPRENDIMIENTO,
    payload: axios.get<IGrupoEmprendimiento>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGrupoEmprendimiento> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GRUPOEMPRENDIMIENTO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGrupoEmprendimiento> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GRUPOEMPRENDIMIENTO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IGrupoEmprendimiento> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_GRUPOEMPRENDIMIENTO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGrupoEmprendimiento> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GRUPOEMPRENDIMIENTO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
