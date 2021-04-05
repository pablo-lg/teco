import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDespliegue, defaultValue } from 'app/shared/model/despliegue.model';

export const ACTION_TYPES = {
  FETCH_DESPLIEGUE_LIST: 'despliegue/FETCH_DESPLIEGUE_LIST',
  FETCH_DESPLIEGUE: 'despliegue/FETCH_DESPLIEGUE',
  CREATE_DESPLIEGUE: 'despliegue/CREATE_DESPLIEGUE',
  UPDATE_DESPLIEGUE: 'despliegue/UPDATE_DESPLIEGUE',
  PARTIAL_UPDATE_DESPLIEGUE: 'despliegue/PARTIAL_UPDATE_DESPLIEGUE',
  DELETE_DESPLIEGUE: 'despliegue/DELETE_DESPLIEGUE',
  RESET: 'despliegue/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDespliegue>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type DespliegueState = Readonly<typeof initialState>;

// Reducer

export default (state: DespliegueState = initialState, action): DespliegueState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DESPLIEGUE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DESPLIEGUE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_DESPLIEGUE):
    case REQUEST(ACTION_TYPES.UPDATE_DESPLIEGUE):
    case REQUEST(ACTION_TYPES.DELETE_DESPLIEGUE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_DESPLIEGUE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_DESPLIEGUE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DESPLIEGUE):
    case FAILURE(ACTION_TYPES.CREATE_DESPLIEGUE):
    case FAILURE(ACTION_TYPES.UPDATE_DESPLIEGUE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_DESPLIEGUE):
    case FAILURE(ACTION_TYPES.DELETE_DESPLIEGUE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DESPLIEGUE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DESPLIEGUE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_DESPLIEGUE):
    case SUCCESS(ACTION_TYPES.UPDATE_DESPLIEGUE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_DESPLIEGUE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_DESPLIEGUE):
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

const apiUrl = 'api/despliegues';

// Actions

export const getEntities: ICrudGetAllAction<IDespliegue> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DESPLIEGUE_LIST,
  payload: axios.get<IDespliegue>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IDespliegue> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DESPLIEGUE,
    payload: axios.get<IDespliegue>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IDespliegue> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DESPLIEGUE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDespliegue> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DESPLIEGUE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IDespliegue> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_DESPLIEGUE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDespliegue> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DESPLIEGUE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
