package com.example.lib;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by yh on 2019/11/18
 **/
public class ReadServiceLanguages {
    /**
     * 源文件路径 --文件夹层级，最好是统一放到一个文件下面
     * 例如：C:\Users\Administrator\Downloads\stringsdownload_20181229101711
     */
    public static final String BASE_SRC_PATH = "E:\\kotlin\\class\\lib\\src\\main\\java\\com\\example\\lib\\tt";

    /**
     * 目标项目路径----文件夹层级，最好是统一放到一个文件下面
     * 例如:D:\AndroidStudioWorkSpace\dev\new\trunk\app\src\main\res
     */
    public static final String BASE_DES_PATH = "E:\\google_version\\butt1\\ButtWorkout\\app\\src\\main\\res";


    public static String languagesGradle = "\"en\", \"de\", \"es\", \"fr\", \"it\", \"nl\", \"pt\", \"ru\", \"sv\",\"pl\",\"ja\",\"ko\",\"tr\",\"da\",\"ar\",\"in-rID\",\"zh-rCN\",\"zh-rTW\"";


    private static final String SERVICE_CONFIG = "_strings.xml";
    private static final String APPFILE_CONFIG = "values";
    /**
     * 项目中涉及到的多语言配置
     */
    public static String[] languages;


    /**
     * 服务器名称 与 本地名称的映射
     * <服务器名称,本地名称>
     */
    private static Map<String,String> nameMap = new HashMap<>();
    private static Map<String,FileConfig> configMap = new HashMap<>();
    private String sourcePath;
    private String targetPath;

    public ReadServiceLanguages(String soucePath,String targetPath,String languagesGradle){
        this.sourcePath = soucePath;
        this.targetPath = targetPath;
        init(languagesGradle);
    }


    private void init(String languagesGradle){
        languages = languagesGradle.split(",");
        for (int i =0 ; i < languages.length ; i++){
            languages[i] = languages[i].replace("\"","").replace(" ","");
        }

        configMap.put("en",new FileConfig("en","en",""));
        configMap.put("hi",new FileConfig("hi","hi","hi"));
        configMap.put("de",new FileConfig("de","de","de"));
        configMap.put("es",new FileConfig("es","es","es"));
        configMap.put("fr",new FileConfig("fr","fr","fr"));
        configMap.put("fa",new FileConfig("fa","fa","fa"));
        configMap.put("it",new FileConfig("it","it","it"));
        configMap.put("nl",new FileConfig("nl","nl","nl"));
        configMap.put("pt",new FileConfig("pt","pt_br","pt"));
        configMap.put("pt-rBR",new FileConfig("pt-rBR","pt_br","pt-rBR"));
        configMap.put("ru",new FileConfig("ru","ru","ru"));
        configMap.put("sv",new FileConfig("sv","sv","sv"));
        configMap.put("pl",new FileConfig("pl","pl","pl"));
        configMap.put("ja",new FileConfig("ja","ja","ja"));
        configMap.put("ko",new FileConfig("ko","ko","ko"));
        configMap.put("tr",new FileConfig("tr","tr","tr"));
        configMap.put("da",new FileConfig("da","da","da"));
        configMap.put("ar",new FileConfig("ar","ar","ar"));
        configMap.put("my",new FileConfig("my","my","my"));
        configMap.put("uk",new FileConfig("uk","uk","uk"));
        configMap.put("ur",new FileConfig("ur","ur","ur"));
        configMap.put("vi",new FileConfig("vi","vi","vi"));
        configMap.put("in-rID",new FileConfig("in-rID","in_ID","in-rID"));
        configMap.put("in",new FileConfig("in","in_ID","in"));
        configMap.put("zh-rCN",new FileConfig("zh-rCN","zh_CN","zh-rCN"));
        configMap.put("zh-rTW",new FileConfig("zh-rTW","zh_TW","zh-rTW"));
        configMap.put("es-rMX",new FileConfig("es-rMX","es-mx","es-rMX"));





    }

    private boolean check(){
        boolean isCheck = true;
        for (String languages : languages){

            System.out.println("---languages:" + languages);

            FileConfig fileConfig = configMap.get(languages);


            File serviceFile = new File(sourcePath, fileConfig.serviceFileName + SERVICE_CONFIG);
            if (!serviceFile.exists()) {
                System.out.println("---复制服务器文件路径不存在:" + serviceFile.getPath());
                isCheck = false;
            }

            //判断当前父目录是否存在
            File file = new File(targetPath, APPFILE_CONFIG + ("".equals(fileConfig.appFileName) ? "" : "-") + fileConfig.appFileName);
            if (!file.exists()) {
                //文件不存在--跳过，正式的时候可以跳过
                System.out.println("---app文件路径不存在:" + serviceFile.getPath());
                isCheck = false;
            }
        }
        return isCheck;
    }




    public  void startCopyFile() {
        System.out.println("---待添加-->" + languages.length);
        System.out.println("---待添加-->" + languages.toString());

        if(!check()){
            return;
        }

        for (String languages : languages){

            FileConfig fileConfig = configMap.get(languages);

            File serviceFile = new File(sourcePath, fileConfig.serviceFileName + SERVICE_CONFIG);
            if (serviceFile.exists()) {
                System.out.println("---复制服务器文件路径:" + serviceFile.getPath());
            }

            String serviceSrcAbsoultPath = serviceFile.getPath();
            //判断当前父目录是否存在
            File file = new File(targetPath, APPFILE_CONFIG + ("".equals(fileConfig.appFileName) ? "" : "-") + fileConfig.appFileName);
            if (!file.exists()) {
                //文件不存在--跳过，正式的时候可以跳过
                System.out.println("---app文件路径不存在:" + serviceFile.getPath());
            } else {
                File ff = new File(file.getPath(), "strings.xml");
                if (!ff.exists()) {
                    boolean f = false;
                    try {
                        f = ff.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (f) {
                        System.out.println("----创建>" + ff.getPath() + " 文件成功");
                        //appendFileContent(desAoultPath, stringBuilder.toString());
                        fileCopy_channel(serviceSrcAbsoultPath, ff.getPath());

                    } else {
                        System.out.println("----创建>" + ff.getPath() + " 文件失败");
                    }
                } else {
                    //获取xml文件内容
                    appendToLastNode(ff.getPath(), parseXmlToList(serviceSrcAbsoultPath));
                }

            }

        }

    }

    /**
     * 获取当前文件夹下面的所有文件
     *
     * @param parentDirectory
     * @return
     */
    private  String[] getAbsoutFilePath(String parentDirectory) {
        File file = new File(parentDirectory);
        if (file.exists() && file.isDirectory()) {
            return file.list();
        }
        return null;
    }




    /**
     * @param fileName 文件路径
     * @param content  写入文件的内容
     */
    public  void appendFileContent(String fileName, String content) {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            //将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
            System.out.println("----复制文件内容成功----");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("----复制文件出现异常----");
        }
    }


    /**
     * @param xmlPath
     */
    public  void appendToLastNode(String xmlPath, ArrayList<LanguageVo> items) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(false);
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document xmldoc = db.parse(xmlPath);


            Element root = xmldoc.getDocumentElement();

            int num = 0;

            if (items != null) {
                for (LanguageVo item : items) {
                    Element son = xmldoc.createElement("string");
                    son.setAttribute("name", item.key);
                    son.setTextContent(item.value);
                    num++;
                    root.appendChild(son);
                }
            }

            // 保存
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer former = factory.newTransformer();
            //设置换行
            former.setOutputProperty(OutputKeys.INDENT, "yes");
            former.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlPath)));
            System.out.println("----复制文件内容成功----  num:" + num);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("----复制文件内容异常----");
        }
    }


    private  ArrayList<LanguageVo> parseXmlToList(String path) {
        ArrayList<LanguageVo> result = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            File file = new File(path);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(path));
            NodeList nl = doc.getElementsByTagName("string");
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                LanguageVo vo = new LanguageVo();
                vo.key = node.getAttributes().getNamedItem("name").getNodeValue();
                vo.value = node.getTextContent();
                result.add(vo);
            }
            NodeList nl1 = doc.getElementsByTagName("string-array");
            for (int i = 0; i < nl1.getLength(); i++) {
                Node node = nl1.item(i);
                LanguageVo vo = new LanguageVo();
                vo.key = node.getAttributes().getNamedItem("name").getNodeValue();
                NodeList children = node.getChildNodes();
                ArrayList<LanguageVo> childList = new ArrayList<>();
                for (int j = 0; j < children.getLength(); j++) {
                    Node childNode = children.item(j);
                    LanguageVo childVo = new LanguageVo();
                    if ("item".equals(childNode.getNodeName())) {
                        String content = childNode.getTextContent();
                        if (content != null && !"".equals(content)) {
                            childVo.value = content;
                        }
                        childList.add(childVo);
                    }
                }
                vo.subItems = childList;
                result.add(vo);
            }


            //将服务器端的名称 替换为 本地的名称
            for (LanguageVo languageVo :result){
                if(nameMap.containsKey(languageVo.key)){
                    languageVo.key = nameMap.get(languageVo.key);
                }
            }


            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解析xml 转成string
     *
     * @param path
     * @return StringBuilder
     */

    private  StringBuilder parseXmlToString(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            File file = new File(path);
            if (!file.exists()) {
            }
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(path));

            NodeList nl = doc.getElementsByTagName("string");

            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                node.getAttributes().getNamedItem("name").getNodeValue();
                node.getTextContent();

                StringWriter writer = new StringWriter();
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.transform(new DOMSource(node), new StreamResult(writer));
                //设置换行
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                String str = writer.toString();
                str = str.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
                stringBuilder.append(str);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }

    public  void writeFile(StringBuilder sb, String destPath) {
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(new File(destPath));
            byte[] bt = sb.toString().getBytes();
            output.write(bt, 0, bt.length);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  void fileCopy_channel(String srcPath, String desPath) {
        FileChannel input = null;
        FileChannel output = null;
        try {
            input = new FileInputStream(srcPath).getChannel();
            output = new FileOutputStream(desPath).getChannel();
            output.transferFrom(input, 0, input.size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
