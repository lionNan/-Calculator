package com.example.counter;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class counter extends AppCompatActivity {
    //0-9是个按键
    private Button[] btn = new Button[10];
    //    显示器显示输出结果
    private EditText input;
    //显示器下方的记忆器，用于记录上一次计算结果
    private TextView mem;
    //三角计算时标志显示，角度还是弧度
    private TextView _drg;
    //小提示，用于加强人机交互的弱检测，提示
    private TextView tip;
    private Button
            div, mul, sub, add, equal, sin, cos, tan, log, ln, sqrt, square, factorial, bksp, left, right, dot, exit, drg, mc, c;
    //保存原来的算式样i，为了输出好看，因计算时样子被改变
    public String str_old;
    //变换样子后的式子
    public String str_new;
    //输入控制，true为重新输入，false为接着输入
    public boolean vbrgin = true;
    //控制DRG按键，true为角度，false为弧度
    public boolean drg_flag = true;
    //pi值为3.14
    public double pi = 4 * Math.atan(1);
    //true表示正确，可以继续输入；false表示有误，输入被锁定
    public boolean tip_lock = true;
    //判断是否按=之后的输入，true表示输入在=之前，false反之
    public boolean equals_flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        //获取界面元素
        input = (EditText) findViewById(R.id.input);
        mem = (TextView) findViewById(R.id.mem);
        tip = (TextView) findViewById(R.id.tip);
        _drg = (TextView) findViewById(R.id._drg);
        btn[0] = (Button) findViewById(R.id.zero);
        btn[1] = (Button) findViewById(R.id.one);
        btn[2] = (Button) findViewById(R.id.two);
        btn[3] = (Button) findViewById(R.id.three);
        btn[4] = (Button) findViewById(R.id.four);
        btn[5] = (Button) findViewById(R.id.five);
        btn[6] = (Button) findViewById(R.id.six);
        btn[7] = (Button) findViewById(R.id.seven);
        btn[8] = (Button) findViewById(R.id.eright);
        btn[9] = (Button) findViewById(R.id.nine);
        mul = (Button) findViewById(R.id.mul);
        sub = (Button) findViewById(R.id.sub);
        add = (Button) findViewById(R.id.add);
        equal = (Button) findViewById(R.id.equal);
        sin = (Button) findViewById(R.id.sin);
        cos = (Button) findViewById(R.id.cos);
        tan = (Button) findViewById(R.id.tan);
        log = (Button) findViewById(R.id.log);
        ln = (Button) findViewById(R.id.ln);
        sqrt = (Button) findViewById(R.id.sqrt);
        square = (Button) findViewById(R.id.square);
        factorial = (Button) findViewById(R.id.factorial);
        bksp = (Button) findViewById(R.id.bksp);
        left = (Button) findViewById(R.id.left);
        right = (Button) findViewById(R.id.right);
        dot = (Button) findViewById(R.id.dot);
        exit = (Button) findViewById(R.id.exit);
        drg = (Button) findViewById(R.id.drg);
        mc = (Button) findViewById(R.id.c);
        //为数字绑定监听器

        for (int i = 0; i < 10; ++i) {
            btn[i].setOnClickListener(actionPerformed);
        }
        //为+-*/等设置监听器
        div.setOnClickListener(actionPerformed);
        mul.setOnClickListener(actionPerformed);
        sub.setOnClickListener(actionPerformed);
        add.setOnClickListener(actionPerformed);
        equal.setOnClickListener(actionPerformed);
        sin.setOnClickListener(actionPerformed);
        cos.setOnClickListener(actionPerformed);
        tan.setOnClickListener(actionPerformed);
        log.setOnClickListener(actionPerformed);
        ln.setOnClickListener(actionPerformed);
        sqrt.setOnClickListener(actionPerformed);
        square.setOnClickListener(actionPerformed);
        factorial.setOnClickListener(actionPerformed);
        bksp.setOnClickListener(actionPerformed);
        left.setOnClickListener(actionPerformed);
        right.setOnClickListener(actionPerformed);
        dot.setOnClickListener(actionPerformed);
        exit.setOnClickListener(actionPerformed);
        drg.setOnClickListener(actionPerformed);
        mc.setOnClickListener(actionPerformed);
        c.setOnClickListener(actionPerformed);
    }

    /**
     * 键盘命令捕捉
     */
    //命令缓存，用于检测输入的合法性
    String[] Tipcommand = new String[500];
    //Tipcommand的指针
    int tip_i = 0;
    private View.OnClickListener actionPerformed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //按键上的命令获取
            String command = ((Button) v).getText().toString();
            //显示器上的字符串
            String str = input.getText().toString();
            //检测输入是否合法
            if (equals_flag == false && "0123456789.()sincostanlnlogn!+-*/^√".indexOf(command) != -1) {
                //检测显示器上的字符串是否合法
                if (right(str)) {
                    if ("-*/^√".indexOf(command) != -1) {
                        for (int i = 0; i < str.length(); i++) {
                            Tipcommand[tip_i] = String.valueOf(str.charAt(i));
                            tip_i++;
                        }
                        vbrgin = false;
                    }
                } else {
                    input.setText(0);
                    vbrgin = true;
                    tip_i = 0;
                    tip_lock = true;
                    tip.setText("欢迎使用!");
                }
                equals_flag = true;
            }
            if (tip_i > 0)
                TipCheecker(Tipcommand[tip_i - 1], command);
            else if (tip_i == 0) {
                TipCheecker("#", command);
            }
            if ("0123456789.()sincostanlnlogn!-*/^√".indexOf(command) != -1 && tip_lock) {
                Tipcommand[tip_i] = command;
                tip_i++;
            }
            //若输入正确。则将输入信息显示到显示屏上
            if ("0123456789.()sincostanlnlogn!+-*/^√".indexOf(command) != -1 && tip_lock) {
                //一共25个键
                print(command);
                //如果单击了DRG，则切换当前弧度角度制，并将切换后的结果显示到按键上方
            } else if (command.compareTo("DRG") == 0 && tip_lock) {
                if (drg_flag == true) {
                    drg_flag = false;
                    _drg.setText("RAD");
                } else {
                    drg_flag = true;
                    _drg.setText("DEG");
                }
                //如果输入是退格键，并且是在按=之前
            } else if (command.compareTo("Bksp") == 0 && equals_flag) {
                //一次删除三个字符
                if (TTO(str) == 3) {
                    if (str.length() > 3) {
                        input.setText(str.substring(0, str.length() - 3));
                    } else if (str.length() == 3) {
                        input.setText("0");
                        vbrgin = true;
                        tip_i = 0;
                        tip.setText("欢迎使用");
                    }
                    //依次删除两个字符
                } else if (TTO(str) == 2) {
                    if (str.length() > 2)
                        input.setText(str.substring(0, str.length() - 2));
                    else if (str.length() == 2) {
                        input.setText(0);
                        vbrgin = true;
                        tip_i = 0;
                        tip.setText("欢迎使用！");
                    }
                    //依次删除一个一个字符u
                } else if (TTO(str) == 1) {
                    //若之前输入的字符串合法，则删除一个字符
                        if (right(str)){
                            if (str.length()>1)
                                input.setText(str.substring(0,str.length()-1));
                            else if (str.length()==1){
                                input.setText("0");
                                vbrgin = true;
                                tip_i=0;
                                tip.setText("欢迎使用!");
                            }
                            //若之前输入的字符串不合法则删除全部字符
                        }else {
                            input.setText("0");
                            vbrgin=true;
                            tip_i=0;
                            tip.setText("欢迎使用！");
                        }
                }
                if (input.getText().toString().compareTo("-")==0||equals_flag==false){
                    input.setText("0");
                    vbrgin = true;
                    tip_i=0;
                    tip.setText("欢迎使用!");

                }
                tip_lock=true;
                if (tip_i>0){
                    tip_i--;
                    //如果是在按=之后输入退格键
                }else if (command.compareTo("Bksp")==0&&equals_flag==false){
                    //将显示器内容设置为0
                    input.setText("0");
                    vbrgin=true;
                    tip_i=0;
                    tip_lock=true;
                    tip.setText("欢迎使用！");
                    //如果输入的是清除键
                }else if (command.compareTo("C")==0){
                    //将显示器内容设置为0
                    input.setText("0");
                    //重新输入标志位置为true
                    vbrgin=true;
                    //缓存命令位数清0
                    tip_i=0;
                    //表示可以继续输入
                    tip_lock=true;
                    //表明输入=之前
                    equals_flag=true;
                    tip.setText("欢迎使用！");
                    //如果输入的是MC，则将储存器清0
                }else if (command.compareTo("MC")==0){
                    mem.setText("0");
                    //如果按Exit则退出程序
                }else if (command.compareTo("exit")==0){
                    System.exit(0);
                    //如果输入的是=并且输入合法
                }else if (command.compareTo("=")==0&&tip_lock&&right(str)&&equals_flag){
                    tip_i=0;
                    //表明不可继续输入
                    tip_lock=false;
                    //表明输入=之后
                    equals_flag=false;
                    //保存原来算式的样子
                    str_old=str;
                    //替换算式中的运算符，便于计算
                    str=str.replaceAll("sin","s");
                    str=str.replaceAll("cos","c");
                    str=str.replaceAll("tab","t");
                    str=str.replaceAll("log","g");
                    str=str.replaceAll("ln","l");
                    str=str.replaceAll("!n","!");
                    //将重新输入标志设置为true
                    vbrgin=true;
                    //将-1x转换成-
                    str_new=str.replaceAll("-","-1x");
//                    计算公式结果
                    new calc().process(str_new);
                }
                //表明可以继续输入
                tip_lock=true;
            }
        }
    };
        //向input输出字符
        private void print(String str){
            //清屏后退出
            if (vbrgin) input.setText(str);
            //在屏幕还原str后增添字符
            else
                input.append(str);
                vbrgin=false;
        }
    /**
     * 判断一个str是否合法的，返回值为true，false
     * 只包含0123456789.()sincostanlnlogn!-* /^√才是合法的str，返回true
     * 非法返回false
     */
    private boolean right(String str){
        int i=0;
        for (i=0;i<str.length();i++){
            if (str.charAt(i)!='0'&&str.charAt(i)!='1'&&str.charAt(i)!='2'&&str.charAt(i)!='3'&&str.charAt(i)!='4'&&str.charAt(i)!='5'&&
                    str.charAt(i)!='6'&&str.charAt(i)!='7'&&str.charAt(i)!='8'&&str.charAt(i)!='9'&&str.charAt(i)!='.'&&str.charAt(i)!='+'&&
                    str.charAt(i)!='-'&&str.charAt(i)!='*'&&str.charAt(i)!='/'&&str.charAt(i)!='^'&&str.charAt(i)!='√'&&str.charAt(i)!='i'&&
                    str.charAt(i)!='n'&&str.charAt(i)!='o'&&str.charAt(i)!='t'&&str.charAt(i)!='l'&&str.charAt(i)!='g'&&str.charAt(i)!='('&&str.charAt(i)!=')'&&
                    str.charAt(i)!='!') break;
        }
        if (i==str.length()){
            return  true;
        }else {
            return false;
        }

        /**
         *检测函数，返回值3.2.1，表示应当一次删除几个TTO为函数名
         * 为Bksp按钮的删除方式提供依据
         * 返回3，表示str尾部为sin，cos，tan，log中的一个，应当一次删除三个
         * 返回2，表示str尾部为ln，！n中的一个，应当依次删除2个
         * 返回1，表示为除返回3.2外的所有情况，只需删除一个（包含非法字符时要另外考虑，应清屏）
         */

    }
        private int TTO(String str){
        if (str.charAt(str.length()-1)=='n'&&str.charAt(str.length()-2)=='i'&&str.charAt(str.length()-3)=='s'||str.charAt(str.length()-1)=='s'&&
                str.charAt(str.length()-2)=='o'&&str.charAt(str.length()-3)=='c'||str.charAt(str.length()-1)=='n'&&str.charAt(str.length()-2)=='a'&&
                str.charAt(str.length()-3)=='t'||str.charAt(str.length()-1)=='g'&&str.charAt(str.length()-2)=='o'&&str.charAt(str.length()-3)=='l'){
            return 3;
        }else if (str.charAt(str.length()-1)=='n'&&str.charAt(str.length()-2)=='l'||str.charAt(str.length()-1)=='!'&&str.charAt(str.length()-2)=='n'){
                return 2;
            }else {
            return 1;
        }
    }
    /***
     *检测函数，对str进行前后语法检测，
     * 为Tip的提示方式提供依据，与Tipshow（）配合使用
     * 编号 字符  其后可以跟随的合法字符
     * 1    （       数字|（|-|.|函数
     * 2    )        算符|）|^√
     * 3.   .       数字|算符|）|^√
     * 4.   数字     .|数字|算符|)|^√
     * 5.   算符     数字|（|.|函数
     * 6.   ^√      (|.|数字
     * 7    函数      数字|（|.
     * 小数点前后均可省略，表示0
     * 数字第一位可以为0
     *
     */
    private void TipCheecker(String tipcommand1,String tipcommand2){
        //TipCode1 表示错误类型，TipCode2表示名词解释类型
        int Tipcode1=0;int Tipcode2 =0;
        //表示命令类型
        int tiptype1 =0; int tiptype2 =0;
        //括号数
        int bracket =0;
        //"+-*^√"不能作为第一位
        if (tipcommand1.compareTo("#")==0&&tipcommand2.compareTo("/")==0||tipcommand2.compareTo("*")==0||
                tipcommand2.compareTo("+")==0||tipcommand2.compareTo("-")==0||tipcommand2.compareTo("√")==0||tipcommand2.compareTo("^")==0){
            Tipcode1=-1;
        }
        //定义存储字符串中最后一位的类型
        else if (tipcommand1.compareTo("#")!=0){
            if (tipcommand1.compareTo("(")==0){
                tiptype1=1;
            }else if (tipcommand1.compareTo(")")==0){
                tiptype1=2;
            }else if (tipcommand1.compareTo(".")==0){
                tiptype1=3;
            }else if ("0123456789".indexOf(tipcommand1)!=-1){
                tiptype1=4;
            }else if ("+-*/".indexOf(tipcommand1)!=-1){
                tiptype1=5;
            }else if ("^√".indexOf(tipcommand1)!=-1){
                tiptype1=6;
            }else if ("sincostanloglnn!".indexOf(tipcommand1)!=-1){
                tiptype1=7;
            }
            //定义欲输入的按键类型
            if (tipcommand2.compareTo("(")==0){
                tiptype2=1;
            }else if (tipcommand2.compareTo(")")==0){
                tiptype2=2;
            }else if (tipcommand2.compareTo(".")==0){
                tiptype2=3;
            }else if ("0123456789".indexOf(tipcommand2)!=-1){
                tiptype2=4;
            }else if ("+-*/".indexOf(tipcommand2)!=-1){
                tiptype2=5;
            }else if ("^√".indexOf(tipcommand2)!=-1){
                tiptype2=6;
            }else if ("sincostanlogln!n".indexOf(tipcommand2)!=-1){
                tiptype2=7;
            }

            switch (tiptype1){
                case 1:
                    //左括号后面直接接右括号，+*/，或者^√
                    if (tiptype2==2||(tiptype2==5&&tipcommand2.compareTo("-")!=0)||tiptype2==6){
                        Tipcode1=1;
                        break;
                    }
                case 2:
                    //右括号后面接左括号，数字”+-*/^√。。。。
                    if (tiptype2==1||tiptype2==3||tiptype2==4||tiptype2==7){
                        Tipcode1=2;
                        break;
                    }
                case 3:
                    //.后面接左括号或者函数
                    if (tiptype2==1||tiptype2==7){
                        Tipcode2=3;
                    }
                    //连续输入两个.
                    if (tiptype2==3){
                        Tipcode1=8;
                    }
                    break;
                case 4:
                    //数字后面直接接左括号，函数
                    if (tiptype2==1||tiptype2==7){
                        Tipcode1=4;
                    }
                    break;
                case 5:
                    //+-*/后面直接接右括号
                    if (tiptype2==2||tiptype2==5||tiptype2==6){
                        Tipcode1=5;
                    }
                    break;
                case 6:
                    //^√后面直接接右括号，+-*/^√以及函数
                    if (tiptype2==2||tiptype2==5||tiptype2==6||tiptype2==7){
                        Tipcode1=6;
                    }
                    break;
                case 7:
                    //函数后面直接右括号+-*/及函数
                    if (tiptype2==2||tiptype2==5||tiptype2==6||tiptype2==7){
                        Tipcode1=7;
                    }
                    break;
            }

        }
        //检测小数点的重复性，Tipconde=0,表明满足前面的规则
        if (Tipcode1==0&&tipcommand2.compareTo(".")==0){
            int tip_point=0;
            for (int i=0;i<tip_i;i++){
                //若之前出现一个小数点，则小数点计数加1
                if (Tipcommand[i].compareTo(".")==0)
                    tip_point++;
                //若出现一下运算符之一，小数点计数清零
                if (Tipcommand[i].compareTo("sin")==0||Tipcommand[i].compareTo("cos")==0||Tipcommand[i].compareTo("tan")==0||Tipcommand[i].compareTo("log")
                        ==0||Tipcommand[i].compareTo("ln")==0||Tipcommand[i].compareTo("!n")==0||Tipcommand[i].compareTo("（")==0||Tipcommand[i].compareTo("）")
                        ==0 ||Tipcommand[i].compareTo("+")==0||Tipcommand[i].compareTo("-")==0||Tipcommand[i].compareTo("*")==0||Tipcommand[i].compareTo("/")
                        ==0||Tipcommand[i].compareTo("^")==0||Tipcommand[i].compareTo("√")==0){
                    tip_point=0;
                }
            }
            tip_point++;
            //若小数点计数大于1，表明小数点重复了
            if (tip_point>1){
                Tipcode1=8;
            }
        }
        //检测右括号是否匹配
            if (Tipcode1==0&&tipcommand2.compareTo(")")==0){
                int tip_right_bracket = 0;
                for (int i=0;i<tip_i;i++){
                    //若出现一个左括号，则计数加一
                    if (Tipcommand[i].compareTo("(")==0){
                        tip_right_bracket++;
                    }
                    //如果出现一个右括号，则计数减一
                    if (Tipcommand[i].compareTo(")")==0){
                        tip_right_bracket--;
                    }
                }
                //如果右括号计数=0，表明没有相应的左括号与当前右括号匹配
                if (tip_right_bracket==0){
                    Tipcode1=10;
                }
            }
        //检测输入=合法性
        if (Tipcode1==0&&tipcommand2.compareTo("=")==0){
            //括号匹配数
            int tip_bracket = 0;
            for (int i =0;i<tip_i;i++){
                if (Tipcommand[i].compareTo("(")==0){
                    tip_bracket++;
                }
                if (Tipcommand[i].compareTo(")")==0){
                    tip_bracket--;
                }
            }
            //若大于0，表明左括号还有未匹配的
            if (tip_bracket>0){
                Tipcode1=9;
                bracket=tip_bracket;
            }else if (tip_bracket==0){
                //若前一字符串是以下之一，表明=不合法
                if ("sincostanlnlog^√".indexOf(tipcommand1)!=-1){
                    Tipcode1 =6;
                }
                if ("+-*/".indexOf(tipcommand1)!=-1){
                    Tipcode1=5;
                }
            }

        }
        //若命令是以下之一，则显示相应的帮助信息
        if (tipcommand2.compareTo(" MC")==0)  Tipcode2=1;
        if (tipcommand2.compareTo("C")==0)  Tipcode2=2;
        if (tipcommand2.compareTo("DRG")==0)  Tipcode2=3;
        if (tipcommand2.compareTo("Bksp")==0)  Tipcode2=4;
        if (tipcommand2.compareTo("sin")==0)  Tipcode2=5;
        if (tipcommand2.compareTo("cos")==0)  Tipcode2=6;
        if (tipcommand2.compareTo("tan")==0)  Tipcode2=7;
        if (tipcommand2.compareTo("log")==0)  Tipcode2=8;
        if (tipcommand2.compareTo("ln")==0)  Tipcode2=9;
        if (tipcommand2.compareTo("!n")==0)  Tipcode2=10;
        if (tipcommand2.compareTo("√")==0)  Tipcode2=12;
        if (tipcommand2.compareTo("^")==0)  Tipcode2=11;
        //显示帮助和错误信息
        Tipshow(bracket,Tipcode1,Tipcode2,tipcommand1,tipcommand2);
    }
    /**
     * 反馈Tip信息，加强人机交互，与TipChecker（）配合使用
     */
    private void Tipshow(int bracket,int tipcode1,int tipcode2,String tipcommand1,String tipcommand2){
        String tipmessage="";
        if (tipcode1!=0) tip_lock=false;//表明输入有误
        switch (tipcode1){
            case -1:
                tipmessage=tipcommand2+"不能作为第一个算符\n";
                break;
            case 1:
                tipmessage=tipcommand1+"后应输入： 数字（。-函数\n";
                break;
            case 2:
                tipmessage=tipcommand1+"后应输入：）算符\n";
                break;
            case 3:
                tipmessage=tipcommand1+"后应输入：（数字算符\n";
                break;
            case 4:
                tipmessage=tipcommand1+"后应输入：（数字算符\n";
                break;
            case 5:
                tipmessage=tipcommand1+"后应输入：（.数字函数\n";
                break;
            case 6:
                tipmessage=tipcommand1+"后应输入：（.数字\n";
                break;
            case 7:
                tipmessage=tipcommand1+"后应输入：（.数字\n";
                break;
            case 8:
                tipmessage=tipcommand1+"小数点重复\n";
                break;
            case 9:
                tipmessage=tipcommand1+"不能计算，缺少"+bracket+"个）";
                break;
            case 10:
                tipmessage=tipcommand1+"不需要 ）";
                break;
        }
        switch (tipcode2){
            case 1:
                tipmessage=tipmessage+"[MC用法：清除记忆MEM]";
                break;
            case 2:
                tipmessage=tipmessage+"[C用法，归零]";
                break;
            case 3:
                tipmessage=tipmessage+"[DRG用法：选择DEG或RAD]";
                break;
            case 4:
                tipmessage=tipmessage+"[Bksp用法：退格]";
                break;
            case 5:
                tipmessage=tipmessage+"sin 函数用法示例： DEG：sin30=0.5 RAD：sin1=0.84";
                break;
            case 6:
                tipmessage=tipmessage+"cos函数用法示例：DEG：cos60=0.5  RAD：cos1=0.54";
                break;
            case 7:
                tipmessage=tipmessage+"tan函数用法示例：DEG：tan45=1  RAD：tan1=1.55";
                break;
            case 8:
                tipmessage=tipmessage+"log函数用法示例：    log10=log（5+5）=1";
                break;
            case 9:
                tipmessage=tipmessage+"ln函数用法示例： ln10=le(5+5)=1";
                break;
            case 10:
                tipmessage=tipmessage+"n!函数用法示例 n！3=3*2*1=6";
                break;
            case 11:
                tipmessage=tipmessage+"开方用法  27√3=3 ";
                break;
            case 12:
                tipmessage=tipmessage+"根号用法  2^3=8";
                break;
        }
        //将提示信息显示到tip
        tip.setText(tipmessage);
    }

}
