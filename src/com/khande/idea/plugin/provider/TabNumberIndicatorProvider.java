package com.khande.idea.plugin.provider;

import com.intellij.openapi.fileEditor.impl.EditorTabTitleProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Khande on 17/8/19.
 */
public class TabNumberIndicatorProvider implements EditorTabTitleProvider {

    @Nullable
    @Override
    public String getEditorTabTitle(Project project, VirtualFile file) {
        return "test";
    }


}
