import Mock from 'mockjs';
import loginAPI from './login';

// 登录相关
Mock.mock(/\/api\/user\/login/, 'post', loginAPI.loginByPhoneOrSn);
Mock.mock(/\/api\/user\/getByToken/, 'post', loginAPI.getInfo)
Mock.mock(/\/api\/user\/logout/, 'post', loginAPI.logout);

export default Mock;
