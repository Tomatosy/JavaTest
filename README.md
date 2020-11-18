# JavaTest

**一、虚拟点名场景**

内置4个人员分别为：

（1）张三、男

（2）李四、女

（3）王五、男

（4）赵六、女

实现男生点名服务，访问Controller后浏览器显示以下内容：

Hello，我叫张三

Hello，我叫王五

**一、实现要求**

**（1）新建Spring Boot项目，JDK版本为OpenJDK 1.8**

（2）建立一个名为Person的Model，包含姓名及性别，使用Lombok

（3）建立一个名为CallService的服务接口，定义callMan方法，输入List<Person>

（4）通过CallServiceImpl实现CallService接口，**体现List数据筛选**。

（5）建立CallController，对外暴露接口，实现4个人员的数据初始化，通过基于接口编程实现功能（**使用依赖倒置，通过Bean对象注入**）。

（6）配置程序启动端口9001，最终Cotroller访问地址：**http://localhost:9001/api/call**

