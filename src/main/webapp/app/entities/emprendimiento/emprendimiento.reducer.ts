import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmprendimiento, defaultValue } from 'app/shared/model/emprendimiento.model';

export const ACTION_TYPES = {
  FETCH_EMPRENDIMIENTO_LIST: 'emprendimiento/FETCH_EMPRENDIMIENTO_LIST',
  FETCH_EMPRENDIMIENTO: 'emprendimiento/FETCH_EMPRENDIMIENTO',
  CREATE_EMPRENDIMIENTO: 'emprendimiento/CREATE_EMPRENDIMIENTO',
  UPDATE_EMPRENDIMIENTO: 'emprendimiento/UPDATE_EMPRENDIMIENTO',
  PARTIAL_UPDATE_EMPRENDIMIENTO: 'emprendimiento/PARTIAL_UPDATE_EMPRENDIMIENTO',
  DELETE_EMPRENDIMIENTO: 'emprendimiento/DELETE_EMPRENDIMIENTO',
  RESET: 'emprendimiento/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmprendimiento>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type EmprendimientoState = Readonly<typeof initialState>;

// Reducer

export default (state: EmprendimientoState = initialState, action): EmprendimientoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMPRENDIMIENTO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMPRENDIMIENTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EMPRENDIMIENTO):
    case REQUEST(ACTION_TYPES.UPDATE_EMPRENDIMIENTO):
    case REQUEST(ACTION_TYPES.DELETE_EMPRENDIMIENTO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_EMPRENDIMIENTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EMPRENDIMIENTO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMPRENDIMIENTO):
    case FAILURE(ACTION_TYPES.CREATE_EMPRENDIMIENTO):
    case FAILURE(ACTION_TYPES.UPDATE_EMPRENDIMIENTO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_EMPRENDIMIENTO):
    case FAILURE(ACTION_TYPES.DELETE_EMPRENDIMIENTO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPRENDIMIENTO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPRENDIMIENTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMPRENDIMIENTO):
    case SUCCESS(ACTION_TYPES.UPDATE_EMPRENDIMIENTO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_EMPRENDIMIENTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMPRENDIMIENTO):
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

const apiUrl = 'api/emprendimientos';

// Actions

export const getEntities: ICrudGetAllAction<IEmprendimiento> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_EMPRENDIMIENTO_LIST,
  payload: axios.get<IEmprendimiento>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IEmprendimiento> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMPRENDIMIENTO,
    payload: axios.get<IEmprendimiento>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEmprendimiento> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMPRENDIMIENTO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmprendimiento> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMPRENDIMIENTO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IEmprendimiento> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_EMPRENDIMIENTO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmprendimiento> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMPRENDIMIENTO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
