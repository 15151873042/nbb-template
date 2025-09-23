import request from '@/utils/request'

export function listPageProduct(query) {
  return request({
    url: '/iot/product/page',
    method: 'get',
    params: query
  })
}

export function listProductName() {
  return request({
    url: '/iot/product/list-all-name',
    method: 'get'
  })
}

export function getProduct(id) {
  return request({
    url: '/iot/product/get/' + id,
    method: 'get'
  })
}

export function addProduct(data) {
  return request({
    url: '/iot/product/add/save',
    method: 'post',
    data: data
  })
}

export function updateProduct(data) {
  return request({
    url: '/iot/product/edit/save',
    method: 'put',
    data: data
  })
}

export function delProduct(id) {
  return request({
    url: '/iot/product/delete/' + id,
    method: 'delete'
  })
}

// 用户状态修改
export function updateProductStatus(id, status) {
  const data = { id, status }
  return request({
    url: '/iot/product/update-status',
    method: 'put',
    data: data
  })
}
