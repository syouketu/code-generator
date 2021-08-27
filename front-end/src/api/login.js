import fetch from 'utils/fetch';

export function loginByPhoneOrSn(user, password) {
  const data = {
    user: user,
    password: password,
    source : 'admin'
  };
  return fetch({
    url: '/api/user/login',
    method: 'post',
    data
  });
}

export function logout(token) {
  return fetch({
    url: '/api/user/logout',
    method: 'post',
    data: {'token': token }
  });
}

export function getInfo(token) {
  return fetch({
    url: '/api/user/getByToken',
    method: 'post',
    data: {'token': token }
  });
}

