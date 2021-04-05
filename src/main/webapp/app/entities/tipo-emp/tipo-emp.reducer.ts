import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITipoEmp, defaultValue } from 'app/shared/model/tipo-emp.model';

export const ACTION_TYPES = {
  FETCH_TIPOEMP_LIST: 'tipoEmp/FETCH_TIPOEMP_LIST',
  FETCH_TIPOEMP: 'tipoEmp/FETCH_TIPOEMP',
  CREATE_TIPOEMP: 'tipoEmp/CREATE_TIPOEMP',
  UPDATE_TIPOEMP: 'tipoEmp/UPDATE_TIPOEMP',
  PARTIAL_UPDATE_TIPOEMP: 'tipoEmp/PARTIAL_UPDATE_TIPOEMP',
  DELETE_TIPOEMP: 'tipoEmp/DELETE_TIPOEMP',
  RESET: 'tipoEmp/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITipoEmp>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type TipoEmpState = Readonly<typeof initialState>;

// Reducer

export default (state: TipoEmpState = initialState, action): TipoEmpState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TIPOEMP_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TIPOEMP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TIPOEMP):
    case REQUEST(ACTION_TYPES.UPDATE_TIPOEMP):
    case REQUEST(ACTION_TYPES.DELETE_TIPOEMP):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_TIPOEMP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TIPOEMP_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TIPOEMP):
    case FAILURE(ACTION_TYPES.CREATE_TIPOEMP):
    case FAILURE(ACTION_TYPES.UPDATE_TIPOEMP):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_TIPOEMP):
    case FAILURE(ACTION_TYPES.DELETE_TIPOEMP):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TIPOEMP_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TIPOEMP):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TIPOEMP):
    case SUCCESS(ACTION_TYPES.UPDATE_TIPOEMP):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_TIPOEMP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TIPOEMP):
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

const apiUrl = 'api/tipo-emps';

// Actions

export const getEntities: ICrudGetAllAction<ITipoEmp> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TIPOEMP_LIST,
  payload: axios.get<ITipoEmp>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ITipoEmp> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TIPOEMP,
    payload: axios.get<ITipoEmp>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITipoEmp> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TIPOEMP,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITipoEmp> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TIPOEMP,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ITipoEmp> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_TIPOEMP,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITipoEmp> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TIPOEMP,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
