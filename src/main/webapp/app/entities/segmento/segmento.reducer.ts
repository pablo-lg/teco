import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISegmento, defaultValue } from 'app/shared/model/segmento.model';

export const ACTION_TYPES = {
  FETCH_SEGMENTO_LIST: 'segmento/FETCH_SEGMENTO_LIST',
  FETCH_SEGMENTO: 'segmento/FETCH_SEGMENTO',
  CREATE_SEGMENTO: 'segmento/CREATE_SEGMENTO',
  UPDATE_SEGMENTO: 'segmento/UPDATE_SEGMENTO',
  PARTIAL_UPDATE_SEGMENTO: 'segmento/PARTIAL_UPDATE_SEGMENTO',
  DELETE_SEGMENTO: 'segmento/DELETE_SEGMENTO',
  RESET: 'segmento/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISegmento>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type SegmentoState = Readonly<typeof initialState>;

// Reducer

export default (state: SegmentoState = initialState, action): SegmentoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SEGMENTO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SEGMENTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SEGMENTO):
    case REQUEST(ACTION_TYPES.UPDATE_SEGMENTO):
    case REQUEST(ACTION_TYPES.DELETE_SEGMENTO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_SEGMENTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SEGMENTO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SEGMENTO):
    case FAILURE(ACTION_TYPES.CREATE_SEGMENTO):
    case FAILURE(ACTION_TYPES.UPDATE_SEGMENTO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_SEGMENTO):
    case FAILURE(ACTION_TYPES.DELETE_SEGMENTO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SEGMENTO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SEGMENTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SEGMENTO):
    case SUCCESS(ACTION_TYPES.UPDATE_SEGMENTO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_SEGMENTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SEGMENTO):
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

const apiUrl = 'api/segmentos';

// Actions

export const getEntities: ICrudGetAllAction<ISegmento> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SEGMENTO_LIST,
  payload: axios.get<ISegmento>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ISegmento> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SEGMENTO,
    payload: axios.get<ISegmento>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISegmento> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SEGMENTO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISegmento> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SEGMENTO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ISegmento> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_SEGMENTO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISegmento> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SEGMENTO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
