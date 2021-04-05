import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPauta, defaultValue } from 'app/shared/model/pauta.model';

export const ACTION_TYPES = {
  FETCH_PAUTA_LIST: 'pauta/FETCH_PAUTA_LIST',
  FETCH_PAUTA: 'pauta/FETCH_PAUTA',
  CREATE_PAUTA: 'pauta/CREATE_PAUTA',
  UPDATE_PAUTA: 'pauta/UPDATE_PAUTA',
  PARTIAL_UPDATE_PAUTA: 'pauta/PARTIAL_UPDATE_PAUTA',
  DELETE_PAUTA: 'pauta/DELETE_PAUTA',
  RESET: 'pauta/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPauta>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type PautaState = Readonly<typeof initialState>;

// Reducer

export default (state: PautaState = initialState, action): PautaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PAUTA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PAUTA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PAUTA):
    case REQUEST(ACTION_TYPES.UPDATE_PAUTA):
    case REQUEST(ACTION_TYPES.DELETE_PAUTA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_PAUTA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PAUTA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PAUTA):
    case FAILURE(ACTION_TYPES.CREATE_PAUTA):
    case FAILURE(ACTION_TYPES.UPDATE_PAUTA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_PAUTA):
    case FAILURE(ACTION_TYPES.DELETE_PAUTA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAUTA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAUTA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PAUTA):
    case SUCCESS(ACTION_TYPES.UPDATE_PAUTA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_PAUTA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PAUTA):
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

const apiUrl = 'api/pautas';

// Actions

export const getEntities: ICrudGetAllAction<IPauta> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PAUTA_LIST,
  payload: axios.get<IPauta>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IPauta> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PAUTA,
    payload: axios.get<IPauta>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPauta> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PAUTA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPauta> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PAUTA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IPauta> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_PAUTA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPauta> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PAUTA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
