package com.jy.medusa.generator;

import com.jy.medusa.utils.MyDateUtils;
import com.jy.medusa.utils.MyUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by neo on 16/7/27.
 */
public class GenService {

    private String entityPath;
    private String servicePath;
    private String serviceImplPath;
    private String mapperPath;

    private String entityName;

    private final String mixMapper = "com.jy.medusa.commons.Mapper";


    public GenService(String tableName, String entityPath, String servicePath, String serviceImplPath, String mapperPath, String tag){
        this.entityPath = entityPath;
        this.servicePath = servicePath;
        this.serviceImplPath = serviceImplPath;
        this.mapperPath = mapperPath;
        this.entityName = MyGenUtils.upcaseFirst(tableName);

        this.tag = tag;

        this.markServiceList = MyGenUtils.genTagStrList(entityName + "Service.java", servicePath, tag, "service");
        this.markServiceImplList = MyGenUtils.genTagStrList(entityName + "ServiceImpl.java", serviceImplPath, tag, "serviceImpl");
        this.markMapperList = MyGenUtils.genTagStrList(entityName + "Mapper.java", mapperPath, tag, "mapper");
    }

    public void process(){

        try {
            //写入service 和 impl
            String path = System.getProperty("user.dir") + "/src/main/java/" + servicePath.replaceAll("\\.", "/");
            String pathImp = System.getProperty("user.dir") + "/src/main/java/" + serviceImplPath.replaceAll("\\.", "/");
            File file = new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
            String resPath1 = path + "/" + entityName + "Service.java";
            String resPath2 = pathImp + "/" + entityName + "ServiceImpl.java";
            MyUtils.writeString2File(new File(resPath1), process1(), "UTF-8");
            MyUtils.writeString2File(new File(resPath2), process2(), "UTF-8");

            //mapper
            String pathmm = System.getProperty("user.dir") + "/src/main/java/" + mapperPath.replaceAll("\\.", "/");
            String resPath3 = pathmm + "/" + entityName + "Mapper.java";
            MyUtils.writeString2File(new File(resPath3), process3(), "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * service
     * @return
     */
    private String process1() {

        StringBuilder sbb = new StringBuilder();

        sbb.append("package " + servicePath + ";\r\n\r\n");

        sbb.append("import " + entityPath + "." + entityName + Home.entityNameSuffix + ";\r\n\r\n");
        /*sbb.append("import java.util.List;\r\n");*/

        //sbb.append("public interface "+ entityName +"Service {\r\n");

        //添加作者
        sbb.append("/**\r\n");
        sbb.append(" * Created by " + Home.author + " on " + MyDateUtils.convertDateToStr(new Date(), null) + "\r\n");
        sbb.append(" */\r\n");

        sbb.append("public interface "+ entityName +"Service extends BaseService<" + entityName+ Home.entityNameSuffix + "> {\r\n");

/*        sbb.append("\t" + entityName + " selectById(" + entityName + " entity);\r\n\r\n");
        sbb.append("\tvoid save" + entityName + "(" + entityName + " entity);\r\n\r\n");
        sbb.append("\tvoid update" + entityName + "(" + entityName + " entity);\r\n\r\n");
        sbb.append("\tvoid delete" + entityName + "(" + entityName + " entity);\r\n\r\n");
        sbb.append("\tvoid deleteMulti" + entityName + "(List<Integer> ids, Class<" + entityName + "> t);\r\n");*/

        MyGenUtils.processAllRemains(markServiceList, sbb, tag, "service");

        sbb.append("}");

        return sbb.toString();
    }

    /**
     * serviceImpl
     * @return
     */
    private String process2() {

        StringBuilder sbb = new StringBuilder();

        sbb.append("package " + serviceImplPath + ";\r\n\r\n");

        sbb.append("import " + entityPath + "." + entityName + Home.entityNameSuffix + ";\r\n");
        sbb.append("import javax.annotation.Resource;\r\n");
        sbb.append("import " + mapperPath + "." + entityName + "Mapper;\r\n");
        sbb.append("import " + servicePath + "." + entityName + "Service;\r\n\r\n");
//        sbb.append("import java.util.List;\r\n");
//        sbb.append("import " + baseMapperPath + ";\r\n\r\n");
        sbb.append("import " + "org.springframework.stereotype.Service" + ";\r\n\r\n");


        //添加作者
        sbb.append("/**\r\n");
        sbb.append(" * Created by " + Home.author + " on " + MyDateUtils.convertDateToStr(new Date(), null) + "\r\n");
        sbb.append(" */\r\n");

        sbb.append("@Service\r\n");

        sbb.append("public class " + entityName + "ServiceImpl extends BaseServiceImpl<" + entityName + Home.entityNameSuffix + "> implements " + entityName + "Service {\r\n\r\n");

        sbb.append("\t@Resource\r\n");
        sbb.append("\tprivate " + entityName + "Mapper " + MyGenUtils.lowcaseFirst(entityName) + "Mapper;\r\n\r\n");
        /*sbb.append("\t@Resource\r\n");
        sbb.append("\tprivate Mapper<" + entityName + ",Integer> superMapper;\r\n\r\n");

        sbb.append("\t@Override\r\n");
        sbb.append("\tpublic " + entityName + " selectById(" + entityName + " entity) {\r\n");
        sbb.append("\t\treturn superMapper.selectOneById(entity);\r\n");
        sbb.append("\t}\r\n\r\n");

        sbb.append("\t@Override\r\n");
        sbb.append("\tpublic void save" + entityName + "(" + entityName + " entity) {\r\n");
        sbb.append("\t\tsuperMapper.create(entity);\r\n");
        sbb.append("\t}\r\n\r\n");

        sbb.append("\t@Override\r\n");
        sbb.append("\tpublic void update" + entityName + "(" + entityName + " entity) {\r\n");
        sbb.append("\t\tsuperMapper.modify(entity);\r\n");
        sbb.append("\t}\r\n\r\n");

        sbb.append("\t@Override\r\n");
        sbb.append("\tpublic void delete" + entityName + "(" + entityName + " entity) {\r\n");
        sbb.append("\t\tsuperMapper.removeById(entity);\r\n");
        sbb.append("\t}\r\n\r\n");

        sbb.append("\t@Override\r\n");
        sbb.append("\tpublic void deleteMulti" + entityName + "(List<Integer> ids, Class<" + entityName + "> t) {\r\n");
        sbb.append("\t\tsuperMapper.removeOfBatch(ids, t);\r\n");
        sbb.append("\t}\r\n");*/

        MyGenUtils.processAllRemains(markServiceImplList, sbb, tag, "serviceImpl");

        sbb.append("}");

        return sbb.toString();
    }

    /**
     * mapper
     * @return
     */
    private String process3() {

        StringBuilder sbb = new StringBuilder();

        sbb.append("package " + mapperPath + ";\r\n\r\n");

        sbb.append("import " + entityPath + "." + entityName + Home.entityNameSuffix + ";\r\n");
        sbb.append("import " + mixMapper + ";\r\n\r\n");//TODO

        //添加作者
        sbb.append("/**\r\n");
        sbb.append(" * Created by " + Home.author + " on " + MyDateUtils.convertDateToStr(new Date(), null) + "\r\n");
        sbb.append(" */\r\n");

        sbb.append("public interface " + entityName + "Mapper extends Mapper<" + entityName + Home.entityNameSuffix + "> {\r\n");

        MyGenUtils.processAllRemains(markMapperList, sbb, tag, "mapper");

        sbb.append("}");

        return sbb.toString();
    }



    private List<String> markServiceList;
    private List<String> markServiceImplList;
    private List<String> markMapperList;
    private String tag;//标记 mark


}
