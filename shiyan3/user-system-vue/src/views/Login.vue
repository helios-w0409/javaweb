<template>
  <div class="page-container">
    <!-- 背景大字，放最外层 -->
    <div class="background-text">欢迎来到图书馆</div>

    <div class="login-wrapper">
      <div class="login-box">
        <h1 class="login-title">欢迎登录</h1>
        <el-form :model="form" label-width="90px" class="login-form">
          <el-form-item label="用户名" class="custom-form-item">
            <el-input
                v-model="form.username"
                placeholder="请输入用户名"
                clearable
                autocomplete="off"
                class="input-box"
            />
          </el-form-item>

          <el-form-item label="密码" class="custom-form-item">
            <el-input
                v-model="form.password"
                type="password"
                placeholder="请输入密码"
                clearable
                autocomplete="off"
                class="input-box"
            />
          </el-form-item>

          <el-form-item class="btn-group">
            <el-button type="primary" class="btn-login" @click="login">
              登录
            </el-button>
            <el-button
                plain
                type="primary"
                class="btn-register"
                @click="$router.push('/register')"
            >
              注册
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      form: {
        username: "",
        password: "",
      },
    };
  },
  methods: {
    async login() {
      try {
        const res = await axios.post(
            "http://localhost:8080/user_system_server_war_exploded/api/login",
            this.form
        );
        if (res.data.success) {
          if (res.data.role === "admin") {
            this.$router.push("/admin/users");
          } else {
            this.$router.push("/home");
          }
        } else {
          this.$message.error(res.data.message || "登录失败");
        }
      } catch (error) {
        this.$message.error("网络错误，登录失败");
      }
    },
  },
};
</script>

<style scoped>
/* 整个页面容器撑满全屏 */
.page-container {
  position: relative;
  height: 100vh;
  width: 100vw;
  background-color: #f2f6fc;
  overflow: hidden;
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
}

/* 背景大字，全屏铺满，z-index最低 */
.background-text {
  position: fixed; /* 用fixed，跟视口绑定 */
  top: 50%;
  left: 25%;
  transform: translate(-50%, -50%);
  font-size: 96px;
  font-weight: 900;
  white-space: nowrap;
  user-select: none;
  pointer-events: none;

  width: auto;
  max-width: 90vw; /* 最大宽度限制 */
  background: linear-gradient(
      90deg,
      #409eff,
      #67c23a,
      #e6a23c,
      #f56c6c,
      #409eff
  );
  background-size: 400% 100%;
  background-clip: text;
  -webkit-background-clip: text;
  color: transparent;

  animation: gradient-move 8s linear infinite;
  border-right: 4px solid #409eff;

  /* 打字机动画 - 改成用 clip-path 裁剪效果 */
  overflow: hidden;
  animation:
      typing 4s steps(8) forwards,
      blink-caret 0.75s step-end infinite 4s;
  z-index: 1;
}

@keyframes gradient-move {
  0% {
    background-position: 0% 0%;
  }
  100% {
    background-position: 400% 0%;
  }
}

@keyframes typing {
  from {
    clip-path: inset(0 100% 0 0);
  }
  to {
    clip-path: inset(0 0 0 0);
  }
}

@keyframes blink-caret {
  0%,
  100% {
    border-color: transparent;
  }
  50% {
    border-color: #409eff;
  }
}

/* 登录框，居中显示，层级高 */
.login-wrapper {
  position: relative;
  z-index: 10;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  box-sizing: border-box;
}

.login-box {
  max-width: 360px;
  width: 100%;
  padding: 40px 30px;
  background: rgba(255 255 255 / 0.95);
  border-radius: 16px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.login-title {
  margin-bottom: 36px;
  font-weight: 700;
  font-size: 28px;
  color: #333333;
}

.login-form {
  width: 100%;
}

.custom-form-item {
  margin-bottom: 28px;
  width: 100%;
}

.custom-form-item:last-child {
  margin-bottom: 36px;
}

.input-box {
  width: 100%;
  border-radius: 8px;
}

.btn-group {
  text-align: center;
}

.btn-login,
.btn-register {
  width: 100%;
  padding: 12px 0;
  font-size: 16px;
  font-weight: 600;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
  margin-top: 0;
  transition: background-color 0.3s ease;
}

.btn-login {
  margin-bottom: 14px;
}

.btn-register {
  box-shadow: none;
}

.btn-register:hover {
  background-color: #f0f9ff;
}
</style>
