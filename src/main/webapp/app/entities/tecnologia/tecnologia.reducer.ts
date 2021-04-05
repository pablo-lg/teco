import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITecnologia, defaultValue } from 'app/shared/model/tecnologia.model';

export const ACTION_TYPES = {
  FETCH_TECNOLOGIA_LIST: 'tecnologia/FETCH_TECNOLOGIA_LIST',
  FETCH_TECNOLOGIA: 'tecnologia/FETCH_TECNOLOGIA',
  CREATE_TECNOLOGIA: 'tecnologia/CREATE_TECNOLOGIA',
  UPDATE_TECNOLOGIA: 'tecnologia/UPDATE_TECNOLOGIA',
  PARTIAL_UPDATE_TECNOLOGIA: 'tecnologia/PARTIAL_UPDATE_TECNOLOGIA',
  DELETE_TECNOLOGIA: 'tecnologia/DELETE_TECNOLOGIA',
  RESET: 'tecnologia/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITecnologia>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type TecnologiaState = Readonly<typeof initialState>;

// Reducer

export default (state: TecnologiaState = initialState, action): TecnologiaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TECNOLOGIA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TECNOLOGIA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TECNOLOGIA):
    case REQUEST(ACTION_TYPES.UPDATE_TECNOLOGIA):
    case REQUEST(ACTION_TYPES.DELETE_TECNOLOGIA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_TECNOLOGIA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TECNOLOGIA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TECNOLOGIA):
    case FAILURE(ACTION_TYPES.CREATE_TECNOLOGIA):
    case FAILURE(ACTION_TYPES.UPDATE_TECNOLOGIA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_TECNOLOGIA):
    case FAILURE(ACTION_TYPES.DELETE_TECNOLOGIA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TECNOLOGIA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TECNOLOGIA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TECNOLOGIA):
    case SUCCESS(ACTION_TYPES.UPDATE_TECNOLOGIA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_TECNOLOGIA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TECNOLOGIA):
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

const apiUrl = 'api/tecnologias';

// Actions

export const getEntities: ICrudGetAllAction<ITecnologia> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TECNOLOGIA_LIST,
  payload: axios.get<ITecnologia>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ITecnologia> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TECNOLOGIA,
    payload: axios.get<ITecnologia>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITecnologia> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TECNOLOGIA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITecnologia> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TECNOLOGIA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ITecnologia> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_TECNOLOGIA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITecnologia> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TECNOLOGIA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
