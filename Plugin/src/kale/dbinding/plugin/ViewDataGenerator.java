package kale.dbinding.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;

import kale.dbinding.GenViewData;

/**
 * @author Kale
 * @date 2015/12/28
 * 
 * 生成viewModel的插件
 */
public class ViewDataGenerator extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {

        long start = System.currentTimeMillis();
        
        Editor[] editors = EditorFactory.getInstance().getAllEditors(); // 得到当前开启的tab
        FileDocumentManager fileDocManager = FileDocumentManager.getInstance();
        /*for (Editor editor : editors) {
            VirtualFile vf = fileDocManager.getFile(editor.getDocument());
            String path = vf.getCanonicalPath();
            System.out.println("path = " + path);
        }*/

        final Project project = e.getProject();
        if (project == null) {
            return;
        }
        final Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor(); // 得到当前的文件
        if (editor == null) {
            return;
        }
        final VirtualFile vf = fileDocManager.getFile(editor.getDocument());
        if (vf == null) {
            return;
        }
        String path = vf.getCanonicalPath();
        if (path == null) {
            return;
        }
        
        
        path = path.substring(0, path.indexOf("src"));
        System.out.println("path = " + path);
        
        GenViewData.generateViewData(path);
        
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(DataKeys.PROJECT.getData(e.getDataContext()));

        long ms = System.currentTimeMillis() - start;
        String timeStr = ms + "ms";
        if (ms > 1000) {
            timeStr = ms / 1000 + "s " + ms % 1000 + "ms";
        }

        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder("<html>Generate completed successfully in " + timeStr + "</html>", MessageType.INFO, null)
                .setFadeoutTime(3000)
                .createBalloon()
                .show(RelativePoint.getCenterOf(statusBar.getComponent()), Balloon.Position.above);
    }
    
}
