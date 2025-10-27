<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>在线购物 - 商品列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/layui-src/dist/css/layui.css" />
    <style>
        body, html { margin:0; padding:0; height:100%; font-family:-apple-system,BlinkMacSystemFont,"Helvetica Neue",Helvetica,Arial,sans-serif; background:linear-gradient(135deg,#f9f9f9,#e6f0f5); color:#333; overflow-x:hidden; }
        .page-container { display:flex; min-height:100vh; }
        .sidebar { width:220px; background-color:#1E9FFF; color:white; padding:20px 0; box-shadow:3px 0 10px rgba(0,0,0,0.1); display:flex; flex-direction:column; align-items:center; }
        .sidebar h3 { margin:0 0 30px; font-weight:700; font-size:24px; letter-spacing:1.5px; }
        .sidebar nav { width:100%; }
        .sidebar nav a { display:block; color:white; text-decoration:none; padding:12px 30px; font-weight:600; transition:background-color 0.3s ease; cursor:pointer; font-size:16px; }
        .sidebar nav a:hover, .sidebar nav a.active { background-color:#0C6DFF; }
        .main-content { flex:1; padding:40px 50px; background:#fff; border-radius:16px 0 0 16px; box-shadow:0 8px 30px rgba(0,0,0,0.08); animation:fadeIn 0.6s ease; overflow:auto; }
        h2 { font-weight:600; color:#222; font-size:26px; margin-bottom:20px; display:flex; justify-content:space-between; align-items:center; }
        #viewCartBtn { font-size:14px; font-weight:500; padding:8px 18px; border-radius:6px; transition:background 0.3s ease; cursor:pointer; background-color:#fcd34d; color:#222; border:none; }
        #viewCartBtn:hover { background-color:#eab308; color:#111; }
        #pagination { text-align:center; margin-top:30px; }
        .layui-table th { background-color:#f9fafb; color:#333; font-weight:500; }
        .layui-table td, .layui-table th { padding:12px 8px; }
        .filter-container { margin-bottom:20px; display:flex; align-items:center; gap:12px; flex-wrap:wrap; }
        .filter-container input, .filter-container select { padding:6px 10px; border:1px solid #ddd; border-radius:4px; font-size:14px; width:160px; }
        .filter-container label { font-weight:500; color:#555; min-width:60px; }
        .layui-btn.filter-btn { padding:6px 15px; height:34px; line-height:22px; }
        @keyframes fadeIn { from { opacity:0; transform:translateY(15px); } to { opacity:1; transform:translateY(0); } }
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
        <div class="layui-carousel" id="productCarousel" style="margin-bottom:30px;" lay-indicator="inside" lay-arrow="hover">
            <div carousel-item>
                <div><img src="images/photo1.jpg" style="width:100%; height:300px; object-fit:cover;"></div>
                <div><img src="images/photo2.jpg" style="width:100%; height:300px; object-fit:cover;"></div>
                <div><img src="images/photo3.jpg" style="width:100%; height:300px; object-fit:cover;"></div>
            </div>
        </div>

        <h2>🛍️ 商品列表
            <button id="viewCartBtn">🛒 查看购物车</button>
            <button id="btnAddProduct" class="layui-btn layui-btn-sm layui-btn-normal">➕ 新增商品</button>
        </h2>

        <div class="filter-container">
            <label for="searchName">商品名</label>
            <input type="text" id="searchName" placeholder="请输入商品名关键字" />
            <label>价格区间</label>
            <input type="number" id="minPrice" placeholder="最低价" min="0" />
            <span style="font-weight:600;">-</span>
            <input type="number" id="maxPrice" placeholder="最高价" min="0" />
            <label for="filterWarehouse">仓库</label>
            <select id="filterWarehouse">
                <option value="">全部</option>
                <option value="上海仓库">上海仓库</option>
                <option value="北京仓库">北京仓库</option>
                <option value="广州仓库">广州仓库</option>
            </select>
            <button id="btnFilter" class="layui-btn layui-btn-normal layui-btn-sm filter-btn">筛选</button>
            <button id="btnClear" class="layui-btn layui-btn-primary layui-btn-sm filter-btn">🧹 清空筛选</button>
        </div>

        <table id="productTable" lay-filter="productTable"></table>
        <div id="pagination"></div>
    </main>
</div>

<script src="https://cdn.jsdelivr.net/npm/layui-src/dist/layui.all.js"></script>
<script>
    layui.use(['table','laypage','layer','jquery','form','carousel'], function(){
        const table = layui.table, laypage = layui.laypage, layer = layui.layer,
            $ = layui.jquery, form = layui.form, carousel = layui.carousel;

        let currentPage = 1, pageSize = 5;

        carousel.render({elem:'#productCarousel', width:'100%', height:'300px', interval:2500, anim:'fade'});

        function getFilters(){
            return {
                name: $('#searchName').val().trim(),
                minPrice: $('#minPrice').val(),
                maxPrice: $('#maxPrice').val(),
                warehouseName: $('#filterWarehouse').val()
            };
        }

        function loadData(page, limit){
            const filters = getFilters();
            $.ajax({
                url:'ProductServlet',
                method:'GET',
                data:{ page, limit, name:filters.name, minPrice:filters.minPrice, maxPrice:filters.maxPrice, warehouseName:filters.warehouseName },
                dataType:'json',
                success:function(res){
                    table.render({
                        elem:'#productTable',
                        cols:[[
                            {field:'id', title:'ID', width:80, sort:true},
                            {field:'name', title:'商品名称', minWidth:200},
                            {field:'price', title:'价格', width:120, templet:d=>'￥'+d.price},
                            {field:'stock', title:'库存', width:100},
                            {field:'warehouseName', title:'仓库名称', width:140},
                            {field:'warehouseLocation', title:'仓库位置', width:180},
                            {title:'操作', width:220, align:'center', toolbar:'#actionBar'}
                        ]],
                        data:res.data, page:false, limit:limit, height:400
                    });
                    laypage.render({
                        elem:'pagination',
                        count:res.totalCount,
                        limit:pageSize,
                        curr:currentPage,
                        layout:['count','prev','page','next','skip'],
                        jump:function(obj, first){ if(!first){ currentPage=obj.curr; loadData(currentPage,pageSize); } }
                    });
                },
                error:function(){ layer.msg('数据加载失败',{icon:2}); }
            });
        }

        // 新增商品
        $('#btnAddProduct').click(function(){
            layer.open({
                type:1,
                title:'➕ 新增商品',
                area:['460px','420px'],
                content:`
            <div style="padding:20px;">
                <form class="layui-form" id="formAddProduct">
                    <div class="layui-form-item">
                        <label class="layui-form-label">商品名称</label>
                        <div class="layui-input-block"><input type="text" name="name" required placeholder="请输入商品名称" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">价格</label>
                        <div class="layui-input-block"><input type="number" name="price" required placeholder="请输入价格" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">库存</label>
                        <div class="layui-input-block"><input type="number" name="stock" required placeholder="请输入库存" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">仓库</label>
                        <div class="layui-input-block">
                            <select name="warehouseId" required>
                                <option value="1">上海仓库</option>
                                <option value="2">北京仓库</option>
                                <option value="3">广州仓库</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-form-item" style="text-align:center;">
                        <button type="button" class="layui-btn layui-btn-normal" id="btnSubmitAdd">提交</button>
                    </div>
                </form>
            </div>
            `,
                success:function(){
                    form.render();
                    $('#btnSubmitAdd').click(function(){
                        $.post('ProductEditServlet?action=add', $('#formAddProduct').serialize(), function(res){
                            const result = typeof res==='string'?JSON.parse(res):res;
                            if(result.success){ layer.msg('新增成功',{icon:1}); layer.closeAll(); loadData(currentPage,pageSize); }
                            else{ layer.msg('新增失败：'+result.message,{icon:2}); }
                        }).fail(()=>layer.msg('请求失败',{icon:2}));
                    });
                }
            });
        });

        // 表格操作
        table.on('tool(productTable)', function(obj){
            const data=obj.data;

            if(obj.event==='edit'){
                layer.open({
                    type:1,
                    title:'✏️ 编辑商品',
                    area:['460px','420px'],
                    content:`
                <div style="padding:20px;">
                    <form class="layui-form" id="formEditProduct">

                        <input type="hidden" name="id" value="`+ data.id +`">

                        <div class="layui-form-item">
                            <label class="layui-form-label">商品名称</label>
                            <div class="layui-input-block"><input type="text" name="name" value="${data.name}" required class="layui-input"></div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">价格</label>
                            <div class="layui-input-block"><input type="number" name="price" value="${data.price}" required class="layui-input"></div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">库存</label>
                            <div class="layui-input-block"><input type="number" name="stock" value="${data.stock}" required class="layui-input"></div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">仓库</label>
                            <div class="layui-input-block">
                                <select name="warehouseId" required>
                                    <option value="1" ${data.warehouseName.includes('上海')?'selected':''}>上海仓库</option>
                                    <option value="2" ${data.warehouseName.includes('北京')?'selected':''}>北京仓库</option>
                                    <option value="3" ${data.warehouseName.includes('广州')?'selected':''}>广州仓库</option>
                                </select>
                            </div>
                        </div>
                        <div class="layui-form-item" style="text-align:center;">
                            <button type="button" class="layui-btn layui-btn-normal" id="btnSubmitEdit">保存修改</button>
                        </div>
                    </form>
                </div>
                `,
                    success:function(){
                        form.render();
                        $('#btnSubmitEdit').click(function(){
                            $.post('ProductEditServlet?action=edit', $('#formEditProduct').serialize(), function(res){
                                const result = typeof res==='string'?JSON.parse(res):res;
                                if(result.success){ layer.msg('修改成功',{icon:1}); layer.closeAll(); loadData(currentPage,pageSize); }
                                else{ layer.msg('修改失败：'+result.message,{icon:2}); }
                            }).fail(()=>layer.msg('请求失败',{icon:2}));
                        });
                    }
                });
            } else if(obj.event==='delete'){
                layer.confirm('确定删除 ['+data.name+'] 吗？',{icon:3,title:'删除商品'}, function(index){
                    $.post('ProductEditServlet', {action:'delete',id:data.id}, function(res){
                        const result = typeof res==='string'?JSON.parse(res):res;
                        if(result.success){ layer.msg('删除成功',{icon:1}); loadData(currentPage,pageSize); }
                        else{ layer.msg('删除失败：'+result.message,{icon:2}); }
                    }).fail(()=>layer.msg('删除失败',{icon:2}));
                    layer.close(index);
                });
            } else if(obj.event==='addCart'){
                $.post('CartServlet', {productId:data.id, quantity:1}, function(){ layer.msg('已加入购物车：'+data.name,{icon:1}); })
                    .fail(()=>layer.msg('加入购物车失败',{icon:2}));
            }
        });

        // 筛选和清空
        $('#btnFilter').click(()=>{ currentPage=1; loadData(currentPage,pageSize); });
        $('#btnClear').click(()=>{ $('#searchName,#minPrice,#maxPrice').val(''); $('#filterWarehouse').val(''); form.render(); loadData(1,pageSize); });

        // 初始化加载
        loadData(currentPage,pageSize);
    });
</script>

<!-- 操作按钮模板 -->
<script type="text/html" id="actionBar">
    <button class="layui-btn layui-btn-xs layui-btn-normal" lay-event="addCart">加入购物车</button>
    <button class="layui-btn layui-btn-xs layui-btn-primary" lay-event="edit">编辑</button>
    <button class="layui-btn layui-btn-xs layui-btn-danger" lay-event="delete">删除</button>
</script>
</body>
</html>
