package kale.dbinding.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import kale.dbinding.GenViewData;

/**
 * @author Kale
 * @date 2015/12/28
 */
public class ReadCurrentFile extends AnAction {

    public void actionPerformed(AnActionEvent e) {
        final Project project = e.getProject();
        if (project == null) {
            return;
        }

        System.out.println("========>"+System.getProperty("user.dir"));

        GenViewData.main(null);
        
        Application application = ApplicationManager.getApplication();

        MyComponent myComponent = application.getComponent(MyComponent.class);
        myComponent.sayHello();

        

        

        Editor[] editors = EditorFactory.getInstance().getAllEditors(); // 得到当前开启的tab
        FileDocumentManager fileDocManager = FileDocumentManager.getInstance();
        for (Editor editor : editors) {
            VirtualFile vf = fileDocManager.getFile(editor.getDocument());
            String path = vf.getCanonicalPath();
            System.out.println("path = " + path);
        }

        LocalFileSystem instance = LocalFileSystem.getInstance();

        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor(); // 得到当前的文件
        if (editor == null) {
            return;
        }
        final Document document = editor.getDocument();
        if (document == null) {
            return;
        }
        VirtualFile virtualFile = FileDocumentManager.getInstance().getFile(document);
        if (virtualFile == null) {
            return;
        }
        final String contents;
        try {
            BufferedReader br = new BufferedReader(new FileReader(virtualFile.getPath()));
            String currentLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((currentLine = br.readLine()) != null) {
                stringBuilder.append(currentLine);
                stringBuilder.append("\n");
            }
            contents = stringBuilder.toString() + "豪哥牛逼！！！";
            System.out.println(contents);

        } catch (IOException e1) {
            return;
        }
        final Runnable readRunner = new Runnable() {
            @Override
            public void run() {
                document.setText(contents);
            }
        };
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                CommandProcessor.getInstance().executeCommand(project, new Runnable() {
                    @Override
                    public void run() {
                        ApplicationManager.getApplication().runWriteAction(readRunner);
                    }
                }, "DiskRead", null);
            }
        });
    }
}
