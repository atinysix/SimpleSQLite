package com.yuntianhe.simplesqlite.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.yuntianhe.simplesqlite.annotations.Database;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class OpenHelperProcessor extends AbstractProcessor {

    public static final String PACKAGE_NAME = "com.yuntianhe.simplesqlite.processor";
    public static final String RESULT_CLASS_NAME = "SimpleSQLite";

    public static final ClassName openHelperInterface = ClassName.get("com.yuntianhe.simplesqlite.library", "IOpenHelper");
    public static final ClassName realOpenHelperClass = ClassName.get("com.yuntianhe.simplesqlite.library", "RealOpenHelper");

    public static final ClassName contextClass = ClassName.get("android.content", "Context");
    public static final ClassName stringClass = ClassName.get("java.lang", "String");
    public static final ClassName hashMapClass = ClassName.get("java.util", "HashMap");
    public static final ClassName mapClass = ClassName.get("java.util", "Map");

    public static final ClassName resultClass = ClassName.get(PACKAGE_NAME, RESULT_CLASS_NAME);

    private Filer mFiler;
    private ProcessorLogger mLogger;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        mFiler = processingEnv.getFiler();
        mLogger = new ProcessorLogger(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (ProcessorUtil.isEmpty(annotations)) {
            return true;
        }

        for (TypeElement annotation : annotations) {
            if (ProcessorUtil.isAnnotation(annotation, Database.class)) {
                List<Element> classList = ProcessorUtil.getElements(roundEnv, Database.class); // 所有被@Table注解的类
                createFile(classList);
            }
        }

        return true;
    }

    private void createFile(List<Element> databaseList) {
        if (ProcessorUtil.isEmpty(databaseList)) {
            return;
        }

        FieldSpec tagField = FieldSpec.builder(String.class, "TAG")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$L.class.getSimpleName()", RESULT_CLASS_NAME)
                .build();

        TypeName resultField = ParameterizedTypeName.get(hashMapClass, stringClass, realOpenHelperClass);
        FieldSpec containerField = FieldSpec.builder(resultField, "sContainer")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("new $T()", resultField)
                .build();

        MethodSpec getOpenHelper = MethodSpec.methodBuilder("getOpenHelper")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .addParameter(String.class, "databaseName")
                .beginControlFlow("for ($T.Entry<String, RealOpenHelper> entry : sContainer.entrySet())", mapClass)
                .beginControlFlow("if (entry.getKey().equals(databaseName))")
                .addStatement("return entry.getValue()")
                .endControlFlow()
                .endControlFlow()
                .addStatement("return null")
                .returns(realOpenHelperClass)
                .build();

        // method: init
        MethodSpec.Builder init = MethodSpec.methodBuilder("init")
                .addParameter(ParameterSpec.builder(contextClass, "context").build())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);
        for (Element database : databaseList) {
            String databaseName = getDatabaseName(database);
            int version = getDatabaseVersion(database);
            ClassName factoryClass = ClassName.get(ProcessorUtil.getPackageName(database), ProcessorUtil.getClassName(database));
            init.addStatement("sContainer.put($S, new $T(context, $S, $L, new $T()))",
                    databaseName, realOpenHelperClass, databaseName, version, factoryClass);
        }

        TypeSpec resultType = TypeSpec.classBuilder(resultClass) // 创建类
                .addField(tagField)
                .addField(containerField)
                .addMethod(getOpenHelper)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(init.build())
                .build();

        JavaFile javaFile = JavaFile.builder(PACKAGE_NAME, resultType)
                .addFileComment("这是自动生成的类文件，每次build都会重新生成，不要手动修改该文件！\n")
                .addFileComment("这是自动生成的类文件，每次build都会重新生成，不要手动修改该文件！\n")
                .addFileComment("这是自动生成的类文件，每次build都会重新生成，不要手动修改该文件！")
                .build(); // 创建文件

        try {
            javaFile.writeTo(mFiler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDatabaseName(Element target) {
        String databaseName = target.getAnnotation(Database.class).name();
        if (ProcessorUtil.isEmpty(databaseName)) {
            databaseName = ProcessorUtil.getClassName(target);
        }
        return databaseName;
    }

    private int getDatabaseVersion(Element target) {
        int version = target.getAnnotation(Database.class).version();
        if (version < 1) {
            mLogger.e(ProcessorUtil.getClassName(target) +"中注解@Database的字段 version 值不能小于1！");
        }
        return version;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();
        set.add(Database.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
