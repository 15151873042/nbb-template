import request from '@/utils/request'

// 查询岗位列表
export function listPageDevice(query) {
  return request({
    url: '/iot/device/page',
    method: 'get',
    params: query
  })
}

// 查询岗位详细
export function getDevice(id) {
  return request({
    url: '/iot/device/get/' + id,
    method: 'get'
  })
}

// 新增岗位
export function addDevice(data) {
  return request({
    url: '/iot/device/add/save',
    method: 'post',
    data: data
  })
}

// 修改岗位
export function updateDevice(data) {
  return request({
    url: '/iot/device/edit/save',
    method: 'put',
    data: data
  })
}

// 删除岗位
export function delDevice(id) {
  return request({
    url: '/iot/device/delete/' + id,
    method: 'delete'
  })
}
