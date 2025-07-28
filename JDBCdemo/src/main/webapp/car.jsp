<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>购物车</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/layui-src/dist/css/layui.css" />
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap');

        * {
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;
        }
        body {
            margin: 0;
            padding: 40px 20px;
            background: linear-gradient(145deg, #f3f4f6, #e5e7eb);
            min-height: 100vh;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
            background: rgba(255, 255, 255, 0.7);
            backdrop-filter: blur(15px);
            border-radius: 20px;
            box-shadow: 0 30px 60px rgba(0, 0, 0, 0.1);
            padding: 40px;
            transition: all 0.3s ease;
        }
        h2 {
            text-align: center;
            font-weight: 600;
            font-size: 28px;
            margin-bottom: 30px;
            color: #111827;
        }
        .summary {
            margin-top: 30px;
            text-align: right;
            font-size: 18px;
            font-weight: 500;
            color: #1f2937;
        }
        .actions {
            text-align: right;
            margin-top: 20px;
        }
        .actions button {
            border-radius: 999px;
            font-weight: 500;
            padding: 8px 22px;
        }
        .layui-btn-danger {
            background: linear-gradient(to right, #ff6a00, #ee0979) !important;
            border: none;
        }
        .layui-btn-primary {
            border-color: #9ca3af !important;
        }

        /* 表格美化 */
        .layui-table th {
            background: #f9fafb;
            font-weight: 600;
        }
        .layui-table td {
            background: #ffffff;
        }
        .layui-btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            line-height: normal; /* 避免与 padding 冲突 */
        }

        /* 数量输入框样式 */
        .quantity-input {
            width: 60px;
            text-align: center;
            border: 1px solid #ddd;
            border-radius: 4px;
            height: 30px;
            line-height: 30px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>🛍 我的购物车</h2>

    <table id="cartTable" lay-filter="cartTable"></table>

    <div class="summary" id="summaryInfo">共 0 件商品，合计 ￥0.00</div>

    <div class="actions">
        <button class="layui-btn layui-btn-danger" id="checkoutBtn">💳 结算 / 清空</button>
        <button class="layui-btn layui-btn-primary" onclick="window.location.href='show.jsp'">🔙 返回商品</button>
    </div>
</div>

<!-- 工具条模板 -->
<script type="text/html" id="bar">
    <button class="layui-btn layui-btn-xs layui-btn-danger" lay-event="deleteItem">🗑 删除</button>
</script>

<!-- 数量编辑模板 -->
<script type="text/html" id="quantityTpl">
    <input type="number" min="1" class="quantity-input" value="{{ d.quantity }}" />
</script>

<script src="https://cdn.jsdelivr.net/npm/layui-src/dist/layui.all.js"></script>
<script>
    layui.use(['table', 'layer', 'jquery'], function () {
        const table = layui.table;
        const layer = layui.layer;
        const $ = layui.jquery;

        let updating = false;  // 防止重复请求锁

        // 存旧数量
        let oldQuantities = {};

        function loadCart() {
            $.ajax({
                url: 'CartServlet',
                method: 'GET',
                dataType: 'json',
                success: function (res) {
                    $('#summaryInfo').text('共 ' + res.totalQuantity + ' 件商品，合计 ￥' + res.totalAmount.toFixed(2));

                    table.render({
                        elem: '#cartTable',
                        cols: [[
                            { field: 'id', title: '商品ID', width: 100 },
                            { field: 'name', title: '商品名称', minWidth: 200 },
                            { field: 'price', title: '单价', width: 120, templet: d => '￥' + d.price.toFixed(2) },
                            { field: 'quantity', title: '数量', width: 120, templet: '#quantityTpl' },
                            { field: 'subtotal', title: '小计', width: 120, templet: d => '￥' + d.subtotal.toFixed(2) },
                            { title: '操作', width: 120, align: 'center', toolbar: '#bar' }
                        ]],
                        data: res.data || [],
                        page: false,
                        height: 400,
                        done: function (res, curr, count) {
                            res.data.forEach(item => {
                                oldQuantities[item.id] = item.quantity;
                            });
                        }
                    });
                },
                error: function () {
                    layer.msg('加载购物车失败', { icon: 2 });
                }
            });
        }

        // 删除商品
        table.on('tool(cartTable)', function (obj) {
            if (obj.event === 'deleteItem') {
                if (updating) return;
                updating = true;

                const productId = obj.data.id;
                $.post('CartServlet', { productId: productId, action: 'delete' }, function (res) {
                    layer.msg('商品已删除 🗑', { icon: 1 });
                    loadCart();
                    updating = false;
                }).fail(function () {
                    layer.msg('删除失败，请稍后再试', { icon: 2 });
                    updating = false;
                });
            }
        });

        // 数量输入框失去焦点，自动更新
        $('body').on('change', '.quantity-input', function () {
            console.log('数量输入框失去焦点，触发更新');
            if (updating) return;

            let input = $(this);
            let newQty = parseInt(input.val());
            if (isNaN(newQty) || newQty < 1) {
                layer.msg('数量必须是大于0的整数', { icon: 2 });
                // 恢复旧数量
                let rowElem = input.closest('tr');
                let productId = rowElem.find('td[data-field="id"]').text();
                input.val(oldQuantities[productId] || 1);
                return;
            }

            let rowElem = input.closest('tr');
            let productId = rowElem.find('td[data-field="id"]').text();
            let oldQty = oldQuantities[productId] || 1;

            if (newQty === oldQty) return;

            updating = true;
            $.post('CartServlet', { productId: productId, quantity: newQty, action: 'update' }, function (res) {
                if(res.success){
                    layer.msg('数量已更新', { icon: 1 });
                    oldQuantities[productId] = newQty;
                    loadCart();
                } else {
                    layer.msg('更新失败', { icon: 2 });
                    input.val(oldQty);
                }
                updating = false;
            }).fail(function () {
                layer.msg('请求失败，请稍后重试', { icon: 2 });
                input.val(oldQty);
                updating = false;
            });
        });

        // 结算按钮点击事件
        $('#checkoutBtn').click(function () {
            layer.confirm('确认结算并清空购物车？', { icon: 3, title: '结算确认' }, function (index) {
                $.ajax({
                    url: 'CartServlet',
                    type: 'PUT',
                    success: function () {
                        layer.msg('🎉 结算成功！购物车已清空', { icon: 1 });
                        loadCart();
                    },
                    error: function () {
                        layer.msg('结算失败，请稍后重试', { icon: 2 });
                    }
                });
                layer.close(index);
            });
        });

        // 初始加载
        loadCart();
    });
</script>
</body>
</html>
