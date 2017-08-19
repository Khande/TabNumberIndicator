package com.khande.idea.plugin.tabnumberindicator;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.MessageBusConnection;
import com.khande.idea.plugin.tabnumberindicator.utils.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Khande on 17/8/19.
 */
public class TabNumberIndicatorAppComponent implements ApplicationComponent {

    private MessageBusConnection connection;

    public TabNumberIndicatorAppComponent() {
        Logger.init(TabNumberIndicatorAppComponent.class.getSimpleName(), Logger.WARN);
    }

    @Override
    public void initComponent() {
        Logger.debug("Init TabNumberIndicatorAppComponent");
        MessageBus messageBus = ApplicationManager.getApplication().getMessageBus();
        connection = messageBus.connect();
        connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new TabNumberIndicatorEditorListener());
    }

    @Override
    public void disposeComponent() {
        Logger.debug("Disposing component");
        connection.disconnect();
    }

    @Override
    @NotNull
    public String getComponentName() {
        return TabNumberIndicatorAppComponent.class.getCanonicalName();
    }
}
