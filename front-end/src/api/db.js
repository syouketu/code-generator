import fetch from 'utils/fetch';

export function fetchDbList() {
  return fetch({
    url: '/api/db/list',
    method: 'post',
    data: {}
  });
}

export function saveDb(form) {
  return fetch({
    url: '/api/db/save',
    method: 'post',
    data: form
  });
}

export function deleteDb(dbName) {
  return fetch({
    url: '/api/db/delete',
    method: 'post',
    data: {
      'db_name': dbName
    }
  });
}

export function fetchTableList(query) {
  return fetch({
    url: '/api/db/tableList',
    method: 'post',
    data: query
  });
}

export function fetchColumnList(query) {
  return fetch({
    url: '/api/db/columnList',
    method: 'post',
    data: query
  });
}

export function saveTable(tableInfo) {
  return fetch({
    url: '/api/db/saveTable',
    method: 'post',
    data: tableInfo
  });
}
