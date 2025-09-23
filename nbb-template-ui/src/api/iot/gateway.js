import request from '@/utils/request'

// 查询岗位列表
export function listPageGateway(query) {
  return request({
    url: '/iot/gateway/page',
    method: 'get',
    params: query
  })
}
