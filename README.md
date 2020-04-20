# Server搭建
基于Axis1搭建的服务端

## 1、Maven导包
```xml
<dependency>
    <groupId>org.apache.axis</groupId>
    <artifactId>axis</artifactId>
    <version>1.4</version>
</dependency>
<dependency>
    <groupId>axis</groupId>
    <artifactId>axis-jaxrpc</artifactId>
    <version>1.4</version>
</dependency>
<dependency>
    <groupId>axis</groupId>
    <artifactId>axis-wsdl4j</artifactId>
    <version>1.5.1</version>
</dependency>
<dependency>
    <groupId>commons-discovery</groupId>
    <artifactId>commons-discovery</artifactId>
    <version>0.2</version>
</dependency>
<dependency>
    <groupId>javax.mail</groupId>
    <artifactId>mail</artifactId>
    <version>1.4</version>
</dependency>
```

## 2、在web.xml中配置axis监听器
```xml
<servlet>
    <servlet-name>AxisServlet</servlet-name>
    <servlet-class>org.apache.axis.transport.http.AxisServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>AxisServlet</servlet-name>
    <url-pattern>/v1/*</url-pattern>
</servlet-mapping>
```

## 3、创建前置接收请求的类
```Java
@Slf4j
public class Dispatcher {
    // 简单类型调用
    public String sum(String num1, String num2) {
        log.info("参数1:[{}]", num1);
        log.info("参数2:[{}]", num2);

        return Integer.parseInt(num1) + Integer.parseInt(num2) + "";
    }

    // 复杂类型调用
    public CommonDTO acceptInfo(CommonDTO commonDTO) {
        log.info(JSON.toJSONString(commonDTO, true));

        commonDTO.setServerFlag(true);
        return commonDTO;
    }
}
```

## 4、创建对象传参的DTO(server和client共用)
```Java
@Data
public class CommonDTO {
    private String company;
    private Integer type;
    private List<String> managerList;
    private Boolean serverFlag; // 是否为服务端
}
```

## 5、创建配置文件server-config.wsdd

```xml
<?xml version="1.0" encoding="UTF-8"?>
<deployment xmlns="http://xml.apache.org/axis/wsdd/" xmlns:java="http://xml.apache.org/axis/wsdd/providers/java">
    <handler name="URLMapper" type="java:org.apache.axis.handlers.http.URLMapper"/>
    <service name="call" provider="java:RPC">
        <!--- 允许调用的方法 --->
        <parameter name="allowedMethods" value="*"/>
        <!--- 前置接收请求的类 --->
        <parameter name="className" value="com.zxb.web.Dispatcher"/>
        <!--- 传递对象 --->
        <beanMapping qname="myNS:common" xmlns:myNS="urn:commonDTO" languageSpecificType="java:com.zxb.domain.CommonDTO"/>
    </service>

    <transport name="http">
        <requestFlow>
            <handler type="URLMapper"/>
        </requestFlow>
    </transport>
</deployment>
```

## 6、启动服务
1. url规则：`http://ip:port/contextPath/url-pattern/serviceName?wsdl`
2. 打开：`http://127.0.0.1:8080/axisServer/v1/call?wsdl` 可以看到xml页面即为成功。

## 说明

1. 前置接收请求的类每次调用都会通过反射实例化一次。
2. 使用对象传参，在反序列化时给对象设置属性值是通过拼接set方法来实现，要求set方法无返回值，不要使用lombok的@Accessors注解。
3. 代码地址：
    * github：https://github.com/senlinmu1008/axis1Server
    * gitee：https://gitee.com/ppbin/axis1Server