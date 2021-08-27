import Mock from 'mockjs';
import { param2Obj } from 'utils';

const List = [];
const count = 100;

for (let i = 0; i < count; i++) {
  List.push(Mock.mock({
    id: '@increment',
    day_attend: '@datetime("yyyy-MM-dd")',
    company: '@ctitle',
    department: '@ctitle',
    user: '@cname',
    "user_code|1-100": 100,
    date_on_duty: '@datetime("HH:mm")',
    date_off_duty: '@datetime("HH:mm")',
    "is_out|1": ['有', '无'],
    attend_address: '@county(true)',
    out_address: '@county(true)'
  }));
}

export default {
  getList: config => {
    const { importance, type, title, page, limit, sort } = param2Obj(config.url);
    let mockList = List.filter(item => {
      if (importance && item.importance !== +importance) return false;
      if (type && item.type !== type) return false;
      if (title && item.title.indexOf(title) < 0) return false;
      return true;
    });
    if (sort === '-id') {
      mockList = mockList.reverse()
    }

    const pageList = mockList.filter((item, index) => index < limit * page && index >= limit * (page - 1));
    return {
      total: mockList.length,
      items: pageList
    }
  }
};
