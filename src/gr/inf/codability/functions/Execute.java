package gr.inf.codability.functions;

import com.intellij.execution.ProgramRunnerUtil;
import com.intellij.execution.RunManager;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import gr.inf.codability.core.Notifications;

public class Execute
{
    public static void executeApp()
    {
        ApplicationManager.getApplication().invokeLater( () -> {

            Project project = ProjectManager.getInstance().getOpenProjects()[ 0 ];

            if ( project == null )
            {
                Notifications.showWarning( "Execute", "No opened projects have been found" );
                return;
            }

            RunManager runManager = RunManager.getInstance( project );
            ConfigurationType[] configurationTypes = runManager.getConfigurationFactories();
            ConfigurationType configurationType = configurationTypes[ 5 ];
            RunnerAndConfigurationSettings[] settings = runManager.getConfigurationSettings( configurationType );

            ProgramRunnerUtil.executeConfiguration( project, settings[0], DefaultRunExecutor.getRunExecutorInstance() );
        } );

    }

    public static void openTerminal()
    {
        ApplicationManager.getApplication().invokeLater( () -> {

            Project project = ProjectManager.getInstance().getOpenProjects()[ 0 ];

            if ( project == null )
            {
                Notifications.showWarning( "Execute", "No opened projects have been found" );
                return;
            }

//            method[ 0 ] = GenerateMembersUtil.generateSimpleSetterPrototype( psiField, newClass );
//            newClass.add( method[ 0 ] );
//
//            CodeStyleManager.getInstance( project ).
//            new WriteCommandAction.Simple(project) {
//                @Override
//                protected void run() throws Throwable
//                {
//                    PsiField psiField2 = elementFactory.createField( "someThingElse", PsiType.INT );
//                    method[ 0 ].add( psiField2 );
//
//                }
//            }.execute();

//            ConsoleView console = TextConsoleBuilderFactory.getInstance().createBuilder( project ).getConsole();
//            console.attachToProcess( ProcessHandlerFactory.getInstance().createProcessHandler(  ) );
        } );
    }
}
