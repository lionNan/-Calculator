package com.example.counter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class counter extends AppCompatActivity implements  {
    //0-9是个按键
    private Button []btn = new Button[10];
//    显示器显示输出结果
    private EditText input;
    //显示器下方的记忆器，用于记录上一次计算结果
    private TextView mem;
    //三角计算时标志显示，角度还是弧度
    private TextView _drg;
    //小提示，用于加强人机交互的弱检测，提示
    private TextView tip;
    private Button
    div,mul,sub,add,equal,sin,cos,tan,log,ln,sqrt,square,factorial,bksp,left,right,dot,exit,drg,mc,c;
    //保存原来的算式样i，为了输出好看，因计算时样子被改变
    public String str_old;
    //变换样子后的式子
    public String str_new;
    //输入控制，true为重新输入，false为接着输入
    public boolean vbrgin =true;
    //控制DRG按键，true为角度，false为弧度
    public boolean drg_flag = true;
    //pi值为3.14
    public double pi= 4*Math.atan(1);
    //true表示正确，可以继续输入；false表示有误，输入被锁定
    public boolean tip_lock = true;
    //判断是否按=之后的输入，true表示输入在=之前，false反之
    public boolean equals_flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        //获取界面元素
        input= (EditText) findViewById(R.id.input);
        mem = (TextView) findViewById(R.id.mem);
        tip = (TextView) findViewById(R.id.tip);
        _drg = (TextView) findViewById(R.id._drg);
        btn[0]= (Button) findViewById(R.id.zero);
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

        for (int i = 0; i<10; ++i){
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
        String [] Tipcommand = new String[500];
        //Tipcommand的指针
        int tip_i=0;
        private View.OnClickListener actionPerformed = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //按键上的命令获取
                String command = ((Button)v).getText().toString();
                //显示器上的字符串
                String str = input.getText().toString();
                //检测输入是否合法
                if (equals_flag==false&&"0123456789.()sincostanlnlogn!+-*/^√".indexOf(command)!=-1){
                    //检测显示器上的字符串是否合法
                    if (right(str)){
                        if ("-*/^√".indexOf(command)!=-1){
                            for (int i=0;i<str.length();i++){
                                Tipcommand[tip_i] =String.valueOf(str.charAt(i));
                                tip_i++;
                            }
                            vbrgin = false;
                        }
                    }else {
                        input.setText(0);
                        vbrgin = true;
                        tip_i=0;
                        tip_lock=true;
                        tip.setText("欢迎使用!");
                    }
                    equals_flag=true;
                }
                if (tip_i>0)
                    TipCheecker(Tipcommand[tip_i-1],command);else if (tip_i=0){
                    Tipchecker("#",command);
                }
                
            }
        }



}
