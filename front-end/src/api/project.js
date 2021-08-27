import fetch from 'utils/fetch';

export function fetchProjectPage(query) {
  return fetch({
    url: '/api/project/project/listPage',
    method: 'post',
    data: query
  });
}

export function fetchProjectList(query) {
  return fetch({
    url: '/api/project/project/list',
    method: 'post',
    data: query
  });
}

export function saveProject(form) {
  return fetch({
    url: '/api/project/project/save',
    method: 'post',
    data: form
  });
}

export function deleteProject(id) {
  return fetch({
    url: '/api/project/project/delete',
    method: 'post',
    data: {
      'id': id
    }
  });
}

export function generateCode(tableInfo, packageConfig) {
  return fetch({
    url: '/api/project/project/generate',
    method: 'post',
    data: {
      'tableInfo': tableInfo,
      'packageConfig': packageConfig
    }
  });
}

