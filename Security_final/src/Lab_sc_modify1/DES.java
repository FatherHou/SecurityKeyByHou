/**
 * 
 */
package Lab_sc_modify1;

/**
 * @author hou
 *
 */
public class DES {
	
	//初始置换 Initial replacement
    private int[] IP={58,50,42,34,26,18,10,2,
                     60,52,44,36,28,20,12,4,
                     62,54,46,38,30,22,14,6,
                     64,56,48,40,32,24,16,8,
                     57,49,41,33,25,17,9,1,
                     59,51,43,35,27,19,11,3,
                     61,53,45,37,29,21,13,5,
                     63,55,47,39,31,23,15,7};
    //逆初始置换 Inverse initial replacement
    private int[] IP_1={40,8,48,16,56,24,64,32,
                       39,7,47,15,55,23,63,31,
                       38,6,46,14,54,22,62,30,
                       37,5,45,13,53,21,61,29,
                       36,4,44,12,52,20,60,28,
                       35,3,43,11,51,19,59,27,
                       34,2,42,10,50,18,58,26,
                       33,1,41,9,49,17,57,25};
    //E扩展  E extension
    private int[] E={32,1,2,3,4,5,
                      4,5,6,7,8,9,
                     8,9,10,11,12,13,
                     12,13,14,15,16,17,
                     16,17,18,19,20,21,
                     20,21,22,23,24,25,
                     24,25,26,27,28,29,
                     28,29,30,31,32,1};
    //P置换 P replacement
    private int[] P={16,7,20,21,29,12,28,17,
                      1,15,23,26,5,18,31,10,
                      2,8,24,14,32,27,3,9,
                      19,13,30,6,22,11,4,25};
    //S box
    private static final int[][][] S = {
            {
                    { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
                    { 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
                    { 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
                    { 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } },
            { 
                    { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
                    { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
                    { 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
                    { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 } },
            { 
                    { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
                    { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
                    { 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
                    { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 } },
            { 
                    { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
                    { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
                    { 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
                    { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 } },
            { 
                    { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
                    { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
                    { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
                    { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 } },
            { 
                    { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
                    { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
                    { 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
                    { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 } },
            { 
                    { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
                    { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
                    { 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
                    { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 } },
            { 
                    { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
                    { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
                    { 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
                    { 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } }
    };
    //Data sheet 1
    private int[] Data={57,49,41,33,25,17,9,
                       1,58,50,42,34,26,18,
                       10,2,59,51,43,35,27,
                        19,11,3,60,52,44,36,
                       63,55,47,39,31,23,15,
                       7,62,54,46,38,30,22,
                       14,6,61,53,45,37,29,
                       21,13,5,28,20,12,4};
    //Data sheet 2
    private int[] Data2={14,17,11,24,1,5,3,28,
                       15,6,21,10,23,19,12,4,
                       26,8,16,7,27,20,13,2,
                       41,52,31,37,47,55,30,40,
                       51,45,33,48,44,49,39,56,
                       34,53,46,42,50,36,29,32};
    //Schedule of Left Shifts
    private int[] LFT={1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};//16
    private int origin_length;	//origin = plaintext
    /**16个子密钥**/
    private int[][] sub_key=new int[16][48];
    
	/*
	 * @author hou
	 * Split the packet and calculate the result of the encryption or decryption
     * @param p
     * @param flag
     * @param p_origin_length
     * @return result
     */
    public byte[] split(byte[] p ,int flag,int p_origin_length){
        origin_length=p.length;
        int g_num;
        int r_num;
        g_num=origin_length/8;
//        System.out.println(g_num);
        r_num=8-(origin_length-g_num*8);	//8不填充
//        System.out.println(r_num);
        byte[] p_padding;
        /****填充********/
        if (r_num<8){	//当小于8的时候,origin_length!=8的倍数
            p_padding=new byte[origin_length+r_num];	//origin_length+r_num=8
            System.arraycopy(p,0,p_padding,0,origin_length);	//将原文复制到p_padding中
            for(int i=0;i<r_num;i++){	//因为origin_length<8,所以需要将空值的位置赋值
                p_padding[origin_length+i]=(byte)r_num;	//空闲位置都设置为r_num
            }
        }else{	//大于8时候，origin_length>=8
            p_padding=p;	//将原文都赋值给p_padding
        }
//        System.out.println(p_padding.length);
        g_num=p_padding.length/8;	//此时g_num>=1,为8的倍数
        byte[] f_p=new byte[8];
        byte[] result_data=new byte[p_padding.length];
//        for(int j=0;j<p_padding.length;j++)
//        	System.out.println(p_padding[j]);
        for(int i=0;i<g_num;i++){
            System.arraycopy(p_padding,i*8,f_p,0,8);	//根据原文数量来复制处理,分块加密
//            for(int j=0;j<f_p.length;j++)
//            	System.out.println(f_p[j]);
            System.arraycopy(descryUnit(f_p,sub_key,flag),0,result_data,i*8,8);
        }
        if (flag==0){	//解密
            byte[] p_result_data=new byte[p_origin_length];
            System.arraycopy(result_data,0,p_result_data,0,p_origin_length);
//            for(int i=0;i<p_result_data.length;i++){
//            	System.out.println(p_result_data[i]);
//            }
            return  p_result_data;
        }
//        for(int i=0;i<result_data.length;i++){
//        	System.out.println(result_data[i]);
//        }
        return result_data;
    }
    
	/*
	 * @author hou
	 * Encrypt each unit
     * @param p
     * @param k subkey
     * @param flag encrypte or decrypte
     * @return encryption
     */
    public byte[] descryUnit(byte[] p,int k[][],int flag){
        int[] p_bit=new int[64];
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<8;i++){
            String p_b=Integer.toBinaryString(p[i]&0xff);	//转二进制
//            System.out.println(p_b);
            while (p_b.length()%8!=0){	
                p_b="0"+p_b;
            }
            stringBuilder.append(p_b);
        }
        String p_str=stringBuilder.toString();
        for(int i=0;i<64;i++){
            int p_t=Integer.valueOf(p_str.charAt(i));	//对每个字符转换为int
            if(p_t==48){
                p_t=0;
            }else if(p_t==49){
                p_t=1;
            }else{
                System.out.println("To bit error!");
            }
            p_bit[i]=p_t;
        }
        /***IP置换,初始的置换***/
        int [] p_IP=new int[64];
        for (int i=0;i<64;i++){
            p_IP[i]=p_bit[IP[i]-1];	//置换后的数组
        }
        if (flag == 1) { // 加密
            for (int i = 0; i < 16; i++) {
                L(p_IP, i, flag, k[i]);	//k[i]是对应字符所对应的子密钥
            }
        } else if (flag == 0) { // 解密
            for (int i = 15; i > -1; i--) {
                L(p_IP, i, flag, k[i]);
            }
        }
        int[] c=new int[64];
        for(int i=0;i<IP_1.length;i++){
            c[i]=p_IP[IP_1[i]-1];	//逆置换,逆置换正好是初始置换的逆运算.例如,第1位经过初始置换后,处于第40位,而通过逆置换,又将第40位换回到第1位
        }
        byte[] c_byte=new byte[8];
        for(int i=0;i<8;i++){	//逆置换后的输出
            c_byte[i]=(byte) ((c[8*i]<<7)+(c[8*i+1]<<6)+(c[8*i+2]<<5)+(c[8*i+3]<<4)+(c[8*i+4]<<3)+(c[8*i+5]<<2)+(c[8*i+6]<<1)+(c[8*i+7]));
        }
        return c_byte;
    }
    
	/*
	 * @author hou
	 * Perform 16 rounds of XOR operation
     * @param M
     * @param times
     * @param flag
     * @param keyarray
     * @return XOR result
     */
    public void L(int[] M, int times, int flag, int[] keyarray){	//times代表迭代的次数
        int[] L0=new int[32];
        int[] R0=new int[32];
        int[] L1=new int[32];
        int[] R1=new int[32];
        int[] f=new int[32];
        System.arraycopy(M,0,L0,0,32);
        System.arraycopy(M,32,R0,0,32);	//分为两块
        L1=R0;	//进行循环的规则,L1=R0以此类推,16次
        f=fFuction(R0,keyarray);	//f函数
        for(int j=0;j<32;j++){
                R1[j]=L0[j]^f[j];	//^为异或
                if (((flag == 0) && (times == 0)) || ((flag == 1) && (times == 15))) {		//加密的最后一次迭代或者解密的最后一次迭代
                    M[j] = R1[j];	//最后一次操作和之前的15轮不同
                    M[j + 32] = L1[j];
                }
                else {
                    M[j] = L1[j];
                    M[j + 32] = R1[j];
                }
        }
    }
    
	/*
	 * @author hou
	 * Calculation of the f function, including the E box S box P box
     * @param r_content
     * @param key
     * @return out of F
     */
    public int[] fFuction(int [] r_content,int [] key){
        int[] result=new int[32];
        int[] e_k=new int[48];
        for(int i=0;i<E.length;i++){
            e_k[i]=r_content[E[i]-1]^key[i];	//E盒扩展变换
        }
        /********S盒替换:由48位变32位，现分割e_k，然后再进行替换*********/
        int[][] s=new int[8][6];
        int[]s_after=new int[32];	//存储S盒替换结果
        for(int i=0;i<8;i++){
            System.arraycopy(e_k,i*6,s[i],0,6);	//输入的高低两位做为行数H，中间四位做为列数L，在S-BOX中查找第H行L列对应的数据
            int r=(s[i][0]<<1)+ s[i][5];	//横坐标,左移1位来表示为2的位置(二进制)
            int c=(s[i][1]<<3)+(s[i][2]<<2)+(s[i][3]<<1)+s[i][4];	//纵坐标,同理左移相应位数来表示二进制的位置,如8421
            String str=Integer.toBinaryString(S[i][r][c]);
            while (str.length()<4){
                str="0"+str;
            }
            for(int j=0;j<4;j++){
                int p=Integer.valueOf(str.charAt(j));
                if(p==48){
                    p=0;
                }else if(p==49){
                    p=1;
                }else{
                    System.out.println("To bit error!");
                }
                s_after[4*i+j]=p;
            }

        }
        /******S盒替换结束*******/
        /****P盒替代****/
        for(int i=0;i<P.length;i++){	//等价于正置换
            result[i]=s_after[P[i]-1];
        }
        return result;
    }
    
	/*
	 * @author hou
	 * Generate subkey
     * @param key
     * @return subkey
     */
    public void generateKeys(String key){
        while (key.length()<8){
            key=key+key;
        }
        key=key.substring(0,8);
        byte[] keys=key.getBytes();
        int[] k_bit=new int[64];
        //取位值
        for(int i=0;i<8;i++){	//8*8位初始密钥
//        	System.out.println(keys[i]);
            String k_str=Integer.toBinaryString(keys[i]&0xff);	//转二进制8位,0xff保证补码的一致性
//          System.out.println(k_str);
            if(k_str.length()<8){
                for(int t=0;t<8-k_str.length();t++){
                    k_str="0"+k_str;
                }
            }
//            System.out.println(k_str);
            for(int j=0;j<8;j++){//
                int p=Integer.valueOf(k_str.charAt(j));	//0和1的ASC码
//                System.out.println(k_str.charAt(j));
                if(p==48){
                    p=0;
                }else if(p==49){
                    p=1;
                }else{
                    System.out.println("To bit error!");
                }
                k_bit[i*8+j]=p;	//给k_bit初始密钥数组赋值
            }
        }
        //k_bit是初始的64位长密钥，下一步开始进行替换
        /***********PC-1压缩****************/
        int [] k_new_bit=new int[56];
        for(int i=0;i<Data.length;i++){
            k_new_bit[i]=k_bit[Data[i]-1];	//这个减1注意点,因为位数没有0,是从1开始,所以还需要减1
        }
        /**************************/
        int[] c0=new int[28];
        int[] d0=new int[28];
        System.arraycopy(k_new_bit,0,c0,0,28);	//将k_new_bit中从索引为0,即第0个数字开始拷贝28个数字,复制到c0里索引为0的数字位置
        System.arraycopy(k_new_bit,28,d0,0,28);
        for(int i=0;i<16;i++){
            int[] c1=new int[28];
            int[] d1=new int[28];
            if(LFT[i]==1){		//对应需要左移的位数
                System.arraycopy(c0,1,c1,0,27);
                c1[27]=c0[0];	//这两步操作是左移1位
                System.arraycopy(d0,1,d1,0,27);
                d1[27]=d0[0];
            }else if(LFT[i]==2){
                System.arraycopy(c0,2,c1,0,26);
                c1[26]=c0[0];
                c1[27]=c0[1];	//这三步操作是左移1位

                System.arraycopy(d0,2,d1,0,26);
                d1[26]=d0[0];
                d1[27]=d0[1];
            }else{
                System.out.println("LFT Error!");
            }
            int[] tmp=new int[56];
            System.arraycopy(c1,0,tmp,0,28);
            System.arraycopy(d1,0,tmp,28,28);	//这两步操作是将左移后的两个部分合并
            for (int j=0;j<Data2.length;j++){		//PC2压缩置换
                sub_key[i][j]= tmp[Data2[j]-1];	//同第一个的置换
            }
            c0=c1;
            d0=d1;	//再将c1,d1作为下一轮的子密钥移位
        }

    }
}
