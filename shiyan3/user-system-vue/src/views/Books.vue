<template>
  <div class="page-wrapper">
    <!-- 顶部导航栏 -->
    <nav class="top-nav">
      <div class="nav-logo">📚 在线图书馆系统</div>
      <ul class="nav-menu">
        <li><a href="#">首页</a></li>
        <li><a href="#">加入我们</a></li>
        <li><a href="#">个人主页</a></li>
      </ul>
    </nav>

    <!-- 页头 -->
    <header class="page-header">
      欢迎来到在线图书馆
    </header>

    <!-- 主内容 -->
    <main class="library-container">
      <!-- 搜索 + 分类筛选 -->
      <div class="search-bar" :class="{ loading: loading }">
        <el-input
            v-model="searchKeyword"
            placeholder="输入书名搜索"
            clearable
            style="flex: 1"
            @clear="handleSearch"
            @keyup.enter="handleSearch"
            :disabled="loading"
        />
        <el-select
            v-model="selectedCategory"
            placeholder="选择分类"
            clearable
            style="width: 160px"
            @change="handleSearch"
            :disabled="loading"
        >
          <el-option
              v-for="category in categories"
              :key="category"
              :label="category"
              :value="category"
          />
        </el-select>
        <el-button
            type="primary"
            @click="handleSearch"
            :loading="loading"
            style="min-width: 80px"
        >搜索</el-button>
      </div>

      <!-- 图书推荐（静态推荐区） -->
      <div class="recommend-section">
        <h3>🔥 推荐书籍</h3>
        <ul class="recommend-list">
          <li v-for="book in recommendedBooks" :key="book.id">
            《{{ book.title }}》 - {{ book.author }}
          </li>
        </ul>
      </div>

      <!-- 图书表格 -->
      <el-table
          :data="pagedBooks"
          border
          style="width: 100%"
          :row-class-name="tableRowClassName"
          @row-click="onRowClick"
      >
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="title" label="书名" />
        <el-table-column prop="author" label="作者" />
        <el-table-column prop="category" label="分类" width="120" />
        <el-table-column prop="status" label="状态" width="100" />
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button
                size="small"
                type="success"
                @click.stop="borrowBook(scope.row)"
                :disabled="scope.row.status !== '可借'"
                class="btn-borrow"
            >
              借书
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-wrapper">
        <el-pagination
            background
            layout="prev, pager, next"
            :page-size="pageSize"
            :total="filteredBooks.length"
            :current-page.sync="currentPage"
            @current-change="handlePageChange"
        />
      </div>
    </main>

    <!-- 页脚 -->
    <footer class="page-footer">
      © 2025 在线图书馆系统 | 技术支持:魏小焱 😎
    </footer>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchKeyword: '',
      selectedCategory: '',
      categories: ['科幻', '文学', '哲学', '历史', '小说'],
      books: [
        { id: 1, title: '三体', author: '刘慈欣', status: '可借', category: '科幻' },
        { id: 2, title: '活着', author: '余华', status: '已借出', category: '小说' },
        { id: 3, title: '围城', author: '钱钟书', status: '可借', category: '小说' },
        { id: 4, title: '解忧杂货店', author: '东野圭吾', status: '可借', category: '小说' },
        { id: 5, title: '时间简史', author: '斯蒂芬·霍金', status: '可借', category: '哲学' },
        { id: 6, title: '史记', author: '司马迁', status: '已借出', category: '历史' },
        { id: 7, title: '银河帝国', author: '艾萨克·阿西莫夫', status: '可借', category: '科幻' },
        { id: 8, title: '苏菲的世界', author: '乔斯坦·贾德', status: '可借', category: '哲学' }
      ],
      recommendedBooks: [
        { id: 101, title: '百年孤独', author: '加西亚·马尔克斯' },
        { id: 102, title: '人类简史', author: '尤瓦尔·赫拉利' },
        { id: 103, title: '小王子', author: '圣埃克苏佩里' }
      ],
      currentPage: 1,
      pageSize: 5,
      loading: false
    };
  },
  computed: {
    filteredBooks() {
      return this.books.filter(book => {
        const matchTitle = !this.searchKeyword || book.title.includes(this.searchKeyword);
        const matchCategory = !this.selectedCategory || book.category === this.selectedCategory;
        return matchTitle && matchCategory;
      });
    },
    pagedBooks() {
      const start = (this.currentPage - 1) * this.pageSize;
      return this.filteredBooks.slice(start, start + this.pageSize);
    }
  },
  methods: {
    handleSearch() {
      this.loading = true;
      setTimeout(() => {
        this.currentPage = 1; // 搜索后回到第一页
        this.loading = false;
        this.$message.success('搜索成功');
      }, 600); // 模拟网络请求动画
    },
    borrowBook(book) {
      if (book.status === '可借') {
        book.status = '已借出';
        this.$message.success(`你已借阅《${book.title}》`);
      }
    },
    handlePageChange(page) {
      this.currentPage = page;
      window.scrollTo({ top: 0, behavior: 'smooth' });
    },
    tableRowClassName({ row }) {
      return row.status === '可借' ? 'row-available' : 'row-borrowed';
    },
    onRowClick(row) {
      this.$message.info(`你点击了《${row.title}》`);
    }
  }
};
</script>

<style>
/* 全局样式必须写在非 scoped 里，确保 html 和 body 全屏撑开 */
html, body {
  height: 100%;
  margin: 0;
  padding: 0;
  background: #f2f6fc; /* 页面背景色 */
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
}

/* 下面是组件的样式 */
.page-wrapper {
  width: 100vw;
  min-height: 100vh;
  min-height: 100dvh; /* 兼容移动端视口高度 */
  background: #f2f6fc;
  display: flex;
  flex-direction: column;
}

/* 顶部导航栏 */
.top-nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #409eff;
  padding: 10px 40px;
  color: white;
  flex-wrap: wrap;
}

.nav-logo {
  font-weight: bold;
  font-size: 22px;
}

.nav-menu {
  list-style: none;
  display: flex;
  margin: 0;
  padding: 0;
}

.nav-menu li {
  margin-left: 25px;
}

.nav-menu li a {
  color: white;
  text-decoration: none;
  font-size: 16px;
  transition: color 0.3s;
}

.nav-menu li a:hover {
  color: #a2d0ff;
}

.page-header {
  background: #409eff;
  color: white;
  font-size: 24px;
  text-align: center;
  padding: 25px 0;
  font-weight: bold;
}

.page-footer {
  background: #dcdfe6;
  text-align: center;
  padding: 15px;
  font-size: 14px;
  color: #606266;
  margin-top: auto;
}

.library-container {
  width: 95%;
  max-width: 1400px;
  margin: 30px auto;
  background: #ffffff;
  border-radius: 12px;
  padding: 40px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.05);
  display: flex;
  flex-direction: column;
}

.search-bar {
  margin-bottom: 25px;
  display: flex;
  gap: 15px;
  align-items: center;
  transition: opacity 0.3s ease;
}

.search-bar.loading {
  opacity: 0.6;
  pointer-events: none;
}

.recommend-section {
  margin-bottom: 30px;
  background: #f9f9f9;
  padding: 15px 20px;
  border-radius: 8px;
  transition: box-shadow 0.3s ease;
}
.recommend-section:hover {
  box-shadow: 0 0 12px rgba(64, 158, 255, 0.3);
}

.recommend-section h3 {
  color: #409eff;
  margin-bottom: 10px;
}

.recommend-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.recommend-list li {
  padding: 5px 0;
  font-size: 15px;
  color: #333;
  transition: color 0.3s ease;
}
.recommend-list li:hover {
  color: #409eff;
}

/* 表格行高亮 */
.el-table .row-available:hover {
  background-color: #e6f7ff !important;
  cursor: pointer;
}
.el-table .row-borrowed {
  color: #999;
}

.btn-borrow {
  transition: background-color 0.3s ease, transform 0.2s ease;
}
.btn-borrow:hover:not(:disabled) {
  background-color: #66bb6a;
  transform: scale(1.05);
}
.btn-borrow:active:not(:disabled) {
  transform: scale(0.95);
}

/* 分页 */
.pagination-wrapper {
  margin-top: 20px;
  text-align: right;
}

/* 响应式 */
@media (max-width: 768px) {
  .top-nav {
    padding: 10px 20px;
  }
  .nav-menu {
    width: 100%;
    justify-content: center;
    flex-wrap: wrap;
  }
  .nav-menu li {
    margin: 8px 15px;
  }

  .search-bar {
    flex-direction: column;
    align-items: stretch;
  }
  .search-bar > * {
    width: 100% !important;
  }
  .search-bar > :not(:last-child) {
    margin-bottom: 10px;
  }

  .library-container {
    padding: 20px 15px;
  }
  .page-header {
    font-size: 20px;
    padding: 15px 0;
  }
}
</style>
