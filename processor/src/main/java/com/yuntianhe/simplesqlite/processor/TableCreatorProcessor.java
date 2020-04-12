package com.yuntianhe.simplesqlite.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.yuntianhe.simplesqlite.annotations.Column;
import com.yuntianhe.simplesqlite.annotations.Table;

import java.util.ArrayList;
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
public class TableCreatorProcessor extends AbstractProcessor {

    public static final String POSTFIX = "Table";
    public static final String PACKAGE_NAME = "com.yuntianhe.simplesqlite.processor";

    public static final ClassName abstractTableClass = ClassName.get("com.yuntianhe.simplesqlite.library", "AbstractTable");
    public static final ClassName tableEntityInterface = ClassName.get("com.yuntianhe.simplesqlite.library", "ITableEntity");
    public static final ClassName baseTableEntityClass = ClassName.get("com.yuntianhe.simplesqlite.library", "BaseTableEntity");
    public static final ClassName realOpenHelperClass = ClassName.get("com.yuntianhe.simplesqlite.library", "RealOpenHelper");

    public static final ClassName contentValuesClass = ClassName.get("android.content", "ContentValues");
    public static final ClassName cursorWrapperClass = ClassName.get("com.yuntianhe.simplesqlite.library", "CursorWrapper");
    public static final ClassName stringBuilderClass = ClassName.get("java.lang", "StringBuilder");

    private Filer mFiler;
    private static ProcessorLogger sLogger;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        mFiler = processingEnv.getFiler();
        sLogger = new ProcessorLogger(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (ProcessorUtil.isEmpty(annotations)) {
            return true;
        }

        for (TypeElement annotation : annotations) {
            if (ProcessorUtil.isAnnotation(annotation, Table.class)) {
                List<Element> classList = ProcessorUtil.getElements(roundEnv, Table.class); // 所有被@Table注解的类
                for (Element element : classList) {
                    createFile(element);
                }
            }
        }

        return true;
    }

    private void createFile(Element outerElement) {
        List<Element> totalColumnList = ProcessorUtil.getInnerElements(outerElement, Column.class);

        String sourcePackageName = ProcessorUtil.getPackageName(outerElement);
        String sourceClassName = ProcessorUtil.getClassName(outerElement);
        String resultClassName = sourceClassName + POSTFIX;
        ClassName sourceClass = ClassName.get(sourcePackageName, sourceClassName);
        String tableName = getTableName(outerElement);
        String databaseName = getDatabaseName(outerElement);

        FieldSpec tableNameField = FieldSpec.builder(String.class, "TABLE_NAME")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", tableName)
                .build();

        FieldSpec databaseField = FieldSpec.builder(String.class, "DATABASE_NAME")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", databaseName)
                .build();

        // 方法: getOpenHelper
        MethodSpec getOpenHelper = MethodSpec.methodBuilder("getOpenHelper")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .addStatement("return SimpleSQLite.getOpenHelper(getDatabaseName())")
                .returns(realOpenHelperClass)
                .build();

        // 方法: getDatabaseName
        MethodSpec getDatabaseName = MethodSpec.methodBuilder("getDatabaseName")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addStatement("return DATABASE_NAME")
                .returns(String.class)
                .build();

        // 方法: getTableName
        MethodSpec getTableName = MethodSpec.methodBuilder("getTableName")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addStatement("return TABLE_NAME")
                .returns(String.class)
                .build();

        // 方法: onCreateInitSql
        MethodSpec onCreateInitSql;
        MethodSpec.Builder onCreateInitSqlBuilder = MethodSpec.methodBuilder("onCreateInitSql")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class);
        onCreateInitSqlBuilder.addStatement("$T initSql = new $T($S)", stringBuilderClass, stringBuilderClass, "create table " + tableName + "(");
        onCreateInitSqlBuilder.addStatement("initSql.append($S)", "_id integer primary key autoincrement default \'1\'");
        for (Element field : totalColumnList) {
            String columnName = getColumnName(field);
            String columnType = getColumnType(field);
            String defaultValue = getColumnDefaultValue(field);
            defaultValue = (ProcessorUtil.isEmpty(defaultValue) ? "" : " defaultValue \'" + defaultValue + "\'");
            onCreateInitSqlBuilder.addStatement("initSql.append($S)", ", " + columnName + " " + columnType + defaultValue);
        }
        onCreateInitSqlBuilder.addStatement("initSql.append($S)", ")");
        onCreateInitSqlBuilder.addStatement("return initSql.toString()")
                .returns(String.class);
        onCreateInitSql = onCreateInitSqlBuilder.build();

        // 方法: onCreateTableEntity
        MethodSpec onCreateTableEntity = MethodSpec.methodBuilder("onCreateTableEntity")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .addStatement("return new $T()", sourceClass)
                .returns(sourceClass)
                .build();

        // method: in
        MethodSpec in;
        MethodSpec.Builder inBuilder = MethodSpec.methodBuilder("in")
                .addParameter(ParameterSpec.builder(sourceClass, "arg").build())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);
        inBuilder.addStatement("$T in = new $T()", contentValuesClass, contentValuesClass);
        for (Element field : totalColumnList) {
            String columnName = getColumnName(field);
            String fieldName = field.getSimpleName().toString();
            inBuilder.addStatement("in.put($S, arg.$L)", columnName, fieldName);
        }
        inBuilder.addStatement("return in");
        inBuilder.returns(contentValuesClass);
        in = inBuilder.build();

        // method: out
        MethodSpec out;
        MethodSpec.Builder outBuilder = MethodSpec.methodBuilder("out")
                .addParameter(ParameterSpec.builder(cursorWrapperClass, "cv").build())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);
        outBuilder.addStatement("$T out = new $T()", sourceClass, sourceClass);
        for (Element field : totalColumnList) {
            String fieldName = field.getSimpleName().toString();
            String fieldType = field.asType().toString();
            switch (fieldType) {
                case "byte":
                case "Byte":
                    outBuilder.addStatement("out.$L = cv.getByte($L)", fieldName, fieldName);
                    break;
                case "short":
                case "Short":
                    outBuilder.addStatement("out.$L = cv.getShort($L)", fieldName, fieldName);
                    break;
                case "int":
                case "Integer":
                    outBuilder.addStatement("out.$L = cv.getInt($L)", fieldName, fieldName);
                    break;
                case "long":
                case "Long":
                    outBuilder.addStatement("out.$L = cv.getLong($L)", fieldName, fieldName);
                    break;
                case "float":
                case "Float":
                    outBuilder.addStatement("out.$L = cv.getFloat($L)", fieldName, fieldName);
                    break;
                case "double":
                case "Double":
                    outBuilder.addStatement("out.$L = cv.getDouble($L)", fieldName, fieldName);
                    break;
                default:
                    outBuilder.addStatement("out.$L = cv.getString($L)", fieldName, fieldName);
                    break;
            }
        }
        outBuilder.addStatement("return out");
        outBuilder.returns(sourceClass);
        out = outBuilder.build();

        TypeSpec resultClass = TypeSpec.classBuilder(resultClassName) // 创建类
                .addField(tableNameField)
                .addField(databaseField)
                .addFields(createFieldSpec(totalColumnList))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(getOpenHelper)
                .addMethod(getDatabaseName)
                .addMethod(getTableName)
                .addMethod(onCreateInitSql)
                .addMethod(onCreateTableEntity)
                .addMethod(in)
                .addMethod(out)
                .superclass(ParameterizedTypeName.get(abstractTableClass, sourceClass))
                .build();

        JavaFile javaFile = JavaFile.builder(PACKAGE_NAME, resultClass)
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
        String databaseName = null;
        if (ProcessorUtil.hasAnnotation(target, Table.class)) {
            databaseName = target.getAnnotation(Table.class).databaseName();
        }
        if (ProcessorUtil.isEmpty(databaseName)) {
            sLogger.e(ProcessorUtil.getClassName(target) + "中@Table注解的 databaseName 不能为空！");
        }
        return databaseName;
    }

    private String getTableName(Element target) {
        String tableName = target.getAnnotation(Table.class).tableName();
        if (ProcessorUtil.isEmpty(tableName)) {
            tableName = target.getSimpleName().toString();
        }
        return tableName;
    }

    private String getColumnName(Element field) {
        String columnName = field.getAnnotation(Column.class).name();
        if (ProcessorUtil.isEmpty(columnName)) {
            columnName = ProcessorUtil.getElementName(field);
        }
        return columnName;
    }

    private String getColumnType(Element field) {
        return ColumnType.get(ProcessorUtil.getElementType(field));
    }

    private  String getColumnDefaultValue(Element field) {
        String defaultValue = field.getAnnotation(Column.class).defaultValue();
        return defaultValue;
    }

    private  List<FieldSpec> createFieldSpec(List<Element> fieldList) {
        List<FieldSpec> list = new ArrayList<>(fieldList.size());
        for (Element field : fieldList) {
            String fieldName = ProcessorUtil.getElementName(field);
            String columnName = getColumnName(field);
            FieldSpec fieldSpec = FieldSpec.builder(String.class, fieldName)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$S", columnName)
                    .build();
            list.add(fieldSpec);
        }
        return list;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();
        set.add(Table.class.getCanonicalName());
//        set.add(PrimaryKey.class.getCanonicalName());
//        set.add(Column.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
