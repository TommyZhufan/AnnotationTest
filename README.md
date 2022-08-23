# AnnotationTest
examples for annotation study.



## 1.SOURCE 级别APT（例子）
com/tommytest/jnitest/compiler/TommyProcessor.java

仅输出一句话，没有生成任何例子


## 2.CLASS 级别（例子暂缺）


## 3. RUNTIME级别例子
> 三个例子：
> （1）View自动初始化（替代findeViewById）findViewById
>
> ```java
> @InjectView(R.id.id_tv_inject)
> private TextView textViewInfo;
> ```
>
>
> （2）自动获取Intent参数
>
> ```java
> @InjectIntentParam("TestAge")
> private int age = 0;
> ```
>
> （3）自动设置OnClick事件
>
> ```
> @InjectOnClick({R.id.btn_test1, R.id.btn_test2})
> private void onClick(View view) {
>     switch (view.getId()) {
>         case R.id.btn_test1:
>             onBtnTest1Click();
>             break;
>         case R.id.btn_test2:
>             onBtnTest2Click();
>             break;
>         default:
>             break;
>     }
> }
> ```
> Activity
> com/tommytest/annotationtest/InjectTestActivity.java
> Inject的类：
> com/tommytest/annotationtest/inject/InjectUtils.java