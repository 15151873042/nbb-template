<template>
   <div class="app-container">
      <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
         <el-form-item label="产品名称" prop="productName">
            <el-input
               v-model="queryParams.ip"
               placeholder="请输入产品名称"
               clearable
               style="width: 200px"
               @keyup.enter="handleQuery"
            />
         </el-form-item>
         <el-form-item>
            <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            <el-button icon="Refresh" @click="resetQuery">重置</el-button>
         </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
         <el-col :span="1.5">
            <el-button
               type="primary"
               plain
               icon="Plus"
               @click="handleAdd"
               v-hasPermi="['system:post:add']"
            >新增</el-button>
         </el-col>
         <el-col :span="1.5">
            <el-button
               type="success"
               plain
               icon="Edit"
               :disabled="single"
               @click="handleUpdate"
               v-hasPermi="['system:post:edit']"
            >修改</el-button>
         </el-col>
         <el-col :span="1.5">
            <el-button
               type="danger"
               plain
               icon="Delete"
               :disabled="multiple"
               @click="handleDelete"
               v-hasPermi="['system:post:remove']"
            >删除</el-button>
         </el-col>
         <el-col :span="1.5">
            <el-button
               type="warning"
               plain
               icon="Download"
               @click="handleExport"
               v-hasPermi="['system:post:export']"
            >导出</el-button>
         </el-col>
         <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="serialList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="产品ID" align="center" prop="id" />
        <el-table-column label="产品名称" align="center" prop="productName"/>
        <el-table-column label="采集间隔" align="center" prop="collectInterval"/>
        <el-table-column label="状态" align="center" key="status">
          <template #default="scope">
            <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
            />
          </template>
        </el-table-column>
         <el-table-column label="创建时间" align="center" prop="createTime" width="180">
            <template #default="scope">
               <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
         </el-table-column>
         <el-table-column label="操作" width="180" align="center" class-name="small-padding fixed-width">
            <template #default="scope">
               <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:post:edit']">修改</el-button>
               <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:post:remove']">删除</el-button>
            </template>
         </el-table-column>
      </el-table>

      <pagination
         v-show="total > 0"
         :total="total"
         v-model:page="queryParams.pageNo"
         v-model:limit="queryParams.pageSize"
         @pagination="getList"
      />

      <!-- 添加或修改岗位对话框 -->
      <el-dialog :title="title" v-model="open" width="60%" append-to-body>
         <el-form ref="saveRef" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="产品名称" prop="productName">
               <el-input v-model="form.productName" placeholder="请输入产品名称" />
            </el-form-item>
            <el-form-item label="采集频率" prop="collectInterval">
              <el-input-number v-model="form.collectInterval" :min="5" style="width:100%">
                <template #suffix>
                  <span>秒/次</span>
                </template>
              </el-input-number>
            </el-form-item>
            <el-form-item label="采集代码" prop="collectInterval">
              <codemirror
                  v-model="form.dynamicCode"
                  placeholder="请输入采集代码。。。"
                  :style="{ height: '500px', width: '100%'}"
                  :autofocus="true"
                  :tab-size="4"
                  :extensions="extensions"
              />
            </el-form-item>
         </el-form>
         <template #footer>
            <div class="dialog-footer">
               <el-button type="primary" @click="submitForm">确 定</el-button>
               <el-button @click="cancel">取 消</el-button>
            </div>
         </template>
      </el-dialog>
   </div>
</template>

<script setup name="Post">
import {
  addProduct,
  delProduct,
  getProduct,
  listPageProduct,
  updateProduct,
  updateProductStatus
} from "@/api/iot/product.js";

import {Codemirror} from 'vue-codemirror'
import {java} from '@codemirror/lang-java'
import {oneDark} from '@codemirror/theme-one-dark'

const { proxy } = getCurrentInstance()

const serialList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const data = reactive({
  form: {},
  queryParams: {
    pageNo: 1,
    pageSize: 10,
    productName: undefined,
    collectInterval: undefined,
    dynamicCode: undefined
  },
  rules: {
    productName: [{ required: true, message: "产品名称不能为空", trigger: "blur" }],
    collectInterval: [{ required: true, message: "采集频率不能为空", trigger: "blur" }],
  }
})

const { queryParams, form, rules } = toRefs(data)

const extensions = [
  java(),
  oneDark
]

/** 查询岗位列表 */
function getList() {
  loading.value = true
  listPageProduct(queryParams.value).then(response => {
    const {data}  = response
    serialList.value = data.list
    total.value = response.total
    loading.value = false
  })
}

/** 取消按钮 */
function cancel() {
  open.value = false
  reset()
}

/** 表单重置 */
function reset() {
  form.value = {
    id: undefined,
    productName: undefined,
    remark: undefined
  }
  proxy.resetForm("saveRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNo = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加产品"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const id = row.id || ids.value
  getProduct(id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改产品"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["saveRef"].validate(valid => {
    if (valid) {
      if (form.value.id != undefined) {
        updateProduct(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addProduct(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const idList = row.id || ids.value
  proxy.$modal.confirm('是否确认删除岗位编号为"' + idList + '"的数据项？').then(function() {
    return delProduct(idList)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 用户状态修改  */
function handleStatusChange(row) {
  let text = row.status === "0" ? "启用" : "停用"
  proxy.$modal.confirm('确认要"' + text + '""' + row.productName + '"产品吗?').then(function () {
    return updateProductStatus(row.id, row.status)
  }).then(() => {
    proxy.$modal.msgSuccess(text + "成功")
  }).catch(function () {
    row.status = row.status === "0" ? "1" : "0"
  })
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download("system/post/export", {
    ...queryParams.value
  }, `post_${new Date().getTime()}.xlsx`)
}

getList()
</script>
