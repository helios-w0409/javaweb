<template>
  <div style="max-width: 900px; margin: 50px auto; padding: 20px; background: #f9fafb; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
    <h2 style="text-align: center; margin-bottom: 20px; color: #409EFF; font-weight: 600;">用户列表</h2>
    <el-table
        :data="users"
        border
        stripe
        style="width: 100%; background: #fff; border-radius: 6px; overflow: hidden;"
        :header-cell-style="{ backgroundColor: '#e6f2ff', color: '#409EFF', fontWeight: '600' }"
    >
      <el-table-column prop="id" label="ID" width="100" align="center" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="role" label="角色" width="140" align="center" />
      <el-table-column label="删除" width="140" align="center">
        <template #default="scope">
          <el-button
              size="small"
              type="danger"
              plain
              circle
              icon="el-icon-delete"
              @click="deleteUser(scope.row)"
              :title="`删除用户 ${scope.row.username}`"
          />
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      users: []
    }
  },
  created() {
    this.fetchUsers()
  },
  methods: {
    async fetchUsers() {
      try {
        const res = await axios.get('http://localhost:8080/user_system_server_war_exploded/api/users')
        if (res.data.success) {
          this.users = res.data.users
        } else {
          this.$message.error('获取用户列表失败')
        }
      } catch (err) {
        this.$message.error('网络错误，获取用户列表失败')
      }
    },
    async deleteUser(user) {
      try {
        const res = await axios.post('http://localhost:8080/user_system_server_war_exploded/api/users/delete', { id: user.id })
        if (res.data.success) {
          this.users = this.users.filter(u => u.id !== user.id)
          this.$message.success(`已删除用户：${user.username}`)
        } else {
          this.$message.error('删除失败：' + (res.data.message || '未知错误'))
        }
      } catch (err) {
        this.$message.error('网络错误，删除失败')
      }
    }
  }
}
</script>
