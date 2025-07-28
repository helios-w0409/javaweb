<template>
  <div class="welcome-wrapper">
    <!-- 这里背景颜色写在这个 div 或 body 都可以 -->
    <el-card class="welcome-card">
      <h1 class="welcome-title">🎉 欢迎您，普通用户！</h1>
      <div class="btn-group">
        <el-button type="primary" size="large" @click="goToBooks">进入图书馆</el-button>
        <el-button
            type="primary"
            size="large"
            @click="goToLogin"
            style="margin-left: 20px;"
        >
          退出登录
        </el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  mounted() {
    this.autoRedirect = setTimeout(() => {
      this.goToBooks();
    }, 5000);
  },
  beforeUnmount() {
    clearTimeout(this.autoRedirect);
  },
  methods: {
    goToBooks() {
      this.$router.push('/books');
    },
    goToLogin() {
      this.$router.push('/login');
    }
  }
};
</script>

<style>
/* 确保html和body撑满全屏 */
html, body {
  height: 100%;
  margin: 0;
  padding: 0;
  background-color: #f2f6fc; /* 统一背景色 */
}

/* 让根容器撑满屏幕 */
.welcome-wrapper {
  min-height: 100vh;
  min-height: 100dvh; /* 更准确的视口高度，尤其移动端 */
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f2f6fc; /* 这里也加一次，防止空白 */
  padding: 20px;
  box-sizing: border-box;
}

/* 其余样式保持之前 */
.welcome-card {
  width: 600px;
  padding: 50px 40px;
  border-radius: 16px;
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.15);
  text-align: center;
}

.welcome-title {
  font-size: 48px;
  font-weight: 700;
  margin-bottom: 40px;
  color: #303133;
}

.btn-group {
  display: flex;
  justify-content: center;
  gap: 30px;
}
</style>
