# springboot-mengma
梦码科学技术协会后端仓库
## 说明

这是梦码网站建设的后端部分，整体基于springboot2.x+mysql
## 上手步骤

需要先将本仓库克隆到本地并进行若干配置，后续的push推送操作都会提交pr经过审查，相关教程请自行上网搜索
## 使用到的其他框架

- [myabtis-plus](https://baomidou.com/pages/24112f/):一个mybatis增强工具，封装了许多crud的操作，当然你也可以选择手写sql，[generator](https://github.com/ncu-mengma/springboot-mengma/blob/main/src/main/java/com/example/demo/generator/MpGenerator.java)是一个mybatis-plus自带的代码生成器，主要基于已有的数据库生成controller-service-mapper-entity等，其中大部分配置我已经写好了，也可以自己修改
- [sa-token](https://sa-token.cc/doc.html):一个鉴权框架，类似于spring-security，但sa-token更小巧，配置更简单，且功能完善，详细使用方法参照官方文档
- [smart-doc](https://smart-doc-group.github.io/#/zh-cn/?id=smart-doc):一个接口文档生成工具，与Swagger相比其对代码的侵入性更小，完全基于java-doc。为了使插件效果正常，请严格遵守文档中的注释规范
- [common-util](https://github.com/shalousun/ApplicationPower/tree/master/common-util):一个封装了很多工具类的模块，为了使smart-doc的文档生成正常，主要会使用这里封装好的[公共返回类](https://github.com/shalousun/ApplicationPower/blob/master/common-util/src/main/java/com/power/common/model/CommonResult.java)。此外，该模块还有许多其他实用的工具类，后续看情况引入
- [flyway](https://blog.csdn.net/Jiao1225/article/details/129590660):一个数据库版本控制工具，主要是同步不同开发人员的本地数据库的(目前也不知道效果怎么样)。可以先通过maven插件将我已上传的[sql脚本](https://github.com/ncu-mengma/springboot-mengma/blob/main/src/main/resources/db/migration/V20231119.00.20__init.sql)进行migrate，后续自己的sql文件应该也写在db目录下。为了避免版本号冲突，命名的版本应为时间戳，如`V20231119.00.20__init.sql`中`20231119.00.20`就表示一个时间

## 数据库规范

- id如没有默认值，应在sql中设置为自动递增
- 尽量不要对已有表结构的字段进行重命名或删除，如一定要执行上述操作，需要先在群里说明(因为可能别人的代码就是基于这些字段的)。但可以新建表或增加表字段
- 多对多的中间关联表应命名为`rel_xxx_yyy`，如用户表`user`和角色表`role`具有多对多的关系(一个用户可能有多个角色，一个角色可能被多个用户拥有)，那么中间关联表应命名为`rel_user_role`，表字段可以为`id`、`uid`、`rid`，其中`uid`、`rid`分别对应用户id和角色id
- 如有必要记录某张表数据的插入时间和更新时间，可以设置字段`create_time`、`update_time`，通过[MyMetaObjectHandler](https://github.com/ncu-mengma/springboot-mengma/blob/main/src/main/java/com/example/demo/mp/handler/MyMetaObjectHandler.java)实现自动注入

