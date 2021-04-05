import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INSE, defaultValue } from 'app/shared/model/nse.model';

export const ACTION_TYPES = {
  FETCH_NSE_LIST: 'nSE/FETCH_NSE_LIST',
  FETCH_NSE: 'nSE/FETCH_NSE',
  CREATE_NSE: 'nSE/CREATE_NSE',
  UPDATE_NSE: 'nSE/UPDATE_NSE',
  PARTIAL_UPDATE_NSE: 'nSE/PARTIAL_UPDATE_NSE',
  DELETE_NSE: 'nSE/DELETE_NSE',
  RESET: 'nSE/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INSE>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type NSEState = Readonly<typeof initialState>;

// Reducer

export default (state: NSEState = initialState, action): NSEState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NSE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_NSE):
    case REQUEST(ACTION_TYPES.UPDATE_NSE):
    case REQUEST(ACTION_TYPES.DELETE_NSE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_NSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_NSE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NSE):
    case FAILURE(ACTION_TYPES.CREATE_NSE):
    case FAILURE(ACTION_TYPES.UPDATE_NSE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_NSE):
    case FAILURE(ACTION_TYPES.DELETE_NSE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_NSE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_NSE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_NSE):
    case SUCCESS(ACTION_TYPES.UPDATE_NSE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_NSE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_NSE):
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

const apiUrl = 'api/nses';

// Actions

export const getEntities: ICrudGetAllAction<INSE> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NSE_LIST,
  payload: axios.get<INSE>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<INSE> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NSE,
    payload: axios.get<INSE>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<INSE> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NSE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INSE> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NSE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<INSE> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_NSE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<INSE> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NSE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
