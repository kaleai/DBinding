package kale.dbinding;

import java.io.File;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * @author Kale
 * @date 2016/8/7
 */
@SupportedAnnotationTypes({"kale.dbinding.VmGenerator"})
public class VmGenerateProcessor extends AbstractProcessor {

    private static final String ROOT = System.getProperty("user.dir");

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.iterator().hasNext()) {
            TypeElement ele = annotations.iterator().next();
            if (roundEnv.getElementsAnnotatedWith(ele).iterator().hasNext()) {
                Element next = roundEnv.getElementsAnnotatedWith(ele).iterator().next();

                VmGenerator annotation = next.getAnnotation(VmGenerator.class);

                if (annotation != null) {
                    String moduleDir = ROOT + File.separator + "app" + File.separator;

//                    log(moduleDir);

//                    String path = annotation.value();
                    GenViewModel.generateViewModel(moduleDir);
                }

            }
        }
        return false;
    }
/*
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Collections.singletonList(
                VmGenerator.class.getCanonicalName()));
    }*/

    private void log(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "======" + msg);
    }
}
