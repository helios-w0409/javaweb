<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>在线购物 - 商品列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/layui-src/dist/css/layui.css" />
    <style>
        body, html {
            margin: 0; padding: 0; height: 100%;
            font-family: -apple-system, BlinkMacSystemFont, "Helvetica Neue", Helvetica, Arial, sans-serif;
            background: linear-gradient(135deg, #f9f9f9, #e6f0f5);
            color: #333;
            overflow-x: hidden;
        }
        .page-container {
            display: flex;
            min-height: 100vh;
        }
        .sidebar {
            width: 220px;
            background-color: #1E9FFF;
            color: white;
            padding: 20px 0;
            box-shadow: 3px 0 10px rgba(0,0,0,0.1);
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .sidebar h3 {
            margin: 0 0 30px;
            font-weight: 700;
            font-size: 24px;
            letter-spacing: 1.5px;
        }
        .sidebar nav {
            width: 100%;
        }
        .sidebar nav a {
            display: block;
            color: white;
            text-decoration: none;
            padding: 12px 30px;
            font-weight: 600;
            transition: background-color 0.3s ease;
            cursor: pointer;
            font-size: 16px;
        }
        .sidebar nav a:hover, .sidebar nav a.active {
            background-color: #0C6DFF;
        }

        .main-content {
            flex: 1;
            padding: 40px 50px;
            background: #fff;
            border-radius: 16px 0 0 16px;
            box-shadow: 0 8px 30px rgba(0, 0, 0, 0.08);
            animation: fadeIn 0.6s ease;
            overflow: auto;
        }

        h2 {
            font-weight: 600;
            color: #222;
            font-size: 26px;
            margin-bottom: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        #viewCartBtn {
            font-size: 14px;
            font-weight: 500;
            padding: 8px 18px;
            border-radius: 6px;
            transition: background 0.3s ease;
            cursor: pointer;
            background-color: #fcd34d;
            color: #222;
            border: none;
        }
        #viewCartBtn:hover {
            background-color: #eab308;
            color: #111;
        }

        #pagination {
            text-align: center;
            margin-top: 30px;
        }
        .layui-table th {
            background-color: #f9fafb;
            color: #333;
            font-weight: 500;
        }
        .layui-table td, .layui-table th {
            padding: 12px 8px;
        }
        .filter-container {
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            gap: 12px;
            flex-wrap: wrap;
        }
        .filter-container input, .filter-container select {
            padding: 6px 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            width: 160px;
        }
        .filter-container label {
            font-weight: 500;
            color: #555;
            min-width: 60px;
        }
        .layui-btn.filter-btn {
            padding: 6px 15px;
            height: 34px;
            line-height: 22px;
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(15px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>
<div class="page-container">
    <aside class="sidebar">
        <h3>商城导航</h3>
        <nav>
            <a href="#" class="active">首页</a>
            <a href="#">商品列表</a>
            <a href="#">购物车</a>
            <a href="#">订单管理</a>
            <a href="#">用户中心</a>
            <a href="#">帮助中心</a>
        </nav>
    </aside>

    <main class="main-content">
        <!-- 商品轮播图 -->
        <div class="layui-carousel" id="productCarousel" style="margin-bottom: 30px;" lay-indicator="inside" lay-arrow="hover">
            <div carousel-item>
                <div><img src="images/photo1.jpg" style="width:100%; height:300px; object-fit:cover;"></div>
                <div><img src="images/photo2.jpg" style="width:100%; height:300px; object-fit:cover;"></div>
                <div><img src="images/photo3.jpg" style="width:100%; height:300px; object-fit:cover;"></div>
            </div>
        </div>

        <h2>🛍️ 商品列表
            <button id="viewCartBtn">🛒 查看购物车</button>
        </h2>

        <!-- 搜索筛选区域 -->
        <div class="filter-container">
            <label for="searchName">商品名</label>
            <input type="text" id="searchName" placeholder="请输入商品名关键字" />

            <label>价格区间</label>
            <input type="number" id="minPrice" placeholder="最低价" min="0" />
            <span style="font-weight: 600;">-</span>
            <input type="number" id="maxPrice" placeholder="最高价" min="0" />

            <label for="filterWarehouse">仓库</label>
            <select id="filterWarehouse">
                <option value="">全部</option>
                <option value="上海仓">上海仓</option>
                <option value="北京仓">北京仓</option>
                <option value="广州仓">广州仓</option>
            </select>

            <button id="btnFilter" class="layui-btn layui-btn-normal layui-btn-sm filter-btn">筛选</button>
            <button id="btnClear" class="layui-btn layui-btn-primary layui-btn-sm filter-btn">🧹 清空筛选</button>
        </div>

        <!-- 商品列表表格 -->
        <table id="productTable" lay-filter="productTable"></table>

        <!-- 分页容器 -->
        <div id="pagination"></div>
    </main>
</div>

<script src="https://cdn.jsdelivr.net/npm/layui-src/dist/layui.all.js"></script>
<script>
    layui.use(['table', 'laypage', 'layer', 'jquery', 'carousel'], function () {
        const table = layui.table;
        const laypage = layui.laypage;
        const layer = layui.layer;
        const $ = layui.jquery;
        const carousel = layui.carousel;

        let currentPage = 1;
        const pageSize = 5;

        carousel.render({
            elem: '#productCarousel',
            width: '100%',
            height: '300px',
            interval: 2000,
            anim: 'fade'
        });

        function getFilters() {
            return {
                name: $('#searchName').val().trim(),
                minPrice: $('#minPrice').val(),
                maxPrice: $('#maxPrice').val(),
                warehouseName: $('#filterWarehouse').val() // 新增仓库筛选
            };
        }

        function loadData(page, limit) {
            const filters = getFilters();
            $.ajax({
                url: 'ProductServlet',
                method: 'GET',
                data: {
                    page: page,
                    limit: limit,
                    name: filters.name,
                    minPrice: filters.minPrice,
                    maxPrice: filters.maxPrice,
                    warehouseName: filters.warehouseName // 新增
                },
                dataType: 'json',
                success: function (res) {
                    table.render({
                        elem: '#productTable',
                        cols: [[
                            { field: 'id', title: 'ID', width: 80, sort: true },
                            { field: 'name', title: '商品名称', minWidth: 200 },
                            { field: 'price', title: '价格', width: 120, sort: true, templet: d => '￥' + d.price },
                            { field: 'stock', title: '库存', width: 100 },
                            { field: 'warehouseName', title: '仓库名称', width: 140 },
                            { field: 'warehouseLocation', title: '仓库位置', width: 180 },
                            { title: '操作', width: 140, align: 'center', toolbar: '#actionBar' }
                        ]],
                        data: res.data,
                        page: false,
                        limit: limit,
                        height: 400
                    });

                    laypage.render({
                        elem: 'pagination',
                        count: res.totalCount,
                        limit: pageSize,
                        curr: currentPage,
                        layout: ['count', 'prev', 'page', 'next', 'skip'],
                        jump: function (obj, first) {
                            if (!first) {
                                currentPage = obj.curr;
                                loadData(currentPage, pageSize);
                            }
                        }
                    });
                },
                error: function () {
                    layer.msg('数据加载失败', { icon: 2 });
                }
            });
        }

        $('#searchName').on('input', function () {
            currentPage = 1;
            loadData(currentPage, pageSize);
        });

        $('#btnFilter').click(function () {
            currentPage = 1;
            loadData(currentPage, pageSize);
        });

        $('#btnClear').click(function () {
            $('#searchName').val('');
            $('#minPrice').val('');
            $('#maxPrice').val('');
            $('#filterWarehouse').val(''); // 清空仓库筛选
            currentPage = 1;
            loadData(currentPage, pageSize);
        });

        table.on('tool(productTable)', function (obj) {
            const data = obj.data;
            if (obj.event === 'addCart') {
                $.ajax({
                    url: 'CartServlet',
                    method: 'POST',
                    data: { productId: data.id, quantity: 1 },
                    success: function () {
                        layer.msg('已加入购物车：' + data.name, { icon: 1 });
                    },
                    error: function () {
                        layer.msg('加入购物车失败', { icon: 2 });
                    }
                });
            }
        });

        document.getElementById('viewCartBtn').onclick = function () {
            window.location.href = 'car.jsp';
        };

        loadData(currentPage, pageSize);
    });
</script>

<script type="text/html" id="actionBar">
    <button class="layui-btn layui-btn-xs layui-btn-normal" lay-event="addCart">加入购物车</button>
</script>

</body>
</html>
