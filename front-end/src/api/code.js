import fetch from 'utils/fetch';

export function generateCode(tableInfo, packageConfig) {
  return fetch({
    url: '/api/code/generate',
    method: 'post',
    data: {
      'table_info': tableInfo,
      'package_config': packageConfig
    }
  });
}
