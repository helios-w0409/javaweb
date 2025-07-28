<template>
  <div class="register-wrapper">
    <div class="register-box">
      <h2 class="register-title">用户注册</h2>
      <el-form :model="form" label-width="80px" class="register-form">
        <el-form-item label="用户名">
          <el-input v-model="form.username" autocomplete="off" clearable />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" autocomplete="off" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="btn-register" @click="register">
            注册
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      form: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    async register() {
      try {
        const res = await axios.post('http://localhost:8080/user_system_server_war_exploded/api/register', this.form)
        alert('注册成功')
        window.location.href = '/login'
      } catch (err) {
        alert('注册失败')
      }
    }
  }
}
</script>

<style scoped>
.register-wrapper {
  min-height: 100vh;
  background-color: #f2f6fc;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  box-sizing: border-box;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.register-box {
  width: 100%;
  max-width: 400px;
  background: #fff;
  padding: 40px 30px;
  border-radius: 16px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
  box-sizing: border-box;
  text-align: center;
}

.register-title {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 36px;
  color: #333;
}

.register-form {
  width: 100%;
}

.el-form-item {
  margin-bottom: 28px;
}

.el-input {
  border-radius: 8px;
}

.btn-register {
  width: 100%;
  padding: 12px 0;
  font-size: 16px;
  font-weight: 600;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
  transition: background-color 0.3s ease;
}

.btn-register:hover {
  background-color: #3a8ee6;
}
</style>
