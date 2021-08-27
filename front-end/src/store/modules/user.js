import {
  loginByPhoneOrSn,
  logout,
  getInfo
} from 'api/login';
import {
  getToken,
  setToken,
  removeToken
} from 'utils/auth';
import defaultAvatar from '@/assets/images/default-avatar.png'
import { Message } from 'element-ui';

const user = {
  state: {
    user: '',
    userId: '',
    status: '',
    code: '',
    token: getToken(),
    name: '',
    avatar: '',
    introduction: '',
    // roles: [],
    roles: '',
    roleName: '',
    roleMenus: [],
    companyId: '',
    departmentId:'',
    setting: {
      articlePlatform: []
    }
  },

  mutations: {
    SET_CODE: (state, code) => {
      state.code = code;
    },
    SET_USER_ID: (state, userId) => {
      state.userId = userId;
    },
    SET_TOKEN: (state, token) => {
      state.token = token;
    },
    SET_INTRODUCTION: (state, introduction) => {
      state.introduction = introduction;
    },
    SET_SETTING: (state, setting) => {
      state.setting = setting;
    },
    SET_STATUS: (state, status) => {
      state.status = status;
    },
    SET_NAME: (state, name) => {
      state.name = name;
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar;
    },
    SET_COMPANY_ID: (state, companyId) => {
      state.companyId = companyId;
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles;
    },
    SET_ROLE_NAME: (state, roleName) => {
      state.roleName = roleName;
    },
    SET_ROLE_MENUS: (state, menus) => {
      state.roleMenus = menus;
    },
    LOGIN_SUCCESS: () => {
      //console.log('login success')
    },
    LOGOUT_USER: state => {
      state.user = '';
    },
    SET_DEPART_ID: (state, departmentId) => {
      state.departmentId = departmentId;
    }
  },

  actions: {
    // 邮箱登录
    LoginByPhoneOrSn({
      commit
    }, userInfo) {
      const user = userInfo.user.trim();
      return new Promise((resolve, reject) => {
        loginByPhoneOrSn(user, userInfo.password).then(response => {
          // alert(response.data.status);
          if (response.data.status == 1) {
            const data = response.data.data;
            setToken(data.token)
            commit('SET_TOKEN', data.token);
          } else {
            Message({
              message: response.data.message,
              type: 'error',
              duration: 3 * 1000
            });
            removeToken();
          }
          resolve(response);
        }).catch(error => {
          reject(error);
        });
      });
    },

    // 获取用户信息
    GetInfo({
      commit,
      state
    }) {
      return new Promise((resolve, reject) => {
        getInfo(getToken()).then(response => {
          if (response.data.status == 1) {
            const data = response.data.data;
            commit('SET_ROLES', data.role);
            commit('SET_ROLE_NAME', data.roleName);
            commit('SET_NAME', data.name);
            commit('SET_USER_ID', data.userId);
            // commit('SET_COMPANY_ID', data.companyId);
            commit('SET_AVATAR', data.avatar);
            commit('SET_ROLE_MENUS', data.roleMenus);
            commit('SET_DEPART_ID', data.departmentId);
            commit('SET_CODE', data.sn);
          } else {
            // console.log('xxxxxx');
            removeToken();
            commit('SET_TOKEN', '');
            commit('SET_ROLES', '');
            Message({
              message: response.data.message,
              type: 'error',
              duration: 3 * 1000
            });
            // console.log(response.data.message);
          }
          resolve(response);
        }).catch(error => {
          reject(error);
        });
      });
    },

    // 登出
    LogOut({
      commit,
      state
    }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '');
          commit('SET_ROLES', '');
          removeToken()
          resolve();
        }).catch(error => {
          reject(error);
        });
      });
    },

    // 前端 登出
    FedLogOut({
      commit
    }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '');
        resolve();
      });
    },

    // 动态修改权限
    ChangeRole({
      commit
    }, role) {
      return new Promise(resolve => {
        commit('SET_ROLES', [role]);
        commit('SET_TOKEN', role);
        setToken(role)
        resolve();
      })
    }
  }
};

export default user;