package gr.inf.codability.functions;

import com.intellij.ide.impl.ProjectUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ApplicationNamesInfo;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.SystemProperties;
import gr.inf.codability.core.Notifications;

import java.io.File;

public class Open
{
    public static void openClass( String className )
    {
        ApplicationManager.getApplication().invokeLater( () -> {

            Project project = ProjectManager.getInstance().getOpenProjects()[ 0 ];

            if ( project == null )
            {
                Notifications.showWarning( "Open Class", "No opened projects have been found" );
                return;
            }

            GlobalSearchScope scope = GlobalSearchScope.allScope( project );
            PsiClass psiClass = JavaPsiFacade.getInstance( project ).findClass( className, scope );

            psiClass.getContainingFile();

            if ( psiClass != null )
            {
                VirtualFile vf = psiClass.getContainingFile().getVirtualFile();

                new OpenFileDescriptor( project, vf, 1, 0 ).navigateInEditor( project, true );

                Notifications.showInfo( "Open Class", "Opened Class: " + className );

            }
            else
            {
                Notifications.showWarning( "Open Class", "Class: " + className + " not found!" );
            }

        });





    }

    public static void openProject( String projectName )
    {
        final String userHome = SystemProperties.getUserHome();
        String ideName = ApplicationNamesInfo.getInstance().getLowercaseProductName();
        String projectsPath = userHome.replace( '/', File.separatorChar ) + File.separator + ideName.replace( " ", "" ) + "Projects";

        Project project = ProjectUtil.openProject( projectsPath + File.separatorChar + projectName, null, true );

        if ( project == null )
            Notifications.showWarning( "Open Project", "Can't find project named: " + projectName );
        else
            Notifications.showInfo( "Open Project", "Opened Project: " + projectName );

    }
}
