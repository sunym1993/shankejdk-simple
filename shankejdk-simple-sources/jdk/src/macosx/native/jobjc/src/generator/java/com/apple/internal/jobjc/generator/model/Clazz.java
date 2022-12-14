/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apple.internal.jobjc.generator.RestrictedKeywords;
import com.apple.internal.jobjc.generator.classes.JObjCClassClassFile;
import com.apple.internal.jobjc.generator.classes.JObjCClassFile;
import com.apple.internal.jobjc.generator.classes.OutputFile;
import com.apple.jobjc.MacOSXFramework;
import com.apple.jobjc.SuperClassExtractor;
import com.apple.jobjc.UnsafeRuntimeAccess;
import com.apple.jobjc.NSClass.NSClassNotFoundException;
import com.apple.jobjc.SEL;

public class Clazz extends Element<Framework> implements OutputFileGenerator {
    private final Map<String, Method> instanceMethodsByName = new HashMap<String, Method>();
    private final Map<String, Method> classMethodsByName = new HashMap<String, Method>();

    public final List<Method> classMethods;
    public final List<Method> instanceMethods;

    public final List<Clazz> subClassers = new ArrayList<Clazz>(0);
    public Clazz superClass;

    public Clazz(String name, List<Method> classMethods, List<Method> instanceMethods, Clazz superClass, Framework parent){
        super(name, parent);
        this.classMethods = classMethods;
        this.instanceMethods = instanceMethods;
        this.superClass = superClass;
    }

    public Clazz(final Node classNode, final Framework parent) {
        super(classNode, parent);
        this.classMethods = new ArrayList<Method>();
        this.instanceMethods = new ArrayList<Method>();

        final NodeList methodNodes = classNode.getChildNodes();
        for (int i = 0; i < methodNodes.getLength(); i++) {
            final Node node = methodNodes.item(i);
            if (!"method".equals(node.getLocalName())) continue;

            final String selName = Element.getAttr(node, "selector");
            if(selName == null || !SEL.validName(selName)){
                System.err.format("Warning: Discarding method %1$s:%2$s:%3$s"
                        + " -- Invalid selector name. Verify.\n",
                        parent.name, name, selName);
                continue;
            }

            final Method method = new Method(node, parent);
            if (method.isClassMethod) {
                classMethods.add(method);
            } else {
                instanceMethods.add(method);
            }
        }
    }

    public String getPackage() {
        return parent.pkg;
    }

    public String getFullPath(){
        return parent.pkg + "." + name;
    }

    @Override
    public String toString() {
        return super.toString() + " " + classMethods + " " + instanceMethods;
    }

    public boolean doesActuallyExist(){
        try{
            UnsafeRuntimeAccess.getNSClass(parent.load(), name);
        }catch(NSClassNotFoundException x){
            return false;
        }
        return true;
    }

    void resolveSuperClass(final MacOSXFramework nativeFramework, final Map<String, Clazz> allClasses) throws Throwable {
        superClass = SuperClassExtractor.getSuperClassFor(name, nativeFramework, allClasses);
    }

    public void disambiguateMethods() {
        disambiguateMethods(instanceMethods, instanceMethodsByName);
        disambiguateMethods(classMethods, classMethodsByName);
    }

    private void disambiguateMethods(final List<Method> methods, final Map<String, Method> methodMap) {
        final Set<String> existingMethodNames = RestrictedKeywords.getNewRestrictedSet();
        for (final Method method : methods) {
            method.disambiguateNameAndArgs(this, existingMethodNames);
            methodMap.put(method.javaName, method);
        }
    }

    public void generateClasses(final List<OutputFile> generatedClassFiles) {
        generatedClassFiles.add(new JObjCClassClassFile(this));
        generatedClassFiles.add(new JObjCClassFile(this));
    }

    Method getParentMethodMatchingName(final String methodName) {
        if(superClass == null) return null;
        Method m = superClass.getMethodNamed(methodName);
        if(m != null) return m;
        return superClass.getParentMethodMatchingName(methodName);
    }

    private Method getMethodNamed(final String methodName) {
        final Method instanceMethod = instanceMethodsByName.get(methodName);
        if (instanceMethod != null) return instanceMethod;

        final Method classMethod = classMethodsByName.get(methodName);
        if (classMethod != null) return classMethod;

        return null;
    }

    int compareTo(Clazz o) {
      return toString().compareTo(o.toString());
    }
}
