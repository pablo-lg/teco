import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IObra, defaultValue } from 'app/shared/model/obra.model';

export const ACTION_TYPES = {
  FETCH_OBRA_LIST: 'obra/FETCH_OBRA_LIST',
  FETCH_OBRA: 'obra/FETCH_OBRA',
  CREATE_OBRA: 'obra/CREATE_OBRA',
  UPDATE_OBRA: 'obra/UPDATE_OBRA',
  PARTIAL_UPDATE_OBRA: 'obra/PARTIAL_UPDATE_OBRA',
  DELETE_OBRA: 'obra/DELETE_OBRA',
  RESET: 'obra/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IObra>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ObraState = Readonly<typeof initialState>;

// Reducer

export default (state: ObraState = initialState, action): ObraState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_OBRA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_OBRA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_OBRA):
    case REQUEST(ACTION_TYPES.UPDATE_OBRA):
    case REQUEST(ACTION_TYPES.DELETE_OBRA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_OBRA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_OBRA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_OBRA):
    case FAILURE(ACTION_TYPES.CREATE_OBRA):
    case FAILURE(ACTION_TYPES.UPDATE_OBRA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_OBRA):
    case FAILURE(ACTION_TYPES.DELETE_OBRA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_OBRA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_OBRA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_OBRA):
    case SUCCESS(ACTION_TYPES.UPDATE_OBRA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_OBRA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_OBRA):
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

const apiUrl = 'api/obras';

// Actions

export const getEntities: ICrudGetAllAction<IObra> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_OBRA_LIST,
  payload: axios.get<IObra>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IObra> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_OBRA,
    payload: axios.get<IObra>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IObra> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_OBRA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IObra> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_OBRA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IObra> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_OBRA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IObra> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_OBRA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
