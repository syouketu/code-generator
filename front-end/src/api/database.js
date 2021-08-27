import fetch from 'utils/fetch';

export function fetchDatabasePage(query) {
  return fetch({
    url: '/api/project/database/listPage',
    method: 'post',
    data: query
  });
}

export function saveDatabase(form) {
  return fetch({
    url: '/api/project/database/save',
    method: 'post',
    data: form
  });
}

export function deleteDatabase(id) {
  return fetch({
    url: '/api/project/database/delete',
    method: 'post',
    data: {
      'id': id
    }
  });
}

export function fetchTableList(query) {
  return fetch({
    url: '/api/project/database/tableList',
    method: 'post',
    data: query
  });
}

export function fetchColumnList(query) {
  return fetch({
    url: '/api/project/database/columnList',
    method: 'post',
    data: query
  });
}

export function saveTable(tableInfo) {
  return fetch({
    url: '/api/project/database/saveTable',
    method: 'post',
    data: tableInfo
  });
}
