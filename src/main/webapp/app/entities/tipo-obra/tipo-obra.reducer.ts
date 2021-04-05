import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITipoObra, defaultValue } from 'app/shared/model/tipo-obra.model';

export const ACTION_TYPES = {
  FETCH_TIPOOBRA_LIST: 'tipoObra/FETCH_TIPOOBRA_LIST',
  FETCH_TIPOOBRA: 'tipoObra/FETCH_TIPOOBRA',
  CREATE_TIPOOBRA: 'tipoObra/CREATE_TIPOOBRA',
  UPDATE_TIPOOBRA: 'tipoObra/UPDATE_TIPOOBRA',
  PARTIAL_UPDATE_TIPOOBRA: 'tipoObra/PARTIAL_UPDATE_TIPOOBRA',
  DELETE_TIPOOBRA: 'tipoObra/DELETE_TIPOOBRA',
  RESET: 'tipoObra/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITipoObra>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type TipoObraState = Readonly<typeof initialState>;

// Reducer

export default (state: TipoObraState = initialState, action): TipoObraState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TIPOOBRA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TIPOOBRA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TIPOOBRA):
    case REQUEST(ACTION_TYPES.UPDATE_TIPOOBRA):
    case REQUEST(ACTION_TYPES.DELETE_TIPOOBRA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_TIPOOBRA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TIPOOBRA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TIPOOBRA):
    case FAILURE(ACTION_TYPES.CREATE_TIPOOBRA):
    case FAILURE(ACTION_TYPES.UPDATE_TIPOOBRA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_TIPOOBRA):
    case FAILURE(ACTION_TYPES.DELETE_TIPOOBRA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TIPOOBRA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TIPOOBRA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TIPOOBRA):
    case SUCCESS(ACTION_TYPES.UPDATE_TIPOOBRA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_TIPOOBRA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TIPOOBRA):
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

const apiUrl = 'api/tipo-obras';

// Actions

export const getEntities: ICrudGetAllAction<ITipoObra> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TIPOOBRA_LIST,
  payload: axios.get<ITipoObra>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ITipoObra> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TIPOOBRA,
    payload: axios.get<ITipoObra>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITipoObra> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TIPOOBRA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITipoObra> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TIPOOBRA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ITipoObra> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_TIPOOBRA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITipoObra> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TIPOOBRA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
