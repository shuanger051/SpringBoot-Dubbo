package com.dubbo.provider.express;

import com.dubbo.api.entity.BeanExample;
import com.dubbo.provider.BaseTest;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import com.ql.util.express.Operator;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * QLExpress 框架是阿里淘宝对外提供的一个轻量型规则引擎，可以使用在积分中心中。
 */
public class QLExpressTest extends BaseTest {

    /**
     * 简单例子
     * @throws Exception
     */
    @Test
    public void quickStart() throws Exception{
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String,Object> context = new DefaultContext<String, Object>();
        context.put("a",1);
        context.put("b",2);
        context.put("c",3);

        //下面五个参数意义分别是： 表达式，上下文，errorList，是否缓存，是否输出日志
        Object result = runner.execute("a+b+c",context,null,true,false);
        System.out.println("a+b+c=" + result);
    }

    @Test
    public void quickStart1() throws Exception{
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String,Object> context = new DefaultContext<String, Object>();
        context.put("a",1);

        //下面五个参数意义分别是： 表达式，上下文，errorList，是否缓存，是否输出日志
        Object result = runner.execute("a==1",context,null,true,false);
        System.out.println("a+b+c=" + result);
    }


    /**
     * 表达式使用for循环语句
     * @throws Exception
     */
    @Test
    public void expressUseFor() throws Exception{

        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String,Object> context = new DefaultContext<String, Object>();
        String express = "n = 100;sum = 0 ;" +
                "for(i = 1;i < n ;i++){" +
                "sum = sum + i " +
                "}" +
                "return sum;";
        Object result = runner.execute(express,context,null,true,false);
        System.out.println("1到100的和是：" + result);
    }

    /**
     * 表达式使用三元表达式
     * @throws Exception
     */
    @Test
    public void ternaryOpr() throws Exception{
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String,Object> context = new DefaultContext<String,Object>();
        context.put("a",9);
        context.put("b",11);
        String express = "a > b ? a : b";
        Object result = runner.execute(express,context,null,true,false);
        System.out.println("a和b中较大的值是：" + result);

        String express1 = "a > b ? 'a' : 'b'";
        Object result1 = runner.execute(express1,context,null,true,false);
        System.out.println("a和b中谁的值比较大：" + result1);
    }

    /**
     * 使用Array数组
     * @throws Exception
     */
    @Test
    public void useArray() throws Exception{
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String,Object> context = new DefaultContext<>();
        String express = "arr = new int[3];" +
                "arr[0]=1;arr[1]=2;arr[2]=3;" +
                "sum = arr[0]+arr[1]+arr[2];" +
                "return sum;";
        Object result = runner.execute(express,context,null,true,false);
        System.out.println("SUM数组集合相加的值为：" + result);
    }

    /**
     * 使用List集合
     * @throws Exception
     */
    @Test
    public void useList() throws Exception{
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String,Object> context = new DefaultContext<>();
        String express = "list = new ArrayList();" +
                "list.add(1);list.add(2);list.add(3);" +
                "sum = 0;" +
                "for(int i = 0;i<list.size();i++){" +
                "   sum = sum  + list.get(i);" +
                "}" +
                "return sum;";
        Object result = runner.execute(express,context,null,true,false);
        System.out.println("List集合元素相加的值为：" + result);
    }

    /**
     * 使用Map集合
     * @throws Exception
     */
    @Test
    public void useMap() throws Exception{
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String,Object> context = new DefaultContext<>();
        String express = "map = new HashMap();" +
                "map.put('a',2);" +
                "map.put('b',2);" +
                "map.put('c',2);" +
                "sum = 0;" +
                "keySet = map.keySet();" +
                "keyArray = keySet.toArray();" +
                "for(int i = 0;i < keyArray.length;i++){" +
                "   sum = sum + map.get(keyArray[i]);" +
                "}" +
                "return sum;";
        Object result = runner.execute(express,context,null,true,false);
        System.out.println("Map 集合元素相加的值为：" + result);

    }

    /**
     * 使用NewList语法
     * @throws Exception
     */
    @Test
    public void useNewList() throws Exception{
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String,Object> context = new DefaultContext<>();
        String express = "list = NewList(1,2,3);return list.get(0) + list.get(1) + list.get(2);";
        Object result = runner.execute(express,context,null,true,false);
        System.out.println("List元素相加的值为：" + result);
    }

    /**
     * 使用NewMap语法
     * @throws Exception
     */
    @Test
    public void useNewMap() throws Exception{
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String,Object> context = new DefaultContext<>();
        String express = "list = NewList(1,2,3);return list.get(0) + list.get(1) + list.get(2);";
        Object result = runner.execute(express,context,null,true,false);
        System.out.println("List元素相加的值为：" + result);
    }

    /**
     * 使用Java bean
     * @throws Exception
     */
    @Test
    public void useJavaBean() throws Exception{
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String,Object> context = new DefaultContext<>();

        //qlExpress 只会引入java.util.* 和 java.lang.*
        String express = "import com.dubbo.provider.controller.QLExpressController;" +
                "controller = new QLExpressController();" +
                "controller.sayHelloForA();" +
                "controller.sayHelloForB();";

        runner.execute(express,context,null,true,false);

    }

    /**
     * 定义function 函数
     * @throws Exception
     */
    @Test
    public void useFunction() throws Exception{
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String,Object> context = new DefaultContext<>();
        String express = "function add(int a,int b){" +
                "   return a + b;" +
                "};" +
                "a = 2;b= 2;" +
                "return add(a,b);";

        Object result = runner.execute(express,context,null,true,false);
        System.out.println("Function Add运算结果为：" + result);

    }

    /**
     * 使用if，then，else
     * @throws Exception
     */
    @Test
    public void useIfThenElse() throws Exception{
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String,Object> context = new DefaultContext<>();

        runner.addOperatorWithAlias("如果","if",null);
        runner.addOperatorWithAlias("则","then",null);
        runner.addOperatorWithAlias("否则","else",null);
        runner.addOperatorWithAlias("返回","return",null);

        context.put("语文",100);
        context.put("数学",100);
        context.put("英语",100);

        String express = "如果 ((语文+数学+英语) > 270 ) 则 返回 1;否则 返回 0; ";

        Object result = runner.execute(express,context,null,true,false);
        System.out.println("运算结果为：" + result);

    }

    @Test
    public void useBindMethod() throws Exception{
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();

        runner.addFunctionOfClassMethod("取绝对值", Math.class.getName(), "abs", new String[]{"double"}, null);
       // runner.addFunctionOfClassMethod("转换为大写", BeanExample.class.getName(),"upper", new String[]{"String"}, null);

        runner.addFunctionOfServiceMethod("打印", System.out, "println", new String[]{"String"}, null);
        //runner.addFunctionOfServiceMethod("contains", new BeanExample(), "anyContains",new Class[]{String.class, String.class}, null);

        String exp = "取绝对值(-100);打印(\"你好吗？\");";
        runner.execute(exp, context, null, false, false);
    }

    @Test
    public void test_compile_script() throws Exception {
        String express = "int 平均分 = (语文+数学+英语+综合考试.科目2)/4.0;return 平均分";
        ExpressRunner runner = new ExpressRunner(true, true);
        String[] names = runner.getOutVarNames(express);
        for (String s : names) {
            System.out.println("var : " + s);
        }

        String[] functions = runner.getOutFunctionNames(express);
        for (String s : functions) {
            System.out.println("function : " + s);
        }
    }


    @Test
    public void test_multi_params_use_array() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        IExpressContext<String, Object> expressContext = new DefaultContext<String, Object>();
        runner.addFunctionOfServiceMethod("getTemplate", new BeanExample(), "getTemplate", new Class[]{Object[].class}, null);
        Object r = runner.execute("getTemplate([11,'22',33L,true])", expressContext, null, false, false);
        System.out.println(r);
    }

    @Test
    public void is_precise() throws Exception {
        ExpressRunner runner = new ExpressRunner(true, false);
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        //订单总价 = 单价 * 数量 + 首重价格 + （总重量 - 首重） * 续重单价
        context.put("单价", 1.25);
        context.put("数量", 100);
        context.put("首重价格", 125.25);
        context.put("总重量", 20.55);
        context.put("首重", 10.34);
        context.put("续重单价", 3.33);

        String express = "单价*数量+首重价格+(总重量-首重)*续重单价";
        Object result = runner.execute(express, context, null, true, false);
        System.out.println("totalPrice:" + result);
    }

    @Test
    public void testAop() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        IExpressContext<String,Object> context = new DefaultContext<String, Object>();

        runner.addClassMethod("size", java.util.List.class, new Operator() {
            @Override
            public Object executeInner(Object[] list) throws Exception {
                ArrayList arrayList = (ArrayList) list[0];
                System.out.println("拦截到List.size()方法");
                return arrayList.size();
            }
        });

        runner.addClassField("长度", java.util.List.class, new Operator() {
            @Override
            public Object executeInner(Object[] list) throws Exception {
                ArrayList arrayList = (ArrayList) list[0];
                System.out.println("拦截到List.长度 字段的计算");
                return arrayList.size();
            }
        });
        Object result = runner.execute("list=new ArrayList();list.add(1);list.add(2);list.add(3);return list.size();",context,null,false,false);
        System.out.println(result);
        result  = runner.execute("list=new ArrayList();list.add(1);list.add(2);list.add(3);return list.长度;",context,null,false,false);
        System.out.println(result);
    }

    @Test
    public void manJian() throws Exception{

        String totalPrice = "130.00";
        String expressStr = "value>=200-20;150<=value and value<=200-15;100<=value and value<=150-10";
        String[] expressArray = expressStr.split(";");
        for (String condition : expressArray) {
            if (!StringUtils.isEmpty(condition)) {
                String[] e = condition.split("-");
                Map param = new HashMap();
                param.put("value", totalPrice);
                String result = execute(e[0], param);
                if (result == "true") {
                    System.out.println("优惠金额为:" + e[1] + "元");
                    return;
                }
            }
        }

    }

    /**
     * 执行规则表达式
     * @param express
     * @param paramMap
     * @return
     */
    public static String execute(String express, Map<String, Object> paramMap) {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        paramMap.forEach((k,v)-> context.put(k, Double.valueOf(String.valueOf(v))));
        Object r = null;
        try {
            r = runner.execute(express, context, null, true, false);
        }catch (Exception e) {
            throw new RuntimeException("表达式解析错误！");
        }

        return String.valueOf(r);
    }


    @Test
    public void manJIA() throws Exception{

        String userLevel = "1";
        String expressStr = "userLevel==1+5;userLevel==2+10;userLevel==3+15";
        String[] expressArray = expressStr.split(";");
        for (String condition : expressArray) {
            if (!StringUtils.isEmpty(condition)) {
                String[] e = condition.split("\\+");
                Map<String,Object> param = new HashMap<>();
                param.put("userLevel", userLevel);
                String result = execute2(e[0], param);
                if (result == "true") {
                    System.out.println("优惠金额为:" + e[1] + "元");
                    return;
                }
            }
        }

    }

    /**
     * 执行规则表达式
     * @param express
     * @param paramMap
     * @return
     */
    public static String execute2(String express, Map<String, Object> paramMap) {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        paramMap.forEach((k,v)-> context.put(k, Integer.parseInt(String.valueOf(v))));
        Object r = null;
        try {
            r = runner.execute(express, context, null, true, false);
        }catch (Exception e) {
            throw new RuntimeException("表达式解析错误！");
        }

        return String.valueOf(r);
    }


}
