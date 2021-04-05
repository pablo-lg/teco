import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEjecCuentas, defaultValue } from 'app/shared/model/ejec-cuentas.model';

export const ACTION_TYPES = {
  FETCH_EJECCUENTAS_LIST: 'ejecCuentas/FETCH_EJECCUENTAS_LIST',
  FETCH_EJECCUENTAS: 'ejecCuentas/FETCH_EJECCUENTAS',
  CREATE_EJECCUENTAS: 'ejecCuentas/CREATE_EJECCUENTAS',
  UPDATE_EJECCUENTAS: 'ejecCuentas/UPDATE_EJECCUENTAS',
  PARTIAL_UPDATE_EJECCUENTAS: 'ejecCuentas/PARTIAL_UPDATE_EJECCUENTAS',
  DELETE_EJECCUENTAS: 'ejecCuentas/DELETE_EJECCUENTAS',
  RESET: 'ejecCuentas/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEjecCuentas>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type EjecCuentasState = Readonly<typeof initialState>;

// Reducer

export default (state: EjecCuentasState = initialState, action): EjecCuentasState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EJECCUENTAS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EJECCUENTAS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EJECCUENTAS):
    case REQUEST(ACTION_TYPES.UPDATE_EJECCUENTAS):
    case REQUEST(ACTION_TYPES.DELETE_EJECCUENTAS):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_EJECCUENTAS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EJECCUENTAS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EJECCUENTAS):
    case FAILURE(ACTION_TYPES.CREATE_EJECCUENTAS):
    case FAILURE(ACTION_TYPES.UPDATE_EJECCUENTAS):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_EJECCUENTAS):
    case FAILURE(ACTION_TYPES.DELETE_EJECCUENTAS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EJECCUENTAS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EJECCUENTAS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EJECCUENTAS):
    case SUCCESS(ACTION_TYPES.UPDATE_EJECCUENTAS):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_EJECCUENTAS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EJECCUENTAS):
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

const apiUrl = 'api/ejec-cuentas';

// Actions

export const getEntities: ICrudGetAllAction<IEjecCuentas> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_EJECCUENTAS_LIST,
  payload: axios.get<IEjecCuentas>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IEjecCuentas> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EJECCUENTAS,
    payload: axios.get<IEjecCuentas>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEjecCuentas> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EJECCUENTAS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEjecCuentas> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EJECCUENTAS,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IEjecCuentas> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_EJECCUENTAS,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEjecCuentas> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EJECCUENTAS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
