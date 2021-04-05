import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDireccion, defaultValue } from 'app/shared/model/direccion.model';

export const ACTION_TYPES = {
  FETCH_DIRECCION_LIST: 'direccion/FETCH_DIRECCION_LIST',
  FETCH_DIRECCION: 'direccion/FETCH_DIRECCION',
  CREATE_DIRECCION: 'direccion/CREATE_DIRECCION',
  UPDATE_DIRECCION: 'direccion/UPDATE_DIRECCION',
  PARTIAL_UPDATE_DIRECCION: 'direccion/PARTIAL_UPDATE_DIRECCION',
  DELETE_DIRECCION: 'direccion/DELETE_DIRECCION',
  RESET: 'direccion/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDireccion>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type DireccionState = Readonly<typeof initialState>;

// Reducer

export default (state: DireccionState = initialState, action): DireccionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DIRECCION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DIRECCION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_DIRECCION):
    case REQUEST(ACTION_TYPES.UPDATE_DIRECCION):
    case REQUEST(ACTION_TYPES.DELETE_DIRECCION):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_DIRECCION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_DIRECCION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DIRECCION):
    case FAILURE(ACTION_TYPES.CREATE_DIRECCION):
    case FAILURE(ACTION_TYPES.UPDATE_DIRECCION):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_DIRECCION):
    case FAILURE(ACTION_TYPES.DELETE_DIRECCION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DIRECCION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DIRECCION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_DIRECCION):
    case SUCCESS(ACTION_TYPES.UPDATE_DIRECCION):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_DIRECCION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_DIRECCION):
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

const apiUrl = 'api/direccions';

// Actions

export const getEntities: ICrudGetAllAction<IDireccion> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DIRECCION_LIST,
  payload: axios.get<IDireccion>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IDireccion> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DIRECCION,
    payload: axios.get<IDireccion>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IDireccion> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DIRECCION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDireccion> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DIRECCION,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IDireccion> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_DIRECCION,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDireccion> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DIRECCION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
