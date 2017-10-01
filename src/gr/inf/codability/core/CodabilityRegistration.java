package gr.inf.codability.core;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import gr.inf.codability.command.CommandDecoder;
import gr.inf.codability.server.MulticastServerThread;
import org.jetbrains.annotations.NotNull;

public class CodabilityRegistration implements ApplicationComponent
{
    public static CommandDecoder commandDecoder;

    @Override
    public void initComponent()
    {
        commandDecoder = new CommandDecoder();

        MulticastServerThread multicastServerThread = new MulticastServerThread();

        ApplicationManager.getApplication().executeOnPooledThread( multicastServerThread );

        Notifications.showActivationCode();
    }

    @Override
    public void disposeComponent()
    {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName()
    {
        return "Codability";
    }
}
