package kale.dbinding.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
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

import org.jetbrains.annotations.Nullable;

import kale.dbinding.GenViewModel;

/**
 * @author Kale
 * @date 2015/12/28
 *
 * 生成viewModel的插件
 */
public class ViewModelGenerator extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        long start = System.currentTimeMillis();

        String path = getPath(e);
        if (path == null) {
            showHint("Please make sure the current file is in android module", true, e);
            return;
        }

        path = path.substring(0, path.indexOf("src"));

        GenViewModel.generateViewModel(path);

        long ms = System.currentTimeMillis() - start;
        String timeStr = ms + "ms";
        if (ms > 1000) {
            timeStr = ms / 1000 + "s " + ms % 1000 + "ms";
        }

        showHint("Generate completed successfully in " + timeStr, false, e);
    }

    private void showHint(String msg, boolean isError, AnActionEvent e) {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(DataKeys.PROJECT.getData(e.getDataContext()));
        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder("<html>" + msg + "</html>", isError ? MessageType.ERROR : MessageType.INFO, null)
                .createBalloon()
                .show(RelativePoint.getCenterOf(statusBar.getComponent()), Balloon.Position.above);
    }

    @Nullable
    private String getPath(AnActionEvent e) {
        FileDocumentManager fileDocManager = FileDocumentManager.getInstance();

        final Project project = e.getProject();
        if (project == null) {
            return null;
        }
        final Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor(); // 得到当前的文件
        if (editor == null) {
            return null;
        }
        final VirtualFile vf = fileDocManager.getFile(editor.getDocument());
        if (vf == null) {
            return null;
        }
        String path = vf.getCanonicalPath();
        if (path == null) {
            return null;
        }
        return path;
    }

}
