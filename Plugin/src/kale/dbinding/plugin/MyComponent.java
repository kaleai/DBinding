package kale.dbinding.plugin;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import org.jetbrains.annotations.NotNull;

/**
 * @author Kale
 * @date 2015/12/28
 */
public class MyComponent implements ProjectComponent {

    public MyComponent(Project project) {
    }

    @Override
    public void initComponent() {
        // TODO: insert component initialization logic here
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "MyComponent";
    }

    @Override
    public void projectOpened() {
        // called when project is opened
    }

    @Override
    public void projectClosed() {
        // called when project is being closed
    }

    public void sayHello() {
        Messages.showMessageDialog("Hello World~!", "标题",Messages.getInformationIcon());
    }
}
