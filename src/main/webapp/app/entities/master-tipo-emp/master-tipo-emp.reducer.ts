import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMasterTipoEmp, defaultValue } from 'app/shared/model/master-tipo-emp.model';

export const ACTION_TYPES = {
  FETCH_MASTERTIPOEMP_LIST: 'masterTipoEmp/FETCH_MASTERTIPOEMP_LIST',
  FETCH_MASTERTIPOEMP: 'masterTipoEmp/FETCH_MASTERTIPOEMP',
  CREATE_MASTERTIPOEMP: 'masterTipoEmp/CREATE_MASTERTIPOEMP',
  UPDATE_MASTERTIPOEMP: 'masterTipoEmp/UPDATE_MASTERTIPOEMP',
  PARTIAL_UPDATE_MASTERTIPOEMP: 'masterTipoEmp/PARTIAL_UPDATE_MASTERTIPOEMP',
  DELETE_MASTERTIPOEMP: 'masterTipoEmp/DELETE_MASTERTIPOEMP',
  RESET: 'masterTipoEmp/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMasterTipoEmp>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type MasterTipoEmpState = Readonly<typeof initialState>;

// Reducer

export default (state: MasterTipoEmpState = initialState, action): MasterTipoEmpState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MASTERTIPOEMP_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MASTERTIPOEMP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MASTERTIPOEMP):
    case REQUEST(ACTION_TYPES.UPDATE_MASTERTIPOEMP):
    case REQUEST(ACTION_TYPES.DELETE_MASTERTIPOEMP):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_MASTERTIPOEMP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_MASTERTIPOEMP_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MASTERTIPOEMP):
    case FAILURE(ACTION_TYPES.CREATE_MASTERTIPOEMP):
    case FAILURE(ACTION_TYPES.UPDATE_MASTERTIPOEMP):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_MASTERTIPOEMP):
    case FAILURE(ACTION_TYPES.DELETE_MASTERTIPOEMP):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MASTERTIPOEMP_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MASTERTIPOEMP):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MASTERTIPOEMP):
    case SUCCESS(ACTION_TYPES.UPDATE_MASTERTIPOEMP):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_MASTERTIPOEMP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MASTERTIPOEMP):
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

const apiUrl = 'api/master-tipo-emps';

// Actions

export const getEntities: ICrudGetAllAction<IMasterTipoEmp> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MASTERTIPOEMP_LIST,
  payload: axios.get<IMasterTipoEmp>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IMasterTipoEmp> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MASTERTIPOEMP,
    payload: axios.get<IMasterTipoEmp>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMasterTipoEmp> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MASTERTIPOEMP,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMasterTipoEmp> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MASTERTIPOEMP,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IMasterTipoEmp> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_MASTERTIPOEMP,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMasterTipoEmp> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MASTERTIPOEMP,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
