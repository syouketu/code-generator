import Mock from 'mockjs';

const apiResult = {
  data: {
    token: '631b10bf8ee960a4014f91cca02ae1368452a319'
  },
  message: '登录成功',
  status: '1',
  success: true
}

const adminUser = {
  data: {
    id: 190,
    role: 'admin',
    roleName: '超级管理员',
    name: '肖杰',
    avatar: '',
    roleMenus: '1,2,3,4,5,11,13,14,21,22,23,24,31,32,41,51,52,54'
  },
  message: '根据token获取用户信息成功',
  status: '1',
  success: true
}

export default {
  loginByPhoneOrSn: form => {
    var user = JSON.parse(form.body);
    console.log(user);
    if (user.user == 'admin' && user.password == 'admin123') {
      return apiResult;
    }
    return {
      data: {},
      message: '用户或密码错误，请重新输入!',
      status: '0',
      success: true
    }

  },
  getInfo: () => adminUser,
  logout: () => 'success'
};
