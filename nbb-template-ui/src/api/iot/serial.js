import request from '@/utils/request'

// 查询岗位列表
export function listPageSerial(query) {
  return request({
    url: '/iot/serial/page',
    method: 'get',
    params: query
  })
}

export function listSerialName() {
  return request({
    url: '/iot/serial/list-all-name',
    method: 'get'
  })
}

// 查询岗位详细
export function getSerial(id) {
  return request({
    url: '/iot/serial/get/' + id,
    method: 'get'
  })
}

// 新增岗位
export function addSerial(data) {
  return request({
    url: '/iot/serial/add/save',
    method: 'post',
    data: data
  })
}

// 修改岗位
export function updateSerial(data) {
  return request({
    url: '/iot/serial/edit/save',
    method: 'put',
    data: data
  })
}

// 删除岗位
export function delSerial(id) {
  return request({
    url: '/iot/serial/delete/' + id,
    method: 'delete'
  })
}
