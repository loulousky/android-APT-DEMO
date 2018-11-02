package com.example.libprocessor;

import com.example.libanniation.Myanniaton;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.example.libanniation.Myanniaton")
@AutoService(Processor.class)
public class MyProcessor extends AbstractProcessor {


    private Messager messager;
    private Filer filer;
    private Elements utils;

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> setem = roundEnvironment.getElementsAnnotatedWith(Myanniaton.class);//获取有这个注解的所以元素

        for (Element e : setem) {
            //判断是不是方法元素 元素分好几种  类型元素是TYPEELEMENT  字段元素 V开通的element
            if(e instanceof ExecutableElement) {

                //找到方法
                Myanniaton myanniaton = e.getAnnotation(Myanniaton.class);
                //  String value = myanniaton.myvalue();
                //    System.out.println(value + e.getSimpleName() + e.toString());
                //     messager.printMessage(Diagnostic.Kind.ERROR, value + e.getSimpleName() + e.toString());


                //上下文的包
                ClassName context = ClassName.get("android.content", "Context");
                ClassName toast = ClassName.get(" android.widget", "Toast");

                //构建类名
                TypeSpec.Builder mainActivityBuilder = TypeSpec.classBuilder("MyT")
                        .addModifiers(Modifier.PUBLIC);

                //构建参数上下文 context
                ParameterSpec contextP = ParameterSpec.builder(context, "context").build();

                //构建吐司用的文本参数
                ParameterSpec stringp = ParameterSpec.builder(String.class, "value").build();


                //构建方法  //$T代表TYPENAME Toast %N代表 参数字段等方法名等
              //  Toast.makeText(this,"aaa",1).show();
                Myanniaton aaa=e.getAnnotation(Myanniaton.class);//获得注解
                MethodSpec mytoast = MethodSpec.methodBuilder("Mytoast").addModifiers(Modifier.PUBLIC).addModifiers(Modifier.STATIC).
                        returns(void.class).addParameter(contextP).
                        //这里重点说明 $T是代表我们classnameGET过来的类或者.CLASS出来的类，$S代表String,$N代表我们自己创建的参数类型等
                addStatement("$T.makeText(context,$S,1).show()",toast,aaa.myvalue()).addStatement("return").build();

                TypeSpec mycalss = mainActivityBuilder.addMethod(mytoast).build();


                JavaFile file = JavaFile.builder("com.example.admin.myanniation.build", mycalss).build();

                try {
                    file.writeTo(filer);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }


            }

        //    }


        }


        return true;
    }


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        utils = processingEnvironment.getElementUtils();
    }
}
