package com.example.demo.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MpGenerator {
    private static final String dbName = "mengma";
    private static final String driverClassName = "com.mysql.cj.jdbc.Driver";
    private static final String dbUrl = "jdbc:mysql://127.0.0.1:3306/" + dbName + "?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";

    private static final String dbUsername = "root";
    private static final String dbPassword = "123456";

    private static final String basePackageName = "com.example.demo";

    private static final String projectPath = System.getProperty("user.dir");
    private static final String outFileUrl="/src/main/java/com/example/demo/";
    public static void main(String[] args) throws Exception {
        // 代码生成器
        AutoGenerator mpg =new AutoGenerator();
        // 全局配置
        GlobalConfig gc=new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");//设置代码生成路径
        gc.setFileOverride(false);//是否覆盖以前文件
        gc.setOpen(false);//是否打开生成目录
        gc.setIdType(IdType.AUTO);//设置主键策略
//        gc.setBaseResultMap(true);//生成基本ResultMap
//        gc.setBaseColumnList(true);//生成基本ColumnList
        gc.setAuthor("zhou");//设置作者
        gc.setServiceName("%sService");//去掉服务默认前缀
        gc.setDateType(DateType.ONLY_DATE);//设置时间类型

        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dbUrl);
        dsc.setDriverName(driverClassName);
        dsc.setUsername(dbUsername);
        dsc.setPassword(dbPassword);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(basePackageName);
        pc.setMapper("mapper");
//        pc.setXml("mapper.xml");
//        pc.setXml(null);
        pc.setEntity("entity");
        pc.setService("service");
        pc.setServiceImpl("serviceImpl");
        pc.setController("controller");
        mpg.setPackageInfo(pc);
        //不生成xml
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        mpg.setTemplate(templateConfig);


        // 策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setNaming(NamingStrategy.underline_to_camel);
        sc.setColumnNaming(NamingStrategy.underline_to_camel);
        sc.setEntityLombokModel(true);//自动lombok
        sc.setRestControllerStyle(true);
        sc.setControllerMappingHyphenStyle(true);
        sc.setLogicDeleteFieldName("deleted");//设置逻辑删除

        //设置自动填充配置
        TableFill gmt_create = new TableFill("create_time", FieldFill.INSERT);
        TableFill gmt_modified = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills=new ArrayList<>();
        tableFills.add(gmt_create);
        tableFills.add(gmt_modified);
        sc.setTableFillList(tableFills);

        //乐观锁
        sc.setVersionFieldName("version");
        sc.setRestControllerStyle(true);//驼峰命名



        //  sc.setTablePrefix("tbl_"); 设置表名前缀
        String[] tables = getTables(dbName);
        sc.setInclude(tables);
        mpg.setStrategy(sc);

        mpg.setCfg(injectionConfig());//只覆盖entity
        // 生成代码
        mpg.execute();
    }
    private static String[] getTables(String dbName) throws Exception {
        List<String> tables = new ArrayList<>();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            System.out.println(Class.forName(driverClassName));
            connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
            String sql = "select table_name from information_schema.tables where table_schema=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,dbName);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                tables.add(resultSet.getString("table_name"));
            }
            String[] result = tables.toArray(new String[tables.size()]);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        throw new Exception("数据库连接异常！");
    }
    private static InjectionConfig injectionConfig(FileType... fileTypeEnum) {
        String serviceTemplatePath = "/templates/vm/myService.java.vm";
        List<FileOutConfig> fileOutConfigList = new ArrayList<>();
        // 自定义Service模板 （如不需要就去掉）
        fileOutConfigList.add(new FileOutConfig(serviceTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return  projectPath+ outFileUrl+"service/" + tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
            }
        });

        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }

        };
        injectionConfig.setFileOutConfigList(fileOutConfigList);

        injectionConfig.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                if (fileTypeEnum.length == 0) {
                    //无参情况下，先检查.java file是否存在：
                    //如果不存在，创建；如果存在，判断是否是entity.java：如果是，创建（覆盖）；否则，不创建。
                    checkDir(filePath);
                    File file = new File(filePath);
                    boolean exist = file.exists();
                    if (exist) {
                        if (FileType.ENTITY == fileType) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    return true;
                } else {
                    //有参情况下，只创建传入的.java，无论存在都直接覆盖。
                    boolean isType = false;
                    for (FileType type : fileTypeEnum) {
                        if (type == fileType) {
                            isType = true;
                            break;
                        }
                    }
                    if (!isType) {
                        return false;
                    }
                    checkDir(filePath);
                    return true;
                }
            }

        });
        return injectionConfig;
    }


}
