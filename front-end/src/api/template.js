import fetch from 'utils/fetch';

export function fetchTemplatePage(query) {
  return fetch({
    url: '/api/project/template/listPage',
    method: 'post',
    data: query
  });
}

export function saveTemplate(form) {
  return fetch({
    url: '/api/project/template/save',
    method: 'post',
    data: form
  });
}

export function deleteTemplate(id) {
  return fetch({
    url: '/api/project/template/delete',
    method: 'post',
    data: {
      'id': id
    }
  });
}

export function generateTemplate(projectId) {
  return fetch({
    url: '/api/project/template/generateTemplate',
    method: 'post',
    data: {
      'projectId': projectId
    }
  });
}
