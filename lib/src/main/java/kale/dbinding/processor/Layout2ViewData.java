package kale.dbinding.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

import kale.dbinding.annotation.BindLayout;

/**
 * @author Kale
 * @date 2015/12/20
 */
@SupportedAnnotationTypes({"BindLayout"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class Layout2ViewData extends AbstractProcessor {

    public boolean isFirst = true;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement te : annotations) {
            for (Element e : roundEnv.getElementsAnnotatedWith(te)) {
                if (e.getKind() == ElementKind.CLASS) {
                    TypeElement varCls = (TypeElement) e;
                    BindLayout bindLayout = e.getAnnotation(BindLayout.class);

                    if (isFirst) {
                        isFirst = false;
                        //MyClass.main(null);
                    }
                }
            }
        }
        return true;
    }

}
