import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGrupoUsuario, defaultValue } from 'app/shared/model/grupo-usuario.model';

export const ACTION_TYPES = {
  FETCH_GRUPOUSUARIO_LIST: 'grupoUsuario/FETCH_GRUPOUSUARIO_LIST',
  FETCH_GRUPOUSUARIO: 'grupoUsuario/FETCH_GRUPOUSUARIO',
  CREATE_GRUPOUSUARIO: 'grupoUsuario/CREATE_GRUPOUSUARIO',
  UPDATE_GRUPOUSUARIO: 'grupoUsuario/UPDATE_GRUPOUSUARIO',
  PARTIAL_UPDATE_GRUPOUSUARIO: 'grupoUsuario/PARTIAL_UPDATE_GRUPOUSUARIO',
  DELETE_GRUPOUSUARIO: 'grupoUsuario/DELETE_GRUPOUSUARIO',
  RESET: 'grupoUsuario/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGrupoUsuario>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type GrupoUsuarioState = Readonly<typeof initialState>;

// Reducer

export default (state: GrupoUsuarioState = initialState, action): GrupoUsuarioState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GRUPOUSUARIO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GRUPOUSUARIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GRUPOUSUARIO):
    case REQUEST(ACTION_TYPES.UPDATE_GRUPOUSUARIO):
    case REQUEST(ACTION_TYPES.DELETE_GRUPOUSUARIO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_GRUPOUSUARIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GRUPOUSUARIO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GRUPOUSUARIO):
    case FAILURE(ACTION_TYPES.CREATE_GRUPOUSUARIO):
    case FAILURE(ACTION_TYPES.UPDATE_GRUPOUSUARIO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_GRUPOUSUARIO):
    case FAILURE(ACTION_TYPES.DELETE_GRUPOUSUARIO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GRUPOUSUARIO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GRUPOUSUARIO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GRUPOUSUARIO):
    case SUCCESS(ACTION_TYPES.UPDATE_GRUPOUSUARIO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_GRUPOUSUARIO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GRUPOUSUARIO):
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

const apiUrl = 'api/grupo-usuarios';

// Actions

export const getEntities: ICrudGetAllAction<IGrupoUsuario> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_GRUPOUSUARIO_LIST,
  payload: axios.get<IGrupoUsuario>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IGrupoUsuario> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GRUPOUSUARIO,
    payload: axios.get<IGrupoUsuario>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGrupoUsuario> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GRUPOUSUARIO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGrupoUsuario> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GRUPOUSUARIO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IGrupoUsuario> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_GRUPOUSUARIO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGrupoUsuario> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GRUPOUSUARIO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
