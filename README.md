# Swagger学习

****************

## Swagger前置历史

* 前后端分离
  * 后端时代：前端只负责写静态页面；后端用模板引擎JSP渲染，后端是主力
  * 前后端分离时代
    * 后端：controller、service、repository
    * 前端：前端控制层、视图层
      * 伪造前端数据(json)，不用后端都能跑	
    * 前后端如何交互——API，json数据格式
    * 优势：前后端低耦合，甚至可以部署在不同服务器上
    * 问题：前端后端人员无法做到及时协商，前端一改，后端崩溃，香满园死亡
      * 解决方法：首先制定schema【计划提纲】，实时更新API，降低集成风险
    * 前端测试后端接口的工具：postman

***************

## Swagger

* 号称世界上最流行的API框架
* RestFul风格 文档在线生成工具=>API与文档定义实时更新
* 直接运行，可以在线测试API接口

*******************

## Springboot集成Swagger

* 导入依赖

  * 三个依赖包都要导入
  * webmvc可以换成webflux，原来只有两个依赖，现在拆成三个

  ```XML
  <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger2</artifactId>
      <version>2.10.5</version>
  </dependency>
  <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger-ui</artifactId>
      <version>2.10.5</version>
  </dependency>
  <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-spring-webmvc</artifactId>
      <version>2.10.5</version>
  </dependency>
  ```

* 开启swagger2

  ```java 
  @Configuration
  @EnableSwagger2WebMvc
  public class SwaggerConfig {
  }
  ```

* 访问`http://localhost:8080/swagger-ui.html`

*************

## 配置Swager主页信息

* 配置自定义swagger信息	

  * 首先要配置一个Docket对象
  * 配置这个对象要配置一下自己的apiInfo
  * 发现这个apiInfo要传入一个ApiInfo对象
  * 于是阅读ApiInfo源码发现只能通过默认构造函数构造，但是有一个静态代码块里面有一个默认构造对象，复制粘贴进来
  * 把里面的信息进行一下修改，发现有个Contact作者信息又需要配置
  * 于是进入Contact类发现又只能通过构造函数构建，也有一个静态代码块，于是又复制粘贴
  * 接着谢盖修改信息完程配置
  * 添加入容器，在@Qulifier注解实现传参

  ```java
  @EnableSwagger2WebMvc
  @Configuration
  public class SwaggerConfig {
  
      @Bean(name = "docket")
      public Docket getDocket(@Qualifier("apiInfo") ApiInfo apiInfo){
          return new Docket(DocumentationType.SWAGGER_2)
                  .apiInfo(apiInfo);
      }
  
      @Bean(name = "apiInfo")
      public ApiInfo getApiInfo(){
          //作者
          Contact myContact = new Contact(
                  "Izumi Sakai",
                  "http://47.113.97.26/",
                  "izumisakai@aliyun.com");
          //API信息
          ApiInfo apiInfo=new ApiInfo(
                  "API文档",
                  "API 文档",
                  "2.0",
                  "猛男队",
                  myContact,
                  "Apache 2.0",
                  "http://www.apache.org/licenses/LICENSE-2.0",
                  new ArrayList());
          return apiInfo;
      }
  }
  ```

*********************

## 配置Swagger扫描的对象

* 配置实现

  * 要看源码才行

  ```java
  @Bean(name = "docket")
  public Docket getDocket(@Qualifier("apiInfo") ApiInfo apiInfo){
      return new Docket(DocumentationType.SWAGGER_2)
              .apiInfo(apiInfo)
              .select()
              .apis(RequestHandlerSelectors.basePackage("com.demo.controller"))
              .build();
  }
  ```

********************

## 配置Swagger的分组

* 配置实现

  * 配置过后不同的分组有不同的controller接口
  * 一个Docket对应一个groupname，配置多个Docket放进容器就能实现多个分组

  ```java
  @Bean(name = "docket")
  public Docket getDocket(@Qualifier("apiInfo") ApiInfo apiInfo){
      return new Docket(DocumentationType.SWAGGER_2)
              .groupName("Izumi Sakai")//配置groupname
  }
  ```

********************

## Swagger中的module显示

* 使用：只要在实体类entities中加上`@Api`注解且controller中有它作为返回值就会在`http://locahost:8080/swagger-ui.html`中显示

  * 实体类(get、set、全参构造、无参构造全部都要写)

    ```java
    @Api
    public class User {
        private Integer id;
        private String name;
    }
    ```

  * controller(以User为返回对象)

    ```java
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public User user(){
        return new User();
    }
    ```

* 会显示json数据，基本就是手写的Api文档

***************

## 总结

* 很好用
* 正式上线时要关闭