package crewmate.lib.processors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeMirror;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;

import crewmate.lib.TM;
import crewmate.lib.XboxButton;
import crewmate.lib.annotations.ControllerDiagram;

public class ControllerDiagramProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(ControllerDiagram.class)) {
            if (element.getKind() == ElementKind.METHOD) {
                ExecutableElement method = (ExecutableElement) element;
                TypeMirror returnType = method.getReturnType();

                if (returnType.toString().equals("TM[]")) {
                    // Extract CTM[] data
                    List<String> customLabels = new ArrayList<>();

                    // Get the default value of the method
                    AnnotationValue value = method.getDefaultValue();
                    if (value != null) {
                        List<AnnotationMirror> mappings = (List<AnnotationMirror>) value.getValue();
                        for (AnnotationMirror mapping : mappings) {
                            Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = mapping
                                    .getElementValues();

                            String buttonName = "";
                            String triggerName = "";

                            for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues
                                    .entrySet()) {
                                if (entry.getKey().getSimpleName().toString().equals("button")) {
                                    buttonName = entry.getValue().getValue().toString();
                                } else if (entry.getKey().getSimpleName().toString().equals("trigger")) {
                                    triggerName = entry.getValue().getValue().toString();
                                }
                            }

                            customLabels.add(buttonName + "=" + triggerName);
                        }
                    }

                    // Convert to format expected by DrawController.py
                    String[] args = customLabels.toArray(new String[0]);

                    // Execute DrawController.py
                    executeDrawController(args);
                }
            }
        }
        return true;
    }

    private void executeDrawController(String[] args) {
        // Implementation to execute DrawController.py
    }
}