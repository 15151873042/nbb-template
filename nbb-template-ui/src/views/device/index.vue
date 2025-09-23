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

      <el-table v-loading="loading" :data="deviceList" @selection-change="handleSelectionChange">
         <el-table-column type="selection" width="55" align="center" />
         <el-table-column label="设备ID" align="center" prop="id" />
         <el-table-column label="设备名称" align="center" prop="deviceName"/>
         <el-table-column label="所属产品" align="center" prop="productName"/>
         <el-table-column label="所属串口" align="center" prop="serialName"/>
         <el-table-column label="地址码" align="center" prop="serialAddressCode"/>
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
      <el-dialog :title="title" v-model="open" width="500px" append-to-body>
         <el-form ref="saveRef" :model="form" :rules="rules" label-width="100px">
            <el-form-item label="设备名称" prop="deviceName">
               <el-input v-model="form.deviceName" placeholder="请输入设备名称" />
            </el-form-item>
            <el-form-item label="产品类型" prop="productId">
              <el-select v-model="form.productId" placeholder="产品类型" clearable>
                <el-option
                    v-for="item in productOptions"
                    :key="item.id"
                    :label="item.productName"
                    :value="item.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="挂载串口" prop="serialId">
              <el-select v-model="form.serialId" placeholder="串口服务器" clearable>
                <el-option
                    v-for="item in serialOptions"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                />
              </el-select>
            </el-form-item>
           <el-form-item label="串口地址码" prop="serialAddressCode">
             <el-input v-model="form.serialAddressCode" placeholder="请输入串口地址码" />
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
  listProductName,
  updateProduct
} from "@/api/iot/product.js";
import {listSerialName} from "@/api/iot/serial.js";
import {addDevice, delDevice, getDevice, listPageDevice, updateDevice} from "@/api/iot/device.js";

const { proxy } = getCurrentInstance()

const deviceList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const serialOptions = ref([])
const productOptions = ref([])

const data = reactive({
  form: {},
  queryParams: {
    pageNo: 1,
    pageSize: 10,
    productName: undefined,
  },
  rules: {
    deviceName: [{ required: true, message: "设备名称不能为空", trigger: "blur" }],
    productId: [{ required: true, message: "产品类型不能为空", trigger: "change" }],
    serialId: [{ required: true, message: "串口服务器不能为空", trigger: "change" }],
    serialAddressCode: [{ required: true, message: "串口地址码不能为空", trigger: "blur" }],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询岗位列表 */
function getList() {
  loading.value = true
  listPageDevice(queryParams.value).then(response => {
    const {data}  = response
    deviceList.value = data.list
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
    deviceName: undefined,
    productId: undefined,
    serialId: undefined,
    serialAddressCode: undefined,
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
  getSerialSelect()
  getProductSelect()
  open.value = true
  title.value = "添加设备"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  getSerialSelect()
  getProductSelect()
  const id = row.id || ids.value
  getDevice(id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改设备"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["saveRef"].validate(valid => {
    if (valid) {
      if (form.value.id != undefined) {
        updateDevice(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addDevice(form.value).then(response => {
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
    return delDevice(idList)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download("system/post/export", {
    ...queryParams.value
  }, `post_${new Date().getTime()}.xlsx`)
}

/** 获取串口下拉框 */
function getSerialSelect() {
  listSerialName().then(response => {
    serialOptions.value = response.data
  })
}

/** 获取产品名称下拉框 */
function getProductSelect() {
  listProductName().then(response => {
    productOptions.value = response.data
  })
}

getList()
</script>
