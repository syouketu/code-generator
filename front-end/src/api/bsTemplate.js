import fetch from 'utils/fetch';

export function fetchTemplatePage(query) {
  return fetch({
    url: '/api/system/bsTemplate/listPage',
    method: 'post',
    data: query
  });
}

export function saveTemplate(form) {
  return fetch({
    url: '/api/system/bsTemplate/save',
    method: 'post',
    data: form
  });
}

export function deleteTemplate(id) {
  return fetch({
    url: '/api/system/bsTemplate/delete',
    method: 'post',
    data: {
      'id': id
    }
  });
}
