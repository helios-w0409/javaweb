
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>商品浏览</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/layui@2.8.17/dist/css/layui.css">
</head>
<body>
<table class="layui-hide" id="productTable" lay-filter="productFilter"></table>
<script src="https://cdn.jsdelivr.net/npm/layui@2.8.17/dist/layui.js"></script>
<script>
layui.use('table', function(){
  var table = layui.table;
  table.render({
    elem: '#productTable',
    url: '/ProductServlet',
    page: true,
    cols: [[
      {field:'name', title:'商品名称'},
      {field:'price', title:'价格'},
      {field:'stock', title:'库存'}
    ]]
  });
});
</script>
</body>
</html>
