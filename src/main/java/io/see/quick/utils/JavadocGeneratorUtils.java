package io.see.quick.utils;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import io.see.quick.JavadocGenerator;

import java.util.ArrayList;
import java.util.List;

public class JavadocGeneratorUtils {

    // for preventing instantiating
    private JavadocGeneratorUtils() {}

    public static List<String> getClassTags(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
        List<String> classTags = new ArrayList<>();
        if (classOrInterfaceDeclaration != null) {
            classOrInterfaceDeclaration.getAnnotations().forEach(annotation -> {
                if (annotation.getNameAsString().equals("Tag")) {
                    classTags.add(annotation.toString());
                }
            });
        }
        return classTags;
    }

    public static List<String> getMethodTags(MethodDeclaration methodDeclaration) {
        List<String> methodTags = new ArrayList<>();
        if (methodDeclaration != null) {
            methodDeclaration.getAnnotations().forEach(annotation -> {
                if (annotation.getNameAsString().equals("Tag")) {
                    methodTags.add(annotation.toString());
                }
            });
        }
        return methodTags;
    }
}
