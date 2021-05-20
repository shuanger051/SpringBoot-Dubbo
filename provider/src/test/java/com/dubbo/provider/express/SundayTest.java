package com.dubbo.provider.express;

import org.junit.Test;

public class SundayTest {

    @Test
    public void testSunday(){
        String s="123";
        String p="123";
        sunday(s,p);
    }

    //注意每次都是从后向前
    public int contains(char[] str,char ch){
        for(int i=str.length-1;i>=0;i--){
            if(str[i]==ch){
                return i;
            }
        }
        return -1;
    }

    /**
     　　* 匹配字符串
     　　* @param s 目标字符串
     　　* @param p 需要匹配的字符串
     　　*/

    public void sunday(String s,String p){
        char[] sarray = s.toCharArray();
        char[] parray = p.toCharArray();
        int slen=s.length();
        int plen=p.length();
        int i=0,j=0;

        while(i<=slen-plen+j){//这句话控制索引i,j的范围
            if(sarray[i]!=parray[j]){//假如主串的sarry[i]与模式串的parray[j]不相等
                if(i==slen-plen+j){
                    break;//假如主串的sarry[i]与模式串的parray[j]不相等,并且i=slen-plen+j,说明这已经
                    //是在和主串中最后可能相等的字符段比较了,并且不相等,说明后面就再也没有相等的了,所以
                    //跳出循环,结束匹配
                }
            //假如是主串的中间字段与模式串匹配，且结果不匹配
            //则就从模式串的最后面开始,(注意是从后向前)向前遍历,找出模式串的后一位在对应的母串的字符是否在子串中存在
                int pos=contains(parray, sarray[i+plen-j]);
                if(pos==-1){//表示不存在
                    i=i+plen+1-j;
                    j=0;
                }else{
                    i=i+plen-pos-j;
                    j=0;
                }
            }else{//假如主串的sarry[i]与模式串的parray[j]相等,则继续下面的操作
                if(j==plen-1){//判断模式串的索引j是不是已经到达模式串的最后位置，
                    //j==plen-1证明在主串中已经找到一个模式串的位置,
                    //且目前主串尾部的索引为i,主串首部的索引为i-j,打印模式串匹配的第一个位置
                    System.out.println("the start pos is "+(i-j)+" the end pos is "+i);
                    //然后主串右移一个位置,再和模式串的首字符比较,从而寻找下一个匹配的位置
                    i=i-j+1;
                    j=0;
                }else{
                    //假如模式串的索引j!=plen-1,说明模式串还没有匹配完,则i++,j++继续匹配,
                    i++;
                    j++;
                }
            }
        }
    }


}