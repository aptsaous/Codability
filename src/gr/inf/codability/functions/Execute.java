package gr.inf.codability.functions;

import com.intellij.execution.ProgramRunnerUtil;
import com.intellij.execution.RunManager;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.application.ApplicationConfigurationType;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
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

            DataContext dataContext = DataManager.getInstance().getDataContextFromFocus().getResult();
            PsiFile psiFile = DataKeys.PSI_FILE.getData( dataContext );

            if ( psiFile == null )
            {
                Notifications.showWarning( "Execute", "No active PsiFile found" );

                return;
            }

            String className = psiFile.getName().replaceFirst( "\\.java", "" );

            PsiJavaFile psiJavaFile = ( PsiJavaFile ) psiFile;
            PsiClass psiClass = psiJavaFile.getClasses()[0];

            if ( psiClass == null )
            {
                Notifications.showWarning( "Execute", "Class: " + className + " not found" );

                return;
            }

            RunManager runManager = RunManager.getInstance( project );
            ApplicationConfigurationType type = ApplicationConfigurationType.getInstance();
            RunnerAndConfigurationSettings settings = runManager.createRunConfiguration( "Demo", type.getConfigurationFactories()[0] );

            ApplicationConfiguration appConfig = ( ApplicationConfiguration ) settings.getConfiguration();

            appConfig.setMainClass( psiClass );
            appConfig.setModuleName( project.getName() );
            appConfig.setModule( ModuleManager.getInstance( project ).findModuleByName( project.getName() ) );
            appConfig.setMainClassName( psiClass.getQualifiedName() );

            ProgramRunnerUtil.executeConfiguration( project, settings, DefaultRunExecutor.getRunExecutorInstance() );
        } );

    }

    // TODO: work in progress
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
