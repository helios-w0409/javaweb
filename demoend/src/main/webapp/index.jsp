<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>用户信息表单</title>
  <script>
    function validateForm(event) {
      event.preventDefault(); // 阻止表单默认提交

      var name = document.getElementById("name").value.trim();
      var password1 = document.getElementById("password1").value;
      var password2 = document.getElementById("password2").value;
      var gender = document.querySelector('input[name="gender"]:checked');
      var hobbies = document.querySelectorAll('input[name="hobby"]:checked');
      var fruits = document.getElementById("fruits").selectedOptions;

      // 姓名验证
      if (name === "") {
        alert("姓名不能为空！");
        return false;
      }

      // 性别验证
      if (!gender) {
        alert("请选择性别！");
        return false;
      }

      // 密码验证
      if (password1.length < 6) {
        alert("密码必须至少 6 位！");
        return false;
      }
      if (password1 !== password2) {
        alert("两次输入的密码不一致！");
        return false;
      }

      // 兴趣爱好验证
      if (hobbies.length === 0) {
        alert("请至少选择一个爱好！");
        return false;
      }

      // 确保至少选择一个水果
      if (fruits.length === 0) {
        alert("请选择至少一个水果！");
        return false;
      }

      // 提交表单
      document.getElementById("userForm").submit();
    }
  </script>
</head>
<body>

<h1>用户信息表单</h1>

<form id="userForm" action="${pageContext.request.contextPath}/form" method="POST" onsubmit="validateForm(event)">
  <!-- 姓名 -->
  <label for="name">姓名：</label>
  <input type="text" id="name" name="name"><br><br>

  <!-- 性别 -->
  <p>性别：</p>
  <label for="male">男</label>
  <input type="radio" id="male" name="gender" value="男">

  <label for="female">女</label>
  <input type="radio" id="female" name="gender" value="女"><br><br>

  <!-- 密码 -->
  <label for="password1">第一次密码：</label>
  <input type="password" id="password1" name="password1"><br><br>

  <label for="password2">第二次密码：</label>
  <input type="password" id="password2" name="password2"><br><br>

  <!-- 选择爱好 -->
  <p>选择你的爱好：</p>
  <label for="basketball">篮球</label>
  <input type="checkbox" id="basketball" name="hobby" value="篮球">

  <label for="football">足球</label>
  <input type="checkbox" id="football" name="hobby" value="足球"><br><br>

  <!-- 选择水果 -->
  <p>选择你喜欢的水果（按住 Ctrl/Shift 可多选）：</p>
  <select id="fruits" name="fruits" multiple size="3">
    <option value="苹果">苹果</option>
    <option value="香蕉">香蕉</option>
    <option value="橙子">橙子</option>
    <option value="葡萄">葡萄</option>
    <option value="西瓜">西瓜</option>
  </select><br><br>

  <!-- 按钮 -->
  <input type="submit" value="提交">
  <input type="reset" value="重置">
</form>

</body>
</html>

