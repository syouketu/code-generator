import Mock from 'mockjs';
import { param2Obj } from 'utils';

const List = [];
const count = 100;

for (let i = 0; i < count; i++) {
  List.push(Mock.mock({
    id: '@increment',
    day_out: '@datetime("yyyy-MM-dd")',
    user: '@cname',
    "user_code|1-100": 100,
    company: '@ctitle',
    department: '@ctitle',
    date_out: '@datetime("HH:mm")',
    date_back: '@datetime("HH:mm")',
    address: '@county(true)',
    customer: '@ctitle',
    customer_contacts: '@cname',
    out_reason: '@ctitle',
    date_add:'@datetime("yyyy-MM-dd HH:mm:ss")'
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
