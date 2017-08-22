package com.khande.idea.plugin.tabnumberindicator;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Khande on 17/8/19.
 */
public class TabNumberIndicatorProjectComponent implements ProjectComponent {

    private MessageBusConnection connection;

    public TabNumberIndicatorProjectComponent() {
    }

    @Override
    public void initComponent() {
        MessageBus messageBus = ApplicationManager.getApplication().getMessageBus();
        connection = messageBus.connect();
        connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new TabNumberIndicatorEditorListener());
    }


    @Override
    public void disposeComponent() {
        connection.disconnect();
    }

    @Override
    @NotNull
    public String getComponentName() {
        return TabNumberIndicatorProjectComponent.class.getCanonicalName();
    }
}
