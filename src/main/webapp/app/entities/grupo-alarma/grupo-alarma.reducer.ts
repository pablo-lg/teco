import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGrupoAlarma, defaultValue } from 'app/shared/model/grupo-alarma.model';

export const ACTION_TYPES = {
  FETCH_GRUPOALARMA_LIST: 'grupoAlarma/FETCH_GRUPOALARMA_LIST',
  FETCH_GRUPOALARMA: 'grupoAlarma/FETCH_GRUPOALARMA',
  CREATE_GRUPOALARMA: 'grupoAlarma/CREATE_GRUPOALARMA',
  UPDATE_GRUPOALARMA: 'grupoAlarma/UPDATE_GRUPOALARMA',
  PARTIAL_UPDATE_GRUPOALARMA: 'grupoAlarma/PARTIAL_UPDATE_GRUPOALARMA',
  DELETE_GRUPOALARMA: 'grupoAlarma/DELETE_GRUPOALARMA',
  RESET: 'grupoAlarma/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGrupoAlarma>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type GrupoAlarmaState = Readonly<typeof initialState>;

// Reducer

export default (state: GrupoAlarmaState = initialState, action): GrupoAlarmaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GRUPOALARMA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GRUPOALARMA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GRUPOALARMA):
    case REQUEST(ACTION_TYPES.UPDATE_GRUPOALARMA):
    case REQUEST(ACTION_TYPES.DELETE_GRUPOALARMA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_GRUPOALARMA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GRUPOALARMA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GRUPOALARMA):
    case FAILURE(ACTION_TYPES.CREATE_GRUPOALARMA):
    case FAILURE(ACTION_TYPES.UPDATE_GRUPOALARMA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_GRUPOALARMA):
    case FAILURE(ACTION_TYPES.DELETE_GRUPOALARMA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GRUPOALARMA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GRUPOALARMA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GRUPOALARMA):
    case SUCCESS(ACTION_TYPES.UPDATE_GRUPOALARMA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_GRUPOALARMA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GRUPOALARMA):
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

const apiUrl = 'api/grupo-alarmas';

// Actions

export const getEntities: ICrudGetAllAction<IGrupoAlarma> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_GRUPOALARMA_LIST,
  payload: axios.get<IGrupoAlarma>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IGrupoAlarma> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GRUPOALARMA,
    payload: axios.get<IGrupoAlarma>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGrupoAlarma> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GRUPOALARMA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGrupoAlarma> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GRUPOALARMA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IGrupoAlarma> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_GRUPOALARMA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGrupoAlarma> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GRUPOALARMA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
